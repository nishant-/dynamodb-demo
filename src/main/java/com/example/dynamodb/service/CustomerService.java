package com.example.dynamodb.service;

import com.example.dynamodb.model.Customer;
import com.example.dynamodb.model.CustomerDto;
import com.example.dynamodb.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.Key;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public void createCustomer(CustomerDto customerDto) {

        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto, customer);
        customerRepository.create(customer);
        System.out.println("Customer Created");

    }

    public CustomerDto findCustomer(Key key) {

        Customer customer =  customerRepository.findByKey(key);
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer, customerDto);
        return customerDto;

    }


}
