package br.com.bee4.infra.streaming.producer;

import br.com.bee4.domain.intermediary.model.Intermediary;
import br.com.bee4.domain.investor.model.Investor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class IntermediaryProducer {

    @Inject
    @Channel("intermediary-out")
    Emitter<Record<Long, String>> emitter;

    ObjectMapper objectMapper = new ObjectMapper();

    public void sendInvestorIntermediary(Intermediary intermediary) throws JsonProcessingException {
        emitter.send(Record.of(intermediary.getId(), objectMapper.writeValueAsString(intermediary).trim()));
    }

}
