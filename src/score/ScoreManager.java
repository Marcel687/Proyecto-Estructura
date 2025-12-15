package score;

import java.io.*;

public class ScoreManager {

    // Clase interna simple para representar un puntaje
    public static class Score {
        public String name;
        public int points;

        public Score(String name, int points) {
            this.name = name;
            this.points = points;
        }
    }

    private final String FILE_PATH = "scores.txt";
    private Score[] highScores;
    private int count;
    private final int MAX_SCORES = 5; // Guardaremos solo el Top 5

    public ScoreManager() {
        highScores = new Score[MAX_SCORES];
        count = 0;
        loadScores();
    }

    /* -------------------------------------------------------
       PERSISTENCIA: CARGAR DATOS (Lectura de archivo)
       ------------------------------------------------------- */
    private void loadScores() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            count = 0;
            while ((line = br.readLine()) != null && count < MAX_SCORES) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    highScores[count++] = new Score(parts[0], Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException e) {
            // Si no existe el archivo, empezamos vacíos.
            System.out.println("No se encontró archivo de puntajes, se creará uno nuevo.");
        }
    }

    /* -------------------------------------------------------
       PERSISTENCIA: GUARDAR DATOS (Escritura en archivo)
       ------------------------------------------------------- */
    public void saveScores() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (int i = 0; i < count; i++) {
                bw.write(highScores[i].name + "," + highScores[i].points);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addScore(String name, int points) {
        // Si hay espacio, lo agregamos al final
        if (count < MAX_SCORES) {
            highScores[count++] = new Score(name, points);
        } else {
            // Si está lleno, reemplazamos el último si el nuevo es mayor
            if (points > highScores[MAX_SCORES - 1].points) {
                highScores[MAX_SCORES - 1] = new Score(name, points);
            } else {
                return; // No entra en el top
            }
        }
        
        // Ordenamos después de insertar
        sortScores();
        saveScores(); // Guardamos inmediatamente
    }

    /* -------------------------------------------------------
       ALGORITMO DE ORDENAMIENTO MANUAL: BUBBLE SORT
       (Ordena de Mayor a Menor)
       ------------------------------------------------------- */
    private void sortScores() {
        if (count <= 1) return;

        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - 1 - i; j++) {
                // Si el actual es MENOR que el siguiente, intercambiamos (Descendente)
                if (highScores[j].points < highScores[j + 1].points) {
                    Score temp = highScores[j];
                    highScores[j] = highScores[j + 1];
                    highScores[j + 1] = temp;
                }
            }
        }
    }

    public Score[] getHighScores() {
        return highScores;
    }
    
    public int getCount() {
        return count;
    }
}