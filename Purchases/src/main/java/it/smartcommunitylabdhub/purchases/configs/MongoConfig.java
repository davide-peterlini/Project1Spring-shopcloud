package it.smartcommunitylabdhub.purchases.configs;

import com.mongodb.ConnectionString;
import io.micrometer.tracing.Tracer;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Bean
    public MongoClient mongoClient(Tracer tracer) {
        ConnectionString connectionString = new ConnectionString(mongoUri);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .addCommandListener(new MongoTracingCommandListener(tracer))
                .build();
        return MongoClients.create(settings);
    }
}

