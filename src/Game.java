import java.util.*;

abstract class SpaceObject {
    protected String name;

    public SpaceObject(String name) {
        this.name = name;
    }

    public String get_name() {
        return name;
    }
}

interface Colonizable {
    void colonize(Colony colony) throws PlanetException;
}

interface Upgradable {
    void upgrade() throws TechnologyException;
}

interface Producible {
    void produce(Colony colony) throws PlanetException;
}

class Planet extends SpaceObject implements Colonizable {
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

class Spaceship extends SpaceObject implements Producible {
    private int cost;

    public Spaceship(String name, int cost) {
        super(name);
        this.cost = cost;
    }

    public void produce(Colony colony) throws PlanetException {
        if (colony.get_resources() < cost) {
            throw new PlanetException("Not enough resources to build " + name);
        }
        colony.deduct_resources(cost);
        System.out.println(name + " built");
    }
}

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

class Technology implements Upgradable {
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

class PlanetException extends Exception {
    public PlanetException(String message) {
        super(message);
    }
}

class TechnologyException extends Exception {
    public TechnologyException(String message) {
        super(message);
    }
}

class Main {
    private Colony current_colony;
    private List<Planet> planets;
    private List<Technology> available_tech;
    private List<Spaceship> available_ships;
    private Scanner scanner;
    private List<Colony> available_colonies;

    public Main() {
        scanner = new Scanner(System.in);
        initialize_game();
    }

    private void initialize_game() {
        planets = new ArrayList<>();
        available_colonies = new ArrayList<>();
        planets.add(new Planet("Earth", 1000));
        current_colony = new Colony("Earth Colony", planets.get(0));
        planets.add(new Planet("Mars", 65));
        planets.add(new Planet("Venus", 45));
        planets.add(new Planet("Titan", 55));
        planets.add(new Planet("Europa", 35));
        available_tech = new ArrayList<>();
        available_tech.add(new Technology("Energy Production"));
        available_tech.add(new Technology("Nuclear Reactor"));
        available_tech.add(new Technology("Hren Ego Znaet Chto Escho"));
        available_ships = new ArrayList<>();
        available_ships.add(new Spaceship("Scout Ship", 200));
        available_ships.add(new Spaceship("Colony Ship", 500));
        available_ships.add(new Spaceship("Cargo Ship", 300));
    }
    public void start_game() {
        while (true) {
            show_menu();
            int choice = get_int_input("Choose an option: ");
            try {
                if (choice == 0) {
                    System.out.println("Exit");
                    break;
                }
                handle_choice(choice);
            } catch (PlanetException | TechnologyException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    private void show_menu() {
        current_colony.display_status();
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Explore planets");
        System.out.println("2. Colonize planet");
        System.out.println("3. Research technology");
        System.out.println("4. Build spaceship");
        System.out.println("5. Gather resources");
        System.out.println("6. Traval to another colony");
        System.out.println("0. Exit");
    }
    private void handle_choice(int choice) throws PlanetException, TechnologyException {
        switch (choice) {
            case 1:
                explore_planets();
                break;
            case 2:
                colonize_planet();
                break;
            case 3:
                research_technology();
                break;
            case 4:
                build_spaceship();
                break;
            case 5:
                gather_resources();
                break;
            case 6:
                another_colony();
                break;
            default:
                System.out.println("Invalid option");
        }
    }
    private void explore_planets() {
        System.out.println("\n=== Available Planets ===");
        for (int i = 0; i < planets.size(); i++) {
            Planet p = planets.get(i);
            System.out.println((i + 1) + ". " + p.get_name() + " - Habitability: " + (p.is_colonized() ? "Colonized" : "Open"));
        }
    }
    private void colonize_planet() throws PlanetException {
        explore_planets();
        int choice = get_int_input("Choose planet to colonize: ") - 1;

        if (choice >= 0 && choice < planets.size() && this.available_ships.size() > 0) {
            Colony temp = new Colony(planets.get(choice).name + " colony", planets.get(choice));
            Planet target = planets.get(choice);
            target.colonize(temp);
            current_colony.add_resources(-100);
            available_colonies.add(temp);
        } else {
            System.out.println("Invalid planet selection");
        }
    }
    private void research_technology() throws TechnologyException {
        System.out.println("\n=== Available Technologies ===");
        for (int i = 0; i < available_tech.size(); i++) {
            Technology t = available_tech.get(i);
            System.out.println((i + 1) + ". " + t.get_name() + " - Level: " + t.get_level());
        }


        int choice = get_int_input("Choose technology to research: ") - 1;
        if (choice >= 0 && choice < available_tech.size()) {
            Technology tech = available_tech.get(choice);
            tech.upgrade();
            current_colony.add_technology(tech);
        } else {
            System.out.println("Invalid technology selection");
        }
    }
    private void build_spaceship() throws PlanetException {
        System.out.println("\n=== Available Spaceships ===");
        for (int i = 0; i < available_ships.size(); i++) {
            Spaceship s = available_ships.get(i);
            System.out.println((i + 1) + ". " + s.get_name());
        }
        int choice = get_int_input("Choose spaceship to build: ") - 1;
        if (choice >= 0 && choice < available_ships.size()) {
            Spaceship ship = available_ships.get(choice);
            ship.produce(current_colony);
            current_colony.add_spaceship(ship);
        } else {
            System.out.println("Invalid spaceship selection");
        }
    }
    private void gather_resources() {
        int gathered = new Random().nextInt(100) + 50;
        current_colony.add_resources(gathered);
        System.out.println("Gathered " + gathered + " resources");
    }
    private void another_colony() {
        if (available_colonies.size() == 0) {
            System.out.println("No available colonies");
            return;
        }
        System.out.println("\n=== Available Colonies ===");
        for (int i = 0; i < available_colonies.size(); i++) {
            System.out.println(available_colonies.get(i).get_name());
        }
        int temp = get_int_input("Enter number from 1 to " + available_colonies.size() + ": ");
        if (temp < 1 || temp > available_colonies.size()) {
            System.out.println("Wrong input");
            return;
        }
        current_colony = available_colonies.get(temp - 1);
    }
    private int get_int_input(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a number");
            scanner.next();
        }
        return scanner.nextInt();
    }
    public static void main(String[] args) {
        Main game = new Main();
        game.start_game();
    }
}
