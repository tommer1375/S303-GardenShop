package Generic.classes.qualities;

public enum Height implements Quality {
    SMALL,
    MEDIUM,
    TALL;

    @Override
    public String getName() {
        return name();
    }
}
