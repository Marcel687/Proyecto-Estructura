package snake;

import java.awt.Point;

import direccion.Direccion;
import estructuras.ColaEnlazada;
import estructuras.ListaDoblementeEnlazada;

public class Snake {

    private ListaDoblementeEnlazada cuerpo;   // Estructura Manual 1
    private ColaEnlazada inputs;      // Estructura Manual 2
    private Direccion actualDir;

    public Snake(int inicioX, int inicioY) {
        cuerpo = new ListaDoblementeEnlazada();
        cuerpo.agregarPrimero(new Point(inicioX, inicioY));

        inputs = new ColaEnlazada();
        actualDir = Direccion.RIGHT;
    }

    public ListaDoblementeEnlazada getBody() {
        return cuerpo;
    }

    public void colaDireccion(Direccion d) {
        inputs.encolar(d);
    }
    
    // Para reiniciar inputs al morir
    public void resetInputs() {
        inputs.clear();
        actualDir = Direccion.RIGHT;
    }

    public void actualizarDireccion() {
        if (!inputs.isEmpty()) {
            Direccion next = inputs.desencolar();
            // Evitar movimientos opuestos de 180 grados
            if ((actualDir == Direccion.UP && next != Direccion.DOWN) ||
                (actualDir == Direccion.DOWN && next != Direccion.UP) ||
                (actualDir == Direccion.LEFT && next != Direccion.RIGHT) ||
                (actualDir == Direccion.RIGHT && next != Direccion.LEFT)) {
            	actualDir = next;
            }
        }
    }

    public Point mover(boolean crecer) {
        //actualizarDireccion();

        Point head = cuerpo.getFirst();
        Point newHead = new Point(head);

        switch (actualDir) {
            case UP -> newHead.y--;
            case DOWN -> newHead.y++;
            case LEFT -> newHead.x--;
            case RIGHT -> newHead.x++;
        }

        cuerpo.agregarPrimero(newHead);

        if (!crecer) {
            cuerpo.eliminarUltimo();
        }

        return newHead;
    }
    
    public Point peekNextHead() {
        Point head = cuerpo.getFirst();
        Point newHead = new Point(head);

        switch (actualDir) {
            case UP -> newHead.y--;
            case DOWN -> newHead.y++;
            case LEFT -> newHead.x--;
            case RIGHT -> newHead.x++;
        }
        return newHead;
    }
}