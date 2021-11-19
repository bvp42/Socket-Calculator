import java.net.*;
import java.util.Random;
import java.util.Scanner;
import java.io.*;

public class Cliente {

    final String HOST = "localhost";

    final int PUERTO = 4002;

    Socket sc;

    PrintWriter salida;

    BufferedReader entrada;

    String mensajeRecibido;

    // Cliente

    public void initCliente(String nombre) {

        try {

            sc = new Socket(HOST, PUERTO); /* conectar a un servidor en localhost con puerto 5000 */

            // creamos el flujo de datos por el que se enviara un mensaje

            salida = new PrintWriter(sc.getOutputStream(), true);
            ;

            entrada = new BufferedReader(new InputStreamReader(sc.getInputStream()));

            System.out.println("A intercambiar mensajes....");

            // enviamos el mensaje

            // Verificacion de unico nombre
            salida.println(nombre);
            Scanner inputNombre = new Scanner(System.in);
            while (true) {
                mensajeRecibido = entrada.readLine();
                System.out.println(mensajeRecibido);

                if (mensajeRecibido.equals("Nombre ocupado")) {
                    System.out.println("Introdusca otro nombre");
                    nombre = inputNombre.nextLine();
                    System.out.println(nombre);
                    salida.println(nombre);
                } else {
                    break;
                }
            }
            multiplicacion("7 3 3 2 5", mensajeRecibido, salida, entrada);

            mensajeRecibido = entrada.readLine();
            System.out.println(mensajeRecibido);
            // sc.close();
            suma("7 3 3 2 5", mensajeRecibido, salida, entrada);

            mensajeRecibido = entrada.readLine();
            System.out.println(mensajeRecibido);
            resta("7 3", mensajeRecibido, salida, entrada);

            mensajeRecibido = entrada.readLine();
            System.out.println(mensajeRecibido);
            division("7 3", mensajeRecibido, salida, entrada);

            mensajeRecibido = entrada.readLine();
            System.out.println(mensajeRecibido);

            System.out.println("Cerrar");
            salida.println("cerrar");
            sc.close();

        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());

        }

    }

    private void suma(String numeros, String mensaje, PrintWriter salidaSuma, BufferedReader entradaSuma) {
        System.out.println("Suma");
        salidaSuma.println("suma");
        try {
            mensaje = entrada.readLine();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        System.out.println(mensaje);
        System.out.println(numeros);
        salidaSuma.println(numeros);
        try {
            mensaje = entradaSuma.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(mensaje);

    }

    private void multiplicacion(String numeros, String mensaje, PrintWriter salidaMultiplicacion,
            BufferedReader entradaMultiplicacion) {
        System.out.println("Multiplicacion");
        salidaMultiplicacion.println("multiplicacion");
        try {
            mensaje = entrada.readLine();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        System.out.println(mensaje);
        System.out.println(numeros);
        salidaMultiplicacion.println(numeros);
        try {
            mensaje = entradaMultiplicacion.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(mensaje);

    }

    private void resta(String numeros, String mensaje, PrintWriter salidaResta, BufferedReader entradaResta) {
        System.out.println("Resta");
        salidaResta.println("resta");
        try {
            mensaje = entrada.readLine();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        System.out.println(mensaje);
        System.out.println(numeros);
        salidaResta.println(numeros);
        try {
            mensaje = entradaResta.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(mensaje);

    }

    private void division(String numeros, String mensaje, PrintWriter salidaDivision, BufferedReader entradaDivision) {
        System.out.println("Division");
        salidaDivision.println("division");
        try {
            mensaje = entrada.readLine();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        System.out.println(mensaje);
        System.out.println(numeros);
        salidaDivision.println(numeros);
        try {
            mensaje = entradaDivision.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(mensaje);

    }

    public static void main(String[] args) {

        Cliente c0 = new Cliente();
        c0.initCliente("Cliente 1");
        Cliente c1 = new Cliente();
        c1.initCliente("Cliente 2");
        Cliente c2 = new Cliente();
        c2.initCliente("Cliente 3");
        Cliente c3 = new Cliente();
        c3.initCliente("Cliente 3");

    }
}
