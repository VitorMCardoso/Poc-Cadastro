package br.com.bee4.domain.investor.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Investor {

    private Long id;
    private String cpf;
    private String name;
    private String password;
    private String email;
}
