import java.util.*;
class Colony {
    private String name;
    private int population;
    private int resources;
    private Planet planet;
    private List<Technology> technologies;
    private List<Spaceship> spaceships;

    public Colony(String name, Planet object) {
        this.name = name;
        this.population = 1000;
        this.resources = 500;
        this.planet = object;
        this.technologies = new ArrayList<>();
        this.spaceships = new ArrayList<>();
    }

    public String get_name() {
        return name;
    }

    public Planet get_planet() {
        return planet;
    }

    public void add_resources(int amount) {
        resources += amount;
    }

    public void deduct_resources(int amount) throws PlanetException {
        if (resources < amount) {
            throw new PlanetException("Insufficient resources");
        }
        resources -= amount;
    }

    public int get_resources() {
        return resources;
    }

    public void add_technology(Technology tech) {
        technologies.add(tech);
    }

    public void add_spaceship(Spaceship ship) {
        spaceships.add(ship);
    }

    public void display_status() {
        System.out.println("\n=== Colony Status ===");
        System.out.println("Name: " + name);
        System.out.println("Population: " + population);
        System.out.println("Resources: " + resources);
        System.out.println("Technologies: " + technologies.size());
        System.out.println("Spaceships: " + spaceships.size());
    }
}