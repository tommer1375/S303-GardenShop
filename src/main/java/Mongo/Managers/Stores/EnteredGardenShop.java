package Mongo.Managers.Stores;

public enum EnteredGardenShop {
    INSTANCE;

    private String _id;
    private String name;
    private double currentValue;

    EnteredGardenShop() {
        _id = "";
        name = "";
        currentValue = 0;
    }
    public void enter(String _id, String name, double currentValue){
        this._id = _id;
        this.name= name;
        this.currentValue = currentValue;
    }
    public void update(){

    }
    @Override
    public String toString() {
        return "Store_id: " + _id
                + "\n\tName: " + name
                + "\n\tCurrent Value: " + currentValue;
    }
}
