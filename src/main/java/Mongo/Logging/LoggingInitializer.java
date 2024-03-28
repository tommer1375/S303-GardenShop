package Mongo.Logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public class LoggingInitializer {
    private static final String PATH_TO_LOGBACK = "/logback.xml";
    public static boolean loadLoggingConfig(){
        try {
            LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
            context.reset();

            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(context);

            configurator.doConfigure(LoggingInitializer.class.getResourceAsStream(PATH_TO_LOGBACK));

            return true;
        } catch (Exception e){
            System.out.println("Path to logger not properly set.");
            return false;
        }
    }
    public static boolean initLogger() {
        try{
            LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
            Logger rootLogger = context.getLogger(Logger.ROOT_LOGGER_NAME);
            rootLogger.setLevel(Level.INFO);

            return true;
        } catch (Exception ignored){
            System.out.println("Path to logger not properly set.");
            return false;
        }
    }
}
