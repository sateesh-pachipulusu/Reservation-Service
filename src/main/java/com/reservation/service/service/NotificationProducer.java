package com.reservation.service.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {
	
    private static final Logger logger = LoggerFactory.getLogger(NotificationProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

	/*
	 * @Value("${reservation.kafka.topic}") private String topicName;
	 */

    public void sendReservationMessge(String message) {
    	logger.info("reservation created and pushed message"+message);
        kafkaTemplate.send("reservation-topic", message);
    }
}