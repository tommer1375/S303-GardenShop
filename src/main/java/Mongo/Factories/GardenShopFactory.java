package Mongo.Factories;

import Generic.Input;
import Generic.classes.Stock;
import Mongo.Connectivity.MongoDAO;

import java.util.ArrayList;
import java.util.List;

public class GardenShopFactory {
    public static void createGardenShop(){
        String name = Input.readString("Introduce the name of the Garden Shop you'd like to create.");
        double currentValue = 0;

        MongoDAO.INSTANCE.createGardenShop(name, currentValue);

        boolean isStockFilled = Input.readIfNo("Would you like to introduce any stock as of this moment?");

        if(isStockFilled){
            List<Stock> stockList = fillStock();
            for(Stock stock : stockList){
                currentValue += stock.getPrice();
            }

        }
    }
    private static List<Stock> fillStock(){
        List<Stock> stockList = new ArrayList<>();
        int quantity = Input.readInt("How many items would you like to add to the stock?");
        while (quantity > 0){
            stockList.add(StockFactory.createStock());
            quantity--;
        }
        return stockList;
    }
}
