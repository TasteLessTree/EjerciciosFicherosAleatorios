import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

/** @author AndrésPérezM
 * */

public class Ejercicio5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        File fichero = new File("data/practicas.dat");

        if (fichero.delete()) {
            System.out.println("Fichero eliminado");
        }

        System.out.println("¿Cuántos alumnos deseas crear?");
        System.out.print(">> ");
        int cantidad = sc.nextInt();

        sc.nextLine();
        for (int i = 0; i < cantidad; i++) {
            System.out.print("Ingrese el nombre del alumno: ");
            String nombre = sc.nextLine();

            if (nombre.length() > 10) {
                System.out.println("El nombre debe tener un máximo de 10 caracteres. Será modificado.");

                char[] arr = new char[10];

                for (int j = 0; j < arr.length; j++) {
                    arr[j] = nombre.charAt(j);
                }

                nombre = new String(arr);
            }

            System.out.print("Ingrese los apellidos del alumno: ");
            String apellidos = sc.nextLine();

            if (apellidos.length() > 35) {
                System.out.println("El apellido debe tener un máximo de 35 caracteres. Será modificado.");

                char[] arr = new char[35];

                for (int j = 0; j < arr.length; j++) {
                    arr[j] = apellidos.charAt(j);
                }

                apellidos = new String(arr);
            }

            System.out.print("Ingrese el DNI del alumno: ");
            String DNI = sc.nextLine();

            if (DNI.length() > 9) {
                char[] arr = new char[9];

                for (int j = 0; j < arr.length; j++) {
                    arr[j] = DNI.charAt(j);
                }

                DNI = new String(arr);
            }

            System.out.print("Ingrese el vehículo de prácticas: ");
            String vehiculo = sc.nextLine();

            if (vehiculo.length() > 2) {
                System.out.println("Error en el vehículo de prácticas");
                return;
            }

            if (vehiculo.length() == 1) {
                char[] arr = new char[2];
                arr[0] = '0';
                arr[1] = vehiculo.charAt(0);

                vehiculo = new String(arr);
            }

            System.out.print("Ingrese el número de prácticas: ");
            String numero = sc.nextLine();

            if (numero.length() > 2) {
                System.out.println("Error en el número de prácticas");
                return;
            }

            if (numero.length() == 1) {
                char[] arr = new char[2];
                arr[0] = '0';
                arr[1] = numero.charAt(0);

                numero = new String(arr);
            }

            insertarAlumnos(fichero, nombre, apellidos, DNI, vehiculo, numero);
            System.out.println();
        }
        mostrarAlumnos(fichero);
    }

    public static void insertarAlumnos(File fichero, String nombre, String apellidos, String DNI, String vehiculo, String numero) {
        try (RandomAccessFile file = new RandomAccessFile(fichero, "rw")) {
            long posicion = fichero.length();
            file.seek(posicion);

            StringBuilder nombreAlumno = new StringBuilder(nombre);
            nombreAlumno.setLength(10);
            file.writeChars(nombreAlumno.toString());

            StringBuilder apellidosAlumno = new StringBuilder(apellidos);
            apellidosAlumno.setLength(35);
            file.writeChars(apellidosAlumno.toString());

            StringBuilder DNIAlumno = new StringBuilder(DNI);
            DNIAlumno.setLength(9);
            file.writeChars(DNIAlumno.toString());

            StringBuilder vehiculoAlumno = new StringBuilder(vehiculo);
            vehiculoAlumno.setLength(2);
            file.writeChars(vehiculoAlumno.toString());

            StringBuilder numeroAlumno = new StringBuilder(numero);
            numeroAlumno.setLength(2);
            file.writeChars(numeroAlumno.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void mostrarAlumnos(File fichero) {
        char[] nombre = new char[10];
        char[] apellidos = new char[35];
        char[] DNI = new char[9];
        char[] vehiculo = new char[2];
        char[] numero = new char[2];

        try (RandomAccessFile file = new RandomAccessFile(fichero, "r")) {
            long posicion = 0;

            while (file.getFilePointer() < file.length()) {
                file.seek(posicion);

                for (int i = 0; i < nombre.length; i++) {
                    nombre[i] = file.readChar();
                }
                for (int i = 0; i < apellidos.length; i++) {
                    apellidos[i] = file.readChar();
                }
                for (int i = 0; i < DNI.length; i++) {
                    DNI[i] = file.readChar();
                }
                for (int i = 0; i < vehiculo.length; i++) {
                    vehiculo[i] = file.readChar();
                }
                for (int i = 0; i < numero.length; i++) {
                    numero[i] = file.readChar();
                }

                String nombreAlumno = new String(nombre);
                String apellidosAlumno = new String(apellidos);
                String DNIAlumno = new String(DNI);
                String vehiculoPracticas = new String(vehiculo);
                String numeroPractAlumno = new String(numero);

                System.out.println("Nombre: " + nombreAlumno + ". Apellidos: " + apellidosAlumno + ". DNI: " + DNIAlumno +
                        ". Vehículo prácticas: " + vehiculoPracticas + ". Número de prácticas: " + numeroPractAlumno);

                posicion += 116; // 20 por 10 chars + 70 por 35 chars + 18 por 9 chars + 4 por 2 chars + 4 por 2 chars
            }
        } catch (IOException e) {
            throw new  RuntimeException(e);
        }
    }
}