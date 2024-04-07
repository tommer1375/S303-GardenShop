package Generic.classes.qualities;

public enum Types {
    TREE("Tree", 0),
    FLOWER("Flower", 1),
    DECORATION("Decoration", 2),
    ERROR("", 0);

    private final String mongoValue;
    private final int mySqlValue;


    Types(String dbValue, int mySqlValue) {
        this.mongoValue = dbValue;
        this.mySqlValue = mySqlValue;
    }
    public String getMongoValue() {
        return mongoValue;
    }
    public int getMySqlValue() {
        return mySqlValue;
    }
}
