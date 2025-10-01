import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Ejercicio1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        File fichero = new File("data/enteros.dat");

        //escribirNumeros(fichero);
        mostrarNumeros(fichero);

        System.out.println("Introduce un valor entre 0 y 9 (ambos incluidos) para reemplazar los 2s");
        System.out.print(">> ");
        int num =  sc.nextInt();

        if (num < 0 || num > 9) {
            System.out.println("Numero incorrecto");
            return;
        }

        reemplazarDoses(fichero, num);
        mostrarNumeros(fichero);
    }

    public static void escribirNumeros(File fichero) {
        try (RandomAccessFile file = new RandomAccessFile(fichero, "rw")) {
            for (int i = 0; i < 6; i++)
                file.writeInt(1);

            for (int i = 0; i < 10; i++)
                file.writeInt(2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void mostrarNumeros(File fichero) {
        System.out.println("===== [" + fichero.getName() + "] =====");
        try (RandomAccessFile file = new RandomAccessFile(fichero, "r")) {
            // Sugerido por IntelliJ
            while (file.getFilePointer() < file.length()) {
                System.out.print(file.readInt() + " ");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("\n===== [" + fichero.getName() + "] =====");
        System.out.println();
    }

    public static void reemplazarDoses(File fichero, int num) {
        int id = 7; // Sabemos que hay 6 unos, el primer 2 tiene posición 7
        long posicion = 0;
        try (RandomAccessFile file = new RandomAccessFile(fichero, "rw")) {
            while (file.getFilePointer() < file.length()) {
                posicion = (id - 1) * 4L;
                posicion += 4; // Tamaño de un int
                file.seek(posicion);
                int valor = file.readInt();

                if (valor == 2) {
                    file.writeInt(num);
                }

                id++;
            }
        } catch (IOException e)  {
            throw new RuntimeException(e);
        }
    }
}