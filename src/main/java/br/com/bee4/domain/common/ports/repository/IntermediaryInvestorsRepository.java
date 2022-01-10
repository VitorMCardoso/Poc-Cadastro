package br.com.bee4.domain.common.ports.repository;

import br.com.bee4.domain.intermediary.model.Intermediary;
import br.com.bee4.domain.investor.model.Investor;

import java.util.List;

public interface IntermediaryInvestorsRepository {
    void updateIdInvestor(Intermediary intermediary, Investor investor);
    List<Investor> getAllInvestorsEmailsIntermediaries(String cpf);
}
