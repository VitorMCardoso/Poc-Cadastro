package br.com.bee4.domain.intermediary.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;



@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Intermediary {

    private Long id;
    private String name;
    private String email;
}
