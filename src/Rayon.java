
/**
 * Classe représentant un Rayon du supermarché.
 * Il s'agit d'un objet PARTAGÉ entre :
 * - le Chef de rayon (qui ajoute du stock)
 * - les Clients (qui prennent des produits)
 *
 * La synchronisation est essentielle :
 * - les clients doivent attendre si le stock est vide
 * - le chef de rayon réveille les clients lorsqu’il ajoute du stock
 */
public class Rayon {

    private int numR; // numero de rayon (0: Sucre, 1: Farine, 2: Beurre, 3: Lait)
    private String product; // Nom du produit (Sucre, Farine, ...)
    private int capacity; // Capacité maximale du rayon STOCK_MAX
    private int stock; // Stock actuel

    public Rayon(int numR, String product, int capacity) {
        this.numR = numR;
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

    public int getNumR() {
        return numR;
    }

    /**
     * Méthode appelée par le Chef de rayon. Il ajoute jusqu'à 'quantity'
     * exemplaires dans la limite de la capacité.
     */
    public synchronized void addStock(int quantity) {
        int i = 0;
        while (stock < capacity && i < quantity) {
            stock++;
            i++;
        }
        if (i > 0) {
            System.out.println(
                    "Chef de rayon remet " + i + " unités de " + product + " dans le Rayon " + numR + " (stock = " + stock
                    + "/" + capacity + ")");

            // On réveille les clients en attente sur ce rayon
            notifyAll();
        }

    }

    /**
     * Appelé par un client qui veut prendre un produit.
     */
    public synchronized void takeProduct(int clientId) throws InterruptedException {
        while (stock == 0) {
            System.out.println("Client " + clientId + " ne peut pas prendre " + product + " mise en attente sur Rayon "
                    + numR + " (" + product + ")");
            wait();
        }
        stock--;
        System.out.println("Client " + clientId + " prend 1 " + product + " du Rayon " + numR + " (stock restant = "
                + stock + ")");
        notifyAll();
    }
}
