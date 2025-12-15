package estructuras;

import java.awt.Point;
import direccion.Direccion;

public class Nodo {
	
	
	//Para la Lista Doblemente Enlazada
	Point dato;
    Nodo siguiente, anterior;
    
    Nodo(Point dato) { 
    	this.dato = dato; 
    }
    
    //Para la cola enlazada
    Direccion valor;
    Nodo proximo;
    
    Nodo(Direccion valor) { 
    	this.valor = valor; 
    }
}
