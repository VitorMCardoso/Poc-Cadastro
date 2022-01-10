package br.com.bee4.domain.investor.ports.repository;

import br.com.bee4.domain.investor.model.Investor;

import java.util.Optional;

public interface InvestorRepository {
    Optional<Investor> findByIdOptional(Long id);
    Investor save(Investor investor);
    void update(Investor investor);
    Optional<Investor> findByCpfOptional(String cpf);
}
