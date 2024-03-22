package Mongo;

import Generic.ConnectType;
import Mongo.src.Helper;

public class Main extends Helper {
    public static void main(String[] args) {
        connectWith(ConnectType.MONGO);
    }
}
