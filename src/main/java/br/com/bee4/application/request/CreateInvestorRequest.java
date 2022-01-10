package br.com.bee4.application.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateInvestorRequest {
    private String cpf;
    private String name;
}
