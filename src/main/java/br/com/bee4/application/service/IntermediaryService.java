package br.com.bee4.application.service;

import br.com.bee4.application.request.CreateIntermediaryRequest;
import br.com.bee4.application.response.InvestorRegisterResponse;
import br.com.bee4.domain.intermediary.model.Intermediary;
import br.com.bee4.domain.investor.model.Investor;

import java.util.Collection;
import java.util.Optional;

public interface IntermediaryService {
    Intermediary createIntermediary(CreateIntermediaryRequest createIntermediaryRequest);
    Intermediary updateIntermediary(Intermediary intermediary);
    Intermediary getIntermediary(Long id);
    Collection<Intermediary> getAllIntermediaries();
    Collection<String> getAllEmailsIntermediaries();
    Optional<Intermediary> findByEmailOptional(String email);
    void updateInvestor(Intermediary intermediary, Investor investor);
    InvestorRegisterResponse getAllInvestorEmailsIntermediaries(String cpf);
}
