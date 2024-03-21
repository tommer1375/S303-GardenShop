package Mongo.Factories;

import Generic.Input;

public class GardenShopFactory {
    public static void createGardenShop(){
        String name = Input.readString("Introduce the name of the Garden Shop you'd like to create.");
        boolean isStockFilled = Input.readIfNo("Would you like to introduce any stock as of this moment?");
        if(isStockFilled){

        }
    }
}
