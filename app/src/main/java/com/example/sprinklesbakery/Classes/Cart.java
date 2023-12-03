package com.example.sprinklesbakery.Classes;

import java.util.ArrayList;
import java.util.List;


public class Cart {

    public static List<Cart> cart = new ArrayList<>();

    private Cake cake;
    private int quantity;

    public Cart(Cake cake, int quantity) {
        this.cake = cake;
        this.quantity = quantity;
    }

    public Cake getCake() {
        return cake;
    }

    public void setCake(Cake cake) {
        this.cake = cake;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static void addToCart(Cake cake) {
        boolean contains = false;
        Cart cartObject = null;
        for (Cart c : cart) {
            if (c.getCake().equals(cake)) {
                contains = true;
                cartObject = c;
                break;
            }
        }

        if (contains) {
            int qty = cartObject.getQuantity()+1;
            cartObject.setQuantity(qty);
        } else {
            cart.add(new Cart(cake, 1));
        }
    }

    public static void removeFromCart(Cake cake) {
        Cart cartObject = null;
        for (Cart c : cart) {
            if (c.getCake().equals(cake)) {
                cartObject = c;
                break;
            }
        }

        int qty = cartObject.getQuantity()-1;
        cartObject.setQuantity(qty);
        if (qty==0) {
            cart.remove(cartObject);
        }

    }

    public static double getCartTotal() {
        double total = 0;
        for (Cart c : cart) {
            total += c.getCake().getPrice()*c.getQuantity();
        }
        return total;
    }



}
