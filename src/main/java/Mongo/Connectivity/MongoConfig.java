package Mongo.Connectivity;

import com.mongodb.MongoClientException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoIterable;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoConfig {
    private static final Logger logger = LoggerFactory.getLogger(MongoConfig.class);
    private static final String USER = "it-academy-access";
    private static final String PASSWORD = "fneH5P95Yqmfnm";
    private static final String HOST = "localhost";
    private static final String PORT = "27017";
    private static final boolean IS_AUTH_APPLIED = false;
    private static final String AUTHMECHANISM = "SCRAM-SHA-256";
    public static final String DATABASE = "gardenShop";
    public enum Collections{
        STORES,
        TICKETS,
        LOGS
    }

    public static String getConnectionString(){
        return "mongodb://" + MongoConfig.USER
                + ":" + MongoConfig.PASSWORD
                + "@" + MongoConfig.HOST
                + ":" + MongoConfig.PORT
                + ((MongoConfig.IS_AUTH_APPLIED) ? "/?authMechanism=" + MongoConfig.AUTHMECHANISM + "&authSource=" : "/?authSource=")
                + MongoConfig.DATABASE;
    }
    public static String getSimpleConnectionString(){
        return "mongodb://" + MongoConfig.HOST + ":" + MongoConfig.PORT + "/";
    }
    public static boolean createDatabaseAndUserIfNotExist() {
        try (MongoClient mongoClient = MongoClients.create(getSimpleConnectionString())) {

            MongoIterable<String> databaseNames = mongoClient.listDatabaseNames();
            for (String databaseName : databaseNames) {
                if (databaseName.equals(DATABASE)) {
                    return true;
                }
            }

            mongoClient.getDatabase(DATABASE).createCollection(Collections.TICKETS.name().toLowerCase());
            mongoClient.getDatabase(DATABASE).createCollection(Collections.STORES.name().toLowerCase());
            mongoClient.getDatabase(DATABASE).createCollection(Collections.LOGS.name().toLowerCase());

            Document roles = new Document("role", "readWrite").append("db", DATABASE);
            Document command = new Document("createUser", USER).append("pwd", PASSWORD).append("roles", java.util.Collections.singletonList(roles));

            Document result = mongoClient.getDatabase(DATABASE).runCommand(command);
            if(result.equals(new Document("ok", 1.0))){
                return true;
            }
        } catch (MongoClientException e) {
            logger.atError().log("Error at MongoConfig.createDatabaseAndUserIfNotExist(): " + e.getMessage());
            return false;
        }
        return false;
    }
}
