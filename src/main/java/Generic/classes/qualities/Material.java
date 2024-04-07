package Generic.classes.qualities;

public enum Material implements Quality {
    WOOD,
    PLASTIC;

    @Override
    public String getName() {
        return name();
    }
}
