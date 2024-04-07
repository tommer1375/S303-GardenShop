package Generic.Managers.Stores;

import Generic.Utilities.ConnectType;
import Generic.Utilities.Input;
import Generic.classes.Products;
import Generic.classes.Stock;
import Generic.classes.Tickets;
import Mongo.Connectivity.MongoDAO;
import Generic.Managers.Stores.stock.StockManager;
import SQL.Connectivity.MySQLDAO;

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
    private ConnectType connectType;

    EnteredGardenShop() {
        _id = "";
        name = "";
        currentStockValue = 0;
    }
    public void enter(String _id, String name, double currentValue, double currentOldSalesValue, List<Stock> stockList, ConnectType connectType){
        this._id = _id;
        this.name= name;
        this.stockList = stockList;
        this.currentStockValue = currentValue;
        this.currentSalesValue = currentOldSalesValue;
        this.connectType = connectType;
    }

//    CRUD Stock operations, to make use of the search info document variable.
    public void createToStock(){
        Stock stock = StockManager.createStock();

        if(stock == null){
            return;
        }

        switch (connectType){
            case MONGO -> {
                switch (MongoDAO.INSTANCE.createSingleStock(this._id, stock)){
                    case 0 -> System.out.println("At least one matching product in stock, to change one of it's qualities, use the \"Modify item from stock\" option.");
                    case 1 -> {
                        stockList.add(stock);
                        this.currentStockValue = stockList.stream().mapToDouble(s -> s.getPrice() * s.getQuantity()).sum();

                        MongoDAO.INSTANCE.updateCurrentStockValue(this._id, this.currentStockValue);

                        System.out.println("Stock added: " + stock);
                    }
                    case 2 -> System.out.println("Failed to update matching product in stock, check for connection errors.");
                }
            }
            case MySQL -> {
                switch (MySQLDAO.INSTANCE.createSingleStock(this._id, stock)){
                    case 0 -> System.out.println("At least one matching product in stock, to change one of it's qualities, use the \"Modify item from stock\" option.");
                    case 1 -> {
                        stockList.add(stock);
                        this.currentStockValue = stockList.stream().mapToDouble(s -> s.getPrice() * s.getQuantity()).sum();

                        MySQLDAO.INSTANCE.updateCurrentStockValue(this._id, this.currentStockValue);

                        System.out.println("Stock added: " + stock);
                    }
                    case 2 -> System.out.println("Failed to update matching product in stock, check for connection errors.");
                }
            }
        }
    }
    public void createTicket(List<Products> productsList, double total){
        switch (connectType){
            case MONGO -> MongoDAO.INSTANCE.createTicket(this._id, productsList, total);
            case MySQL -> MySQLDAO.INSTANCE.createTicket(this._id, productsList, total);
        }
    }

    public String readStockInFull(){
        return this.stockList.stream()
                .map(Stock::toString)
                .collect(Collectors.joining("", "Current Stock: ", ""));
    }
    public String readStockInQuantities(){
        return stockList.stream()
                .collect(Collectors.groupingBy(
                        stock -> stock.getType().name(),
                        Collectors.counting()))
                .entrySet().stream()
                .map(entry -> "- " + entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining("\n", "Current Stock:\n", "")
        );
    }
    public String readTickets(){
        List<Tickets> pastTicketList = switch (connectType){
            case MONGO -> MongoDAO.INSTANCE.readTicketsFromEnteredStore(this._id);
            case MySQL -> MySQLDAO.INSTANCE.readTicketsFromEnteredStore(this._id);
            default -> null;
        };

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
        this.stockList = StockManager.createShopStock();
        this.currentStockValue = stockList.stream().mapToDouble(s -> s.getPrice() * s.getQuantity()).sum();

        switch (connectType){
            case MONGO -> {
                MongoDAO.INSTANCE.deleteFullStock(this._id);

                MongoDAO.INSTANCE.createStock(this._id, (ArrayList<Stock>) this.stockList);
                MongoDAO.INSTANCE.updateCurrentStockValue(this._id, this.currentStockValue);}
            case MySQL -> {
                MySQLDAO.INSTANCE.deleteFullStock(this._id);

                MySQLDAO.INSTANCE.createStock(this._id, (ArrayList<Stock>) this.stockList);
                MySQLDAO.INSTANCE.updateCurrentStockValue(this._id, this.currentStockValue);
            }
        }
        System.out.println("Stock replaced.");
    }
    public void updateItemFromStock(){
        if(stockList.isEmpty()){
            System.out.println("No products in stock.");
            return;
        }
        String product_id = Input.readString("Introduce the object's product_id.");
        Stock stock;

        for (int i = 0; i < stockList.size(); i++) {
            stock = stockList.get(i);

            if(stock.getProduct_id().equals(product_id)){
                Stock updatedStock = StockManager.updateStockItem(stock);

                switch (connectType){
                    case MONGO -> {
                        switch (MongoDAO.INSTANCE.updateStock(this._id, updatedStock)){
                            case 0 -> System.out.println("No matching product in stock, make sure the object_id is properly copied.");
                            case 1 -> {
                                stockList.set(i, updatedStock);
                                this.currentStockValue = stockList.stream().mapToDouble(s -> s.getPrice() * s.getQuantity()).sum();
                                MongoDAO.INSTANCE.updateCurrentStockValue(this._id, this.currentStockValue);

                                System.out.println(stock
                                        + "\nChanged to:"
                                        + updatedStock);
                            }
                            case 2 -> System.out.println("Failed to update matching product in stock, check for connection errors.");
                        }
                        return;
                    }
                    case MySQL -> {
                        switch (MySQLDAO.INSTANCE.updateStock(this._id, updatedStock)){
                            case 0 -> System.out.println("No matching product in stock, make sure the object_id is properly copied.");
                            case 1 -> {
                                stockList.set(i, updatedStock);
                                this.currentStockValue = stockList.stream().mapToDouble(s -> s.getPrice() * s.getQuantity()).sum();
                                MySQLDAO.INSTANCE.updateCurrentStockValue(this._id, this.currentStockValue);

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
        }
        System.out.println("No matching product in stock, make sure the object_id is properly copied.");
    }
    public void updateItemFromStockForTicketCreation(Stock updatedStock){
        for(Stock stock : stockList){
            if(stock.getProduct_id().equals(updatedStock.getProduct_id())){
                stock.setQuantity(updatedStock.getQuantity());

                switch (connectType){
                    case MONGO -> {
                        switch (MongoDAO.INSTANCE.updateStock(this._id, stock)){
                            case 0 -> System.out.println("No matching product in stock, make sure the object_id is properly copied.");
                            case 1 -> {
                                this.currentSalesValue += this.currentStockValue - stockList.stream()
                                        .mapToDouble(s -> s.getPrice() * s.getQuantity())
                                        .sum();
                                this.currentStockValue = stockList.stream()
                                        .mapToDouble(s -> s.getPrice() * s.getQuantity())
                                        .sum();
                                MongoDAO.INSTANCE.updateCurrentStockValue(this._id, this.currentStockValue);
                                MongoDAO.INSTANCE.updateCurrentSalesValue(this._id, this.currentSalesValue);
                            }
                            case 2 -> System.out.println("Failed to update matching product in stock, check for connection errors.");
                        }
                    }
                    case MySQL -> {
                        switch (MySQLDAO.INSTANCE.updateStock(this._id, stock)){
                            case 0 -> System.out.println("No matching product in stock, make sure the object_id is properly copied.");
                            case 1 -> {
                                this.currentSalesValue += this.currentStockValue - stockList.stream()
                                        .mapToDouble(s -> s.getPrice() * s.getQuantity())
                                        .sum();
                                this.currentStockValue = stockList.stream()
                                        .mapToDouble(s -> s.getPrice() * s.getQuantity())
                                        .sum();
                                MySQLDAO.INSTANCE.updateCurrentStockValue(this._id, this.currentStockValue);
                                MySQLDAO.INSTANCE.updateCurrentSalesValue(this._id, this.currentSalesValue);
                            }
                            case 2 -> System.out.println("Failed to update matching product in stock, check for connection errors.");
                        }
                    }
                }
            }
        }
    }

    public void deleteItemFromStock(){
        if(stockList.isEmpty()){
            System.out.println("No products in stock to eliminate.");
            return;
        }
        String product_id = Input.readString("Introduce the object's product_id.");
        boolean isFound;
        Stock stock;

        for (int i = 0; i < stockList.size(); i++) {
            stock = stockList.get(i);
            isFound = stock.getProduct_id().equals(product_id);

            if (isFound) {
                switch (connectType){
                    case MONGO -> {
                        switch (MongoDAO.INSTANCE.deleteSingleStock(this._id, stock.getProduct_id())){
                            case 0 -> System.out.println("No matching product in stock, make sure the object_id is properly copied.");
                            case 1 -> {
                                stockList.remove(stock);
                                this.currentStockValue = stockList.stream().mapToDouble(s -> s.getPrice() * s.getQuantity()).sum();

                                MongoDAO.INSTANCE.updateCurrentStockValue(this._id, this.currentStockValue);
                                System.out.println("Stock item deleted: " + stock);
                            }
                            case 2 -> System.out.println("Failed to update matching product in stock, check for connection errors.");
                        }
                    }
                    case MySQL -> {
                        switch (MySQLDAO.INSTANCE.deleteSingleStock(this._id, stock.getProduct_id())){
                            case 0 -> System.out.println("No matching product in stock, make sure the object_id is properly copied.");
                            case 1 -> {
                                stockList.remove(stock);
                                this.currentStockValue = stockList.stream().mapToDouble(s -> s.getPrice() * s.getQuantity()).sum();

                                MySQLDAO.INSTANCE.updateCurrentStockValue(this._id, this.currentStockValue);
                                System.out.println("Stock item deleted: " + stock);
                            }
                            case 2 -> System.out.println("Failed to update matching product in stock, check for connection errors.");
                        }
                    }
                }
                return;
            }
        }
    }
    public boolean deleteFromActiveShops(){
        if(MongoDAO.INSTANCE.deleteGardenShop(this._id)){
            System.out.println("Store properly deleted.");
            return true;
        } else {
            System.out.println("Failed to declare bankruptcy, check for connection errors.");
            return false;
        }
    }

    public String get_id() {
        return _id;
    }
    public Stock getMatchingStock(String product_id){
        return this.stockList.stream()
                .filter(stock -> stock.getProduct_id().equals(product_id))
                .findFirst()
                .orElse(null);
    }
    public boolean isStockListEmpty(){
        return stockList.isEmpty();
    }

    @Override
    public String toString() {
        return "Store_id: " + _id
                + "\n\tName: " + name
                + "\n\tCurrent Stock Value: " + currentStockValue
                + "\n\tCurrent Sales Value: " + currentSalesValue + "\n";
    }
}
