package br.com.bee4.infra.repository;

import br.com.bee4.infra.entity.InvestorEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PanacheInvestorRepository implements PanacheRepository<InvestorEntity> {
}
