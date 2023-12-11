import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class hiloCliente extends Thread {
    private Socket socketCliente;
    private String[] preguntas = {"¿Cuál es la capital de Francia?", "¿En qué año se fundó Google?", "¿Cuánto es 2 + 2?",
    "¿Cuál es el país más grande del mundo?", "¿Cuál es el río más largo del planeta?"};
    private String[] respuestasCorrectas = {"Paris", "1998", "4", "Rusia", "Nilo"};

    public hiloCliente(Socket socketCliente) {
        this.socketCliente = socketCliente;
    }

    private void evaluarRespuesta(String respuesta, String respuestaCorrecta) {
        if (respuesta.equalsIgnoreCase(respuestaCorrecta)) {
            enviarRespuesta("Correcto");
        } else {
            enviarRespuesta("Incorrecto");
        }
    }

    private void enviarRespuesta(String mensaje) {
        try {
            PrintWriter salida = new PrintWriter(socketCliente.getOutputStream(), true);
            salida.println(mensaje);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        int puntaje = 0;

        try {
            // Crear buffers para recibir y enviar datos al cliente
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
            PrintWriter salida = new PrintWriter(socketCliente.getOutputStream(), true);

            for (int i = 0; i < preguntas.length; i++) {
                // Enviar pregunta al cliente
                salida.println(preguntas[i]);

                // Recibir respuesta del cliente
                String respuesta = entrada.readLine();
                System.out.println("Respuesta del cliente: " + respuesta);

                // Evaluar la respuesta y enviar resultado al cliente
                evaluarRespuesta(respuesta, respuestasCorrectas[i]);

                if (respuesta.equalsIgnoreCase(respuestasCorrectas[i])) {
                    puntaje++;
                }

                // Verificar si es la última pregunta
                if (i == preguntas.length - 1) {
                    salida.println(puntaje);
                }
            }

            // Cerrar conexión con el cliente
            socketCliente.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
