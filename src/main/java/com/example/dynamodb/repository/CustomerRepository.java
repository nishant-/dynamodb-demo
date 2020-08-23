package com.example.dynamodb.repository;

import com.example.dynamodb.model.Customer;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

@Repository
public class CustomerRepository {

	private final DynamoDbTable<Customer> customerTable;

	public CustomerRepository(DynamoDbTable<Customer> customerTable) {
		this.customerTable = customerTable;
	}

	public void create(Customer customer) {
		customerTable.putItem(customer);
	}

	public Customer delete(Customer customer) {
		return customerTable.deleteItem(customer);
	}

	public Customer findByKey(Key key) {

		return customerTable.getItem(key);

	}
}
