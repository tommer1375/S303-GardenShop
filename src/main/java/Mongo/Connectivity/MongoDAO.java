package Mongo.Connectivity;

import Generic.DAO;
import Generic.classes.GardenShop;
import Generic.classes.Products;
import Generic.classes.Tickets;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.util.List;

public class MongoDAO implements DAO {
    private final MongoClient mongoClient;
    private final MongoDatabase mongoDatabase;

    public MongoDAO(){
        ConnectionString connectionString = new ConnectionString("mongodb://"
                + MongoConfig.USER + ":"
                + MongoConfig.PASSWORD + "@"
                + MongoConfig.HOST + ":"
                + MongoConfig.PORT + "/?authMechanism="
                + MongoConfig.AUTHMECHANISM + "&authSource="
                + MongoConfig.DATABASE);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        this.mongoClient = MongoClients.create(mongoClientSettings);
        this.mongoDatabase = this.mongoClient.getDatabase("gardenShop");
    }


//    Create methods implemented
    @Override
    public void createGardenShop() {

    }
    @Override
    public void createStock() {

    }
    @Override
    public void createTicket() {

    }

//    Read methods implemented
    @Override
    public List<GardenShop> readGardenShops() {
        return null;
    }
    @Override
    public List<Products> readShopStock() {
        return null;
    }
    @Override
    public String readShopValue() {
        return null;
    }
    @Override
    public String readSalesValue() {
        return null;
    }
    @Override
    public List<Tickets> readOldPurchases() {
        return null;
    }

//    Update methods implemented
    @Override
    public void updateGardenShop() {

    }
    @Override
    public void updateStock() {

    }

//    Delete methods impelemted
    @Override
    public void deleteGardenShop() {

    }
    @Override
    public void deleteStock() {

    }
}