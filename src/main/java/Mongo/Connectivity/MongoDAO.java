package Mongo.Connectivity;

import Generic.DAO;
import Generic.classes.Tickets;
import Mongo.Managers.Stores.EnteredGardenShop;
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
        try (MongoClient mongoClient = MongoClients.create(mongoClientSettings)) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase("gardenShop");
            for (Collections collection : Collections.values()){
                collectionsList.add(mongoDatabase.getCollection(collection.name().toLowerCase()));
            }
        } catch (Exception e){
            System.out.println("Issue when connecting MongoClient.");
            System.exit(0);
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
    public void createStock(Document filter, List<Document> newStockList){
        collectionsList
                .get(Collections.STORES.getIndex())
                .updateOne(filter, new Document("stock", newStockList));
    }
    public void createSingleStock(Document stock){
        if(collectionsList
                .get(Collections.STORES.getIndex())
                .countDocuments(stock) > 0){
            System.out.println("At least one matching product in stock, to change one of it's qualities, use the \"Modify item from stock\" option.");
        } else {
            collectionsList
                    .get(Collections.STORES.getIndex())
                    .updateMany(EnteredGardenShop.INSTANCE.getSearchInfo()
                            , new Document("$push"
                            , new Document("stock", stock)));
        }
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
    public Document readGardenShop(String name){
        return collectionsList
                .get(Collections.STORES.getIndex())
                .find(new Document("name", name))
                .first();
    }
    @Override
    public List<Document> readShopStock(Document searchInfo) {
        List<Document> currentShopStock = new ArrayList<>();

        collectionsList
                .get(Collections.STORES.getIndex())
                .find(EnteredGardenShop.INSTANCE.getSearchInfo())
                .forEach(currentShopStock::add);

        return currentShopStock;
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

//    Delete methods implemented
    @Override
    public void deleteGardenShop() {

    }
    @Override
    public void deleteStock(Document filter, Document update) {
        collectionsList.get(Collections.STORES.getIndex())
                .updateOne(filter, update);
    }
}