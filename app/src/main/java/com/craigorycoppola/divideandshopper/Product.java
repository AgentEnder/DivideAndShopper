package com.craigorycoppola.divideandshopper;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private String _name;
    private double _price;
    private int _quantity;
    private ArrayList<String> buyers;


    public Product(String name, double price){
        _price = price;
        _name = name;
        _quantity = 1;
        buyers = new ArrayList<>();
    }

    public String get_name() {
        return _name;
    }

    public double get_price() {
        return _price*_quantity;
    }

    public int get_quantity() {
        return _quantity;
    }

    public void increment_quantity(){
        _quantity++;
    }

    public void decrement_quantity(){
        _quantity--;
    }

    public void add_buyer(String shopper){
        buyers.add(shopper);
    }

    public ArrayList<String> get_buyers(){
        return buyers;
    }
}
