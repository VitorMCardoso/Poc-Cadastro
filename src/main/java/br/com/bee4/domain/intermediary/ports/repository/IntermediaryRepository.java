package br.com.bee4.domain.intermediary.ports.repository;


import br.com.bee4.domain.intermediary.model.Intermediary;

import java.util.Collection;
import java.util.Optional;

public interface IntermediaryRepository {
    Optional<Intermediary> findByIdOptional(Long id);
    Optional<Intermediary> findByEmailOptional(String email);
    Intermediary save(Intermediary intermediary);
    void update(Intermediary intermediary);
    Collection<Intermediary> findAll();
    Collection<String> getAllEmails();
}
