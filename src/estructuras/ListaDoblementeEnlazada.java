package estructuras;

import java.awt.Point;

public class ListaDoblementeEnlazada { //Lista Doblemente Enlazada para el cuerpo de snake
	private Nodo cabeza, cola;
    private int longitud = 0;

    public void agregarPrimero(Point p) { //Representa que la serpiente avanzó una casilla. La coordenada p es hacia donde se movió la cabeza
        Nodo newNode = new Nodo(p); //Crea un nuevo nodo, le pasa el punto
        if (cabeza == null) { //Si la lista esta vacia
            cabeza = cola = newNode; //el nuevo nodo es igual a la cabeza y cola
        } else { //Si ya habia cuerpo
            newNode.siguiente = cabeza;//Conecta el nuevo nodo hacia la antigua cabeza
            cabeza.anterior = newNode;//Conecta la antigua cabeza hacia atrás al nuevo nodo
            cabeza = newNode; //Actualiza el puntero cabeza para que sea el nuevo nodo
        }
        longitud++;//Incrementa el tamaño de la lista
    }

    public void eliminarUltimo() { //Borra el rastro de la serpiente
        if (cola == null) return; //Si la lista esta vacia, significa que no hay nodos
        if (cabeza == cola) { //Si hay un solo elemento
            cabeza = cola = null; //La lista queda vacia, ambos quedan el null
        } else { //Si hay mas de dos elementos
            cola = cola.anterior; //Mueve la cola hacia atras
            cola.siguiente = null;//Corta el enlaze con el nodo eliminado
        }
        longitud--;//Reduce el tamaño de la lista
    }

    public Point getFirst() { //Sirve para saber dónde está la cabeza actualmente
        if (cabeza != null){ //Si la lista tiene al menos un nodo
        	return cabeza.dato; //Devuelve el dato del nodo
        } else {
        	return null;
        }
    }

    // Método auxiliar para detectar colisiones
    public boolean colisiones(Point p) { //Si la cabeza chocó con el cuerpo de la serpiente.
        Nodo actual = cabeza; //Empieza desde el primer nodo de la lista
        while (actual != null) {//Mientras actual no sea nulo
            if (actual.dato.equals(p)) return true; //si encuentra un nodo con la misma posicion, ha chocado
            actual = actual.siguiente; //pasa al siguiente nodo
        }
        return false;
    }

    // Método para iterar sin usar java.util.Iterator
    public Point iterar(int index) {//Permite obtener el nodo que esta en una posicion especifica
        Nodo actual = cabeza; //Empieza desde el primer nodo de la lista
        for (int i = 0; i < index; i++) { //Recorre la lista index veces, avanzando al siguiente nodo cada vez
        	if (actual == null) return null; //Si el índice es mayor que el tamaño de la lista devuelve nulo
            actual = actual.siguiente;
        }
        return (actual != null) ? actual.dato : null; //si actual apunta a un nodo válido, devuelve su dato (el Point)
    }

    public int longitud() { 
    	return longitud; //Devuelve el tamaño actual
    }
}
