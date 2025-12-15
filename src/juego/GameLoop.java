package juego;

import javax.swing.*;
import snake.SnakeGame;

public class GameLoop {

    public static void main(String[] args) {
        
        SnakeGame game = new SnakeGame(30, 30); //inicia el tamaño del tablero de 30 x 30

        JFrame frame = new JFrame("Proyecto Final Estructuras - Snake"); //Titulo del Frame
        GamePanel panel = new GamePanel(game); //Crea el panel y le pasa el juego
        frame.add(panel); //Pasa al frame el panel

        // Ajustar tamaño ventana
        frame.setSize(620, 640); //Define el tamaño en pixeles
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Programa deja de ejecutarse al presiona la "x"
        frame.setResizable(false); //No se puede cambiar el tamaño de la ventana
        frame.setLocationRelativeTo(null); //Centra la pantalla al centro del monitor
        frame.setVisible(true); //hace que la ventana sea visible
        
        // Loop del juego
        Timer timer = new Timer(120, e -> { //indica que el código dentro del bloque se ejecutará cada 100 milisegundos
            game.update(); //Avanza la lógica. La serpiente se mueve una casilla, se comprueba si comió o si chocó.
            panel.repaint(); //Fuerza a la interfaz gráfica a borrar el dibujo anterior y pintar el estado nuevo
        });

        timer.start(); //Da la orden de inicio. Sin esto, la ventana aparecería congelada.
    }
}