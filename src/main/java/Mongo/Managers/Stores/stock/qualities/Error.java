package Mongo.Managers.Stores.stock.qualities;

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
