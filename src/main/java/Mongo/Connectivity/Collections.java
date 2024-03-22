package Mongo.Connectivity;

public enum Collections {
    STORES(0),
    TICKETS(1);

    private final int place;

    Collections(int place) {
        this.place = place;
    }

    public int getPlace() {
        return place;
    }
}
