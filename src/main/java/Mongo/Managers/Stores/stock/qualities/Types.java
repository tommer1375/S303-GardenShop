package Mongo.Managers.Stores.stock.qualities;

public enum Types {
    TREE("Tree"),
    FLOWER("Flower"),
    DECORATION("Decoration"),
    ERROR("");

    private final String dbValue;


    Types(String dbValue) {
        this.dbValue = dbValue;
    }
    public String getDbValue() {
        return dbValue;
    }
}
