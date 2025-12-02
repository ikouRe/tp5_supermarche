public class ChefRayon extends Thread {
    private Rayon[] rayons;

    public ChefRayon(Rayon[] rayons) {
        this.rayons = rayons;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(500);
                for (Rayon rayon : rayons) {
                    Thread.sleep(200);
                    rayon.addStock(5);
                }
            }
        } catch (InterruptedException e) {
            // TODO: handle exception
        }
    }

}
