public class Employe extends Thread {
    private final Tapis tapis;

    public Employe(Tapis tapis) {
        this.tapis = tapis;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int item = tapis.retirer();

                if (item == -1) {
                    System.out.println("Employé: fin du client, paiement...");
                    Thread.sleep(300);
                    continue;
                }

                System.out.println("Employé prend item: " + item);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
        }
    }
}
