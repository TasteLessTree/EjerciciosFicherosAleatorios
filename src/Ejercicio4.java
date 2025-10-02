import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

/** @author AndrésPérezM
 * */

public class Ejercicio4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        File fichero = new File("data/juegos.dat");

        System.out.println("¿Cuál es la edad mínima para jugar el juego?");
        System.out.print(">> ");
        int edad = sc.nextInt();

        System.out.println("¿Cuál es la temática del juego?");
        System.out.print(">> ");
        String tematica = sc.next();

        recomendarJuegos(fichero, edad, tematica);
    }

    public static void recomendarJuegos(File fichero, int edad, String tematica) {
        long posicion = 0;

        int id;
        char[] nombre = new char[10];
        int edadFile;
        char[] clasificacion = new char[10];

        try (RandomAccessFile file = new RandomAccessFile(fichero, "r")) {
            while (file.getFilePointer() < file.length()) {
                file.seek(posicion);

                id = file.readInt();

                for (int i = 0; i < nombre.length; i++) {
                    nombre[i] = file.readChar();
                }

                edadFile = file.readInt();

                for (int i = 0; i < clasificacion.length; i++) {
                    clasificacion[i] = file.readChar();
                }

                String titulo = new String(nombre);
                String clasif = new String(clasificacion);

                if (titulo.trim().equals(tematica.trim()) && edadFile == edad) {
                    System.out.println("Número de orden: " + id + ". Título: " + titulo + ". Edad mínima: " + edad + ". Claficiación: " + clasif);
                }

                posicion += 48; // 20 por cada string, 4 por cada entero
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}