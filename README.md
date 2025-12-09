#  TP Supermarché — Programmation Concurrente en Java

##  Présentation
Ce projet simule le fonctionnement d’un supermarché à l’aide de threads Java.  
Les acteurs — **clients**, **employé de caisse**, **chef de rayon** — interagissent avec des **ressources partagées** (chariots, rayons, tapis de caisse) nécessitant l’usage de mécanismes de synchronisation (`synchronized`, `wait()`, `notifyAll()`, `join()`, `sleep()`).

---

##  Synchronisation utilisée

### synchronized  
Garantit l'accès exclusif aux ressources critiques (`Rayon`, `FileChariots`, `Tapis`).

### wait()  
Utilisé lorsqu’un thread doit attendre :
- chariots indisponibles ;
- rayon vide ;
- tapis plein (côté client) ou vide (côté employé) ;
- attente de la fin du paiement.

### notifyAll()  
Réveille les threads en attente :
- chariot rendu ;
- stock ajouté dans un rayon ;
- article ajouté/retiré du tapis ;
- paiement terminé.

### join()  
Dans `Main`, permet d’attendre la fin de tous les clients avant de clôturer la simulation.

---

##  Acteurs

### Client
- prend un chariot ;
- parcourt les rayons et prélève des articles ;
- attend en cas de rayon vide ;
- accède à la caisse et dépose ses articles sur le tapis ;
- pose `-1` pour indiquer la fin ;
- attend le paiement puis rend le chariot.

### Employé de caisse
- prend les articles du tapis (FIFO) ;
- détecte `-1` pour traiter le paiement ;
- réveille le client une fois terminé.

### Chef de rayon
- réapprovisionne les rayons régulièrement ;
- réveille les clients en attente de stock.

---

##  Ressources partagées

### FileChariots  
Stock limité de chariots. Les clients attendent si vide.

### Rayon  
Stock d’un produit. Réapprovisionné par le chef de rayon.

### Tapis de caisse  
Buffer circulaire à capacité fixe.  
Client bloqué si plein, employé bloqué si vide. Marqueur `-1` = fin du dépôt.

---

##  Structure du projet
- `Client`
- `Employe`
- `ChefRayon`
- `Rayon`
- `FileChariots`
- `Tapis`
- `Main`

---

##  Compilation & exécution

### Depuis la ligne de commande
cd src
javac *.java
java Main

### Depuis un IDE
Compiler puis exécuter simplement la classe `Main`.

---

## Exemple de déroulement

Le programme affiche la trace complète de la simulation :
attentes sur les ressources, ajouts de stock, dépôts sur le tapis,
paiements, etc.  
Ces messages permettent d’observer le fonctionnement précis
de la synchronisation entre les threads.

---

## Conclusion

La simulation met en œuvre l’ensemble des mécanismes demandés :
attente bloquante, réveils, accès exclusif à la caisse, gestion du stock,
interactions entre threads et respect strict du scénario imposé.
Le projet illustre comment coordonner plusieurs threads autour de
ressources partagées tout en évitant les blocages permanents.