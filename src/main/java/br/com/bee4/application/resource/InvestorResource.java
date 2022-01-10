package br.com.bee4.application.resource;

import br.com.bee4.application.request.CreateInvestorRequest;
import br.com.bee4.application.request.UpdateInvestorRequest;
import br.com.bee4.application.response.InvestorRegisterResponse;
import br.com.bee4.domain.intermediary.model.Intermediary;
import br.com.bee4.application.service.IntermediaryService;
import br.com.bee4.domain.investor.model.Investor;
import br.com.bee4.application.service.InvestorService;
import br.com.bee4.infra.streaming.producer.IntermediaryProducer;
import br.com.bee4.infra.utils.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path(Constants.PATH_INVESTOR)
@ApplicationScoped
@Tag(name = Constants.TAG_INVESTOR, description = Constants.DESCRIPTION_INVESTOR)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InvestorResource {

    private final InvestorService investorService;
    private final IntermediaryService intermediaryService;
    private final IntermediaryProducer intermediaryProducer;

    @Inject
    public InvestorResource(InvestorService investorService, IntermediaryService intermediaryService, IntermediaryProducer intermediaryProducer) {
        this.investorService = investorService;
        this.intermediaryService = intermediaryService;
        this.intermediaryProducer = intermediaryProducer;
    }

    @POST
    public Response createInvestor(@Valid CreateInvestorRequest createInvestorRequest) {
        Investor investor = this.investorService.createInvestor(Investor.builder()
                .cpf(createInvestorRequest.getCpf())
                .name(createInvestorRequest.getName())
                .build());
        return Response.ok(investor).status(201).build();
    }

    @PATCH
    @Path(Constants.PATH_CPF_INVESTOR)
    public Response completeRegister(@PathParam("cpf") String cpf, @Valid UpdateInvestorRequest updateInvestorRequest) throws JsonProcessingException {
        Optional<Intermediary> intermediaryOptional = this.intermediaryService.findByEmailOptional(updateInvestorRequest.getEmail());
        if (intermediaryOptional.isPresent()) {
            this.intermediaryService.updateInvestor(intermediaryOptional.get(), this.investorService.getInvestorByCpf(cpf));
        } else if (updateInvestorRequest.getEmail() != null) {
            return Response.ok().status(404).build();
        }
        Investor investor = this.investorService.completeRegister(updateInvestorRequest.getPassword(), updateInvestorRequest.getEmail(), cpf);
        intermediaryProducer.sendInvestorIntermediary(intermediaryOptional.get());
        return Response.ok(investor).status(200).build();
    }

    @GET
    @Path(Constants.PATH_CPF_INVESTOR)
    public Response getInvestorByCpf(@PathParam("cpf") String cpf) {
        InvestorRegisterResponse investorRegisterResponse = intermediaryService.getAllInvestorEmailsIntermediaries(cpf);
        if (investorRegisterResponse != null) {
            return Response.ok(investorRegisterResponse).status(201).build();
        }
        return Response.noContent().build();
    }
}
