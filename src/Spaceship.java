public class Spaceship extends SpaceObject implements Producible {
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