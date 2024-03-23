package Mongo.Managers.Stores;

import Generic.Utilities.Input;
import Generic.Utilities.MongoUtilities;
import Mongo.Connectivity.MongoDAO;
import Mongo.Managers.Stores.stock.StockManager;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public enum EnteredGardenShop {
    INSTANCE;

    private String _id;
    private String name;
    private double currentValue;
    private List<Document> stockList;
    private Document searchInfo;

    EnteredGardenShop() {
        _id = "";
        name = "";
        currentValue = 0;
    }
    public void enter(String _id, String name, double currentValue){
        this._id = _id;
        this.name= name;
        this.searchInfo = new Document("_id", new ObjectId(_id));
        this.stockList = MongoDAO.INSTANCE.readShopStock(this.searchInfo);
        this.currentValue = currentValue;
    }
    public void update(){
        MongoUtilities.enterGardenShop(this.name);
    }

    public String readStock(){
        return this.stockList.stream()
                .map(document ->
                        "- [ Product_id" + document.getObjectId("product_id") + ", "
                        + "Type: " + document.getString("type") + ", "
                        + "Price: " + document.getDouble("price") + "€, "
                        + "Quantity: " + document.getInteger("quantity.") + " ]")
                .collect(Collectors.joining("\n", "Current Stock:\\n", ""));
    }
    public void replaceStock(){
        MongoDAO.INSTANCE.deleteStock(this.searchInfo, new Document("$unset", new Document("stock", "")));

        List<Document> newStockList = StockManager.createShopStock();

        this.stockList = newStockList;
        MongoDAO.INSTANCE.createStock(this.searchInfo, newStockList);
        System.out.println("Stock replaced.");
    }
    public void addToStock(){
        Document stock = StockManager.createStockDocument();

        stockList.add(stock);
        MongoDAO.INSTANCE.createSingleStock(stock);
        System.out.println("Stock added: " + "\n" + MongoUtilities.printSingleStock(Objects.requireNonNull(stock)));
    }
    public void modifyItemFromStock(){
        ObjectId product_id = new ObjectId(Input.readString("Introduce the object's product_id."));
        boolean isFound;

        for (int i = 0; i < stockList.size(); i++) {
            Document stock = stockList.get(i);
            isFound = stock.getObjectId("product_id").equals(product_id);

            if(isFound){
                Document newStock = StockManager.updateStockDocument(stock);

                stockList.remove(i);
                stockList.add(i, newStock);
                System.out.println(MongoUtilities.printSingleStock(stock)
                        + "\nChanged to:"
                        + MongoUtilities.printSingleStock(newStock));
                return;
            }
        }

        System.out.println("An object with that product_id isn't registered.");
    }
    public Document getSearchInfo(){
        return this.searchInfo;
    }

    @Override
    public String toString() {
        return "Store_id: " + _id
                + "\n\tName: " + name
                + "\n\tCurrent Value: " + currentValue;
    }
}
