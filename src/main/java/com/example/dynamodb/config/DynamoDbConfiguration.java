package com.example.dynamodb.config;

import com.example.dynamodb.model.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.ResourceInUseException;

import java.net.URI;

@Configuration
public class DynamoDbConfiguration {

    @Value("${app.table.name}")
    private String tableName;

    @Value("${access.key}")
    private String accessKey;

    @Value("${secret.key}")
    private String secretKey;

    @Value("${endpoint.url}")
    private String endpointUrl;

    @Bean("dynamoDbClient")
    public DynamoDbClient dynamoDbClient() {

        return DynamoDbClient.builder().region(Region.AP_SOUTHEAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .endpointOverride(URI.create(endpointUrl)).build();

    }

    @Bean("dynamoDbEnhancedClient")
    @DependsOn("dynamoDbClient")
    public DynamoDbEnhancedClient dynamoDbEnhancedClient() {

        return DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient()).build();
    }

    @Bean("customerTable")
    @DependsOn("dynamoDbEnhancedClient")
    public DynamoDbTable<Customer> customerTable() {
        DynamoDbTable<Customer> custTable = dynamoDbEnhancedClient().table(tableName,
                TableSchema.fromBean(Customer.class));
        try {
            custTable.createTable();
        } catch (ResourceInUseException e) {
            if (e.getMessage().startsWith("Table already created")) {
                System.out.println("Table = " + tableName + " already created");
            }
        }
        return custTable;
    }
}
