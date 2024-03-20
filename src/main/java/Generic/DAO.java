package Generic;

import Mongo.src.classes.GardenShop;

import java.util.List;

public interface DAO {
    List<GardenShop> seeGardenShops();
    void addGardenShop();
}
