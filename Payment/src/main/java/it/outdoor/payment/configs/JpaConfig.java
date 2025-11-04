package it.outdoor.payment.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "it.outdoor.payment.repositories")
@EnableTransactionManagement
public class JpaConfig {
}