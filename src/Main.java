public class Main {
    public static void main(String[] args) throws InterruptedException {

        // Create rayons
        Rayon[] rayons = {
                new Rayon(1, "Sucre", 10),
                new Rayon(2, "Farine", 10),
                new Rayon(3, "Beurre", 10),
                new Rayon(4, "Lait", 10)
        };
        // petit chiffre pour test rapide
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
        // Attendre que tous les clients aient terminé
        for (Client c : clients)
            c.join();

        System.out.println("=== Tous les clients sont passés. Fin de simulation. ===");
        chef.interrupt();
        employe.interrupt();
    }
}
