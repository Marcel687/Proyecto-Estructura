package juego;

import javax.swing.*;
import direccion.Direccion;
import estructuras.ArrayList;
import estructuras.ListaDoblementeEnlazada;
import score.ScoreManager;
import snake.SnakeGame;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel {

    private SnakeGame game;
    private ScoreManager scoreManager;
    private final int TILE_SIZE = 20;
    private boolean scoreSaved = false;

    // --- COLORES PARA LA CUADRÍCULA ---
    private final Color COLOR_GRASS_LIGHT = new Color(170, 215, 81); // Verde césped claro
    private final Color COLOR_GRASS_DARK = new Color(162, 209, 73);  // Verde césped oscuro

    public GamePanel(SnakeGame game) {
        this.game = game;
        this.scoreManager = new ScoreManager();
        
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (game.isGameOver()) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        game.reset();
                        scoreSaved = false;
                        repaint();
                    }
                    return;
                }

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> game.getSnake().colaDireccion(Direccion.UP);
                    case KeyEvent.VK_DOWN -> game.getSnake().colaDireccion(Direccion.DOWN);
                    case KeyEvent.VK_LEFT -> game.getSnake().colaDireccion(Direccion.LEFT);
                    case KeyEvent.VK_RIGHT -> game.getSnake().colaDireccion(Direccion.RIGHT);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 1. PINTAR FONDO CUADRICULADO 
        int widthTiles = getWidth() / TILE_SIZE;
        int heightTiles = getHeight() / TILE_SIZE;

        for (int y = 0; y < heightTiles; y++) {
            for (int x = 0; x < widthTiles; x++) {
                // Alternar colores según si la suma de coordenadas es par o impar
                if ((x + y) % 2 == 0) {
                    g.setColor(COLOR_GRASS_LIGHT);
                } else {
                    g.setColor(COLOR_GRASS_DARK);
                }
                g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

        // 2. PINTAR PAREDES
        g.setColor(new Color(0, 102, 0)); //color verde oscuro
        ArrayList walls = game.getWalls();
        for (int i = 0; i < walls.tamaño(); i++) {
            Point p = walls.get(i);
            g.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }

        // 3. PINTAR COMIDA
        g.setColor(Color.RED);
        Point food = game.getFood();
        if (food != null) {
            g.fillOval(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }

        // 4. PINTAR SERPIENTE
        g.setColor(new Color(0, 0, 153)); // Tu color azul original
        ListaDoblementeEnlazada body = game.getSnake().getBody();
        for (int i = 0; i < body.longitud(); i++) {
            Point p = body.iterar(i);
            g.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }

        // 5. PINTAR PUNTAJE
        g.setColor(Color.WHITE);
        g.drawString("Score: " + game.getScore(), 20, 20);

        // 6. MANEJO GAME OVER
        if (game.isGameOver()) {
            handleGameOver(g);
        }
    }

    private void handleGameOver(Graphics g) {
        // Fondo semitransparente
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());

        // Guardar puntaje si no se ha guardado aún
        if (!scoreSaved) {
            scoreSaved = true; // Evitar diálogos infinitos
            // Usar SwingUtilities para evitar bloquear el pintado
            SwingUtilities.invokeLater(() -> {
                String name = JOptionPane.showInputDialog(this, "Game Over! Score: " + game.getScore() + "\nEnter Name:");
                if (name != null && !name.trim().isEmpty()) {
                    scoreManager.addScore(name, game.getScore());
                }
                repaint(); // Repintar para mostrar la tabla actualizada
            });
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        drawCenteredString(g, "GAME OVER", getHeight() / 4);

        g.setFont(new Font("Arial", Font.PLAIN, 18));
        drawCenteredString(g, "Press ENTER to Restart", getHeight() / 4 + 40);

        // Mostrar Tabla de High Scores
        drawHighScores(g);
    }

    private void drawHighScores(Graphics g) {
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Monospaced", Font.BOLD, 20));

        int startY = getHeight() / 2;
        drawCenteredString(g, "--- TOP 5 SCORES ---", startY);

        ScoreManager.Score[] scores = scoreManager.getHighScores();
        for (int i = 0; i < scoreManager.getCount(); i++) {
            String text = (i + 1) + ". " + scores[i].name + " - " + scores[i].points;
            drawCenteredString(g, text, startY + 30 + (i * 25));
        }
    }

    private void drawCenteredString(Graphics g, String text, int y) {
        FontMetrics fm = g.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(text)) / 2;
        g.drawString(text, x, y);
    }
}