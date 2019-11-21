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

/**
 *
 * @author Stream
 */
public class Procesos {
    private final Process proceso;
    private BufferedReader brProceso;
    private PrintStream psProceso;

    private void crearCanalesComunicación() {
        brProceso = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
        psProceso = new PrintStream(proceso.getOutputStream());
    }

    public Procesos(String[] orden) throws IOException {
        proceso = Runtime.getRuntime().exec(orden);
        crearCanalesComunicación();
    }

    public Procesos(String orden) throws IOException {
        proceso = Runtime.getRuntime().exec(orden);
        crearCanalesComunicación();
    }

    public Procesos(List orden) throws IOException {
        proceso = new ProcessBuilder().command(orden).start();
        crearCanalesComunicación();
    }

    public void escribir(String datos) {
        psProceso.println(datos);
        psProceso.flush();
    }

    public String leer() throws IOException {
        return brProceso.readLine();
    }

    public void fin() throws IOException {
        brProceso.close();
        psProceso.close();
        proceso.destroy();
    }
}
