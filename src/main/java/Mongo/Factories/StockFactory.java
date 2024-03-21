package Mongo.Factories;

import Generic.Input;
import Generic.classes.Stock;
import Generic.classes.Types;

public class StockFactory {
    public static Stock addStock(){
        String typeText = Input.readString("Introduce the type of stock you'd like to add.");
        try
        Types type
        double price
        int quantity
        String quality

        return new Stock(type, price, quantity, quality);
    }
}
