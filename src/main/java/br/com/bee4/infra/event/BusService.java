package br.com.bee4.infra.event;

import br.com.bee4.domain.intermediary.model.Intermediary;
import br.com.bee4.infra.streaming.producer.IntermediaryProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.vertx.ConsumeEvent;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class BusService {

    private final IntermediaryProducer intermediaryProducer;

    public BusService(IntermediaryProducer intermediaryProducer) {
        this.intermediaryProducer = intermediaryProducer;
    }

    @ConsumeEvent("intermediary")
    public void intermediary(Intermediary intermediary) throws JsonProcessingException {
        intermediaryProducer.sendInvestorIntermediary(intermediary);
    }
}
