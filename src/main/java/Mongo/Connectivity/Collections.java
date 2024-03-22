package Mongo.Connectivity;

public enum Collections {
    STORES(0),
    TICKETS(1);

    private final int index;

    Collections(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
