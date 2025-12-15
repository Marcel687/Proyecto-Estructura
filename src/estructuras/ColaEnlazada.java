package estructuras;

import direccion.Direccion;

public class ColaEnlazada { //Cola Enlazada para el Buffer de Inputs
	private Nodo frente, fin;

    public void encolar(Direccion d) {
        Nodo newNode = new Nodo(d); //Creamos un nuevo nodo con la direccion
        if (fin == null) { //Verifica que el ultimo nodo tenga un valor
            frente = fin = newNode;//El nuevo nodo es tanto el frente como el final
        } else {
            fin.proximo = newNode; //El último nodo actual apunta al nuevo nodo
            fin = newNode; //Actualiza el puntero fin para que sea el nuevo nodo
        }
    }

    public Direccion desencolar() {
        if (frente == null) return null; //Verifica si la cola esta vacia, si lo esta, devuelve null
        Direccion dato = frente.valor; //Guarda el valor del nodo que está al frente
        frente = frente.proximo; //Mueve el puntero frente al siguiente nodo
        if (frente == null) fin = null; //Si al avanzar la cola se quedó vacía, pone fin en null
        return dato; //Devuelve el dato
    }

    public boolean isEmpty() {
        return frente == null; //El puntero frente es nulo?
    }
    
    public void clear() {
        frente = fin = null; //Rompe las referencias poniendo frente y fin en null
    }

}
