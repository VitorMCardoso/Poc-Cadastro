package br.com.bee4.application.service;

import br.com.bee4.domain.investor.model.Investor;


public interface InvestorService {

    Investor createInvestor(Investor investor);
    Investor completeRegister(String password, String email, String cpf);
    Investor getInvestorByCpf(String cpf);
}
