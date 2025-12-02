import java.util.Random;

public class Client extends Thread {
    private int id;
    private FileChariots chariots;
    private Rayon[] rayons;
    private Tapis tapis;

    private int[] liste;

    public Client(int id, FileChariots c, Rayon[] r, Tapis t) {
        this.id = id;
        this.chariots = c;
        this.rayons = r;
        this.tapis = t;
        genererListe();
    }

    private void genererListe() {
        Random rnd = new Random();
        liste = new int[4];
        for (int i = 0; i < 4; i++)
            liste[i] = rnd.nextInt(3) + 1; // want 1-3 items each
    }

    @Override
    public void run() {
        try {
            // 1: take chariot
            chariots.prendreChariot(id);

            // 2: visit rayons
            for (int i = 0; i < 4; i++) {
                for (int k = 0; k < liste[i]; k++) {
                    rayons[i].takeProduct(id);
                }
                Thread.sleep(300);
            }

            // 3: go to caisse
            System.out.println("Client " + id + " arrive en caisse");

            // 4: put items on tapis
            for (int i = 0; i < 4; i++) {
                for (int k = 0; k < liste[i]; k++) {
                    tapis.deposer(i, id);
                    Thread.sleep(20);
                }
            }

            // 5: marker
            tapis.deposer(-1, id);
            System.out.println("Client " + id + " a fini de déposer.");

            // 6: simulate payment
            Thread.sleep(300);

            // 7: return chariot
            chariots.rendreChariot(id);

        } catch (InterruptedException e) {
        }
    }
}
