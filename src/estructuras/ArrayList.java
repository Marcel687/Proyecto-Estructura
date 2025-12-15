package estructuras;

import java.awt.Point;

public class ArrayList { //Arreglo Dinámico (Para las Paredes y Ordenamiento)
	
	private Point[] datos; //Donde se guardan los datos
    private int tamaño; //Contador que sice cuantos espacios hemos usado

    public ArrayList() {
        datos = new Point[10]; //Creamos una lista con espacio inicial de 10 elementos
        tamaño = 0; //Empieza en 0, no hay ningun elemento
    }

    public void agregar(Point p) {
        if (tamaño == datos.length) { //Esta lleno el arreglo interno
            cambiarTamaño(); //aumentamos el tamaño
        }
        datos[tamaño++] = p; //Guarda el punto p en la posición actual disponible, despues incrementa el contador tamaño en 1 para que apunte al siguiente hueco libre.
    }

    private void cambiarTamaño() {
        Point[] newData = new Point[datos.length * 2]; //Crea un nuevo arreglo que es el doble de grande que el actual
        System.arraycopy(datos, 0, newData, 0, datos.length); //Copia desde datos (empezando en 0) hacia newData (empezando en 0) un total de datos.length elementos"
        datos = newData;//Ahora la variable datos apunta al nuevo arreglo grande.
    }

    public Point get(int index) {
        if (index < 0 || index >= tamaño) throw new IndexOutOfBoundsException(); //Verifica que estes dentro del intervalo
        return datos[index]; //Si el índice es válido, devuelve el objeto guardado alli
    }

    public void set(int index, Point p) {
        if (index < 0 || index >= tamaño) throw new IndexOutOfBoundsException(); //Verifica que estes dentro del intervalo
        datos[index] = p; //Asigna un valor P en posicion index
    }

    public int tamaño() { 
    	return tamaño; //Devuelve cuntos elementos hay en el arreglo
    }

    public boolean contains(Point p) {
        for (int i = 0; i < tamaño; i++) { //Recorre el arreglo desde el principio 0 hasta el último dato real tamaño
            if (datos[i].equals(p)) return true; //Compara el elemento actual con el parametro p
        }
        return false;
    }
    
    public void imprimirEnConsola() {
        System.out.println("--- Contenido del ArrayList (" + tamaño + " elementos) ---");
        for (int i = 0; i < tamaño; i++) {
            // datos[i] es un objeto Point. Point tiene atributos x e y.
            Point p = datos[i];
            System.out.println("Índice " + i + ": (" + p.x + ", " + p.y + ")");
        }
        System.out.println("----------------------------------------------------");
    }
}
