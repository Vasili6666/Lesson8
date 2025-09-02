package enums;

public enum FilterOptions {
    NAME_A_TO_Z("az"),
    NAME_Z_TO_A("za"),
    PRICE_LOW_TO_HIGH("lohi"),
    PRICE_HIGH_TO_LOW("hilo");

    private final String value;

    FilterOptions(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name() + " (" + value + ")";
    }
}