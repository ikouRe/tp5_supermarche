public class Main {
    public static void main(String[] args) throws InterruptedException {

        // Create rayons
        Rayon[] rayons = {
                new Rayon("Sucre", 10),
                new Rayon("Farine", 10),
                new Rayon("Beurre", 10),
                new Rayon("Lait", 10)
        };

        FileChariots chariots = new FileChariots(2);
        Tapis tapis = new Tapis(10);

        // Create threads
        ChefRayon chef = new ChefRayon(rayons);
        Employe employe = new Employe(tapis);

        chef.start();
        employe.start();

        Client[] clients = new Client[5];
        for (int i = 0; i < 5; i++) {
            clients[i] = new Client(i + 1, chariots, rayons, tapis);
            clients[i].start();
        }

        for (Client c : clients)
            c.join();

        System.out.println("=== Tous les clients sont passés. Fin de simulation. ===");
    }
}
