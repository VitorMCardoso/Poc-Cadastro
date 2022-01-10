package br.com.bee4.application.service.impl;

import br.com.bee4.application.service.InvestorService;
import br.com.bee4.domain.investor.model.Investor;
import br.com.bee4.domain.investor.ports.repository.InvestorRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class InvestorServiceImpl implements InvestorService {

    private final InvestorRepository investorRepository;

    @Inject
    public InvestorServiceImpl(InvestorRepository investorRepository) {
        this.investorRepository = investorRepository;
    }

    @Override
    public Investor createInvestor(Investor investor) {
        return this.investorRepository.save(Investor.builder()
                .cpf(investor.getCpf())
                .name(investor.getName())
                .build());
    }

    @Override
    public Investor completeRegister(String password, String email, String cpf) {
        this.investorRepository.update(Investor.builder()
                .email(email)
                .password(password)
                .cpf(cpf)
                .build());
        return getInvestorByCpf(cpf);
    }

    @Override
    public Investor getInvestorByCpf(String cpf) {
        return this.investorRepository.findByCpfOptional(cpf).orElse(null);
    }

}
