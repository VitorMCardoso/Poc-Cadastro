package br.com.bee4.application.resource;

import br.com.bee4.application.request.CreateIntermediaryRequest;
import br.com.bee4.domain.intermediary.model.Intermediary;
import br.com.bee4.application.service.IntermediaryService;
import br.com.bee4.infra.utils.Constants;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path(Constants.PATH_INTERMEDIARY)
@ApplicationScoped
@Tag(name = Constants.TAG_INTERMEDIARY, description = Constants.DESCRIPTION_INTERMEDIARY)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class IntermediaryResource {

    private final IntermediaryService intermediaryService;

    @Inject
    public IntermediaryResource(IntermediaryService intermediaryService) {
        this.intermediaryService = intermediaryService;
    }

    @POST
    public Response createIntermediary(@Valid CreateIntermediaryRequest createIntermediaryRequest) {
        Intermediary intermediary = this.intermediaryService.createIntermediary(createIntermediaryRequest);
        return Response.ok(intermediary).status(201).build();
    }

    @PATCH
    @Path(Constants.PATH_ID_INTERMEDIARY)
    public Response updateIntermediary(@PathParam("idIntermediary") Long idIntermediary, @Valid CreateIntermediaryRequest intermediaryRequest) {
        Intermediary intermediary = this.intermediaryService.updateIntermediary(Intermediary.builder()
                .id(idIntermediary)
                .name(intermediaryRequest.getName())
                .email(intermediaryRequest.getEmail())
                .build());
        return Response.ok(intermediary).status(200).build();
    }

    @GET
    @Path(Constants.PATH_ID_INTERMEDIARY)
    public Response getIntermediary(@PathParam("idIntermediary") Long idIntermediary) {
        Intermediary intermediary = this.intermediaryService.getIntermediary(idIntermediary);
        if (intermediary != null) {
            return Response.ok(intermediary).status(200).build();
        }
        return Response.ok().status(404).build();
    }

    @GET
    @Path(Constants.PATH_ALL_EMAIL_INTERMEDIARY)
    public Response getAllEmailsIntermediaries() {
        Collection<String> allEmailsIntermediaries = this.intermediaryService.getAllEmailsIntermediaries();
        if (!allEmailsIntermediaries.isEmpty()) {
            return Response.ok(allEmailsIntermediaries).status(200).build();
        }
        return Response.noContent().build();
    }

}
