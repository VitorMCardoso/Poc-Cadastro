package br.com.bee4.infra.streaming.consumer;

import br.com.bee4.domain.intermediary.model.Intermediary;
import br.com.bee4.domain.investor.model.Investor;
import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class IntermediaryConsumer {

    private final Logger logger = Logger.getLogger(IntermediaryConsumer.class);

    @Incoming("intermediary-in")
    public void receive(Record<Investor, String> record) {
        logger.infof("HEREEE =>>>>>  %d - %s", record.key(), record.value());
    }


}
