package br.com.bee4.infra.streaming.deserializer;

import br.com.bee4.domain.intermediary.model.Intermediary;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class IntermediaryDeserializer extends ObjectMapperDeserializer<Intermediary> {

    public IntermediaryDeserializer() {
        super(Intermediary.class);
    }
}
