
class Tree extends Product {
    private double height;

    public Tree(String name, double price, double height) {
        super(name, price);
        this.height = height;
    }

	@Override
	public String toString() {
		return "Tree " + super.toString() + ", height " + height + "cm";
	}

	
    
    
}