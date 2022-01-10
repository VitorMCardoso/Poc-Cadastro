package br.com.bee4.application.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateInvestorRequest {
    private String password;
    private String email;
}
