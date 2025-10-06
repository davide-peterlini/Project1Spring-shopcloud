package it.smartcommunitylabdhub.shopgateway;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/catalog")
    public ResponseEntity<String> catalogFallback() {
        return ResponseEntity
                .status(503)
                .body("⚠️ Il servizio Catalog non è al momento disponibile. Riprova più tardi.");
    }

    @GetMapping("/purchase")
    public ResponseEntity<String> purchaseFallback() {
        return ResponseEntity
                .status(503)
                .body("⚠️ Il servizio Purchase non è al momento disponibile. Riprova più tardi.");
    }

    @GetMapping("/cart")
    public ResponseEntity<String> cartFallback() {
        return ResponseEntity
                .status(503)
                .body("⚠️ Il servizio Cart non è al momento disponibile. Riprova più tardi.");
    }

}
