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

    @Override
    public String toString() {
        return "\n- Store_id: " + this._id
                + "\n - Name: " + this.name ;
    }
}
