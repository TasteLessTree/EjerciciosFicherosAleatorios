import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

/** @author AndrésPérezM
 * */

public class Ejercicio2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        File fichero = new File("data/enteros.dat");

        System.out.println("¿Qué número quieres insertar? [0, 9]");
        System.out.print(">> ");
        int num = sc.nextInt();

        if (num < 0 || num > 9) {
            System.out.println("Numero incorrecto");
            return;
        }

        System.out.println("¿Cúantas veces lo deseas insertar?");
        System.out.print(">> ");
        int cantidad = sc.nextInt();

        insertarNumero(fichero, num, cantidad);
        mostrarNumeros(fichero);
    }

    public static void insertarNumero(File fichero, int num, int cantidad) {
        try (RandomAccessFile file = new RandomAccessFile(fichero, "rw")) {
            long posicion = file.length();
            file.seek(posicion);

            for (int i = 0; i < cantidad; i++)
                file.writeInt(num);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void mostrarNumeros(File fichero) {
        System.out.println("===== [" + fichero.getName() + "] =====");
        try (RandomAccessFile file = new RandomAccessFile(fichero, "r")) {
            while (file.getFilePointer() < file.length()) {
                System.out.print(file.readInt() + " ");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("\n===== [" + fichero.getName() + "] =====");
        System.out.println();
    }
}