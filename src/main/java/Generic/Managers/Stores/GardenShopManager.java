package Generic.Managers.Stores;

import Generic.Utilities.ConnectType;
import Generic.Managers.Stores.stock.StockManager;
import Generic.Utilities.Input;
import Generic.classes.GardenShop;
import Generic.classes.Stock;
import Mongo.Connectivity.MongoDAO;
import Generic.Managers.Utilities;
import Generic.Managers.Tickets.TicketManager;
import SQL.Connectivity.MySQLDAO;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class GardenShopManager {
    private static final Logger logger = LoggerFactory.getLogger(GardenShopManager.class);
    public static void createGardenShop(ConnectType connectType){
        String name = Input.readString("Introduce the name of the Garden Shop you'd like to create.");

        ArrayList<Stock> stocks = StockManager.createShopStock();

        double currentStockValue = stocks.stream()
                .mapToDouble(stock -> stock.getPrice() * stock.getQuantity())
                .sum();

        switch (connectType){
            case MONGO -> MongoDAO.INSTANCE.createGardenShop(name, stocks, currentStockValue);
            case MySQL -> MySQLDAO.INSTANCE.createGardenShop(name, stocks, currentStockValue);
        }
    }
    public static GardenShop createGardenShopFromDocument(Document document){
        String product_id = document.getObjectId("_id").toString();
        List<Stock> stockList = document.getList("stock", Document.class).stream()
                .map(StockManager::createStockFromDocument)
                .toList();

        return new GardenShop.Builder()
                ._id(product_id)
                .name(document.getString("name"))
                .currentStockValue(document.getDouble("current_stock_value"))
                .currentSalesValue(document.getDouble("current_sales_value"))
                .stockList(stockList)
                .build();
    }
    @SuppressWarnings("unused")
    public static GardenShop createGardenShopFromResultSet(ResultSet resultSet){
        try {
            List<Stock> stockList = MySQLDAO.INSTANCE.readShopStock(String.valueOf(resultSet.getInt("idstores")));
            return new GardenShop.Builder()
                    ._id(String.valueOf(resultSet.getInt("idstores")))
                    .name(resultSet.getString("name"))
                    .stockList(stockList)
                    .currentStockValue(resultSet.getDouble("current_stock_value"))
                    .currentSalesValue(resultSet.getDouble("current_sales_value"))
                    .build();
        } catch (SQLException e) {
            getLogger(GardenShopManager.class).atError().log("Unable to create connection at createGardenShop(), check connection settings." + "\n"
                    + "Error Message: " + e.getMessage() + "\n"
                    + "SQL State: " + e.getSQLState());
            return null;
        }
    }
    public static void readActiveGardenShops(ConnectType connectType){
        List<GardenShop> activeGardenShops = switch (connectType){
            case MONGO -> MongoDAO.INSTANCE.readGardenShops();
            case MySQL -> MySQLDAO.INSTANCE.readGardenShops();
            default -> null;
        };
        if(activeGardenShops == null){
            switch(connectType){
                case MONGO -> logger.atError().log("MongoDAO.INSTANCE.readGardenShops() == null, check it.");
                case MySQL -> logger.atError().log("MySQLDAO.INSTANCE.readGardenShops() == null, check it.");
            }
            return;
        }

        String activeGardenShopPrintable;
        if (activeGardenShops.isEmpty()){
            activeGardenShopPrintable = "Current Active Garden Shops:\n- Empty";
        } else {
            activeGardenShopPrintable = activeGardenShops.stream()
                    .map(GardenShop::toString)
                    .collect(Collectors.joining("", "Current Active Garden Shops:", ""));
        }

        System.out.println(activeGardenShopPrintable);
    }
    public static void enterGardenShop(ConnectType connectType){
        String name = Input.readString("Introduce the name of the Garden Shop whose Management System you'd like to enter.");
        boolean isInDatabase = Utilities.enterGardenShop(name, connectType);

        if (!isInDatabase){
            System.out.println("That garden shop isn't registered in our system.");
            return;
        }

        while(true){
            switch (Input.readInt(EnteredGardenShop.INSTANCE
                    + """
                        0. Exit store.
                        1. See current Stock.
                        2. Update Stock.
                        3. See Past Tickets.
                        4. Create Ticket.
                        5. Inform HQ of Bankruptcy.
                        """)){
                case 0 -> {return;}
                case 1 -> StockManager.readStock();
                case 2 -> StockManager.updateStock();
                case 3 -> TicketManager.readPastTickets();
                case 4 -> TicketManager.createTicket();
                case 5 -> { if (EnteredGardenShop.INSTANCE.deleteFromActiveShops()) return;}
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
