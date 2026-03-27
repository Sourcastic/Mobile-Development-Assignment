package com.example.l230759ass1;

public class Snack {
    private int imageResId;
    private String name;
    private double price;
    private int quantity;

    public Snack(int imageResId, String name, double price) {
        this.imageResId = imageResId;
        this.name = name;
        this.price = price;
        this.quantity = 0;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increment() {
        quantity++;
    }

    public void decrement() {
        if (quantity > 0) quantity--;
    }
}