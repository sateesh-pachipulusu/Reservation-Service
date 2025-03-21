package com.reservation.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reservation.service.feign.CustomerServiceClient;
import com.reservation.service.model.Customer;
import com.reservation.service.model.Reservation;
import com.reservation.service.repository.ReservationRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class ReservationService {

    @Autowired
    private CustomerServiceClient customerServiceClient;

    @Autowired
    private NotificationProducer notificationProducer;
    
    @Autowired
    ReservationRepository repository;
    
    @CircuitBreaker(name = "customerService", fallbackMethod = "getCustomerFallback")
    public Reservation makeReservation(Reservation reservation) {
        Customer customer = customerServiceClient.getCustomerById(reservation.getCustomerId());
        if (customer != null) {
        	
        	repository.save(reservation);
        	
            // Logic for making reservation
            notificationProducer.
            sendReservationMessge("Reservation made for customer: " + customer.getName()+" on Date time : ");
            return reservation;
        } else {
            return null;
        }
    }
    
    public Reservation getCustomerFallback(Reservation reservation, Throwable t) {
        System.err.println("Fallback method called for customer service: " + t.getMessage());
        return null; 
    }
}