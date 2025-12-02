public class FileChariots {
    private int nbChariots;

    public FileChariots(int nbChariots) {
        this.nbChariots = nbChariots;
    }

    public synchronized void prendreChariot(int clientId) throws InterruptedException {
        while (nbChariots == 0) {
            System.out.println("Client " + clientId + " is waiting for a chariot.");
            wait();
        }
        nbChariots--;
        System.out.println("Client " + clientId + " took a chariot. Chariots left: " + nbChariots);
    }

    public synchronized void rendreChariot(int clientId) {
        nbChariots++;
        System.out.println("Client " + clientId + " returned a chariot. Chariots available: " + nbChariots);
        notify();
    }
}
