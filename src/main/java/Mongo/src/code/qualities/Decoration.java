package Mongo.src.code.qualities;

public enum Decoration implements Quality {
    WOOD,
    PLASTIC;

    @Override
    public String getName() {
        return name();
    }
}
