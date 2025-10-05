import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

/** @author AndrésPérezM
 * */

public class Ejercicio7 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        File precio = new File("data/precio.dat");
        File descuento = new File("data/descuento.dat");

        if (descuento.delete()) {
            System.out.println("Fichero eliminado");
        }

        System.out.println("¿Cuanto descuento quieres aplicar?");
        System.out.print(">> ");
        int porcentaje = sc.nextInt();

        aplicarDescuento(precio, descuento, porcentaje);
        mostarDescuento(descuento);
    }

    public static void aplicarDescuento(File precioFile, File descuentoFile, int descuento){
        char[] nombreAlumno = new char[10];
        char[] DNI = new char[9];
        char[] numero = new char[2];

        try (RandomAccessFile file = new RandomAccessFile(precioFile, "r")) {
            long posicion = 0;

            while (file.getFilePointer() < file.length()) {
                file.seek(posicion);

                for (int i = 0; i < nombreAlumno.length; i++) {
                    nombreAlumno[i] = file.readChar();
                }
                for (int i = 0; i < DNI.length; i++) {
                    DNI[i] = file.readChar();
                }
                for (int i = 0; i < numero.length; i++) {
                    numero[i] = file.readChar();
                }

                String nombre = new String(nombreAlumno);
                String dni = new String(DNI);
                String cantidadClases = new String(numero);
                double precio = file.readDouble();

                try (RandomAccessFile rafDescuento = new RandomAccessFile(descuentoFile, "rw")) {
                    long descuentoPosicion = rafDescuento.length();
                    rafDescuento.seek(descuentoPosicion);
                    rafDescuento.writeChars(dni);
                    rafDescuento.writeDouble(precio);

                    /* Para calcular el descuento, por ejemplo el 25% de descuento 500
                    *   1. Calcular el 25% de 500:
                    *       · (25/100) * 500 -> 0.25 * 500 = 125.
                    *   2. Restar el resultado a la cantidad inicial
                    *       · 500 - 125 = 375.
                    * */
                    double porcentaje = ((double) descuento / 100) * precio;
                    double precioDescuento = precio - porcentaje;

                    rafDescuento.writeDouble(precioDescuento);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                posicion += 50; // 20 por 10 chars + 18 por 9 chars + 4 por 2 chars + 8 por double
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void mostarDescuento(File descuento) {
        char[] DNI = new char[9];
        double precioAntiguo;
        double precioActual;

        try (RandomAccessFile file = new RandomAccessFile(descuento, "r")) {
            long posicion = 0;

            while (file.getFilePointer() < file.length()) {
                file.seek(posicion);

                for (int i = 0; i < DNI.length; i++) {
                    DNI[i] = file.readChar();
                }

                String dni = new String(DNI);
                precioAntiguo = file.readDouble();
                precioActual = file.readDouble();

                System.out.println(". DNI: " + dni + ". Precio antiguo: " + precioAntiguo + ". Precio con descuento: " + precioActual);

                posicion += 34; // 18 por 9 chars + 16 por 2 double
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}