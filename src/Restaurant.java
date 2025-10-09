package src;

enum TableState {
    Free,
    Taken,
    Waiting,
}

class Egor extends Exception {
    Egor(String message) {
        super(message);
    }
}

class Table {
    private int id;
    private TableState state;
    public Table(int id_) {
        this.id = id_;
        this.state = TableState.Free;
    }
    public int get_id() {
        return this.id;
    }
    public TableState get_state() {
        return this.state;
    }
    public void take() throws Egor {
        if (this.state != TableState.Free) {
            throw new Egor("This table is already taken");
        }
        this.state = TableState.Taken;
        System.out.println("You have booked a table");
    }
    public void leave() {
        if (this.state == TableState.Free) {
            return;
        }
        System.out.println("You have left the table");
        this.state = TableState.Free;
        return;
    }
    public void ask_waiter(Waiter waiter, Order order, Kitchen kitchen) {
        waiter.get_to_kitchen(order, kitchen);
    }
}

class Dish {
    public String title;
    public String price;
    private String description;
    public Dish(String title_, String price_ , String description_) {
        this.title = title_;
        this.price = price_;
        this.description = description_;
    }
    public String get_description() {
        return this.description;
    }
    public void show() {
        System.out.println(this.title + ": " + this.price + "\n" + this.description);
    }
}

enum OrderState {
    None,
    InProgress,
    Done,
}

class Order {
    private int table_id;
    private int order_size;
    private Dish[] dishes;
    private int order_id;
    private int waiter_id;
    public OrderState state;
    public Order(int table_id_, int waiter_id_, int order_id_, int order_size_, Dish[] dishes_) {
        this.table_id = table_id_;
        this.order_size = order_size_;
        this.dishes = dishes_;
        this.order_id = order_id_;
        this.waiter_id = waiter_id_;
        this.state = OrderState.None;
    }
    public void take() {
        if (this.state == OrderState.None) {
            this.state = OrderState.InProgress;
        }
    }f
    public void done() {
        if (this.state == OrderState.InProgress) {
            this.state = OrderState.Done;
        }
    }
    public void show() {
        for (int i = 0; i < this.order_size; i++) {
            if (dishes[i] == null) {
                return;
            }
            dishes[i].show();
        }
        System.out.println("\n");
        return;
    }

}

class Waiter {
    public String first_name;
    public String last_name;
    public int id;
    public Waiter(String first, String last, int id_) {
        this.first_name = first;
        this.last_name = last;
        this.id = id_;
    }
    public void get_to_kitchen(Order order, Kitchen kitchen) {
        kitchen.push(order);
    }
    public void deliver(Order order) {
        System.out.println(this.first_name + " " + this.last_name + " delivered your order: \n");
        order.show();
    }
}

class Kitchen {
    public Order[] orders;
    public int amount;
    private int maxs;
    public Kitchen(int max_size) {
        this.orders = new Order[max_size];
        this.amount = 0;
        this.maxs = max_size;
        System.out.println("Your order created");
    }
    public void push(Order order) {
        if (amount == maxs) {
            return;
        }
        this.orders[amount] = order;
        this.amount++;
    }
    public Order get() {
        if (this.amount == 0) {
            return null;
        }
        Order order = this.orders[amount - 1];
        this.orders[amount - 1] = null;
        this.amount -= 1;
        return  order;
    }
}


public class Restaurant {
    public Kitchen kitchen;
    public Table[] tables;
    public Waiter[] waiters;
    private boolean[] dish_done;
    public Restaurant() {
        this.kitchen = new Kitchen(100);
        this.tables = new Table[50];
        this.waiters = new Waiter[10];
        this.dish_done = new boolean[10];
        for (int i = 0; i < 50; i++) {
            tables[i] = new Table(i);
        }
        for (int i = 0; i < 10; i++) {
            waiters[i] = new Waiter("Name", "Lastname", i);
        }
        for (int i = 0; i < 10; i++) {
            dish_done[i] = false;
        }
    }
    public Table get_a_table() {
        for (int i = 0; i < 50; i++) {
            if (this.tables[i].get_state() == TableState.Free) {
                try {
                    this.tables[i].take();
                    return this.tables[i];
                } catch (Egor egor) {
                    System.out.println("Oshibka");
                    return null;
                }
            }
        }
        return null;
    }
}