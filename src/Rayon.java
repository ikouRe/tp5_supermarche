public class Rayon {
    private String product;
    private int capacity;
    private int stock;

    public Rayon(String product, int capacity) {
        this.product = product;
        this.capacity = capacity;
        this.stock = 0;
    }

    public String getProduct() {
        return product;
    }

    public int getCapacity() {
        return capacity;
    }

    // function add stock called by the chef
    public synchronized void addStock(int quantity) {
        int i = 0;
        while (stock < capacity && i < quantity) {
            stock++;
            i++;
        }
        if (i > 0) {
            System.out.println("Added " + i + " units of " + product + ". Current stock: " + stock);
            notifyAll();
        }

    }

    // fonction take one product from stock by a cliet
    public synchronized void takeProduct(int clientId) throws InterruptedException {
        while (stock == 0) {
            System.out.println("Client " + clientId + " is waiting for product " + product);
            wait();
        }
        stock--;
        System.out.println("Client " + clientId + " took product " + product + ". Current stock: " + stock);
        notifyAll();
    }
}
