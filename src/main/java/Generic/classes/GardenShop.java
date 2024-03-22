package Generic.classes;

import java.util.ArrayList;
import java.util.List;

public class GardenShop {
    private final String _id;
    private final String name;
    private final List<Stock> stockList;
    private double currentValue = 0;

    public GardenShop (String _id, String name, List<Stock> stockList, double currentValue){
        this._id = _id;
        this.name = name;
        this.stockList = stockList;
        this.currentValue = currentValue;
    }

    private String getStock(){
        String textToReturn = this.name + "'s stock:";
        if (this.stockList.isEmpty()){
            return textToReturn = textToReturn.concat("\n\t- Empty.");
        }

        for (Stock stock : stockList){
            textToReturn = textToReturn.concat(stock.toString());
        }

        return textToReturn;
    }
    private double getCurrentValue(){
        return currentValue;
    }
    @Override
    public String toString() {
        return "\n\t- Store_id: " + this._id
                + "\n\t\t- Name: " + this.name;
    }
}
