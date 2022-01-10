package br.com.bee4.application.response;

import br.com.bee4.domain.investor.model.Investor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InvestorRegisterResponse {

    private Investor investor;
    private List<String> registeredEmails ;
}
