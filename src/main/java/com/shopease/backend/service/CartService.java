package com.shopease.backend.service;

import java.util.ArrayList;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.shopease.backend.entity.Cart;
import com.shopease.backend.entity.CartItem;
import com.shopease.backend.entity.Product;
import com.shopease.backend.entity.User;
import com.shopease.backend.repository.CartItemRepository;
import com.shopease.backend.repository.CartRepository;
import com.shopease.backend.repository.ProductRepository;
import com.shopease.backend.repository.UserRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            ProductRepository productRepository,
            UserRepository userRepository
    ) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    private User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userRepository.findByEmail(email);
    }

    private double calculateTotal(Cart cart) {
        return cart.getCartItems()
                .stream()
                .mapToDouble(CartItem::getPrice)
                .sum();
    }

    // Get cart of logged-in user
    public Cart getCart() 
    {
        User user = getLoggedInUser();

        Cart cart = cartRepository.findByUser(user);

        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setCartItems(new ArrayList<>());
            cart.setTotalPrice(0);
            cart = cartRepository.save(cart);
        }

        return cart;
    }

    // Add or merge product
    public Cart addProductToCart(Long productId, int quantity) {
        Cart cart = getCart();
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null)
            return null;

        CartItem existing = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
            existing.setPrice(existing.getQuantity() * product.getPrice());
            cartItemRepository.save(existing);
        } else {
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(quantity);
            item.setPrice(quantity * product.getPrice());
            cartItemRepository.save(item);

            cart.getCartItems().add(item);
        }

        cart.setTotalPrice(calculateTotal(cart));
        return cartRepository.save(cart);
    }

    public Cart removeItemFromCart(Long cartItemId) {
        Cart cart = getCart();
        CartItem item = cartItemRepository.findById(cartItemId).orElse(null);

        if (item == null)
            return cart;

        cart.getCartItems().remove(item);
        cartItemRepository.delete(item);

        cart.setTotalPrice(calculateTotal(cart));
        return cartRepository.save(cart);
    }
}
