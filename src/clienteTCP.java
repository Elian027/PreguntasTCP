import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class clienteTCP {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String mensaje = "";

        try {
            // Crear socket para conectarse al servidor
            Socket socketCliente = new Socket("localhost", 5000);
            System.out.println("Cliente conectado");

            // Crear buffers para recibir y enviar datos al cliente
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
            PrintWriter salida = new PrintWriter(socketCliente.getOutputStream(), true);

            int puntaje = 0;

            for (int i = 0; i < 5; i++) {
                String pregunta = entrada.readLine();
                System.out.println("Pregunta: " + pregunta);

                System.out.print("Respuesta: ");
                mensaje = sc.nextLine();
                salida.println(mensaje);

                String resultado = entrada.readLine();
                System.out.println("Resultado: " + resultado);

                if (resultado.equals("Correcto")) {
                    puntaje++;
                }
            }

            // Recibir y mostrar puntaje final
            String puntajeFinal = entrada.readLine();
            System.out.println("Puntaje final: " + puntajeFinal);

            // Cerrar conexión con el servidor
            socketCliente.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
