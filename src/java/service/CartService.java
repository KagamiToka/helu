package service;

import model.Cart;
import model.CartItem;
import model.Product;

public class CartService implements ICartService {

    @Override
    public void addToCart(Cart cart, Product product, int quantity) {
        if (cart == null || product == null || quantity <= 0) {
            throw new IllegalArgumentException("Sản phẩm hoặc số lượng không hợp lệ!");
        }
        cart.addItem(product, quantity); // Sử dụng phương thức có sẵn trong Cart
    }

    @Override
    public void updateCartItem(Cart cart, int productId, int quantity) {
        if (cart == null || quantity < 0) {
            throw new IllegalArgumentException("Dữ liệu không hợp lệ!");
        }
        cart.updateItem(productId, quantity);
    }

    @Override
    public void removeCartItem(Cart cart, int productId) {
        if (cart == null) {
            throw new IllegalArgumentException("Dữ liệu không hợp lệ!");
        }
        cart.removeItem(productId);
    }

    @Override
    public double getTotalPrice(Cart cart) {
        if (cart == null) {
            return 0.0;
        }
        return cart.getTotalPrice();
    }

    @Override
    public boolean isCartEmpty(Cart cart) {
        return cart == null || cart.getItems().isEmpty();
    }
}
