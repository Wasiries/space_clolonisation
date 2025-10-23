import java.util.*;

// Абстрактный класс для космических объектов
abstract class SpaceObject {
    protected String name;

    public SpaceObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

// Интерфейсы
interface Colonizable {
    void colonize(Colony colony) throws PlanetException;
}

interface Upgradable {
    void upgrade() throws TechnologyException;
}

interface Producible {
    void produce(Colony colony) throws PlanetException;
}

// Класс планеты
class Planet extends SpaceObject implements Colonizable {
    private boolean isColonized;
    private int habitability; // от 0 до 100

    public Planet(String name, int habitability) {
        super(name);
        this.habitability = habitability;
        this.isColonized = false;
    }

    public boolean isColonized() {
        return isColonized;
    }

    @Override
    public void colonize(Colony colony) throws PlanetException {
        if (isColonized) {
            throw new PlanetException("Planet " + name + " is already colonized!");
        }
        if (habitability < 30) {
            throw new PlanetException("Planet " + name + " is not habitable enough!");
        }
        isColonized = true;
        System.out.println("Successfully colonized " + name + "!");
    }
}

class Spaceship extends SpaceObject implements Producible {
    private int cost;

    public Spaceship(String name, int cost) {
        super(name);
        this.cost = cost;
    }

    @Override
    public void produce(Colony colony) throws PlanetException {
        if (colony.getResources() < cost) {
            throw new PlanetException("Not enough resources to build " + name);
        }
        colony.deductResources(cost);
        System.out.println(name + " built successfully!");
    }
}

class Colony {
    private String name;
    private int population;
    private int resources;
    private List<Technology> technologies;
    private List<Spaceship> spaceships;

    public Colony(String name) {
        this.name = name;
        this.population = 1000;
        this.resources = 500;
        this.technologies = new ArrayList<>();
        this.spaceships = new ArrayList<>();
    }

    public void addResources(int amount) {
        resources += amount;
    }

    public void deductResources(int amount) throws PlanetException {
        if (resources < amount) {
            throw new PlanetException("Insufficient resources");
        }
        resources -= amount;
    }

    public int getResources() {
        return resources;
    }

    public void addTechnology(Technology tech) {
        technologies.add(tech);
    }

    public void addSpaceship(Spaceship ship) {
        spaceships.add(ship);
    }

    public void displayStatus() {
        System.out.println("\n=== Colony Status ===");
        System.out.println("Name: " + name);
        System.out.println("Population: " + population);
        System.out.println("Resources: " + resources);
        System.out.println("Technologies: " + technologies.size());
        System.out.println("Spaceships: " + spaceships.size());
    }
}

// Класс технологии
class Technology implements Upgradable {
    private String name;
    private int level;

    public Technology(String name) {
        this.name = name;
        this.level = 1;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public void upgrade() throws TechnologyException {
        if (level >= 5) {
            throw new TechnologyException("Technology " + name + " is already at maximum level!");
        }
        level++;
        System.out.println(name + " upgraded to level " + level);
    }
}

// Класс ресурса
class Resource {
    private String name;
    private int amount;

    public Resource(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }
}


// Исключения
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

// Основной класс игры
class Main {
    private Colony currentColony;
    private List<Planet> planets;
    private List<Technology> availableTech;
    private List<Spaceship> availableShips;
    private Scanner scanner;

    public Main() {
        scanner = new Scanner(System.in);
        initializeGame();
    }

    private void initializeGame() {
        // Создаем начальную колонию на Земле
        currentColony = new Colony("Earth Colony");

        // Инициализируем планеты
        planets = new ArrayList<>();
        planets.add(new Planet("Mars", 65));
        planets.add(new Planet("Venus", 45));
        planets.add(new Planet("Titan", 55));
        planets.add(new Planet("Europa", 35));

        // Инициализируем технологии
        availableTech = new ArrayList<>();
        availableTech.add(new Technology("Energy Production"));
        availableTech.add(new Technology("Life Support"));
        availableTech.add(new Technology("Propulsion"));

        // Инициализируем корабли
        availableShips = new ArrayList<>();
        availableShips.add(new Spaceship("Scout Ship", 200));
        availableShips.add(new Spaceship("Colony Ship", 500));
        availableShips.add(new Spaceship("Cargo Ship", 300));
    }

    public void startGame() {
        System.out.println("Welcome to Space Colonization Game!");
        System.out.println("You start with a colony on Earth.");

        while (true) {
            showMenu();
            int choice = getIntInput("Choose an option: ");

            try {
                if (choice == 0) {
                    System.out.println("Thanks for playing!");
                    break;
                }
                handleChoice(choice);
            } catch (PlanetException | TechnologyException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void showMenu() {
        currentColony.displayStatus();
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Explore planets");
        System.out.println("2. Colonize planet");
        System.out.println("3. Research technology");
        System.out.println("4. Build spaceship");
        System.out.println("5. Gather resources");
        System.out.println("0. Exit");
    }

    private void handleChoice(int choice) throws PlanetException, TechnologyException {
        switch (choice) {
            case 1:
                explorePlanets();
                break;
            case 2:
                colonizePlanet();
                break;
            case 3:
                researchTechnology();
                break;
            case 4:
                buildSpaceship();
                break;
            case 5:
                gatherResources();
                break;
            default:
                System.out.println("Invalid option!");
        }
    }

    private void explorePlanets() {
        System.out.println("\n=== Available Planets ===");
        for (int i = 0; i < planets.size(); i++) {
            Planet p = planets.get(i);
            System.out.println((i + 1) + ". " + p.getName() +
                    " - Habitability: " + (p.isColonized() ? "Colonized" : "Open"));
        }
    }

    private void colonizePlanet() throws PlanetException {
        explorePlanets();
        int choice = getIntInput("Choose planet to colonize: ") - 1;

        if (choice >= 0 && choice < planets.size()) {
            Planet target = planets.get(choice);
            target.colonize(currentColony);
            currentColony.addResources(-100); // Cost of colonization
        } else {
            System.out.println("Invalid planet selection!");
        }
    }


    private void researchTechnology() throws TechnologyException {
        System.out.println("\n=== Available Technologies ===");
        for (int i = 0; i < availableTech.size(); i++) {
            Technology t = availableTech.get(i);
            System.out.println((i + 1) + ". " + t.getName() + " - Level: " + t.getLevel());
        }

        int choice = getIntInput("Choose technology to research: ") - 1;
        if (choice >= 0 && choice < availableTech.size()) {
            Technology tech = availableTech.get(choice);
            tech.upgrade();
            currentColony.addTechnology(tech);
        } else {
            System.out.println("Invalid technology selection!");
        }
    }

    private void buildSpaceship() throws PlanetException {
        System.out.println("\n=== Available Spaceships ===");
        for (int i = 0; i < availableShips.size(); i++) {
            Spaceship s = availableShips.get(i);
            System.out.println((i + 1) + ". " + s.getName());
        }

        int choice = getIntInput("Choose spaceship to build: ") - 1;
        if (choice >= 0 && choice < availableShips.size()) {
            Spaceship ship = availableShips.get(choice);
            ship.produce(currentColony);
            currentColony.addSpaceship(ship);
        } else {
            System.out.println("Invalid spaceship selection!");
        }
    }

    private void gatherResources() {
        int gathered = new Random().nextInt(100) + 50;
        currentColony.addResources(gathered);
        System.out.println("Gathered " + gathered + " resources!");
    }

    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a number!");
            scanner.next();
        }
        return scanner.nextInt();
    }

    public static void main(String[] args) {
        Main game = new Main();
        game.startGame();
    }
}
