package com.astroloop.controller;

import com.astroloop.dto.PremiumProductResponse;
import com.astroloop.dto.PurchaseRequest;
import com.astroloop.dto.PurchaseResponse;
import com.astroloop.service.PremiumService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/premium")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PremiumController {

    private final PremiumService premiumService;

    @GetMapping("/products")
    public ResponseEntity<List<PremiumProductResponse>> getProducts() {
        return ResponseEntity.ok(premiumService.getProducts());
    }

    @GetMapping("/products/{category}")
    public ResponseEntity<List<PremiumProductResponse>> getProductsByCategory(
            @PathVariable String category) {
        return ResponseEntity.ok(premiumService.getProductsByCategory(category));
    }

    @PostMapping("/purchase")
    public ResponseEntity<PurchaseResponse> purchase(
            @Valid @RequestBody PurchaseRequest request,
            HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("currentUserId");
        PurchaseResponse response = premiumService.purchaseProduct(userId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/purchases")
    public ResponseEntity<List<PurchaseResponse>> getMyPurchases(HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("currentUserId");
        return ResponseEntity.ok(premiumService.getUserPurchases(userId));
    }
}
