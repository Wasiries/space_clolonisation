public class Technology implements Upgradable {
    private String name;
    private int level;

    public Technology(String name) {
        this.name = name;
        this.level = 1;
    }

    public String get_name() {
        return name;
    }

    public int get_level() {
        return level;
    }

    public void upgrade() throws TechnologyException {
        if (level >= 5) {
            throw new TechnologyException("Technology " + name + " is already at maximum level");
        }
        level++;
        System.out.println(name + " upgraded to level " + level);
    }
}