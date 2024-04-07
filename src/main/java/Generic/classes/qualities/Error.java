package Generic.classes.qualities;

public enum Error implements Quality {
    TREE,
    FLOWER,
    DECORATION;

    @Override
    public String getName() {
        return name();
    }
}
