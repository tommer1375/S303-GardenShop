package SQL.Connectivity;

import Generic.DAO;
import Generic.classes.GardenShop;
import Generic.classes.Products;
import Generic.classes.Stock;
import Generic.classes.Tickets;
import Generic.classes.qualities.*;
import Generic.classes.qualities.Types;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.*;

@SuppressWarnings("LoggingSimilarMessage")
public enum MySQLDAO implements DAO {
    INSTANCE;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB = "garden_shop";
    private static final String USER = "it_academy_access";
    private static final String PASSWORD = "fneH5P95Yqmfnm";

    //    Create methods implemented
    @Override
    public void createGardenShop(String name, ArrayList<Stock> stockList, double currentStockValue) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL + DB, USER, PASSWORD)) {
            String sql = "INSERT INTO stores (name, current_stock_value, current_sales_value) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setDouble(2, currentStockValue);
            statement.setDouble(3, 0);
            int result = statement.executeUpdate();

            if (result == 0) {
                getLogger(MySQLDAO.class).atError().log("Error creating garden shop. Check code.");
            }

            try (ResultSet generatedKey = statement.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    int id = generatedKey.getInt(1);
                    GardenShop createdGardenShop = new GardenShop.Builder()
                            ._id(String.valueOf(id))
                            .name(name)
                            .stockList(stockList)
                            .currentStockValue(currentStockValue)
                            .currentSalesValue(0)
                            .build();

                    if (stockList.isEmpty()){
                        System.out.println("Garden Shop Properly Created:" + createdGardenShop.toString());
                        return;
                    }

                    createStock(String.valueOf(id), stockList);
                    System.out.println("Garden Shop Properly Created:" + createdGardenShop.toString());
                }
            }
        } catch (SQLException e) {
            getLogger(MySQLDAO.class).atError().log("SQLException caught at createGardenShop(), check connection settings." + "\n"
                    + "Error Message: " + e.getMessage() + "\n"
                    + "SQL State: " + e.getSQLState());
        }
    }
    @Override
    public void createStock(String store_id, ArrayList<Stock> newStockList) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL + DB, USER, PASSWORD)) {
            String productSql = "INSERT INTO products (type, price, quality) VALUES (?, ?, ?)";
            String stockSql = "INSERT INTO stock (idstore, idproduct, quantity) VALUES (?, ?, ?)";

            PreparedStatement productStatement = connection.prepareStatement(productSql, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement stockStatement = connection.prepareStatement(stockSql);

            for (Stock stock : newStockList) {
                productStatement.setInt(1, stock.getType().getMySqlValue());
                productStatement.setDouble(2, stock.getPrice());
                productStatement.setString(3, stock.getQuality().getName());

                int productResult = productStatement.executeUpdate();

                if (productResult == 0) {
                    getLogger(MySQLDAO.class).atError().log("Error creating product. Check code.");
                    continue;
                }

                try (ResultSet generatedKeys = productStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int storeID = Integer.parseInt(store_id);
                        int productId = generatedKeys.getInt(1);
                        stock.setProduct_id(String.valueOf(productId));

                        stockStatement.setInt(1, storeID);
                        stockStatement.setInt(2, productId);
                        stockStatement.setInt(3, stock.getQuantity());

                        int stockResult = stockStatement.executeUpdate();

                        if (stockResult == 0) {
                            getLogger(MySQLDAO.class).atError().log("Error creating stock entry. Check code.");
                        }
                    } else {
                        getLogger(MySQLDAO.class).atError().log("Failed to retrieve auto-generated keys for product insertion.");
                    }
                }
            }
        } catch (SQLException e) {
            getLogger(MySQLDAO.class).atError().log("SQLException caught at createStock(), check connection settings." + "\n"
                    + "Error Message: " + e.getMessage() + "\n"
                    + "SQL State: " + e.getSQLState());
        }
    }
    @Override
    public int createSingleStock(String store_id, Stock stock) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL + DB, USER, PASSWORD)) {
            String productSql = "INSERT INTO products (type, price, quality) VALUES (?, ?, ?)";
            String stockSql = "INSERT INTO stock (idstore, idproduct, quantity) VALUES (?, ?, ?)";

            PreparedStatement productStatement = connection.prepareStatement(productSql, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement stockStatement = connection.prepareStatement(stockSql);

            productStatement.setInt(1, stock.getType().getMySqlValue());
            productStatement.setDouble(2, stock.getPrice());
            productStatement.setString(3, stock.getQuality().getName());

            int productResult = productStatement.executeUpdate();

            if (productResult == 0) {
                getLogger(MySQLDAO.class).atError().log("Error creating product. Check code.");
                return 2;
            }
            try (ResultSet generatedKeys = productStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int storeID = Integer.parseInt(store_id);
                    int productId = generatedKeys.getInt(1);
                    stock.setProduct_id(String.valueOf(productId));

                    stockStatement.setInt(1, storeID);
                    stockStatement.setInt(2, productId);
                    stockStatement.setInt(3, stock.getQuantity());

                    int stockResult = stockStatement.executeUpdate();

                    if (stockResult == 0) {
                        getLogger(MySQLDAO.class).atError().log("Error creating stock entry. Check code.");
                    }
                    return 1;
                } else {
                    getLogger(MySQLDAO.class).atError().log("Failed to retrieve auto-generated keys for product insertion.");
                    return 2;
                }
            }
        } catch (SQLException e) {
            getLogger(MySQLDAO.class).atError().log("SQLException caught at createSingleStock(), check connection settings." + "\n"
                    + "Error Message: " + e.getMessage() + "\n"
                    + "SQL State: " + e.getSQLState());
            return 2;
        }
    }
    public void createTicket(String store_id, List<Products> products, double total) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL + DB, USER, PASSWORD)) {
            String ticketSql = "INSERT INTO tickets (idstore, total) VALUES (?, ?)";
            PreparedStatement ticketStatement = connection.prepareStatement(ticketSql, Statement.RETURN_GENERATED_KEYS);
            ticketStatement.setInt(1, Integer.parseInt(store_id));
            ticketStatement.setDouble(2, total);
            ticketStatement.executeUpdate();

            int ticketId;
            try (java.sql.ResultSet generatedKeys = ticketStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ticketId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating ticket failed, no ID obtained.");
                }
            }

            String purchaseSql = "INSERT INTO purchases (idtickets, idproduct, quantity, purchase_total) VALUES (?,?,?,?)";
            PreparedStatement purchaseStatement = connection.prepareStatement(purchaseSql);
            for (Products product : products) {
                purchaseStatement.setInt(1, ticketId);
                purchaseStatement.setInt(2, Integer.parseInt(product.getProduct_id()));
                purchaseStatement.setInt(3, product.getQuantity());
                purchaseStatement.setDouble(4, product.getTotal());
                purchaseStatement.executeUpdate();
            }

        } catch (SQLException e) {
            getLogger(MySQLDAO.class).atError().log("SQLException caught at createTicket(), check connection settings" + "\n"
                    + "Error Message: " + e.getMessage() + "\n"
                    + "SQL State: " + e.getSQLState());
        }
    }

    //    Read methods implemented
    @Override
    public List<GardenShop> readGardenShops() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL + DB, USER, PASSWORD)) {
            List<GardenShop> gardenShops = new ArrayList<>();
            GardenShop gardenShop;
            String sql = "SELECT * FROM stores";
            PreparedStatement statement = connection.prepareStatement(sql);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    List<Stock> stockList = readShopStock(String.valueOf(resultSet.getInt("idstores")));
                    gardenShop = new GardenShop.Builder()
                            ._id(String.valueOf(resultSet.getInt("idstores")))
                            .name(resultSet.getString("name"))
                            .stockList(stockList)
                            .currentStockValue(resultSet.getDouble("current_stock_value"))
                            .currentSalesValue(resultSet.getDouble("current_sales_value"))
                            .build();
                    gardenShops.add(gardenShop);
                }
                return gardenShops;
            }
        } catch (SQLException e) {
            getLogger(MySQLDAO.class).atError().log("SQLException caught at readGardenShops(), check connection settings." + "\n"
                    + "Error Message: " + e.getMessage() + "\n"
                    + "SQL State: " + e.getSQLState());
            return null;
        }
    }
    @Override
    public GardenShop readGardenShop(String name) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL + DB, USER, PASSWORD)) {
            String sql = "SELECT * FROM stores WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    List<Stock> stockList = readShopStock(String.valueOf(resultSet.getInt("idstores")));
                    return new GardenShop.Builder()
                            ._id(String.valueOf(resultSet.getInt("idstores")))
                            .name(resultSet.getString("name"))
                            .stockList(stockList)
                            .currentStockValue(resultSet.getDouble("current_stock_value"))
                            .currentSalesValue(resultSet.getDouble("current_sales_value"))
                            .build();
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            getLogger(MySQLDAO.class).atError().log("SQLException caught at readGardenShop(), check connection settings." + "\n"
                    + "Error Message: " + e.getMessage() + "\n"
                    + "SQL State: " + e.getSQLState());
        }
        return null;
    }
    @Override
    public List<Stock> readShopStock(String gardenShop_id) {
        List<Stock> stockList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL + DB, USER, PASSWORD)) {
            String sql = "SELECT s.idproduct, p.type, p.price, p.quality, s.quantity " +
                    "FROM stock s " +
                    "INNER JOIN products p ON s.idproduct = p.idproducts " +
                    "INNER JOIN stores st ON s.idstore = st.idstores " +
                    "WHERE st.idstores = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, gardenShop_id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String product_id = resultSet.getString("idproduct");
                Generic.classes.qualities.Types type = switch (resultSet.getInt("type")) {
                    case 0 -> Types.TREE;
                    case 1 -> Types.FLOWER;
                    case 2 -> Types.DECORATION;
                    default -> Types.ERROR;
                };
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                Quality quality = switch (type) {
                    case TREE -> Height.valueOf(resultSet.getString("quality"));
                    case FLOWER -> Color.valueOf(resultSet.getString("quality"));
                    case DECORATION -> Material.valueOf(resultSet.getString("quality"));
                    default -> null;
                };

                Stock stock = new Stock.Builder()
                        .product_id(product_id)
                        .type(type)
                        .price(price)
                        .quantity(quantity)
                        .quality(quality)
                        .build();

                stockList.add(stock);
            }

        } catch (SQLException e) {
            getLogger(MySQLDAO.class).atError().log("Error reading shop stock. Check connection settings." + "\n"
                    + "Error Message: " + e.getMessage() + "\n"
                    + "SQL State: " + e.getSQLState());
        }

        return stockList;
    }
    @Override
    public List<Tickets> readTicketsFromEnteredStore(String store_id) {
        List<Tickets> ticketsList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL + DB, USER, PASSWORD)) {
            String sql = "SELECT t.idtickets, t.idstore, t.total, p.idproduct, p.quantity, p.purchase_total " +
                    "FROM tickets t " +
                    "JOIN purchases p ON t.idtickets = p.idtickets " +
                    "WHERE t.idstore = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(store_id));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String ticketId = String.valueOf(resultSet.getInt("idtickets"));

                Tickets ticket = null;
                for (Tickets t : ticketsList) {
                    if (t.get_id().equals(ticketId)) {
                        ticket = t;
                        break;
                    }
                }
                if (ticket == null) {
                    // If ticket does not exist, create a new one and add to ticketsList
                    ticket = new Tickets.Builder()
                            ._id(ticketId)
                            .store_id(store_id)
                            .total(resultSet.getDouble("total")) // Total for the ticket
                            .build();
                    ticketsList.add(ticket);
                }

                ticket.getProductsList().add(new Products.Builder()
                        .product_id(String.valueOf(resultSet.getString("idproduct")))
                        .quantity(resultSet.getInt("quantity"))
                        .total(resultSet.getDouble("purchase_total"))
                        .build());
            }
            return ticketsList;
        } catch (SQLException e) {
            getLogger(MySQLDAO.class).atError().log("SQLException caught at readTicketsFromEnteredStore(), check connection settings." + "\n"
                    + "Error Message: " + e.getMessage() + "\n"
                    + "SQL State: " + e.getSQLState());
            return null;
        }
    }

    //    Update methods implemented
    @Override
    public int updateStock(String store_id, Stock update) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL + DB, USER, PASSWORD)) {
            String sql = "UPDATE stock, products " +
                    "SET stock.quantity = ?, products.price = ? " +
                    "WHERE stock.idproduct = products.idproducts AND stock.idproduct = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, update.getQuantity());
            statement.setDouble(2, update.getPrice());
            statement.setInt(3, Integer.parseInt(update.getProduct_id()));
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                return 0;
            } else {
                return 1;
            }
        } catch (SQLException e) {
            getLogger(MySQLDAO.class).atError().log("SQLException caught at updateStock(), check connection settings." + "\n"
                    + "Error Message: " + e.getMessage() + "\n"
                    + "SQL State: " + e.getSQLState());
            return 2;
        }
    }
    @Override
    public void updateCurrentStockValue(String store_id, double newStockValue) {
        if (store_id == null) {
            getLogger(MySQLDAO.class).atError().log("store_id is null in updateCurrentStockValue().");
            return;
        }

        try (Connection connection = DriverManager.getConnection(JDBC_URL + DB, USER, PASSWORD)) {
            String query = "UPDATE stores SET current_stock_value = ? WHERE idstores = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDouble(1, newStockValue);
            statement.setInt(2, Integer.parseInt(store_id));
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                getLogger(MySQLDAO.class).atError().log("No rows affected in updateCurrentStockValue()'s statement.executeUpdate() method execution."
                        + "Store_id = " + store_id + "Store_id numerical value = " + Integer.parseInt(store_id));
            }
        } catch (SQLException e) {
            getLogger(MySQLDAO.class).atError().log("SQLException caught at updateStock(), check connection settings." + "\n"
                    + "Error Message: " + e.getMessage() + "\n"
                    + "SQL State: " + e.getSQLState());
        } catch (NumberFormatException e) {
            getLogger(MySQLDAO.class).atError().log("NumberFormatException caught when parsing store_id to integer: " + store_id);
        }
    }

    @Override
    public void updateCurrentSalesValue(String store_id, double newSalesValue) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL + DB, USER, PASSWORD)) {
            String query = "UPDATE stores SET current_sales_value = ? WHERE idstores = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDouble(1, newSalesValue);
            statement.setInt(2, Integer.parseInt(store_id));
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                getLogger(MySQLDAO.class).atError().log("No rows affected in updateCurrentStockValue()'s statement.executeUpdate() method execution.");
            }
        } catch (SQLException e) {
            getLogger(MySQLDAO.class).atError().log("SQLException caught at updateStock(), check connection settings." + "\n"
                    + "Error Message: " + e.getMessage() + "\n"
                    + "SQL State: " + e.getSQLState());
        }
    }

    //    Delete methods implemented
    @Override
    public boolean deleteGardenShop(String store_id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL + DB, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM stores WHERE idstores = ?");
            statement.setInt(1, Integer.parseInt(store_id));
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            getLogger(MySQLDAO.class).atError().log("SQLException caught at deleteGardenShop(), check connection settings" + "\n"
                    + "Error Message: " + e.getMessage() + "\n"
                    + "SQL State: " + e.getSQLState());
            return false;
        }
    }
    @Override
    public int deleteSingleStock(String ignored, String stock_id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL + DB, USER, PASSWORD)) {
            String stockSql = "DELETE FROM stock WHERE idproduct = ?";
            String productSql = "DELETE FROM products WHERE idproducts = ?";

            PreparedStatement productStatement = connection.prepareStatement(productSql);
            PreparedStatement stockStatement = connection.prepareStatement(stockSql);

            productStatement.setInt(1, Integer.parseInt(stock_id)); // Assuming stock_id is the product ID
            stockStatement.setInt(1, Integer.parseInt(stock_id));

            stockStatement.executeUpdate();
            int rowsAffected = productStatement.executeUpdate();

            if (rowsAffected == 0) {
                return 0;
            }

            return 1;
        } catch (SQLException e) {
            getLogger(MySQLDAO.class).atError().log("SQLException caught at deleteSingleStock(), check connection settings." + "\n"
                    + "Error Message: " + e.getMessage() + "\n"
                    + "SQL State: " + e.getSQLState());
            return 2;
        }
    }
    @Override
    public void deleteFullStock(String store_id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL + DB, USER, PASSWORD)) {
            String sql = "DELETE stock, products " +
                    "FROM stock " +
                    "JOIN products ON stock.idproduct = products.idproducts " +
                    "WHERE stock.idstore = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(store_id));
            statement.executeUpdate();
        } catch (SQLException e) {
            getLogger(MySQLDAO.class).atError().log("SQLException caught at deleteFullStock(), check connection settings." + "\n"
                    + "Error Message: " + e.getMessage() + "\n"
                    + "SQL State: " + e.getSQLState());
        }
    }
}
