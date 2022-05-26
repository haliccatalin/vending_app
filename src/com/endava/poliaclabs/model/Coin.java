package com.endava.poliaclabs.model;

/**
 * Represents the model class for a coin that is used by the Vending Machine.
 */
public class Coin implements Comparable<Coin>{

    /**
     * The code that is displayed in the menu to the user.
     */
    private Integer cod;

    /**
     * The value of the coin.
     */
    private Integer value;

    /**
     * Constructor with parameters.
     */
    public Coin(Integer cod, Integer value) {
        this.cod = cod;
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    /**
     * Method used to compare 2 coins.
     */
    @Override
    public int compareTo(Coin coin) {
        return coin.value.compareTo(this.value);
    }
}
