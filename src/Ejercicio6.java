import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/** @author AndrésPérezM
 * */

public class Ejercicio6 {
    public static void main(String[] args) {
        File practicas = new File("data/practicas.dat");
        File precio = new File("data/precio.dat");

        if (precio.delete()) {
            System.out.println("Precio.dat eliminado");
        }

        pagarLasClases(practicas, precio, 45.7);
        mostrarPagos(precio);
    }

    public static void pagarLasClases(File practicas, File precio, double precioPorClase) {
        char[] nombre = new char[10];
        char[] apellidos = new char[35];
        char[] DNI = new char[9];
        char[] vehiculo = new char[2];
        char[] numero = new char[2];

        try (RandomAccessFile pract = new RandomAccessFile(practicas, "r")) {
            long posicionPracticas = 0;

            while (pract.getFilePointer() < pract.length()) {
                pract.seek(posicionPracticas);

                for (int i = 0; i < nombre.length; i++) {
                    nombre[i] = pract.readChar();
                }
                for (int i = 0; i < apellidos.length; i++) {
                    apellidos[i] = pract.readChar();
                }
                for (int i = 0; i < DNI.length; i++) {
                    DNI[i] = pract.readChar();
                }
                for (int i = 0; i < vehiculo.length; i++) {
                    vehiculo[i] = pract.readChar();
                }
                for (int i = 0; i < numero.length; i++) {
                    numero[i] = pract.readChar();
                }

                String nombreAlumno = new String(nombre);
                String DNIAlumno = new String(DNI);
                String numeroPractAlumno = new String(numero);
                escribirPrecio(precio, nombreAlumno, DNIAlumno, numeroPractAlumno, precioPorClase);

                posicionPracticas += 116; // 20 por 10 chars + 70 por 35 chars + 18 por 9 chars + 4 por 2 chars + 4 por 2 chars
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void mostrarPagos(File precio) {
        char[] nombreAlumno = new char[10];
        char[] DNI = new char[9];
        char[] numero = new char[2];

        try (RandomAccessFile file = new RandomAccessFile(precio, "r")) {
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
                double precioTotal = file.readDouble();

                System.out.println("Nombre: " + nombre + ". DNI: " + dni +
                        ". Número de prácticas: " + cantidadClases + ". Precio a pagar: " + precioTotal);

                posicion += 50; // 20 por 10 chars + 18 por 9 chars + 4 por 2 chars + 8 por double
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void escribirPrecio(File fichero, String nombre, String dni, String cantidadClases, double precio) {
        try (RandomAccessFile file = new RandomAccessFile(fichero, "rw")) {
            long posicionPrecio = file.length();
            file.seek(posicionPrecio);
            file.writeChars(nombre);
            file.writeChars(dni);
            file.writeChars(cantidadClases);
            double clases = Double.parseDouble(cantidadClases);
            double total = precio * clases;
            file.writeDouble(total);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}