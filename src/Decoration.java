
class Decoration extends Product {
    enum Material {WOOD, PLASTIC}

    private Material material;

    public Decoration(String name, double price, Material material) {
        super(name, price);
        this.material = material;
    }

	@Override
	public String toString() {
		return "Decoration " + super.toString() + ", material " + material;
	}
    
    
}