package br.com.bee4.infra.entity;

import br.com.bee4.domain.intermediary.model.Intermediary;
import br.com.bee4.domain.investor.model.Investor;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Cacheable
@Table(name = "intermediary")
@RegisterForReflection
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IntermediaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private String name;
    private String email;
    @ManyToMany(targetEntity = InvestorEntity.class, cascade = CascadeType.MERGE)
    @JoinTable(name = "intermediary_investors")
    private List<InvestorEntity> intermediaryInvestors;

    public Intermediary toIntermediary() {
        return Intermediary.builder()
                .id(this.id)
                .email(this.email)
                .name(this.name)
                .name(this.name)
                .build();
    }
}
