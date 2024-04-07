package Mongo.Connectivity;

import Generic.DAO;
import Generic.classes.GardenShop;
import Generic.classes.Products;
import Generic.classes.Stock;
import Generic.classes.Tickets;
import Generic.Managers.Stores.GardenShopManager;
import Generic.Managers.Stores.stock.StockManager;
import Generic.Managers.Tickets.TicketManager;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientException;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.InsertOneOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public enum MongoDAO implements DAO {
    INSTANCE;

    private final Logger logger = LoggerFactory.getLogger(MongoDAO.class);
    private final MongoClientSettings mongoClientSettings;

    MongoDAO(){
        String conectionString = (MongoConfig.createDatabaseAndUserIfNotExist()) ? MongoConfig.getConnectionString() : MongoConfig.getSimpleConnectionString();
        ConnectionString connectionString = new ConnectionString(conectionString);

        mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        logger.atInfo().setMessage("Successfully logged into the server.").log();
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

            InsertOneResult result = stores.insertOne(gardenShop, options);
            if(result.wasAcknowledged()){
                System.out.println("Garden Shop Properly Created:" + GardenShopManager.createGardenShopFromDocument(gardenShop).toString());
            } else {
                logger.atError().log("Couldn't create garden shop, check MongoDB configuration.");
            }
        } catch (MongoClientException e){
            logger.atError().log("Error at MongoClient creation on MongoDAO.INSTANCE.createGardenShop()", e);
            System.exit(0);
        }
    }
    @Override
    public void createStock(String store_id, ArrayList<Stock> newStockList){
        try(MongoClient mongoClient = MongoClients.create(mongoClientSettings)){
            MongoDatabase mongoDatabase = mongoClient.getDatabase(MongoConfig.DATABASE);
            MongoCollection<Document> stores = mongoDatabase.getCollection(MongoConfig.Collections.STORES.name().toLowerCase());

            Document filter = new Document("_id", new ObjectId(store_id));
            Document command = new Document("$set", new Document("stock", newStockList));

            stores.updateOne(filter, command);

        } catch (MongoClientException e){
            logger.atError().log("Error at MongoClient creation on MongoDAO.INSTANCE.createStock()", e);
        }
    }
    @Override
    public int createSingleStock(String store_id, Stock stock){
        try(MongoClient mongoClient = MongoClients.create(mongoClientSettings)){
            MongoDatabase mongoDatabase = mongoClient.getDatabase(MongoConfig.DATABASE);
            MongoCollection<Document> stores = mongoDatabase.getCollection(MongoConfig.Collections.STORES.name().toLowerCase());

            Document filter = new Document("_id", new ObjectId(store_id));
            Document command = new Document("$push", new Document("stock", stock.getStockDocument()));

            stores.updateOne(filter, command);
            return 1;

        } catch (MongoClientException e){
            logger.atError().log("Error at MongoClient creation on MongoDAO.INSTANCE.createSingleStock()", e);
            return 2;
        }
    }
    @Override
    public void createTicket(String store_id, List<Products> products, double total) {
        try(MongoClient mongoClient = MongoClients.create(mongoClientSettings)){
            MongoDatabase mongoDatabase = mongoClient.getDatabase(MongoConfig.DATABASE);
            MongoCollection<Document> tickets = mongoDatabase.getCollection(MongoConfig.Collections.TICKETS.name().toLowerCase());

            List<Document> productsDocumentList = products.stream().map(Products::getProductDocument).toList();

            Document ticket = new Document()
                    .append("_id", new ObjectId())
                    .append("store_id", new ObjectId(store_id))
                    .append("products", new ArrayList<>(productsDocumentList))
                    .append("total", total);

            InsertOneOptions options = new InsertOneOptions()
                    .bypassDocumentValidation(false);

            tickets.insertOne(ticket, options);
        } catch (MongoClientException e){
            logger.atError().log("Error at MongoClient creation on MongoDAO.INSTANCE.createTicket()", e);
        }
    }

//    Read methods implemented
    @Override
    public List<GardenShop> readGardenShops() {
        try(MongoClient mongoClient = MongoClients.create(mongoClientSettings)){
            MongoDatabase mongoDatabase = mongoClient.getDatabase(MongoConfig.DATABASE);
            MongoCollection<Document> stores = mongoDatabase.getCollection(MongoConfig.Collections.STORES.name().toLowerCase());
            Bson filter = Filters.eq("status", "Active");

            return stores.find(filter)
                    .map(GardenShopManager::createGardenShopFromDocument)
                    .into(new ArrayList<>());
        } catch (MongoClientException e){
            logger.atError().log("Error at MongoClient creation on MongoDAO.INSTANCE.readGardenShops()", e);
            return null;
        }
    }
    @Override
    public GardenShop readGardenShop(String name){
        try(MongoClient mongoClient = MongoClients.create(mongoClientSettings)){
            MongoDatabase mongoDatabase = mongoClient.getDatabase(MongoConfig.DATABASE);
            MongoCollection<Document> stores = mongoDatabase.getCollection(MongoConfig.Collections.STORES.name().toLowerCase());

            Document gardenShopDocument = stores.find(new Document("name", name)).first();

            if(gardenShopDocument == null || gardenShopDocument.getString("status").equals("Inactive")){return null;}

            return GardenShopManager.createGardenShopFromDocument(gardenShopDocument);
        } catch (MongoClientException e){
            logger.atError().log("Error at MongoClient creation on MongoDAO.INSTANCE.readGardenShop()", e);
            return null;
        }
    }
    @Override
    public List<Stock> readShopStock(String gardenShop_id) {
        try(MongoClient mongoClient = MongoClients.create(mongoClientSettings)){
            MongoDatabase mongoDatabase = mongoClient.getDatabase(MongoConfig.DATABASE);
            MongoCollection<Document> stores = mongoDatabase.getCollection(MongoConfig.Collections.STORES.name().toLowerCase());

            return Objects.requireNonNull(stores.find(new Document("_id", new ObjectId(gardenShop_id))).first())
                    .getList("stock", Document.class).stream()
                    .map(StockManager::createStockFromDocument)
                    .collect(Collectors.toList());
        } catch (MongoClientException e){
            logger.atError().log("Error at MongoClient creation on MongoDAO.INSTANCE.readShopStock()", e);
            return null;
        }
    }
    @Override
    public List<Tickets> readTicketsFromEnteredStore(String store_id) {
        try(MongoClient mongoClient = MongoClients.create(mongoClientSettings)){
            MongoDatabase mongoDatabase = mongoClient.getDatabase(MongoConfig.DATABASE);
            MongoCollection<Document> tickets = mongoDatabase.getCollection(MongoConfig.Collections.TICKETS.name().toLowerCase());

            Document filter = new Document("store_id", new ObjectId(store_id));

            return tickets.find()
                    .filter(filter)
                    .map(TicketManager::createTicketFromDocument)
                    .into(new ArrayList<>());
        } catch (MongoClientException e){
            logger.atError().log("Error at MongoClient creation on MongoDAO.INSTANCE.readTicketsFromEnteredStore()", e);
            return null;
        }
    }

    //    Update methods implemented
    @Override
    public int updateStock(String store_id, Stock update) {
        try(MongoClient mongoClient = MongoClients.create(mongoClientSettings)){
            MongoDatabase mongoDatabase = mongoClient.getDatabase(MongoConfig.DATABASE);
            MongoCollection<Document> stores = mongoDatabase.getCollection(MongoConfig.Collections.STORES.name().toLowerCase());

            Document filter = new Document("_id", new ObjectId(store_id))
                    .append("stock.product_id", update.getProduct_id());
            Document command = new Document("$set", update.getStockDocument());

            UpdateResult updated = stores.updateOne(filter, command);

            if(updated.wasAcknowledged()){
                return 1;
            } else {
                return 2;
            }
        } catch (MongoClientException e){
            logger.atError().log("Error at MongoClient creation on MongoDAO.INSTANCE.updateStock()", e);
            return 0;
        }
    }
    @Override
    public void updateCurrentStockValue(String store_id, double newStockValue){
        try(MongoClient mongoClient = MongoClients.create(mongoClientSettings)){
            MongoDatabase mongoDatabase = mongoClient.getDatabase(MongoConfig.DATABASE);
            MongoCollection<Document> stores = mongoDatabase.getCollection(MongoConfig.Collections.STORES.name().toLowerCase());

            Document filter = new Document("_id", new ObjectId(store_id));
            Document command = new Document("$set", new Document("current_stock_value", newStockValue));

            stores.updateOne(filter, command);
        } catch (MongoClientException e){
            logger.atError().log("Error at MongoClient creation on MongoDAO.INSTANCE.updateCurrentStockValue()", e);
        }
    }
    @Override
    public void updateCurrentSalesValue(String store_id, double newSalesValue){
        try(MongoClient mongoClient = MongoClients.create(mongoClientSettings)){
            MongoDatabase mongoDatabase = mongoClient.getDatabase(MongoConfig.DATABASE);
            MongoCollection<Document> stores = mongoDatabase.getCollection(MongoConfig.Collections.STORES.name().toLowerCase());

            Document filter = new Document("_id", new ObjectId(store_id));
            Document command = new Document("$set", new Document("current_sales_value", newSalesValue));

            stores.updateOne(filter, command);
        } catch (MongoClientException e){
            logger.atError().log("Error at MongoClient creation on MongoDAO.INSTANCE.updateCurrentSalesValue()", e);
        }
    }

    //    Delete methods implemented
    @Override
    public boolean deleteGardenShop(String store_id) {
        try(MongoClient mongoClient = MongoClients.create(mongoClientSettings)){
            MongoDatabase mongoDatabase = mongoClient.getDatabase(MongoConfig.DATABASE);
            MongoCollection<Document> stores = mongoDatabase.getCollection(MongoConfig.Collections.STORES.name().toLowerCase());

            Document filter = new Document("_id", new ObjectId(store_id));
            Document update = new Document("status", "Inactive");
            Document command = new Document("$set", update);

            UpdateResult updated = stores.updateOne(filter, command);

            return updated.wasAcknowledged() && updated.getModifiedCount() > 0;
        } catch (MongoClientException e){
            logger.atError().log("Error at MongoClient creation on MongoDAO.INSTANCE.deleteGardenShop()", e);
            return false;
        }
    }
    @Override
    public int deleteSingleStock(String store_id, String stock_id) {
        try(MongoClient mongoClient = MongoClients.create(mongoClientSettings)){
            MongoDatabase mongoDatabase = mongoClient.getDatabase(MongoConfig.DATABASE);
            MongoCollection<Document> stores = mongoDatabase.getCollection(MongoConfig.Collections.STORES.name().toLowerCase());

            Document filter = new Document("_id", new ObjectId(store_id))
                    .append("stock.product_id", stock_id);

            if(stores.countDocuments(filter) == 0){
                return 0;
            }

            DeleteResult deleted = stores.deleteOne(filter);

            if(deleted.wasAcknowledged()){
                return 1;
            } else {
                return 2;
            }
        } catch (MongoClientException e){
            logger.atError().log("Error at MongoClient creation on MongoDAO.INSTANCE.deleteSingleStock()", e);
            return 2;
        }
    }
    @Override
    public void deleteFullStock(String store_id) {
        try(MongoClient mongoClient = MongoClients.create(mongoClientSettings)){
            MongoDatabase mongoDatabase = mongoClient.getDatabase(MongoConfig.DATABASE);
            MongoCollection<Document> stores = mongoDatabase.getCollection(MongoConfig.Collections.STORES.name().toLowerCase());

            Document filter = new Document("_id", new ObjectId(store_id));
            Document command = new Document("$unset", new Document("stock", ""));

            stores.updateOne(filter, command);
        } catch (MongoClientException e){
            logger.atError().log("Error at MongoClient creation on MongoDAO.INSTANCE.deleteFullStock()", e);
        }
    }
}