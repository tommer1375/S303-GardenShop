package Mongo.Managers.Stores;

import Generic.Utilities.Input;
import Mongo.Managers.MongoUtilities;
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
    private double currentStockValue;
    private double currentSalesValue;
    private List<Document> stockList;

    EnteredGardenShop() {
        _id = "";
        name = "";
        currentStockValue = 0;
    }
    public void enter(String _id, String name, double currentValue, double currentOldSalesValue){
        this._id = _id;
        this.name= name;
        this.stockList = MongoDAO.INSTANCE.readShopStock(new Document("_id", new ObjectId(_id)));
        this.currentStockValue = currentValue;
        this.currentSalesValue = currentOldSalesValue;
    }

//    CRUD Stock operations, to make use of the search info document variable.
    public void createToStock(){
        Document stock = StockManager.createStock();

        if(stock == null){
            return;
        }

        Document filter = getStockFilter(stock);

        stockList.add(stock);
        MongoDAO.INSTANCE.createSingleStock(filter, stock);
        System.out.println("Stock added: " + "\n" + MongoUtilities.printSingleStock(Objects.requireNonNull(stock)));
    }

    public String readStockInFull(){
        return this.stockList.stream()
                .map(document ->
                        "- [ Product_id" + document.getObjectId("product_id") + ", "
                        + "Type: " + document.getString("type") + ", "
                        + "Price: " + document.getDouble("price") + "€, "
                        + "Quantity: " + document.getInteger("quantity.") + " ]")
                .collect(Collectors.joining("\n", "Current Stock: ", ""));
    }
    public String readStockInQuantities(){
        return stockList.stream()
                .collect(Collectors.groupingBy(
                        doc -> doc.getString("type"),
                        Collectors.counting()))
                .entrySet().stream()
                .map(entry -> "- " + entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining("\n", "Current Stock:", "")
        );
    }

    public void updateFullStock(){
        MongoDAO.INSTANCE.deleteFullStock(getSearchInfo());

        List<Document> newStockList = StockManager.createShopStock();

        this.stockList = newStockList;
        MongoDAO.INSTANCE.createStock(getSearchInfo(), newStockList);
        System.out.println("Stock replaced.");
    }
    public void updateItemFromStock(){
        ObjectId product_id = new ObjectId(Input.readString("Introduce the object's product_id."));
        boolean isFound;

        for (int i = 0; i < stockList.size(); i++) {
            Document stock = stockList.get(i);
            isFound = stock.getObjectId("product_id").equals(product_id);

            if(isFound){
                Document updatedStock = StockManager.updateStockDocument(stock);

                int result = MongoDAO.INSTANCE.updateStock(getStockFilter(stock), updatedStock);

                switch (result){
                    case 0 -> System.out.println("No matching product in stock, make sure the object_id is properly copied.");
                    case 1 -> {
                        stockList.set(i, updatedStock);
                        System.out.println(MongoUtilities.printSingleStock(stock)
                                + "\nChanged to:"
                                + MongoUtilities.printSingleStock(updatedStock));
                    }
                    case 2 -> System.out.println("Failed to update matching product in stock, check for connection errors.");
                }
                return;
            }
        }
    }

    public void deleteItemFromStock(){
        ObjectId product_id = new ObjectId(Input.readString("Introduce the object's product_id."));
        boolean isFound;

        for (int i = 0; i < stockList.size(); i++) {
            Document stock = stockList.get(i);
            isFound = stock.getObjectId("product_id").equals(product_id);

            if (isFound) {
                int result = MongoDAO.INSTANCE.deleteSingleStock(getStockFilter(stock));

                switch (result){
                    case 0 -> System.out.println("No matching product in stock, make sure the object_id is properly copied.");
                    case 1 -> {
                        stockList.remove(stock);
                        System.out.println("Stock item deleted: " + MongoUtilities.printSingleStock(stock));
                    }
                    case 2 -> System.out.println("Failed to update matching product in stock, check for connection errors.");
                }
                return;
            }
        }
    }


    public Document getSearchInfo(){
        return new Document("_id", new ObjectId(_id));
    }
    private Document getStockFilter(Document stock) {
        return getSearchInfo()
                .append("stock.product_id", stock.getObjectId("product_id"));
    }
    public Document getTicketFilter(){
        return new Document("store_id", new ObjectId(_id));
    }
    public Document getMatchingStock(ObjectId productId){
        return stockList.stream()
                .filter(product -> product.getObjectId("product_id").equals(productId))
                .findFirst()
                .orElse(new Document());
    }

    @Override
    public String toString() {
        return "Store_id: " + _id
                + "\n\tName: " + name
                + "\n\tCurrent Stock Value: " + currentStockValue
                + "\n\tCurrent Sales Value: " + currentSalesValue;
    }
}
