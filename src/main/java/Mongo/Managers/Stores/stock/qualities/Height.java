package Mongo.Managers.Stores.stock.qualities;

public enum Height implements Quality {
    SMALL,
    MEDIUM,
    TALL;

    @Override
    public String getName() {
        return name();
    }
}
