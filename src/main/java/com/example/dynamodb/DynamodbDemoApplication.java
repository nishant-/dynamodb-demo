package com.example.dynamodb;

import com.example.dynamodb.model.Customer;
import com.example.dynamodb.model.CustomerDto;
import com.example.dynamodb.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@SpringBootApplication
public class DynamodbDemoApplication implements CommandLineRunner {

    @Autowired
    CustomerService customerService;

    public static void main(String[] args) {
        SpringApplication.run(DynamodbDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        LocalDate localDate = LocalDate.parse("2020-04-07");
        LocalDateTime localDateTime = localDate.atStartOfDay();
        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
        CustomerDto customerDto = CustomerDto.builder().name("Susan Blue")
                .id("id103")
                .email("sblue@noserver.com")
                .regDate(instant)
                .build();

        customerService.createCustomer(customerDto);
        Key customerKey = Key.builder().partitionValue("id103").sortValue("Susan Blue").build();
        CustomerDto customer = customerService.findCustomer(customerKey);
        System.out.println(customer);
    }
}
