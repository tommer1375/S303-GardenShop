package Generic.classes;

public enum Types {
    TREE("Tree"),
    FLOWER("Flower"),
    DECORATION("Decoration");

    private final String dbValue;


    Types(String dbValue) {
        this.dbValue = dbValue;
    }
    public String getDbValue() {
        return dbValue;
    }
}
