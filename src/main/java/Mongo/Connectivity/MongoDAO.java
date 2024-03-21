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


    @Override
    public List<GardenShop> seeGardenShops() {
        return null;
    }
    @Override
    public List<Products> seeShopStock() {
        return null;
    }
    @Override
    public String seeShopValue() {
        return null;
    }
    @Override
    public String seeSalesValue() {
        return null;
    }
    @Override
    public List<Tickets> seeOldPurchases() {
        return null;
    }
    @Override
    public void addGardenShop() {

    }
    @Override
    public void modifyGardenShop() {

    }
    @Override
    public void removeGardenShop() {

    }
    @Override
    public void addStock() {

    }
    @Override
    public void modifyStock() {

    }
    @Override
    public void removeStock() {

    }
    @Override
    public void addTicket() {

    }
}