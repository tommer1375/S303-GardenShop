package Mongo.Managers.Stores.stock.qualities;

public enum Color implements Quality {
    RED,
    ORANGE,
    YELLOW,
    GREEN,
    BLUE,
    INDIGO,
    VIOLET,
    WHITE,
    PINK;

    @Override
    public String getName() {
        return name();
    }
}
