package Generic.classes;

import java.util.ArrayList;
import java.util.List;

public class GardenShop {
    private String _id;
    private String name;
    private final List<Stock> stockList = new ArrayList<>();
    private double currentValue = 0;

    public GardenShop (String _id, String name){
        this._id = _id;
        this.name = name;
    }

//    Getters
//    Setters

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
    @Override
    public String toString() {
        return "\n\t- Store_id: " + this._id
                + "\n\t\t- Name: " + this.name;
    }
}
