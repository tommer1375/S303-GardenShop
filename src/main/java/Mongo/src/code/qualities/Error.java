package Mongo.src.code.qualities;

public enum Error implements Quality {
    TREE,
    FLOWER,
    DECORATION,
    ERROR;

    @Override
    public String getName() {
        return name();
    }
}
