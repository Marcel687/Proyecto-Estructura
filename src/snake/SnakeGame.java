package snake;

import java.awt.Point;
import java.util.Random;
import estructuras.ArrayList;
import estructuras.ListaDoblementeEnlazada;

public class SnakeGame {

    private final int width;
    private final int height;
    private Snake snake;
    private Point food;
    private ArrayList walls;
    private boolean gameOver;
    
    private int score;

    public SnakeGame(int width, int height) {
        this.width = width;
        this.height = height;
        initGame();
    }

    public void initGame() {
        snake = new Snake(width / 2, height / 2);
        walls = new ArrayList();
        gameOver = false;
        score = 0; // REINICIAR PUNTAJE
        
        generateWalls();
        mergeSortWalls(0, walls.tamaño() - 1);
        spawnFoodRecursive(); 
    }

    private void mergeSortWalls(int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortWalls(left, mid);
            mergeSortWalls(mid + 1, right);
            merge(left, mid, right);
        }
    }

    private void merge(int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Point[] L = new Point[n1];
        Point[] R = new Point[n2];

        for (int i = 0; i < n1; ++i) L[i] = walls.get(left + i);
        for (int j = 0; j < n2; ++j) R[j] = walls.get(mid + 1 + j);

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (L[i].x <= R[j].x) {
                walls.set(k, L[i]);
                i++;
            } else {
                walls.set(k, R[j]);
                j++;
            }
            k++;
        }
        while (i < n1) walls.set(k++, L[i++]);
        while (j < n2) walls.set(k++, R[j++]);
    }

    /* -------------------------------------------------------
       GENERACIÓN DE MUROS
       ------------------------------------------------------- */
    private void generateWalls() {
        for (int x = 0; x < width; x++) {
            walls.agregar(new Point(x, 0));
            walls.agregar(new Point(x, height - 1));
        }
        for (int y = 0; y < height; y++) {
            walls.agregar(new Point(0, y));
            walls.agregar(new Point(width - 1, y));
        }
    }

    private void spawnFoodRecursive() {
        food = generateFoodRecursive();
    }

    private Point generateFoodRecursive() {
        Random r = new Random();
        Point p = new Point(r.nextInt(width), r.nextInt(height));
        if (walls.contains(p) || snake.getBody().colisiones(p)) {
            return generateFoodRecursive(); 
        }
        return p;
    }

    public void update() {
        if (gameOver) return;

        snake.actualizarDireccion();
        
        Point newHead = snake.peekNextHead();

        if (newHead.x < 0 || newHead.x >= width || 
            newHead.y < 0 || newHead.y >= height) {
            gameOver = true;
            return;
        }

        if (walls.contains(newHead)) {
            gameOver = true;
            return;
        }

        ListaDoblementeEnlazada body = snake.getBody();
        
        for (int i = 1; i < body.longitud(); i++) {
            if (body.iterar(i).equals(newHead)) {
                gameOver = true;
                return;
            }
        }

        // 4. Comer o Moverse
        boolean eat = newHead.equals(food);
        snake.mover(eat);

        if (eat) {
            score += 10;
            spawnFoodRecursive();
        }
    }
    
    public void reset() {
        initGame();
    }

    public Snake getSnake() { 
    	return snake; 
    }
    
    public Point getFood() { 
    	return food; 
    }
    public ArrayList getWalls() { 
    	return walls; 
    }
    public boolean isGameOver() { 
    	return gameOver; 
    }
    
    public int getScore() { 
    	return score; 
    }
}