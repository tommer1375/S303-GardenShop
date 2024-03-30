
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;

public class SQLDAO  {


    public String driver = "com.mysql.jdbc.Driver";

    public static Connection connectDB() {
        Connection connection;
        String host = "jdbc:mysql://localhost/";
        String user = "root";
        String pass = "1234";
        String bd = "Garden_Shop";

        System.out.println("Connecting...");

        try {
            connection = DriverManager.getConnection(host + bd, user, pass);
            System.out.println("Connecting to DB " + bd + ".");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void loadGardenShop(Connection connection, ArrayList<GardenShop> gardenshop) {
        String loadGarden = "SELECT * FROM stores";
        Statement st;
        ResultSet rs;
        int id;
        String name;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(loadGarden);
            while (rs.next()) {
                id = rs.getInt("idstores");
                name = rs.getString("name");

                GardenShop floristShop = new GardenShop(id, name);
                gardenshop.add(floristShop);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    //Mostrar el stock de un producto de una tienda
    public static void loadProducts(Connection connection, ArrayList<GardenShop> gardenshop) {
        String loadProducts = "SELECT p.idproducts, p.type, p.price, p.height, p.color, p.material, s.quantity" +
                "FROM stock s" +
                "JOIN products p ON s.idproduct = p.idproducts" +
                "WHERE s.idstore = (SELECT idstores FROM stores WHERE name = 'Garden_shop')";

        try (Statement stProducts = connection.createStatement();
             ResultSet rsProducts = stProducts.executeQuery(loadProducts)) {

            while (rsProducts.next()) {
                int idProduct = rsProducts.getInt("idproducts");
                String type = rsProducts.getString("type");
                double price = rsProducts.getDouble("price");
                double height = rsProducts.getDouble("height");
                String color = rsProducts.getString("color");
                String material = rsProducts.getString("material");
                int quantity = rsProducts.getInt("quantity");

                // Imprimir los detalles del producto
                System.out.println("Product ID: " + idProduct);
                System.out.println("Type: " + type);
                System.out.println("Price: " + price);
                System.out.println("Height: " + height);
                System.out.println("Color: " + color);
                System.out.println("Material: " + material);
                System.out.println("Quantity: " + quantity);
                System.out.println();
            }

        } catch (SQLException e) {
            System.out.println("Error to load products: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    //Importe total de ventas de una tienda
    public static void loadShoplSales(Connection connection, ArrayList<GardenShop> gardenshop) {
        String loadShopSales = "SELECT SUM(p.total) AS total_sales " +
                "FROM purchases p " +
                "JOIN tickets t ON p.idtickets = t.idtickets " +
                "JOIN stores s ON t.idstore = s.idstores " +
                "WHERE s.name = 'Garden_shop'";

        try (Statement stProducts = connection.createStatement();
             ResultSet rsProducts = stProducts.executeQuery(loadShopSales)) {

            while (rsProducts.next()) {
                double totalSales = rsProducts.getDouble("total_sales");

                System.out.println("Total sales in Garden Shop: $" + totalSales);
            }

        } catch (SQLException e) {
            System.out.println("Error to load shop sales: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //Valor de los productos de una tienda
    public static void loadTotalProducts(Connection connection, ArrayList<GardenShop> gardenshop) {
        String loadTotalProducts = "SELECT SUM(p.price * s.quantity) AS total_value"+
        "FROM stock s" +
        "JOIN products p ON s.idproduct = p.idproducts" +
        "JOIN stores st ON s.idstore = st.idstores" +
        "WHERE st.name = 'Garden Shop'";

        try (Statement stProducts = connection.createStatement();
             ResultSet rsProducts = stProducts.executeQuery(loadTotalProducts)) {
            while (rsProducts.next()) {
                double totalValue = rsProducts.getDouble("total_value");

                System.out.println("Total value of products in Garden Shop: $" + totalValue);
            }

        } catch (SQLException e) {
            System.out.println("Error to load products: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //Mostrar listado de todas la ventas
    public static void loadTotalSales(Connection connection, ArrayList<GardenShop> gardenshop) {
        String loadTotalSales = "SELECT t.idtickets, s.name AS store_name, p.type, p.price, p.height, p.color, p.material, pu.quantity, pu.total"+
        "FROM purchases pu" +
        "JOIN tickets t ON pu.idtickets = t.idtickets" +
        "JOIN products p ON pu.idproduct = p.idproducts" +
        "JOIN stores s ON t.idstore = s.idstores";

        try (Statement stProducts = connection.createStatement();
             ResultSet rsProducts = stProducts.executeQuery(loadTotalSales)) {

            while (rsProducts.next()) {
                int idTickets = rsProducts.getInt("idtickets");
                String storeName = rsProducts.getString("store_name");
                String productType = rsProducts.getString("type");
                double productPrice = rsProducts.getDouble("price");
                double productHeight = rsProducts.getDouble("height");
                String productColor = rsProducts.getString("color");
                String productMaterial = rsProducts.getString("material");
                int productQuantity = rsProducts.getInt("quantity");
                double productTotal = rsProducts.getDouble("total");

                // Imprimir los datos obtenidos
                System.out.println("Ticket ID: " + idTickets);
                System.out.println("Store Name: " + storeName);
                System.out.println("Product Type: " + productType);
                System.out.println("Product Price: " + productPrice);
                System.out.println("Product Height: " + productHeight);
                System.out.println("Product Color: " + productColor);
                System.out.println("Product Material: " + productMaterial);
                System.out.println("Product Quantity: " + productQuantity);
                System.out.println("Product Total: " + productTotal);
            }

            } catch(SQLException e){
                System.out.println("Error to load products: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }

    public static void createGardenShop(Connection connection, String name) {
        String insertGardenShop = "insert into stores (name) values ('" + name + "');";
        Statement st;

        try {
            st = connection.createStatement();
            st.executeUpdate(insertGardenShop);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);

        }

        public static void createTree (Connection connection,int type, double price, double height){

            String insertTree = "INSERT INTO products (type, price, height) VALUES ('" + type + "', " + price + ", " + height + ");";
            Statement st;
            try {
                st = connection.createStatement();
                st.executeUpdate(insertTree);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }

        }
        public static void createFlower (Connection connection,int type, double price, String color){
            String insertFlower = "INSERT INTO products (type, price, color) VALUES ('" + type + "', " + price + ", '" + color + "');";
            Statement st;
                try {
                    st = connection.createStatement();
                    st.executeUpdate(insertFlower);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    throw new RuntimeException(e);
                }
            }

        public static void createDecor (Connection connection, int type, double price, String material){

            String insertDecor = "INSERT INTO products (type, price, material) VALUES ('" + type + "', " + price + ", '" + material + "');";
            Statement st;
            try {
                st = connection.createStatement();
                st.executeUpdate(insertDecor);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }

        public static void createStock (Connection connection,int idstore, int idproduct, int quantity){

            String insertStock = "INSERT INTO stock (idstore, idproduct, quantity) VALUES ('" + idstore + "', " + idproduct + "," + quantity + ");";
            Statement st;
            try {
                st = connection.createStatement();
                st.executeUpdate(insertStock);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }

        public static void createPurchase (Connection connection,int idtickets, int idproduct, int quantity, double total){

            String insertPurchase = "INSERT INTO purchases (idtickets, idproduct, quantity, total) VALUES ('" + idtickets + "', " + idproduct + "," + quantity + ", " + total + ");";

            Statement st;
            try {
                st = connection.createStatement();
                st.executeUpdate(insertPurchase);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }

        public static void createTicket (Connection connection, int idstore){

            String insertTicket = "INSERT INTO tickets (idstore) VALUES (?)";
            Statement st;
            try {
                st = connection.createStatement();
                st.executeUpdate(insertTicket);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }

        public static void deleteProducts (Connection connection,int idproducts){
            String deleteProduct = "DELETE FROM products WHERE idproducts = '" + idproducts + "'";
            Statement st;

            try {
                st = connection.createStatement();
                st.executeUpdate(deleteProduct);
            } catch (SQLException e) {
                System.out.println("Error to delete product: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }

        public static void disconnectDB (Connection connection){
            try {
                connection.close();
                System.out.println("Disconnecting to DB...");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


