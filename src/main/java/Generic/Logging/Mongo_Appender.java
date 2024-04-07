package Generic.Logging;

import Mongo.Connectivity.MongoConfig;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Date;

@SuppressWarnings("FieldCanBeLocal")
public class Mongo_Appender extends UnsynchronizedAppenderBase<ILoggingEvent> {
    private final String uri = MongoConfig.getConnectionString();
    private final  String databaseName = MongoConfig.DATABASE;
    private final String collectionName = "logs";

    private MongoClient mongoClient;
    private MongoCollection<Document> collection;

    @Override
    public void start() {
        mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        collection = database.getCollection(collectionName);

        super.start();
    }

    @Override
    public void stop() {
        if (mongoClient != null) {
            mongoClient.close();
        }
        super.stop();
    }

    @Override
    protected void append(ILoggingEvent event) {
        if (!isStarted()) {
            return;
        }

        Date timestamp = new Date(event.getTimeStamp());
        String thread = event.getThreadName();
        String level = event.getLevel().toString();
        String loggerName = event.getLoggerName();
        String message = event.getMessage();

        Document log = new Document()
                .append("Time", timestamp)
                .append("Thread", thread)
                .append("Level", level)
                .append("Logger", loggerName)
                .append("Message", message);

        collection.insertOne(log);
    }
}