import java.util.logging.Logger;

/**
 * 
 * Son rôle est de :
 * - aller à l'entrepôt (500 ms)
 * - se déplacer vers chaque rayon (200 ms)
 * - remplir chaque rayon avec jusqu'à 5 articles
 *
 * 
 */
public class ChefRayon extends Thread {
    private static final Logger logger = Logger.getLogger(ChefRayon.class.getName());
    private Rayon[] rayons;

    public ChefRayon(Rayon[] rayons) {
        this.rayons = rayons;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Passage par l'entrepôt
                System.out.println("Chef de rayon se rend à l'entrepôt pour faire le plein.");
                Thread.sleep(500);
                // Parcours des 4 rayons
                for (Rayon rayon : rayons) {
                    System.out.println("Chef de rayon se dirige vers le rayon " + rayon.getNumR() + " ("
                            + rayon.getProduct() + ")");
                    Thread.sleep(200);
                    // Remplissage du rayon (jusqu'à 5 articles)
                    rayon.addStock(5);
                }
            }
        } catch (InterruptedException e) {
            logger.warning("Chef de rayon interrompu. Fin du travail.");
            Thread.currentThread().interrupt();
        }
    }

}
