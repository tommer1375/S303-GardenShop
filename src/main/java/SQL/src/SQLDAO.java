
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;

public class SQLDAO implements DAO {


    public String driver = "com.mysql.jdbc.Driver";

    public static Connection connectDB() {
        Connection connection;
        String host = "jdbc:mysql://localhost/";
        String user = "root";
        String pass = "1234";
        String bd = "GardenShop";

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
    public static void loadProducts(Connection connection, ArrayList<GardenShop> gardenshop) { oducto de una tienda
        String loadProducts = "SELECT p.idproducts, p.type, p.price, p.height, p.color, p.material, s.quantity" +
                "FROM stock s" +
                "JOIN products p ON s.idproduct = p.idproducts" +
                "WHERE s.idstore = (SELECT idstores FROM stores WHERE name = 'gardenshop')";

        try (Statement stProducts = connection.createStatement();
             ResultSet rsProducts = stProducts.executeQuery(loadProducts)) {


        } catch (SQLException e) {
            System.out.println("Error to load products: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //Importe total de ventas de una tienda
    public static void loadShoplSales(Connection connection, ArrayList<GardenShop> gardenshop) {de una tienda
        String loadShopSales = "SELECT SUM(p.total) AS total_sales" +
                "FROM purchases p" +
                "JOIN tickets t ON p.idtickets = t.idtickets" +
                "JOIN stores s ON t.idstore = s.idstores" +
                "WHERE s.name = 'gardenshop'";

        try (Statement stProducts = connection.createStatement();
             ResultSet rsProducts = stProducts.executeQuery(loadShopSales)) {


        } catch (SQLException e) {
            System.out.println("Error to load products: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    //Valor de los productos de una tienda
    public static void loadTotalProducts(Connection connection, ArrayList<GardenShop> gardenshop) {
        String loadTotalProducts = "SELECT SUM(p.price * s.quantity) AS total_value"+
        "FROM stock s"+
        "JOIN products p ON s.idproduct = p.idproducts"+
        "JOIN stores st ON s.idstore = st.idstores"+
        "WHERE st.name = 'Garden Shop'";

        try (Statement stProducts = connection.createStatement();
             ResultSet rsProducts = stProducts.executeQuery(loadTotalProducts)) {


        } catch (SQLException e) {
            System.out.println("Error to load products: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //Mostrar listado de todas la ventas
    public static void loadTotalSales(Connection connection, ArrayList<GardenShop> gardenshop) {
        String loadTotalSales = "SELECT t.idtickets, s.name AS store_name, p.type, p.price, p.height, p.color, p.material, pu.quantity, pu.total"+
        "FROM purchases pu"+
        "JOIN tickets t ON pu.idtickets = t.idtickets"+
        "JOIN products p ON pu.idproduct = p.idproducts"+
        "JOIN stores s ON t.idstore = s.idstores";

        try (Statement stProducts = connection.createStatement();
             ResultSet rsProducts = stProducts.executeQuery(loadTotalSales)) {


        } catch (SQLException e) {
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

            String insertTree = "insert into products (type, price, color) values ('" + type + "', " + price + ", " + height + ");";
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

            String insertFlower = "insert into products (type, price, color) values ('" + type + "', " + price + ", " + color + ");";
            Statement st;

            try {
                st = connection.createStatement();
                st.executeUpdate(insertFlower);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }
        public static void createDecor (Connection connection,int type, double price, String material){

            String insertDecor = "insert into products (type, price, color) values ('" + type + "', " + price + ", " + material + ");";
            Statement st;

            try {
                st = connection.createStatement();
                st.executeUpdate(insertDecor);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }

        public static void deleteProducts (Connection connection,int id){
            String deleteProduct = "DELETE FROM products WHERE idproducts = '" + id + "'";
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


