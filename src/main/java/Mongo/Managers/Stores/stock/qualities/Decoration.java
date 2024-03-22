package Mongo.Managers.Stores.stock.qualities;

public enum Decoration implements Quality {
    WOOD,
    PLASTIC;

    @Override
    public String getName() {
        return name();
    }
}
