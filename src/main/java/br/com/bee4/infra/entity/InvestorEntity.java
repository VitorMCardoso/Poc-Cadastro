package br.com.bee4.infra.entity;


import br.com.bee4.domain.investor.model.Investor;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Cacheable
@Table(name = "investor")
@RegisterForReflection
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvestorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private String cpf;
    private String name;
    private String password;
    private String email;

    public Investor toInvestor() {
        return Investor.builder()
                .cpf(this.cpf)
                .email(this.email)
                .id(this.id)
                .name(this.name)
                .build();
    }
}
