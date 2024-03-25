
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

    }

    public static void createGardenShop (Connection connection, String name) {
        String insertGardenShop = "insert into stores (name) values ('" + name + "');";
        Statement st;

        try {
            st = connection.createStatement();
            st.executeUpdate(insertGardenShop);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);

        }

        public static void createTree (Connection connection, int type, double price, double height) {

            String insertTree = "insert into products (type, price, color) values ('" + type + "', " + price + ", " + height+ ");";
            Statement st;

            try {
                st = connection.createStatement();
                st.executeUpdate(insertTree);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }
        public static void createFlower (Connection connection, int type, double price, String color) {

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
        public static void createDecor (Connection connection, int type, double price, String material) {

            String insertDecor= "insert into products (type, price, color) values ('" + type + "', " + price + ", " +material+ ");";
            Statement st;

            try {
                st = connection.createStatement();
                st.executeUpdate(insertDecor);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }

        public static void deleteProducts (Connection connection, int id) {
            String deleteProduct = "DELETE FROM products WHERE idproducts = '" +id+"'";
            Statement st;

            try {
                st = connection.createStatement();
                st.executeUpdate(deleteProduct);
            } catch (SQLException e) {
                System.out.println("Error to delete product: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }

        public static void disconnectDB(Connection connection) {
            try {
                connection.close();
                System.out.println("Disconnecting to DB...");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


