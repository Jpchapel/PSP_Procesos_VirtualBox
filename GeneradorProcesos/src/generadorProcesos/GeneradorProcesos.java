/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generadorProcesos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stream
 */
public class GeneradorProcesos {

    private final Process proceso;
    private BufferedReader bufferedReader;
    private PrintStream printStream;

    private void crearCanalesComunicacion() {
        bufferedReader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
        printStream = new PrintStream(proceso.getOutputStream());
    }

    public GeneradorProcesos(String[] ordenes) throws IOException {
        proceso = Runtime.getRuntime().exec(ordenes);
        crearCanalesComunicacion();
    }

    public GeneradorProcesos(String orden) throws IOException {
        proceso = Runtime.getRuntime().exec(orden);
        crearCanalesComunicacion();
    }

    public GeneradorProcesos(List ordenes) throws IOException {
        proceso = new ProcessBuilder().command(ordenes).start();
        crearCanalesComunicacion();
    }

    public void escribir(String datos) {
        printStream.println(datos);
        printStream.flush();
    }

    public String leer() throws IOException {
        return bufferedReader.readLine();
    }

    public String leerLineas() throws IOException {
        String linea;
        StringBuilder resultado = new StringBuilder();
        while ((linea = bufferedReader.readLine()) != null) {
            resultado.append(linea).append("\n");
        }
        return resultado.toString();
    }

    public void fin(){
        try {
            bufferedReader.close();
            printStream.close();
            proceso.destroy();
        } catch (IOException ex) {
            Logger.getLogger(GeneradorProcesos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
