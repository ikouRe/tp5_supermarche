public class Tapis {
    private int[] casesTapis;
    private int taille;
    private int debut = 0;
    private int fin = 0;
    private int nbElements = 0;

    public Tapis(int taille) {
        this.taille = taille;
        casesTapis = new int[taille];
    }

    public synchronized void deposer(int item, int clientId) throws InterruptedException {
        while (nbElements == taille) {
            System.out.println("Client " + clientId + " attend: tapis plein");
            wait();
        }
        casesTapis[fin] = item;
        // gestion circulaire des indices
        fin = (fin + 1) % taille;
        nbElements++;
        notifyAll();
    }

    public synchronized int retirer() throws InterruptedException {
        while (nbElements == 0) {
            wait();
        }
        int item = casesTapis[debut];
        // gestion circulaire des indices
        debut = (debut + 1) % taille;
        nbElements--;
        notifyAll();
        return item;
    }
}
