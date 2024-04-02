
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Florist {
    private String name;
    private List<Product> stock;

    public Florist(String name) {
        this.name = name;
        this.stock = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }
    
    
    /**
	 * @return the stock
	 */
	public List<Product> getStock() {
		return stock;
	}

	public void addProduct(Product product) {
        stock.add(product);
    }

    public boolean removeProduct(String productName) {
        return stock.removeIf(product -> product.getName().equalsIgnoreCase(productName));
    }

    public Product getProductByName(String name) {
        for (Product product : stock) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }

    public void displayStock() {
        System.out.println("Stock for " + name + ": ");
        for (Product product : stock) {
            System.out.println(product);
        }
    }

    public void printStockWithQuantities() {
        System.out.println("Stock quantities for " + name + ": ");
        stock.stream().collect(Collectors.groupingBy(Product::getClass, Collectors.counting()))
                .forEach((product, quantity) -> System.out.println(product.getSimpleName() + ": " + quantity));
    }

    public double calculateTotalStockValue() {
        return stock.stream().mapToDouble(Product::getPrice).sum();
    }

   
}

