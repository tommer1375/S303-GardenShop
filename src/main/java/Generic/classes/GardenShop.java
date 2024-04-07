package Generic.classes;

import Generic.Utilities.ConnectType;
import Generic.Managers.Stores.EnteredGardenShop;

import java.util.List;
import java.util.stream.Collectors;

public class GardenShop {
    private final String _id;
    private final String name;
    private final double currentStockValue;
    private final double currentSalesValue;
    private final List<Generic.classes.Stock> stockList;

    public GardenShop(Builder builder){
        this._id = builder._id;
        this.name = builder.name;
        this.currentStockValue = builder.currentStockValue;
        this.currentSalesValue = builder.currentSalesValue;
        this.stockList = builder.stockList;
    }
    public void enterGardenShop(ConnectType connectType){
        EnteredGardenShop.INSTANCE.enter(_id, name, currentStockValue, currentSalesValue, stockList, connectType);
    }
    @Override
    public String toString() {
        return "\n- Store_id: " + this._id
                + "\n\t-Name: " + this.name
                + this.stockList.stream().map(Stock::toString).collect(Collectors.joining("", "\n\t-Current Stock: ", ""))
                + "\n\t-Current Stock Value: " + this.currentStockValue + "€"
                + "\n\t-Current Sales Value: " + this.currentSalesValue + "€";
    }
    @SuppressWarnings("FieldCanBeLocal")
    public static class Builder{
        private String _id;
        private String name;
        private double currentStockValue;
        private double currentSalesValue;
        private List<Stock> stockList;

        public Builder(){

        }

        public Builder _id(String _id){
            this._id = _id;
            return this;
        }
        public Builder name(String name){
            this.name = name;
            return this;
        }
        public Builder currentStockValue(double currentStockValue){
            this.currentStockValue = currentStockValue;
            return this;
        }
        public Builder currentSalesValue(double currentSalesValue){
            this.currentSalesValue = currentSalesValue;
            return this;
        }
        public Builder stockList(List<Stock> stockList){
            this.stockList = stockList;
            return this;
        }
        public GardenShop build(){
            return new GardenShop(this);
        }
    }
}
