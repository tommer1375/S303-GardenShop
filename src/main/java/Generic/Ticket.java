import java.util.*;
public class Ticket {

        private Integer idTicket;
        private List<Product> productList;
        private double totalPrice;

        public Ticket(int idTicket) {
            this.idTicket=idTicket;
            this.productList = new ArrayList<>();
            this.totalPrice = 0.0;
        }
        public void addProduct(Product product) {
            productList.add(product);
            totalPrice += product.getPrice();
        }

        public List<Product> getProductList() {
            return productList;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void printTicket() {
            System.out.println("TICKET Purchase");
            for (Product product : productList) {
                System.out.println("Product: " + product.getName() + " - Price: " + product.getPrice());
            }
            System.out.println("Total: " + totalPrice);

        }
    }