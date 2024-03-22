package Generic.Utilities;

import org.bson.Document;

import java.util.ArrayList;

public class MongoUtilities {
    public static String extractGardenShopDescription(Document document){
        return "\n- Store_id: " + document.getObjectId("_id")
                + "\n\tName: " + document.getString("name")
                + "\n\tCurrent Value: " + document.getDouble("current_value") + "€";
    }
    public static double getCurrentValue(ArrayList<Document> stock){
        double currentValue = 0;

        for (Document document : stock){
            currentValue += document.getDouble("price");
        }

        return currentValue;
    }
}
