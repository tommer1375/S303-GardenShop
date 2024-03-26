package Mongo.Connectivity;

import Generic.DAO;
import Generic.classes.GardenShop;
import Generic.classes.Stock;
import Mongo.Managers.MongoUtilities;
import Mongo.Managers.Stores.EnteredGardenShop;
import Mongo.Managers.Stores.stock.StockManager;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientException;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.InsertOneOptions;
import com.mongodb.client.model.Projections;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public enum MongoDAO implements DAO {
    INSTANCE;

    private final Logger logger = LoggerFactory.getLogger(MongoDAO.class);
    private final MongoClientSettings mongoClientSettings;

    MongoDAO(){
        ConnectionString connectionString = new ConnectionString(MongoConfig.getConnectionString());

        mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        logger.atInfo().log("Connection made without any errors for connection string:\n"
                    + MongoConfig.getConnectionString());
    }

    //    Create methods implemented
    @Override
    public void createGardenShop(String name, ArrayList<Stock> stockList, double currentStockValue) {
        try(MongoClient mongoClient = MongoClients.create(mongoClientSettings)){
            MongoDatabase mongoDatabase = mongoClient.getDatabase(MongoConfig.DATABASE);
            MongoCollection<Document> stores = mongoDatabase.getCollection(MongoConfig.Collections.STORES.name().toLowerCase());

            ArrayList<Document> stockDocumentList = stockList.stream()
                    .map(Stock::getStockDocument)
                    .collect(Collectors.toCollection(ArrayList::new));

            Document gardenShop = new Document("_id", new ObjectId())
                    .append("name", name)
                    .append("stock", stockDocumentList)
                    .append("current_stock_value", currentStockValue)
                    .append("current_sales_value", 0.0)
                    .append("status", "Active");

            InsertOneOptions options = new InsertOneOptions()
                    .bypassDocumentValidation(false);

            stores.insertOne(gardenShop, options);
            logger.atInfo().log("Garden Shop Properly Created:\n" + MongoUtilities.extractDocumentDescription(gardenShop));
        } catch (MongoClientException e){
            logger.atError().log("Error at MongoClient creation on MongoDAO.INSTANCE.createGardenShop()", e);
        }
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
    public void createTicket(ObjectId _id, ObjectId store_id, List<Document> products, double total) {
        Document ticket = new Document()
                .append("_id", _id)
                .append("store_id", store_id)
                .append("products", new ArrayList<>(products))
                .append("total", total);

        InsertOneOptions options = new InsertOneOptions()
                .bypassDocumentValidation(false);

        collectionsList.get(Collections.TICKETS.getIndex()).insertOne(ticket, options);
    }

//    Read methods implemented
    @Override
    public List<GardenShop> readGardenShops() {
        try(MongoClient mongoClient = MongoClients.create(mongoClientSettings)){
            MongoDatabase mongoDatabase = mongoClient.getDatabase(MongoConfig.DATABASE);
            MongoCollection<Document> stores = mongoDatabase.getCollection(MongoConfig.Collections.STORES.name().toLowerCase());


        } catch (MongoClientException e){
            logger.atError().log("Error at MongoClient creation on MongoDAO.INSTANCE.readGardenShops()", e);
            return null;
        }
        return collectionsList.get(Collections.STORES.getIndex())
                .find()
                .map(StockManager::createStockFromDocument)
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