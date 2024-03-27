package Mongo.Managers.Stores;

import Generic.Utilities.Input;
import Generic.classes.Stock;
import Generic.classes.Tickets;
import Mongo.Connectivity.MongoDAO;
import Mongo.Managers.Stores.stock.StockManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public enum EnteredGardenShop {
    INSTANCE;

    private String _id;
    private String name;
    private double currentStockValue;
    private double currentSalesValue;
    private List<Stock> stockList;

    EnteredGardenShop() {
        _id = "";
        name = "";
        currentStockValue = 0;
    }
    public void enter(String _id, String name, double currentValue, double currentOldSalesValue){
        this._id = _id;
        this.name= name;
        this.stockList = MongoDAO.INSTANCE.readShopStock(_id);
        this.currentStockValue = currentValue;
        this.currentSalesValue = currentOldSalesValue;
    }

//    CRUD Stock operations, to make use of the search info document variable.
    public void createToStock(){
        Stock stock = StockManager.createStock();

        if(stock == null){
            return;
        }

        int result = MongoDAO.INSTANCE.createSingleStock(this._id, stock);

        switch (result){
            case 0 -> System.out.println("At least one matching product in stock, to change one of it's qualities, use the \"Modify item from stock\" option.");
            case 1 -> {
                stockList.add(stock);
                System.out.println("Stock added: " + stock);
            }
            case 2 -> System.out.println("Failed to update matching product in stock, check for connection errors.");
        }
    }

    public String readStockInFull(){
        return this.stockList.stream()
                .map(Stock::toString)
                .collect(Collectors.joining("\n", "Current Stock: ", ""));
    }
    public String readStockInQuantities(){
        return stockList.stream()
                .collect(Collectors.groupingBy(
                        stock -> stock.getType().getClass().getSimpleName(),
                        Collectors.counting()))
                .entrySet().stream()
                .map(entry -> "- " + entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining("\n", "Current Stock:", "")
        );
    }
    public String readTickets(){
        List<Tickets> pastTicketList = MongoDAO.INSTANCE.readTicketsFromEnteredStore(this._id);
        if(pastTicketList == null){
            return "";
        }
        if(pastTicketList.isEmpty()){
            return "No tickets have been found under this store's ID.";
        }
        return pastTicketList.stream()
                .map(Tickets::toString)
                .collect(Collectors.joining("", "Tickets from store, ID: " + this._id, ""));
    }

    public void updateFullStock(){
        MongoDAO.INSTANCE.deleteFullStock(this._id);

        ArrayList<Stock> newStockList = StockManager.createShopStock();

        this.stockList = newStockList;
        MongoDAO.INSTANCE.createStock(this._id, newStockList);
        System.out.println("Stock replaced.");
    }
    public void updateItemFromStock(){
        String product_id = Input.readString("Introduce the object's product_id.");
        boolean isFound;
        Stock stock;

        for (int i = 0; i < stockList.size(); i++) {
            stock = stockList.get(i);
            isFound = stock.getProduct_id().equals(product_id);

            if(isFound){
                Stock updatedStock = StockManager.updateStockDocument(stock);

                int result = MongoDAO.INSTANCE.updateStock(this._id, updatedStock);

                switch (result){
                    case 0 -> System.out.println("No matching product in stock, make sure the object_id is properly copied.");
                    case 1 -> {
                        stockList.set(i, updatedStock);
                        System.out.println(stock
                                + "\nChanged to:"
                                + updatedStock);
                    }
                    case 2 -> System.out.println("Failed to update matching product in stock, check for connection errors.");
                }
                return;
            }
        }
    }

    public void deleteItemFromStock(){
        String product_id = Input.readString("Introduce the object's product_id.");
        boolean isFound;
        Stock stock;

        for (int i = 0; i < stockList.size(); i++) {
            stock = stockList.get(i);
            isFound = stock.getProduct_id().equals(product_id);

            if (isFound) {
                int result = MongoDAO.INSTANCE.deleteSingleStock(this._id, stock.getProduct_id());

                switch (result){
                    case 0 -> System.out.println("No matching product in stock, make sure the object_id is properly copied.");
                    case 1 -> {
                        stockList.remove(stock);
                        System.out.println("Stock item deleted: " + stock);
                    }
                    case 2 -> System.out.println("Failed to update matching product in stock, check for connection errors.");
                }
                return;
            }
        }
    }
    public void deleteFromActiveShops(){

    }

    public String get_id() {
        return _id;
    }
    public Stock getMatchingStockID(String product_id){
        return stockList.stream()
                .filter(stock -> stock.getProduct_id().equals(product_id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return "Store_id: " + _id
                + "\n\tName: " + name
                + "\n\tCurrent Stock Value: " + currentStockValue
                + "\n\tCurrent Sales Value: " + currentSalesValue;
    }
}
