import java.util.Random;
import java.util.logging.Logger;

/**
 * Classe représentant un client du supermarché.
 *
 * Règles :
 * - Le client doit prendre un chariot avant d'entrer.
 * - Il traverse les quatre rayons dans l'ordre.
 * - Il doit attendre si un rayon est vide.
 * - Un seul client peut déposer à la caisse à la fois.
 * - Le client dépose ses articles un par un, puis dépose le marqueur -1.
 * - Il attend ensuite la fin du paiement avant de rendre son chariot.
 */
public class Client extends Thread {
    private static final Logger logger = Logger.getLogger(Client.class.getName());
    private int id;
    private FileChariots chariots;
    private Rayon[] rayons;
    private Tapis tapis;
    // Liste des quantités à acheter pour les 4 produits (0: Sucre, 1: Farine, 2:
    // Beurre, 3: Lait)
    private int[] listeAchat;

    public Client(int id, FileChariots c, Rayon[] r, Tapis t) {
        this.id = id;
        this.chariots = c;
        this.rayons = r;
        this.tapis = t;
        genererListe();
    }

    /**
     * Génère une liste de courses aléatoire entre 1 et 5 articles par rayon.
     */
    private void genererListe() {
        Random rnd = new Random();
        listeAchat = new int[4];
        for (int i = 0; i < 4; i++)
            listeAchat[i] = rnd.nextInt(5) + 1; // want 1-5 items each
    }

    @Override
    public void run() {
        try {

            chariots.prendreChariot(id);
            // parcours des 4 rayons
            for (int i = 0; i < 4; i++) {
                System.out.println("Client " + id + " entre dans le rayon " + rayons[i].getNumR()
                        + " (" + rayons[i].getProduct() + ")");
                for (int k = 0; k < listeAchat[i]; k++) {
                    rayons[i].takeProduct(id); // peut bloquer si stock 0
                }
                Thread.sleep(300); // déplacement entre les rayons
            }

            System.out.println("Client " + id + " arrive en caisse");
            tapis.accederCaisse(id);
            // Dépôt des produits
            for (int i = 0; i < 4; i++) {
                for (int k = 0; k < listeAchat[i]; k++) {
                    tapis.deposer(i, id);
                    Thread.sleep(20); // temps de dépôt d'un article
                }
            }

            tapis.deposer(-1, id);
            System.out.println("Client " + id + " a fini de déposer ses articles.");

            // Attendre le paiement effectué par l’employé
            tapis.attendrePaiement(id);

            // Libérer la caisse
            tapis.libererCaisse(id);

            chariots.rendreChariot(id);

        } catch (InterruptedException e) {
            logger.warning("Client " + id + " interrompu pendant son exécution.");
        }
    }
}
