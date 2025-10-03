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

        double precioPorClase = 45.7;
        pagarLasClases(practicas, precio, precioPorClase);
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

                posicionPracticas += 46; // 20 por 10 chars + 18 por 9 chars + 8 por double
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void mostrarPagos(File precio) {
        try (RandomAccessFile file = new RandomAccessFile(precio, "r")) {
            long posicion = 0;

            while (file.getFilePointer() < file.length()) {
                file.seek(posicion);

                String nombre = file.readUTF();
                String dni = file.readUTF();
                String cantidadClases = file.readUTF();
                double precioTotal = file.readDouble();

                System.out.println("Nombre: " + nombre + ". DNI: " + dni +
                        ". Número de prácticas: " + cantidadClases + ". Precio a pagar: " + precioTotal);

                posicion += 46; // 20 por 10 chars + 18 por 9 chars + 8 por double
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void escribirPrecio(File fichero, String nombre, String dni, String cantidadClases, double precio) {
        try (RandomAccessFile file = new RandomAccessFile(fichero, "rw")) {
            long posicionPrecio = file.length();
            file.seek(posicionPrecio);

            file.writeUTF(nombre);
            file.writeUTF(dni);
            file.writeUTF(cantidadClases);
            file.writeDouble(precio * Double.parseDouble(cantidadClases));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}