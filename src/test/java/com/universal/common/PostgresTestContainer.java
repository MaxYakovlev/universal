package com.universal.common;

import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostgresTestContainer {
    public static PostgreSQLContainer container;

    static {
        container = (PostgreSQLContainer) new PostgreSQLContainer()
                .withUsername("test")
                .withPassword("test")
                .withDatabaseName("test")
                .withReuse(true);

        container.start();
    }
}
