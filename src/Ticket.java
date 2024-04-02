import java.util.ArrayList;
import java.util.List;

class Ticket {
    private List<Product> products;
    private double total;

    public Ticket() {
        this.products = new ArrayList<>();
        this.total = 0.0;
    }

    public void addProduct(Product product) {
        products.add(product);
        total += product.getPrice();
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "Ticket " +
                "products: " + products +
                ", total: " + total +
                "Euros";
    }
}