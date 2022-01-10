package br.com.bee4.application.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateIntermediaryRequest {
    private String name;
    private String email;
}
