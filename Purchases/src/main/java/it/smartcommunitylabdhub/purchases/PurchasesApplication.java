package it.smartcommunitylabdhub.purchases;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PurchasesApplication {

    public static void main(String[] args) {
        SpringApplication.run(PurchasesApplication.class, args);
    }

}
