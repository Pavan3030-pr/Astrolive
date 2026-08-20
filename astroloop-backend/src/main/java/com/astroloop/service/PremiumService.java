package com.astroloop.service;

import com.astroloop.dto.PremiumProductResponse;
import com.astroloop.dto.PurchaseRequest;
import com.astroloop.dto.PurchaseResponse;
import com.astroloop.entity.PremiumProduct;
import com.astroloop.entity.Purchase;
import com.astroloop.entity.User;
import com.astroloop.repository.PremiumProductRepository;
import com.astroloop.repository.PurchaseRepository;
import com.astroloop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PremiumService {

    private final PremiumProductRepository productRepository;
    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;

    public List<PremiumProductResponse> getProducts() {
        return productRepository.findByActiveTrue().stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public List<PremiumProductResponse> getProductsByCategory(String category) {
        return productRepository.findByCategoryAndActiveTrue(category).stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public PurchaseResponse purchaseProduct(Long userId, PurchaseRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        PremiumProduct product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Purchase purchase = Purchase.builder()
                .user(user)
                .product(product)
                .amount(product.getPrice())
                .simulated(true)
                .status("COMPLETED")
                .build();

        purchase = purchaseRepository.save(purchase);

        // Upgrade user if membership
        if ("membership".equals(product.getCategory())) {
            user.setPremium(true);
            userRepository.save(user);
        }

        return PurchaseResponse.builder()
                .id(purchase.getId())
                .productName(product.getName())
                .amount(purchase.getAmount())
                .status(purchase.getStatus())
                .simulated(purchase.isSimulated())
                .createdAt(purchase.getCreatedAt())
                .build();
    }

    public List<PurchaseResponse> getUserPurchases(Long userId) {
        return purchaseRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(p -> PurchaseResponse.builder()
                        .id(p.getId())
                        .productName(p.getProduct().getName())
                        .amount(p.getAmount())
                        .status(p.getStatus())
                        .simulated(p.isSimulated())
                        .createdAt(p.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    private PremiumProductResponse mapToProductResponse(PremiumProduct product) {
        String features = switch (product.getCategory()) {
            case "report" -> "Personalized cosmic insights, transit analysis, 20-page PDF";
            case "membership" -> "Unlimited cosmic cards, priority booking, exclusive content";
            case "credits" -> "20% savings, any session type, valid for 90 days";
            case "challenge" -> "Higher rewards, exclusive badges, cosmic leaderboards";
            default -> "Premium features included";
        };

        return PremiumProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .tier(product.getTier())
                .features(features)
                .build();
    }
}
