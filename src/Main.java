package src;

public class Main {
    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant();
        Table table = restaurant.get_a_table();
        Kitchen kitchen = new Kitchen(100);
        int order_size = 2;
        Dish[] dishes = new Dish[order_size];
        dishes[0] = new Dish("Bliny", "1337", "Nastoyashiy blin");
        dishes[1] = new Dish("Pelmen", "228", "mnogo myasa malo testa");
        Order order = new Order(table.get_id(), 0, 1, order_size, dishes);
        restaurant.waiters[0].get_to_kitchen(order, kitchen);
        restaurant.waiters[0].deliver(kitchen.get());
    }
}
