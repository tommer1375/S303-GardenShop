
class Flower extends Product {
    private String color;

    public Flower(String name, double price, String color) {
        super(name, price);
        this.color = color;
    }

	@Override
	public String toString() {
		return "Flower " + super.toString() + ", color=" + color;
	}

	
    
    
    
}
