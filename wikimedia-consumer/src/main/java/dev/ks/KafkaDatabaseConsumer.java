package dev.ks;

import dev.ks.entity.WikimediaData;
import dev.ks.repository.WikimediaDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaDatabaseConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDatabaseConsumer.class);
    private final WikimediaDataRepository dataRepository;

    public KafkaDatabaseConsumer(WikimediaDataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @KafkaListener(topics = "wikimedia_recent_change", groupId = "myGroup")
    public void consume(String message) {
        LOGGER.info(String.format("Event message received -> %s", message));
        WikimediaData wikimediaData = new WikimediaData();
        wikimediaData.setWikiEventData(message);
        dataRepository.save(wikimediaData);
    }
}
