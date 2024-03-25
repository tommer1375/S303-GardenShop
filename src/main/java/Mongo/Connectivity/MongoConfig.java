package Mongo.Connectivity;

public class MongoConfig {
    private static final String USER = "it-academy-access";
    private static final String PASSWORD = "fneH5P95Yqmfnm";
    private static final String HOST = "localhost";
    private static final String PORT = "27017";
    private static final boolean IS_AUTH_APPLIED = false;
    private static final String AUTHMECHANISM = "SCRAM-SHA-256";
    private static final String DATABASE = "gardenShop";

    public static String getConnectionString(){
        return "mongodb://" + MongoConfig.USER
                + ":" + MongoConfig.PASSWORD
                + "@" + MongoConfig.HOST
                + ":" + MongoConfig.PORT
                + ((MongoConfig.IS_AUTH_APPLIED) ? "/?authMechanism=" + MongoConfig.AUTHMECHANISM + "&authSource=" : "/?authSource=")
                + MongoConfig.DATABASE;
    }
}
