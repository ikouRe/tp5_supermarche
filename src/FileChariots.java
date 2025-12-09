/**
 * Classe représentant la file de chariots du supermarché.
 *
 * Cet objet est PARTAGÉ entre tous les clients.
 * Règles:
 * - chaque client doit prendre un chariot avant d'entrer
 * - si aucun chariot n'est disponible, le client attend
 * - lorsqu'un client a fini ses courses, il remet son chariot
 */
public class FileChariots {
    private int nbChariots;

    public FileChariots(int nbChariots) {
        this.nbChariots = nbChariots;
    }

    /**
     * Méthode appelée par un client qui veut prendre un chariot.
     * Si la file est vide donc : mise en attente.
     */
    public synchronized void prendreChariot(int clientId) throws InterruptedException {
        while (nbChariots == 0) {
            System.out.println(
                    "Client " + clientId + " ne peut pas prendre de chariot. Mise en attente sur FileChariots.");
            wait();
        }
        nbChariots--;
        System.out.println("Client " + clientId + " prend un chariot. Chariots restants: " + nbChariots);
    }

    /**
     * Le client remet un chariot une fois ses courses terminées.
     */
    public synchronized void rendreChariot(int clientId) {
        nbChariots++;
        System.out.println("Client " + clientId + " rend un chariot. Chariots disponibles: " + nbChariots);
        notifyAll(); // Réveille les clients en attente
    }
}
