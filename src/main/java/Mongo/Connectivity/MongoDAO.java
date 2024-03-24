package Mongo.Connectivity;

import Generic.DAO;
import Mongo.Managers.Stores.EnteredGardenShop;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.InsertOneOptions;
import com.mongodb.client.model.Projections;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
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
    public void createGardenShop(String name, ArrayList<Document> stock, double currentStockValue) {
        Document gardenShop = new Document("_id", new ObjectId())
                .append("name", name)
                .append("stock", stock)
                .append("current_stock_value", currentStockValue)
                .append("current_sales_value", 0.0)
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
    public void createSingleStock(Document filter, Document stock){
        if(collectionsList
                .get(Collections.STORES.getIndex())
                .countDocuments(filter) > 0){
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
        return collectionsList.get(Collections.STORES.getIndex())
                .find()
                .into(new ArrayList<>());
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
        return collectionsList
                .get(Collections.STORES.getIndex())
                .find(EnteredGardenShop.INSTANCE.getSearchInfo())
                .projection(Projections.include("stock"))
                .into(new ArrayList<>());
    }
    @Override
    public List<Document> readPastTickets() {
        return collectionsList
                .get(Collections.TICKETS.getIndex())
                .find()
                .filter(EnteredGardenShop.INSTANCE.getTicketFilter())
                .into(new ArrayList<>());
    }

//    Update methods implemented
    @Override
    public int updateStock(Document filter, Document update) {
        if(collectionsList.get(Collections.STORES.getIndex())
                   .countDocuments(filter) == 0){
            return 0;
        } else {
            UpdateResult result = collectionsList.get(Collections.STORES.getIndex())
                    .updateOne(filter, update);
            if (result.wasAcknowledged()) {
                return 1;
            } else{
                return 2;
            }
        }
    }

    //    Delete methods implemented
    @Override
    public void deleteGardenShop() {

    }

    @Override
    public int deleteSingleStock(Document filter) {
        if(collectionsList.get(Collections.STORES.getIndex())
                .countDocuments(filter) == 0){
            return 0;
        } else {
            DeleteResult result = collectionsList.get(Collections.STORES.getIndex())
                    .deleteOne(filter);
            if (result.wasAcknowledged()) {
                return 1;
            } else{
                return 2;
            }
        }
    }

    @Override
    public void deleteFullStock(Document filter) {
        collectionsList.get(Collections.STORES.getIndex())
                .updateOne(filter, new Document("$unset", new Document("stock", "")));
    }
}