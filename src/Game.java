
 import java.util.*;

// interface Upgradable {
//     boolean can_upgrade();
//     int get_level();
//     void upgrade();
//     int upgrade_cost();
//     void downgrade();
// }

// interface EnergyActions {
//     int extract(int energy_);
//     void insert(int energy_);
// }

// class Planet {
//     private String name;
//     private boolean is_colonized;
//     private Colony colony;
//     public Planet(String name) {
//         this.name = name;
//         this.colony = null;
//         this.is_colonized = false;
//     }
//     public void colonize(Colony colony) {
//         this.is_colonized = true;
//         this.colony = colony;
//         System.out.println("Планета " + name + " колонизирована");
//     }
//     public Colony get_colony() {
//         return this.colony;
//     }
//     public String get_name() { 
//         return name; 
//     }
//     public boolean is_colonized() { 
//         return is_colonized; 
//     }
// }

// abstract class EnergyStorage implements Upgradable, EnergyActions {
//     protected int capacity;
//     protected int level;
//     protected int energy_level;
//     public int get_energy_level() {
//         return this.energy_level;
//     }
//     public int get_capacity() {
//         return this.capacity;
//     }
//     public int get_level() {
//         return this.level;
//     }
//     public int extract(int energy_) {
//         int result = Math.min(this.energy_level, energy_);
//         this.energy_level = this.energy_level - result;
//         return result;
//     }
//     public void insert(int energy_) {
//         this.energy_level = Math.min(this.capacity, this.energy_level + energy_);
//     }
//     public boolean can_upgrade() {
//         if (this.level >= 5) {
//             return false;
//         }
//         if (this.energy_level < this.capacity) {
//             return false;
//         }
//         return true;
//     }
//     public void upgrade() {
//         if (this.can_upgrade()) {
//             this.energy_level = 0;
//             this.capacity *= 2;
//             this.level++;
//         }
//     }
//     public void downgrade() {
//         if (this.level >= 1) {
//             this.capacity /= 2;
//             this.level--;
//             this.energy_level = Math.min(energy_level, this.capacity);
//         }
//     }
//     public int upgrade_cost() {
//         return this.capacity;
//     }
// }

// class SpaceshipStorage extends EnergyStorage {
//     public SpaceshipStorage(int capacity_, int level_, int energy_level_) {
//         this.capacity = capacity_;
//         this.level = level_;
//         this.energy_level = energy_level_;
//     }
// }

// class Spaceship {
//     private String name;
//     private Planet current_location;
//     private SpaceshipStorage storage;
//     public Spaceship(String name, int capacity, Planet planet_) {
//         this.name = name;
//         this.storage = new SpaceshipStorage(capacity, 1, capacity);
//         this.current_location = planet_;
//     }
//     public void fly_to(Planet destination) {
//         System.out.println("Корабль " + name + " летит к " + destination.get_name());
//         this.current_location = destination;
//     }
//     public void establish_colony(Planet planet, String colony_name) {
//         if (planet.is_colonized()) {
//             System.out.println("Планета уже колонизирована");
//             return;
//         }
//         Colony new_colony = new Colony(colony_name, new ColonyStorage((int)(this.storage.capacity / (Math.pow(2, this.storage.level - 1))), 1, 0));
//         planet.colonize(new_colony);
//     }
//     public String get_name() { 
//         return name; 
//     }
//     public Planet location() {
//         return this.current_location;
//     }
// }

// class ColonyStorage extends EnergyStorage {
//     public ColonyStorage(int capacity_, int level_, int energy_level_) {
//         this.capacity = capacity_;
//         this.level = level_;
//         this.energy_level = energy_level_;
//     }
// }

// class Colony {
//     private String name;
//     private List<Technology> researched_tech;
//     private ColonyStorage storage;
//     public Colony(String name, ColonyStorage storage_) {
//         this.name = name;
//         this.researched_tech = new ArrayList<>();
//         this.storage = storage_;
//     }
//     public void research_technology(Technology tech) {
//         if (tech.is_researched()) {
//             System.out.println("Технология уже исследована");
//             return;
//         }
//         tech.research(storage);
//         if (tech.is_researched()) {
//             researched_tech.add(tech);
//         } else {
//             System.out.println("Недостаточно ресурсов, чтобы исследовать");
//         }
//     }
//     public String get_name() { 
//         return name; 
//     }
//     public ColonyStorage get_storage() {
//         return this.storage;
//     }
//     public List<Technology> available_tech() {
//         return this.researched_tech;
//     }
// }

// class Technology {
//     private String name;
//     private int cost;
//     private boolean researched;

//     public Technology(String name, int cost) {
//         this.name = name;
//         this.cost = cost;
//         this.researched = false;
//     }
//     public void research(EnergyActions storage_) {
//         if (this.researched) {
//             return;
//         }
//         int energy = storage_.extract(this.cost);
//         if (energy < this.cost) {
//             return;
//         }
//         researched = true;
//         System.out.println("Технология '" + name + "' исследована");
//     }
//     public boolean is_researched() { 
//         return researched; 
//     }
//     public String get_name() { 
//         return name; 
//     }
//     public int get_cost() { 
//         return cost; 
//     }
// }

// public class Game {
//     private List<Planet> planets;
//     private List<Spaceship> spaceships;
//     private Scanner scanner;
//     public Game() {
//         planets = new ArrayList<>();
//         spaceships = new ArrayList<>();
//         scanner = new Scanner(System.in);
//         initialize_game();
//     }
//     private void initialize_game() {
//         Technology solar_power = new Technology("Solar energy", 100);
//         Technology fusion_reactor = new Technology("Thermonuclear energy", 300);
//         Planet earth = new Planet("Earth");
//         Planet mars = new Planet("Mars");
//         earth.colonize(new Colony("Mordovia", new ColonyStorage(10, 1, 10)));
//         earth.get_colony().research_technology(fusion_reactor);
//         earth.get_colony().research_technology(solar_power);
//         planets.add(earth);
//         planets.add(mars);
//         Spaceship starterShip = new Spaceship("First", 100, earth);
//         spaceships.add(starterShip);
//     }
//     public void start_game() {
//         while (true) {
//             print_menu();
//             int choice = scanner.nextInt();
//             scanner.nextLine();
//             switch (choice) {
//                 case 1:
//                     list_planets();
//                     break;
//                 case 2:
//                     list_ships();
//                     break;
//                 case 3:
//                     colonize_planet();
//                     break;
//                 case 4:
//                     research_technology();
//                     break;
//                 case 5:
//                     System.out.println("Выход из игры...");
//                     return;
//                 default:
//                     System.out.println("Неверный ввод!");
//             }
//         }
//     }
//     private void print_menu() {
//         System.out.println("\n=== Космическая колонизация ===");
//         System.out.println("1. Список планет");
//         System.out.println("2. Список кораблей");
//         System.out.println("3. Колонизировать планету");
//         System.out.println("4. Исследовать технологию");
//         System.out.println("5. Выход");
//         System.out.print("Выберите действие: ");
//     }
//     private void list_planets() {
//         System.out.println("\nПланеты");
//         for (Planet planet : planets) {
//             System.out.println("- " + planet.get_name() + 
//                 " | Колонизирована: " + (planet.is_colonized() ? "Да" : "Нет"));
//         }
//     }
//     private void list_ships() {
//         System.out.println("\nКосмические корабли");
//         for (Spaceship ship : spaceships) {
//             System.out.println("- " + ship.get_name());
//         }
//     }
//     private void colonize_planet() {
//         System.out.println("\nКолонизация планеты");
//         System.out.print("Введите название корабля: ");
//         String shipName = scanner.nextLine();
//         if (ship == null) {
//             System.out.println("Корабль не найден!");
//             return;
//         }
//         System.out.print("Введите название планеты: ");
//         String planetName = scanner.nextLine();
//         Planet planet = find_planet(planetName);
//         if (planet == null) {
//             System.out.println("Планета не найдена");
//             return;
//         }
//         System.out.print("Введите название колонии: ");
//         String colony_name = scanner.nextLine();
//         ship.fly_to(planet);
//         ship.establish_colony(planet, colony_name);
//     }
//     private void research_technology() {
//         System.out.println("\nИсследование технологий");
//         System.out.print("Введите название планеты: ");
//         String planetName = scanner.nextLine();
//         Planet planet = find_planet(planetName);
//         if (planet == null || !planet.is_colonized()) {
//             System.out.println("Планета не найдена или не колонизирована");
//             return;
//         }
//         List<Technology> available_tech = planet.get_colony().available_tech();
//         if (available_tech.isEmpty()) {
//             System.out.println("Нет доступных технологий для исследования");
//             return;
//         }
//         System.out.println("Доступные технологии:");
//         for (int i = 0; i < available_tech.size(); i++) {
//             Technology tech = available_tech.get(i);
//             System.out.println((i + 1) + ". " + tech.get_name() + 
//                 " | Стоимость: " + tech.get_cost());
//         }
//         System.out.print("Выберите технологию: ");
//         int techChoice = scanner.nextInt() - 1;
//         if (techChoice >= 0 && techChoice < available_tech.size()) {
//             Technology selectedTech = available_tech.get(techChoice);
//             planet.get_colony().research_technology(selectedTech);
//         } else {
//             System.out.println("Неверный выбор");
//         }
//     }
//     private Spaceship findShip(String name) {
//         return spaceships.stream().filter(s -> s.get_name().equalsIgnoreCase(name)).findFirst().orElse(null);
//     }
//     private Planet find_planet(String name) {
//         return planets.stream().filter(p -> p.get_name().equalsIgnoreCase(name)).findFirst().orElse(null);
//     }
//     public static void main(String[] args) {
//         Game game = new Game();
//         game.start_game();
//     }
// }

interface Producible {

}

interface Upgradable {

}

interface Tradable {

}

interface Colonizable {

}

class PlanteException extends Exception {
    PlanteException(String message) {
        super(message);
    }
}

class ColonyException extends Exception {
    ColonyException(String message) {
        super(message);
    }
}

class TechnologyException extends Exception {
    TechnologyException(String message) {
        super(message);
    }
}

abstract class SpaceObject {

}

class Asteroid extends SpaceObject {

}

class Planet extends SpaceObject implements Colonizable {

}

class Technology {

}

class Colony {

}