package br.com.bee4.infra.adapters;

import br.com.bee4.domain.investor.model.Investor;
import br.com.bee4.domain.investor.ports.repository.InvestorRepository;
import br.com.bee4.infra.repository.PanacheInvestorRepository;
import br.com.bee4.infra.entity.InvestorEntity;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;

@ApplicationScoped
public class PostgresInvestorRepository implements InvestorRepository {

    private final PanacheInvestorRepository repository;

    @Inject
    public PostgresInvestorRepository(final PanacheInvestorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Investor> findByIdOptional(Long id) {
        Optional<InvestorEntity> optionalInvestorEntity = this.repository.findByIdOptional(id);
        return optionalInvestorEntity.map(InvestorEntity::toInvestor);
    }

    @Override
    @Transactional
    public Investor save(Investor investor) {
        InvestorEntity build = InvestorEntity.builder()
                .cpf(investor.getCpf())
                .name(investor.getName())
                .build();
        this.repository.persistAndFlush(build);
        return findByIdOptional(build.getId()).get();
    }

    @Override
    @Transactional
    public void update(Investor investor) {
        Optional<Investor> optionalInvestor = findByCpfOptional(investor.getCpf());
        if (optionalInvestor.isPresent()) {
            Long actualId = optionalInvestor.get().getId();
            this.repository.update("name = ?1 , email = ?2, password = ?3 where id = ?4",
                    optionalInvestor.get().getName(),
                    investor.getEmail() == null ? optionalInvestor.get().getEmail() : investor.getEmail(),
                    investor.getPassword() == null ? optionalInvestor.get().getPassword() : investor.getPassword(),
                    actualId);
        }

    }

    @Override
    public Optional<Investor> findByCpfOptional(String cpf) {
        PanacheQuery<InvestorEntity> investorEntityPanacheQuery = this.repository.find("cpf", cpf);
        Optional<InvestorEntity> first = investorEntityPanacheQuery.stream().findFirst();
        if (first.isPresent()) {
            InvestorEntity investorEntity = first.get();
            return Optional.of(investorEntity.toInvestor());
        }
        return Optional.empty();
    }
}
