public class Planet extends SpaceObject implements Colonizable {
    private boolean isColonized;
    private int habitability;

    public Planet(String name, int habitability) {
        super(name);
        this.habitability = habitability;
        this.isColonized = false;
    }

    public boolean is_colonized() {
        return isColonized;
    }

    public void colonize(Colony colony) throws PlanetException {
        if (isColonized) {
            throw new PlanetException("Planet " + name + " is already colonized");
        }
        if (habitability < 30) {
            throw new PlanetException("Planet " + name + " is not habitable enough");
        }
        isColonized = true;
        System.out.println("Successfully colonized " + name);
    }
}