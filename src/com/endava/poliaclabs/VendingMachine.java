package com.endava.poliaclabs;

import com.endava.poliaclabs.model.Coin;
import com.endava.poliaclabs.model.CurrencyType;
import com.endava.poliaclabs.model.Product;
import com.endava.poliaclabs.service.DbService;
import com.endava.poliaclabs.service.IoService;

import java.util.*;

/**
 * Class that implements the functionality of the Vending Machine.
 */
public class VendingMachine {

    /**
     * The currency that the Vending Machine uses.
     */
    private CurrencyType currency;

    /**
     * The stock of products. The key from the map represents the product and the value
     * is the number of that product available in the stock.
     */
    private Map<Product, Integer> productStock;

    /**
     * The stock of coins. The key from the map represents the coin and the value
     * is the number of that coin available in the stock.
     * It is a TreeMap because we need the coins to be sorted for the pay rest algorithm.
     */
    private TreeMap<Coin, Integer> coinStock;

    // este un keyword care ii spune compilatorului ca nu trebuie sa
    // serializeze/deserializeze obiectele
    private transient IoService ioService;
    private transient DbService dbService;

    /**
     * Constructor without parameters.
     */
    public VendingMachine(){}

    /**
     * Display the menu products to the user.
     */
    public void displayMenu() {
        ioService.displayMessage("Welcome!");
        ioService.displayMessage("Cod  Product\t Price \tSize");
        for (Product product : productStock.keySet()) {
            ioService.displayMessage(product.getCod() + "\t" + product.getName() + "\t\t" + product.getPrice()+currency + "\t" + product.getSize());
        }
        ioService.displayMessage("0 - Terminate");
    }

    /**
     * Display the coin menu to the user in order to select what coins to introduce.
     */
    public void displayCoinStock() {
        ioService.displayMessage("Cod  Value");
        for (Coin coin : coinStock.keySet()) {
            ioService.displayMessage(coin.getCod() + "  " + coin.getValue()+currency);
        }
    }

    /**
     * Deliver the product to the user.
     * @param product - the product to be delivered.
     */
    public void deliverProduct(Product product) {
        productStock.put(product, productStock.get(product) - 1);
        ioService.displayMessage("Pick up your product!");
    }

    /**
     * Insert the coins needed to buy the selected product.
     * @param productPrice - the price of the chosen product.
     * @return the sum that was introduced by the user
     */
    public Integer insertCoins(Integer productPrice) {
        Integer sum = 0;
        int option;
        int toPay;
        boolean ok;
        // While the amount of money introduced by the user is less than the price of the product.
        while (sum < productPrice) {
            ioService.displayMessage("Insert coins:");
            ok = false;
            option = ioService.readUserInput();
            for (Coin coin : coinStock.keySet()) {
                if (coin.getCod() == option) {
                    coinStock.put(coin, 1 + coinStock.get(coin));
                    sum = sum + coin.getValue();
                    ioService.displayMessage("Amount: " + sum + " " + currency);
                    toPay = productPrice - sum;
                    ioService.displayMessage("Amount left to introduce: " + (toPay > 0 ? toPay : 0) + " " + currency);
                    ok = true;
                }
            }
            if (ok == false) {
                ioService.displayMessage("Option is not valid.");
            }
        }
        return sum;
    }

    /**
     * User selects a product to buy.
     * @return the selected product
     */
    public Product buyProduct() {
        ioService.displayMessage("Choose a product:");
        int option = ioService.readUserInput();
        boolean ok = false;

        if (option == 0) {
            this.shutDown();
        }
        for (Product p : productStock.keySet()) {
            if (p.getCod() == option) {
                Integer quantity = productStock.get(p);
                if (quantity > 0) {
                    return p;
                } else {
                    ioService.displayMessage("Not enough products in the stock.");
                    return this.buyProduct();
                }
            }
        }
        if (ok == false) {
            ioService.displayMessage("Option is not valid.");
            return this.buyProduct();
        }
        return null;
    }

    /**
     * Pay the change to the user.
     * @param change
     */
    public void payChange(Integer change) {
        if(change == 0){
            ioService.displayMessage("No change to pay back.");
        }else {
            // The coin stock is sorted so pay change to the user starting with the greater coin value.
            for (Coin coin : coinStock.keySet()) {
                while (coin.getValue() <= change) {
                    if (coinStock.get(coin) > 0) {
                        ioService.displayMessage("Paying change " + coin.getValue() + " " + currency);
                        coinStock.put(coin, coinStock.get(coin) - 1);
                        change = change - coin.getValue();
                    } else {
                        break;
                    }
                }
            }
            if (change == 0) {
                ioService.displayMessage("Successfully payed back the change!");
            } else {
                ioService.displayMessage("Not enough coins to pay back the change.");
                ioService.displayMessage("Change that could not be payed back: " + change);
            }
        }
    }

    /**
     * Starting point of the Vending Machine.
     */
    public void start() {
        while (true) {
            this.displayMenu();
            Product product = this.buyProduct();
            this.displayCoinStock();
            Integer sum = this.insertCoins(product.getPrice());
            this.deliverProduct(product);
            //buy another product
            this.payChange(sum - product.getPrice());
            //save the VM
            dbService.write(this);
        }
    }

    /**
     * Shut down the Vending Machine.
     */
    public void shutDown(){
        System.exit(0);
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public Map<Product, Integer> getProductStock() {
        return productStock;
    }

    public Map<Coin, Integer> getCoinStock() {
        return coinStock;
    }

    public void setIoService(IoService ioService) {
        this.ioService = ioService;
    }

    public void setDbService(DbService dbService) {
        this.dbService = dbService;
    }
}
