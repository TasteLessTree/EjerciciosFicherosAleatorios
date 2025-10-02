import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

/** @author AndrésPérezM
 * */

public class Ejercicio3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        File fichero = new File("data/juegos.dat");

        System.out.println("¿Cuál es el identificador del juego?");
        System.out.print(">> ");
        int id = sc.nextInt();

        sc.nextLine();
        System.out.println("¿Cuál es el nombre del juego?");
        System.out.print(">> ");
        String nombre = sc.nextLine();

        System.out.println("¿Cuál es la edad mínima para jugar el juego?");
        System.out.print(">> ");
        int edad = sc.nextInt();

        sc.nextLine();
        System.out.println("¿Cuál es la clasificación del juego?");
        System.out.print(">> ");
        String clasificacion = sc.nextLine();

        anyadirJuego(fichero, id, nombre, edad, clasificacion);
        mostrarNumeros(fichero);
    }

    public static void anyadirJuego(File fichero, int id, String nombre, int edad, String clasificacion) {
        try (RandomAccessFile file = new RandomAccessFile(fichero, "rw")) {
            long posicion = file.length();
            file.seek(posicion);

            file.writeInt(id);

            StringBuilder titulo = new StringBuilder(nombre);
            titulo.setLength(10);
            file.writeChars(titulo.toString());

            file.writeInt(edad);

            StringBuilder clasif = new StringBuilder(clasificacion);
            clasif.setLength(10);
            file.writeChars(clasif.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void mostrarNumeros(File fichero) {
        System.out.println("===== [" + fichero.getName() + "] =====");

        long posicion = 0;

        int id;
        char[] nombre = new char[10];
        int edad;
        char[] clasificacion = new char[10];

        try (RandomAccessFile file = new RandomAccessFile(fichero, "r")) {
            while (file.getFilePointer() < file.length()) {
                file.seek(posicion);
                id = file.readInt();

                for (int i = 0; i < nombre.length; i++) {
                    nombre[i] = file.readChar();
                }

                edad = file.readInt();

                for (int i = 0; i < clasificacion.length; i++) {
                    clasificacion[i] = file.readChar();
                }

                String titulo = new String(nombre);
                String clasif = new String(clasificacion);

                System.out.println("Número de orden: " + id + ". Título: " + titulo + ". Edad mínima: " + edad + ". Claficiación: " + clasif);
                posicion += 48; // 20 por cada string, 4 por cada entero
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println();
    }
}