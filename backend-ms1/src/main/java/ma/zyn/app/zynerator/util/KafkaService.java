package ma.zyn.app.zynerator.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }

    @KafkaListener(topics = "example-topic", groupId = "consumer-group-1")
    public void consume(String message) {
        System.out.println("Message reçu : " + message);
    }
}
