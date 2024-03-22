package Mongo.Connectivity;

import Generic.DAO;
import Generic.classes.Products;
import Generic.classes.Tickets;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.InsertOneOptions;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public enum MongoDAO implements DAO {
    INSTANCE;
    private final MongoClient mongoClient;
    private final MongoDatabase mongoDatabase;
    private final List<MongoCollection<Document>> collectionsList = new ArrayList<>();

    MongoDAO(){
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
        for (Collections collection : Collections.values()){
            collectionsList.add(this.mongoDatabase.getCollection(collection.name().toLowerCase()));
        }
    }

//    Create methods implemented
    @Override
    public void createGardenShop(String name, ArrayList<Document> stock, double currentValue) {
        Document gardenShop = new Document("_id", new ObjectId())
                .append("name", name)
                .append("stock", stock)
                .append("current_value", currentValue)
                .append("status", "Active");

        InsertOneOptions options = new InsertOneOptions()
                .bypassDocumentValidation(false);

        collectionsList.get(Collections.STORES.getIndex()).insertOne(gardenShop, options);
    }
    @Override
    public void createTicket() {

    }

//    Read methods implemented
    @Override
    public List<Document> readGardenShops() {
        List<Document> gardenShopsList = new ArrayList<>();
        FindIterable<Document> stores = collectionsList.get(Collections.STORES.getIndex()).find();

        stores.forEach(gardenShopsList::add);

        return gardenShopsList;
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