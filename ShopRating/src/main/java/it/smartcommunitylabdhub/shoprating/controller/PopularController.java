package it.smartcommunitylabdhub.shoprating.controller;

import it.smartcommunitylabdhub.shoprating.models.dto.PopularProductDTO;
import it.smartcommunitylabdhub.shoprating.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PopularController {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private RestTemplate restTemplate;

    // GET /api/popular?page=0&size=10
    @GetMapping("/popular")
    public ResponseEntity<Page<PopularProductDTO>> getPopularProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        // 1) Get list of products from Catalog
        String catalogUrl = "http://catalog/api/products"; // uses service discovery (gateway/eureka)
        List<Map<String, Object>> products = Collections.emptyList();
        try {
            // Expecting ProductDTO[] from Catalog
            Object[] resp = restTemplate.getForObject(catalogUrl, Object[].class);
            if (resp != null) {
                products = Arrays.stream(resp)
                        .map(o -> (Map<String, Object>) o)
                        .collect(Collectors.toList());
            }
        } catch (RestClientException e) {
            // If catalog not reachable, return empty page
            return ResponseEntity.ok(Page.empty());
        }

        // 2) Get average ratings from DB
        List<Map<String, Object>> averages = ratingService.getPopularProducts();
        // Build map productId(Long) -> avg(Double)
        Map<Long, Double> avgMap = new HashMap<>();
        for (Map<String, Object> m : averages) {
            Object pid = m.get("productId");
            Object avg = m.get("averageRating");
            if (pid != null && avg != null) {
                try {
                    Long productId = pid instanceof Number ? ((Number) pid).longValue() : Long.parseLong(pid.toString());
                    Double average = avg instanceof Number ? ((Number) avg).doubleValue() : Double.parseDouble(avg.toString());
                    avgMap.put(productId, average);
                } catch (NumberFormatException ignored) {
                }
            }
        }

        // 3) Merge: for each product from catalog include id, title, price, average (0 if missing)
        List<PopularProductDTO> merged = products.stream().map(p -> {
            String id = Objects.toString(p.get("id"), null);
            String title = Objects.toString(p.get("title"), null);
            Double price = null;
            Object priceObj = p.get("price");
            if (priceObj instanceof Number) price = ((Number) priceObj).doubleValue();
            else if (priceObj != null) {
                try { price = Double.parseDouble(priceObj.toString()); } catch (NumberFormatException ignored) {}
            }

            Double avg = 0.0;
            if (id != null) {
                try {
                    Long pid = Long.parseLong(id);
                    if (avgMap.containsKey(pid)) avg = avgMap.get(pid);
                } catch (NumberFormatException ignored) {
                    // if id is not numeric, leave avg as 0
                }
            }

            return new PopularProductDTO(id, title, price, avg);
        }).collect(Collectors.toList());

    // 4) Sort by averageRating desc (nulls/absent treated as 0)
    merged.sort((a, b) -> Double.compare(Optional.ofNullable(b.getAverageRating()).orElse(0.0), Optional.ofNullable(a.getAverageRating()).orElse(0.0)));

        // 5) Pagination
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, merged.size());
        List<PopularProductDTO> pageContent = Collections.emptyList();
        if (fromIndex < merged.size()) pageContent = merged.subList(fromIndex, toIndex);

        Pageable pageable = PageRequest.of(page, size);
        Page<PopularProductDTO> resultPage = new PageImpl<>(pageContent, pageable, merged.size());

        return ResponseEntity.ok(resultPage);
    }
}
