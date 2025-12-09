
import java.util.logging.Logger;

/**
 * Classe représentant l'employé de caisse. Son rôle : - retirer les items du
 * tapis (FIFO) - détecter le marqueur -1 = fin du dépôt par un client -
 * effectuer le paiement - signaler la fin du paiement au client
 *
 */
public class Employe extends Thread {

    private static final Logger logger = Logger.getLogger(Employe.class.getName());
    private final Tapis tapis;

    public Employe(Tapis tapis) {
        this.tapis = tapis;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // L’employé récupère un item du tapis
                int item = tapis.retirer();

                if (item == -1) {
                    // Temps de paiement
                    Thread.sleep(300);
                    // Le paiement est terminé -> signaler au client
                    tapis.signalerPaiementTermine();

                    System.out.println("Employé : paiement terminé -> Client suivant !");
                    continue; // passe au client suivant
                }

                System.out.println("Employé prend item: " + item);
                Thread.sleep(100); // temps de scan d’un produit
            }
        } catch (InterruptedException e) {
            logger.warning("Employé interrompu -> arrêt du poste de caisse.");
            Thread.currentThread().interrupt();
        }
    }
}
