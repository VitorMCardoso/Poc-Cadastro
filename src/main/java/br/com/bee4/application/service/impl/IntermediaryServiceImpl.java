package br.com.bee4.application.service.impl;

import br.com.bee4.application.request.CreateIntermediaryRequest;
import br.com.bee4.application.response.InvestorRegisterResponse;
import br.com.bee4.application.service.IntermediaryService;
import br.com.bee4.domain.common.ports.repository.IntermediaryInvestorsRepository;
import br.com.bee4.domain.intermediary.model.Intermediary;
import br.com.bee4.domain.intermediary.ports.repository.IntermediaryRepository;
import br.com.bee4.domain.investor.model.Investor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class IntermediaryServiceImpl implements IntermediaryService {

    private final IntermediaryRepository intermediaryRepository;
    private final IntermediaryInvestorsRepository investorIntermediariesRepository;

    @Inject
    public IntermediaryServiceImpl(IntermediaryRepository intermediaryRepository, IntermediaryInvestorsRepository investorIntermediariesRepository) {
        this.intermediaryRepository = intermediaryRepository;
        this.investorIntermediariesRepository = investorIntermediariesRepository;
    }

    @Override
    public Intermediary createIntermediary(CreateIntermediaryRequest createIntermediaryRequest) {
        return this.intermediaryRepository.save(Intermediary.builder()
                .name(createIntermediaryRequest.getName())
                .email(createIntermediaryRequest.getEmail())
                .build());
    }

    @Override
    public Intermediary updateIntermediary(Intermediary intermediary) {
        this.intermediaryRepository.update(intermediary);
        return this.intermediaryRepository.findByIdOptional(intermediary.getId()).orElse(null);
    }

    @Override
    public Intermediary getIntermediary(Long id) {
        return this.intermediaryRepository.findByIdOptional(id).orElse(null);
    }

    @Override
    public Collection<Intermediary> getAllIntermediaries() {
        return this.intermediaryRepository.findAll();
    }

    @Override
    public Collection<String> getAllEmailsIntermediaries() {
        return this.intermediaryRepository.getAllEmails();
    }

    @Override
    public Optional<Intermediary> findByEmailOptional(String email) {
        return this.intermediaryRepository.findByEmailOptional(email);
    }

    @Override
    public void updateInvestor(Intermediary intermediary, Investor investor) {
        this.investorIntermediariesRepository.updateIdInvestor(intermediary, investor);
    }

    @Override
    public InvestorRegisterResponse getAllInvestorEmailsIntermediaries(String cpf) {
        List<Investor> investorList = this.investorIntermediariesRepository.getAllInvestorsEmailsIntermediaries(cpf);
        return InvestorRegisterResponse.builder()
                .investor(investorList.get(0))
                .registeredEmails(investorList.stream().map(Investor::getEmail).collect(Collectors.toList()))
                .build();
    }
}
