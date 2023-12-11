import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class servidorTCP {
    public static void main(String[] args) {
        try {
            // Crear socket del servidor
            ServerSocket socketServidor = new ServerSocket(5000);
            System.out.println("Esperando conexiones...");

            while (true) {
                // Esperar y aceptar conexiones de clientes
                Socket socketCliente = socketServidor.accept();
                System.out.println("Cliente conectado: " + socketCliente.getInetAddress().getHostAddress());

                // Crear un hilo para manejar la conexión con el cliente
                hiloCliente hilo = new hiloCliente(socketCliente);
                hilo.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}