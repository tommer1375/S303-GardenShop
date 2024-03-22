package Mongo.src.code.qualities;

import Generic.Input;

public enum Height implements Quality {
    SMALL,
    MEDIUM,
    TALL;

    @Override
    public String getName() {
        return name();
    }
}
