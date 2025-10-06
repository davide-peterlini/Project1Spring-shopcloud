package it.smartcommunitylabdhub.resttemplate;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@RestController
public class TestController {


    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/test")
    public List<Product> test() {
        return restTemplate.getForObject("http://catalog/api/products",
                        List.class);
    }

}
