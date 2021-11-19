
import java.net.*;
import java.io.*;
import java.util.concurrent.*;

public class ServidorMultiHilado implements Runnable {
	static final int PUERTO = 4002;
	Socket s;
	static ConcurrentMap<String, String> datos = new ConcurrentHashMap<String, String>();

	public ServidorMultiHilado() {

		initServidor();

	}

	public ServidorMultiHilado(Socket s) {

		this.s = s;

	}

	// SERVIDOR
	public void initServidor() {

		ServerSocket sc;

		Socket so;

		try {

			sc = new ServerSocket(PUERTO);/* crea socket servidor que escuchara en puerto 5000 */

			while (true) {
				System.out.println("Esperando una conexion:");
				so = sc.accept();

				ServidorMultiHilado hilo = new ServidorMultiHilado(so);
				Thread tcliente = new Thread(hilo);
				tcliente.start();

				// Inicia el socket, ahora esta esperando una conexion por parte del cliente

				System.out.println("Un cliente se ha conectado.");

			}

		} catch (Exception e) {

			System.out.println("Error: " + e.getMessage());

		}
	}

	@Override
	public void run() {
		// Canales de entrada y salida de datos
		PrintWriter salida = null;

		String mensajeRecibido = "";

		BufferedReader entrada = null;
		String cliente = "";

		try {
			entrada = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		try {
			salida = new PrintWriter(s.getOutputStream(), true);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		System.out.println("Confirmando conexion al cliente....");

		while (true) {
			try {
				mensajeRecibido = entrada.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// Se rompío la connexion
			if (mensajeRecibido == null) {
				break;
			} else {
				System.out.println(mensajeRecibido);
				if (datos.get(mensajeRecibido) != null) {
					System.out.println("Ya existe este nombre de usuario:" + mensajeRecibido);
					salida.println("Nombre ocupado");
				} else {
					break;
				}
			}
		}
		// Si es nulo se rompio la conneccion al agregar el nombre
		if (mensajeRecibido != null) {

			cliente = mensajeRecibido;
			datos.put(cliente, "1");

			while (true) {
				salida.println(cliente + ": ¿Que operacion quieres realizar?");
				try {
					mensajeRecibido = entrada.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
		}

		System.out.println("Cerrando conexion...");

		// Borrar cliente
		if (cliente != "") {
			datos.remove(cliente);
		}

		try {
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		salidaSuma.println(cliente + ": " + Integer.toString(suma));
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
		salidaMultiplicacion.println(cliente + ": " + Integer.toString(multiplicacion));
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

		ServidorMultiHilado s = new ServidorMultiHilado();

	}
}
