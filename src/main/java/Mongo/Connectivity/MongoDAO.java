package Mongo.Connectivity;

import Generic.DAO;
import Mongo.src.classes.GardenShop;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.util.List;

public class MongoDAO implements DAO {
    private ConnectionString connectionString;
    private MongoClientSettings mongoClientSettings;
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;


//    mongodb://it-academy-access:fneH5P95Yqmfnm@localhost:27017/?authMechanism=SCRAM-SHA-256&authSource=gardenShop
    public MongoDAO(){
        this.connectionString = new ConnectionString("mongodb://"
                + MongoConfig.USER + ":"
                + MongoConfig.PASSWORD + "@"
                + MongoConfig.HOST + ":"
                + MongoConfig.PORT + "/?authMechanism="
                + MongoConfig.AUTHMECHANISM + "&authSource="
                + MongoConfig.DATABASE);
        this.mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(this.connectionString)
                .build();
        this.mongoClient = MongoClients.create(this.mongoClientSettings);
        this.mongoDatabase = this.mongoClient.getDatabase("gardenShop");
    }


    @Override
    public List<GardenShop> seeGardenShops() {
        return null;
    }

    @Override
    public void addGardenShop() {

    }
}
