package it.smartcommunitylabdhub.configurationclient.controllers;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class TestController {

    @Value("${user.role}")
    private String role;

    @Value("${user.fullname}")
    private String fullname;

    @Value("${user.password}")
    private String password;

    @GetMapping("/role")
    public String getRole() {

        return String.format("Il mio ruolo e' %s ed il nome e' %s e la mia password e' %s", role, fullname, password);
    }

}
