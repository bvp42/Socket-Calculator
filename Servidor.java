
import java.net.*;

import java.io.*;

public class Servidor {

    final int PUERTO = 4002;

    ServerSocket sc;

    Socket so;

    PrintWriter salida;

    String mensajeRecibido;

    // SERVIDOR
    public void initServidor() {

        BufferedReader entrada;

        try {

            sc = new ServerSocket(PUERTO);/* crea socket servidor que escuchara en puerto 5000 */
            System.out.println("Esperando una conexion:");
            so = sc.accept();

            // Inicia el socket, ahora esta esperando una conexion por parte del cliente

            System.out.println("Un cliente se ha conectado.");

            // Canales de entrada y salida de datos

            entrada = new BufferedReader(new InputStreamReader(so.getInputStream()));

            salida = new PrintWriter(so.getOutputStream(), true);

            System.out.println("Confirmando conexion al cliente....");
            mensajeRecibido = entrada.readLine();
            
            System.out.println(mensajeRecibido);
            String cliente = mensajeRecibido;

            while (true) {
                salida.println(cliente + ": ¿Que operacion quieres realizar?");
                mensajeRecibido = entrada.readLine();
                if (mensajeRecibido == null || mensajeRecibido.compareTo("cerrar") == 0) {
                    break;

                }
                System.out.println(cliente + ": " + mensajeRecibido);

                switch (mensajeRecibido) {
                    case "suma":
                        if (suma(mensajeRecibido, salida, entrada, cliente) == false) {
                            mensajeRecibido = null;
                        }
                        break;
                    case "multiplicacion":
                        if (multiplicacion(mensajeRecibido, salida, entrada, cliente) == false) {
                            mensajeRecibido = null;
                        }
                        break;
                    case "resta":
                        if (resta(mensajeRecibido, salida, entrada, cliente) == false) {
                            mensajeRecibido = null;
                        }
                        break;
                    case "division":
                        if (division(mensajeRecibido, salida, entrada, cliente) == false) {
                            mensajeRecibido = null;
                        }
                        break;
                }
                if (mensajeRecibido == null) {
                    break;
                }
            }

            System.out.println("Cerrando conexion...");

            so.close();

            sc.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());

        }
    }

    private boolean suma(String mensaje, PrintWriter salidaSuma, BufferedReader entradaSuma, String cliente) {
        int suma = 0;
        System.out.println("Opcion suma recibida esperando numeros...");
        salidaSuma.println(cliente + ": Mandame los numeros separados con espacios");
        try {
            mensaje = entradaSuma.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (mensaje == null) {
            return false;
        }

        System.out.println("Recibo numeros " + mensaje);

        String[] numeros = mensaje.split(" ");
        for (int i = 0; i < numeros.length; i++) {
            suma += Integer.parseInt(numeros[i]);
        }
        System.out.println("Suma: " + Integer.toString(suma));
        salida.println(cliente + ": " + Integer.toString(suma));
        return true;
    }

    private boolean multiplicacion(String mensaje, PrintWriter salidaMultiplicacion,
            BufferedReader entradaMultiplicacion, String cliente) {
        int multiplicacion = 1;
        System.out.println("Opcion multiplicacion recibida esperando numeros...");
        salidaMultiplicacion.println(cliente + ": Mandame los numeros separados con espacios");
        try {
            mensaje = entradaMultiplicacion.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (mensaje == null) {
            return false;
        }

        System.out.println("Recibo numeros " + mensaje);

        String[] numeros = mensaje.split(" ");
        for (int i = 0; i < numeros.length; i++) {
            multiplicacion = Integer.parseInt(numeros[i]) * multiplicacion;
        }
        System.out.println("Multiplicacion: " + Integer.toString(multiplicacion));
        salida.println(cliente + ": " + Integer.toString(multiplicacion));
        return true;
    }

    private boolean resta(String mensaje, PrintWriter salidaResta, BufferedReader entradaResta, String cliente) {
        int resta = 0;
        System.out.println("Opcion resta recibida esperando numeros...");
        while (true) {
            salidaResta.println(cliente + ": Mandame 2 numeros separados con espacios");
            try {
                mensaje = entradaResta.readLine();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (mensaje == null) {
                return false;
            }
            if (mensaje.split(" ").length == 2)
                break;
            salidaResta.println(cliente + ":No recibí 2 numeros");
        }
        System.out.println("Recibo numeros " + mensaje);

        String[] numeros = mensaje.split(" ");
        resta = Integer.parseInt(numeros[0]) - Integer.parseInt(numeros[1]);
        System.out.println("Resta: " + Integer.toString(resta));
        salidaResta.println(cliente + ": " + Integer.toString(resta));
        return true;

    }

    private boolean division(String mensaje, PrintWriter salidaDivision, BufferedReader entradaDivision,
            String cliente) {
        float division = 0;
        System.out.println("Opcion division recibida esperando numeros...");
        while (true) {
            salidaDivision.println(cliente + ": Mandame 2 numeros separados con espacios");
            try {
                mensaje = entradaDivision.readLine();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (mensaje == null) {
                return false;
            }
            if (mensaje.split(" ").length == 2)
                break;
        }
        System.out.println("Recibo numeros " + mensaje);

        String[] numeros = mensaje.split(" ");
        division = Float.parseFloat(numeros[0]) / Float.parseFloat(numeros[1]);
        System.out.println("Division: " + Float.toString(division));
        salidaDivision.println(cliente + ": " + Float.toString(division));
        return true;

    }

    public static void main(String[] args) {

        Servidor s = new Servidor();
        s.initServidor();

    }
}
