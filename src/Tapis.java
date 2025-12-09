
/**
 * Classe représentant le tapis de caisse.
 * - Modélisé comme un buffer circulaire (FIFO) de taille fixe
 * - Contient des produits (0 à 3) ou un marqueur -1 ("client suivant")
 *
 * Cette classe est un OBJET PARTAGÉ entre :
 * - les clients (qui déposent)
 * - l'employé de caisse (qui retire)
 *
 */
public class Tapis {

    private int[] casesTapis;
    private int taille;
    private int debut = 0;
    private int fin = 0;
    private int nbElements = 0;
    private boolean occupied = false;
    private boolean paymentDone = false;

    public Tapis(int taille) {
        this.taille = taille;
        casesTapis = new int[taille];
    }

    /**
     * Le client demande à entrer à la caisse. Un seul client peut déposer ses
     * produits à la fois.
     */
    public synchronized void accederCaisse(int clientId) throws InterruptedException {
        while (occupied) {
            System.out.println("Client " + clientId + " attend son tour à la caisse . mise en attente sur Tapis");
            wait();
        }
        occupied = true;
        paymentDone = false; // nouveau client
        System.out.println("Client " + clientId + " accède à la caisse.");
    }

    /**
     * Le client attend la fin du paiement (signalé par l'employé).
     */
    public synchronized void attendrePaiement(int clientId) throws InterruptedException {
        while (!paymentDone) {
            System.out.println("Client " + clientId + " attend la fin du paiement . mise en attente sur Tapis");
            wait();
        }
    }

    /**
     * Libère la caisse après paiement.
     */
    public synchronized void libererCaisse(int clientId) {
        occupied = false;
        System.out.println("Client " + clientId + " quitte la caisse.");
        notifyAll();
    }

    /**
     * Appelé par l'employé lorsque le paiement est terminé.
     */
    public synchronized void signalerPaiementTermine() {
        paymentDone = true;
        notifyAll();
    }

    /*
     * Déposer un item sur le tapis
     * 
     */
    public synchronized void deposer(int item, int clientId) throws InterruptedException {
        while (nbElements == taille) {
            System.out.println("Client " + clientId + " attend: tapis plein");
            wait();
        }
        if (item == -1) {
            System.out.println("client a finis de déposer ses items.");
        } else {
            System.out.println("Client " + clientId + " dépose l'item " + item + " sur le tapis (case " + fin + ")");
        }
        casesTapis[fin] = item;
        // gestion circulaire des indices
        fin = (fin + 1) % taille;
        nbElements++;
        notifyAll();
    }

    public synchronized int retirer() throws InterruptedException {
        while (nbElements == 0) {
            System.out.println("Employé attend : tapis vide (mise en attente sur Tapis)");
            wait();
        }

        int item = casesTapis[debut];

        if (item == -1) {
            System.out.println("Employé: fin du client, paiement...");
        } else {
            System.out.println("Employé retire l'item " + item + " . Tapis (case " + debut + ")");
        }

        debut = (debut + 1) % taille;
        nbElements--;

        notifyAll();
        return item;

    }
}
