package com.endava.poliaclabs.model;

/**
 * Model class of the products that can be bought from the Vending Machine.
 */
public class Product{
    /**
     *   The code of the product. Used in selection from the menu.
     */
    private int cod;

    /**
     * The name of the product.(Water, Coke)
     */
    private String name;

    /**
     * Price of the product.
     */
    private int price;

    /**
     * Size (1L, 250ml)
     */
    private String size;

    /**
     * Constructor with parameters.
     */
    public Product(int cod, String name, int price, String size) {
        this.cod = cod;
        this.name = name;
        this.price = price;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

}
