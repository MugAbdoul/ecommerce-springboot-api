package com.abdoul.backend.controller.user;

import com.abdoul.backend.entities.Cart;
import com.abdoul.backend.entities.Product;
import com.abdoul.backend.entities.User;
import com.abdoul.backend.entities.others.CartProductAdd;
import com.abdoul.backend.service.CartService;
import com.abdoul.backend.service.ProductService;
import com.abdoul.backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/user/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public CartController(CartService cartService, UserService userService, ProductService productService) {
        this.cartService = cartService;
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Cart> getOrCreateCart(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Cart> optionalCart = cartService.getCartByUser(user);
            Cart cart = optionalCart.orElseGet(() -> cartService.addCart(user));
            return ResponseEntity.ok(cart);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/{cartId}/product")
    public ResponseEntity<Cart> addProductToCart(@PathVariable UUID cartId, @RequestBody CartProductAdd cartProductAdd) {
        Product product = productService.getProductById(cartProductAdd.getProductId());
        if (product != null) {
            Cart cart = cartService.addProductToCart(cartId, product, cartProductAdd.getQuantity());
            if (cart != null) {
                return ResponseEntity.ok(cart);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{cartId}/product/{itemId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable UUID cartId, @PathVariable Long itemId) {
        cartService.deleteProductFromCart(cartId, itemId);
        return ResponseEntity.ok("Deleted Succefully");
    }

    @PutMapping("/{cartId}/product/{itemId}")
    public ResponseEntity<Cart> updateProductInCart(@PathVariable UUID cartId, @PathVariable Long itemId, @RequestBody CartProductAdd cartProductAdd) {
        Cart cart = cartService.updateProductInCart(cartId, itemId, cartProductAdd.getQuantity());
        if (cart != null) {
            return ResponseEntity.ok(cart);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}