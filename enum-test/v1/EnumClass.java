public enum EnumClass {
    DOUBLE(2.0),
    TRIPLE(3.0);

    private final double multiplier;

    EnumClass(double multiplier) {
        this.multiplier = multiplier;
    }

    Double applyMultiplier(Double value) {
        return multiplier * value;
    }

}
