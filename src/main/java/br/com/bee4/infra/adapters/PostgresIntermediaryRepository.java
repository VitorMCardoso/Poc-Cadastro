package br.com.bee4.infra.adapters;

import br.com.bee4.domain.common.ports.repository.IntermediaryInvestorsRepository;
import br.com.bee4.domain.intermediary.model.Intermediary;
import br.com.bee4.domain.intermediary.ports.repository.IntermediaryRepository;
import br.com.bee4.domain.investor.model.Investor;
import br.com.bee4.infra.entity.IntermediaryEntity;
import br.com.bee4.infra.entity.InvestorEntity;
import br.com.bee4.infra.repository.PanacheIntermediaryRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class PostgresIntermediaryRepository implements IntermediaryRepository, IntermediaryInvestorsRepository {

    private final PanacheIntermediaryRepository repository;

    @Inject
    public PostgresIntermediaryRepository(PanacheIntermediaryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Intermediary> findByIdOptional(Long id) {
        Optional<IntermediaryEntity> optionalIntermediaryEntity = this.repository.findByIdOptional(id);
        return optionalIntermediaryEntity.map(IntermediaryEntity::toIntermediary);
    }

    @Override
    public Optional<Intermediary> findByEmailOptional(String email) {
        PanacheQuery<IntermediaryEntity> intermediaryEntityPanacheQuery = this.repository.find("email", email);
        return intermediaryEntityPanacheQuery.stream().map(IntermediaryEntity::toIntermediary).findFirst();
    }

    @Override
    @Transactional
    public Intermediary save(Intermediary intermediary) {
        IntermediaryEntity build = IntermediaryEntity.builder()
                .email(intermediary.getEmail())
                .name(intermediary.getName())
                .build();
        this.repository.persistAndFlush(build);
        return findByIdOptional(build.getId()).get();
    }

    @Override
    @Transactional
    public void update(Intermediary intermediary) {
        Optional<Intermediary> optionalIntermediary = findByIdOptional(intermediary.getId());
        if (optionalIntermediary.isPresent()) {
            Long actualId = optionalIntermediary.get().getId();
            this.repository.update("name = ?1 , email = ?2 where id = ?3",
                    intermediary.getName(),
                    intermediary.getEmail(),
                    actualId);
        }
    }

    @Override
    @Transactional
    public void updateIdInvestor(Intermediary intermediary, Investor investor) {
        Optional<IntermediaryEntity> optionalIntermediaryEntity = this.repository.findByIdOptional(intermediary.getId());
        if (optionalIntermediaryEntity.isPresent() &&
            optionalIntermediaryEntity.get().getIntermediaryInvestors().stream().noneMatch(investorEntity -> investorEntity.getCpf().equals(investor.getCpf()))) {
            optionalIntermediaryEntity.get().setIntermediaryInvestors(Collections.singletonList(InvestorEntity.builder()
                    .id(investor.getId())
                    .build()));
            this.repository.persistAndFlush(optionalIntermediaryEntity.get());
        }
    }

    @Override
    public Collection<Intermediary> findAll() {
        List<IntermediaryEntity> intermediaryEntities = this.repository.listAll();
        return intermediaryEntities.stream().map(IntermediaryEntity::toIntermediary).collect(Collectors.toList());
    }

    @Override
    public Collection<String> getAllEmails() {
        return findAll().stream().map(Intermediary::getEmail).collect(Collectors.toList());
    }

    @Override
    public List<Investor> getAllInvestorsEmailsIntermediaries(String cpf) {
        List<Investor> investorList = new ArrayList<>();
        List<IntermediaryEntity> intermediaryEntities = this.repository.listAll();
        intermediaryEntities.forEach(intermediaryEntity -> {
            String intermediaryEntityEmail = intermediaryEntity.getEmail();
            intermediaryEntity.getIntermediaryInvestors().forEach(investorEntity -> investorList.add(Investor.builder()
                            .cpf(investorEntity.getCpf())
                            .email(intermediaryEntityEmail)
                            .name(investorEntity.getName())
                    .build()));
        });
        return investorList.stream().filter(investor -> investor.getCpf().equals(cpf)).collect(Collectors.toList());
    }

}
