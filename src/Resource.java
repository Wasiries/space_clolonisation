class Resource {
    private String name;
    private int amount;

    public Resource(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public String get_name() {
        return name;
    }

    public int get_amount() {
        return amount;
    }
}