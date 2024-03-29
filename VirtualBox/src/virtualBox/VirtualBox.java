/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualBox;

import generadorProcesos.GeneradorProcesos;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stream
 */
public class VirtualBox {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        VirtualBox virtualbox = new VirtualBox();
        virtualbox.go(args);
    }

    private void go(String[] args) {
        Scanner sc = new Scanner(System.in);
        int respuesta = 0;

        do {
            System.out.println("Menu:");
            System.out.println("1 - Ver Máquinas Virtuales");
            System.out.println("2 - Crear Máquinas Virtuales");
            System.out.println("3 - Salir");
            System.out.println("Opción:");
            try {
                respuesta = sc.nextInt();

                if (respuesta < 1 || respuesta > 3) {
                    System.out.println("Opcion incorrecta. Introduzca opcion valida");
                } else {
                    if (respuesta == 1) {
                        verMaquinas();
                    } else if (respuesta == 2) {
                        crearMaquinas(args);
                    }
                }
            } catch (InputMismatchException ex) {
                System.out.println("Entrada Incorrecta. Introduzca un numero del 1 al 3");
            }
        } while (respuesta != 3);

    }

    private void verMaquinas() {

        GeneradorProcesos procesoVisor = null;

        try {
            ArrayList<String> values = new ArrayList<>();
            values.add("C:\\Program Files\\Oracle\\VirtualBox\\vboxmanage");
            values.add("list");
            values.add("vms");

            procesoVisor = new GeneradorProcesos(values);
            String salida;
            while ((salida = procesoVisor.leer()) != null) {
                System.out.println(salida);
            }
        } catch (IOException ex) {
            Logger.getLogger(VirtualBox.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
                procesoVisor.fin();
        }
    }

    private void crearMaquinas(String[] args) {
        GeneradorProcesos procesoCreador = null;
        try {
            procesoCreador = new GeneradorProcesos("java -cp C:\\Users\\Stream\\Desktop\\Clases\\REPETIR\\REPASOPSP\\LectorCSV\\build\\classes lectorcsv.LectorCSV " + args[0] + " " + args[1]);
            String salida;
            while ((salida = procesoCreador.leer()) != null) {
                String[] datos = salida.split(" ");
                String nombreMaquina = datos[0];
                String sistema = datos[1];
                String memoria = datos[2];

                procesoNombrarMaquina(nombreMaquina);
                Thread.sleep(5000);//Dormimos el Hilo para que se complete cada instrucción
                procesoSOMaquina(nombreMaquina, sistema);
                Thread.sleep(5000);
                procesoRAMMaquina(nombreMaquina, memoria);
                Thread.sleep(5000);
            }

        } catch (IOException ex) {
            Logger.getLogger(VirtualBox.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(VirtualBox.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
                procesoCreador.fin();
        }

    }

    private void procesoNombrarMaquina(String nombreMaquina) {
        GeneradorProcesos proceso = null;

        try {
            ArrayList<String> values = new ArrayList<>();
            values.add("C:\\Program Files\\Oracle\\VirtualBox\\vboxmanage");
            values.add("createvm");
            values.add("-name");
            values.add(nombreMaquina);
            values.add("-register");

            proceso = new GeneradorProcesos(values);
            String salida;
            while ((salida = proceso.leer()) != null) {
                System.out.println(salida);
            }
        } catch (IOException ex) {
            Logger.getLogger(VirtualBox.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
                proceso.fin();
        }
    }

    private void procesoSOMaquina(String nombreMaquina, String sistema) {
        GeneradorProcesos proceso = null;

        try {
            ArrayList<String> values = new ArrayList<>();
            values.add("C:\\Program Files\\Oracle\\VirtualBox\\vboxmanage");
            values.add("modifyvm");
            values.add(nombreMaquina);
            values.add("--ostype");
            values.add(sistema);

            proceso = new GeneradorProcesos(values);

        } catch (IOException ex) {
            Logger.getLogger(VirtualBox.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
                proceso.fin();
        }
    }

    private void procesoRAMMaquina(String nombreMaquina, String memoria) {
        GeneradorProcesos proceso = null;

        try {
            ArrayList<String> values = new ArrayList<>();
            values.add("C:\\Program Files\\Oracle\\VirtualBox\\vboxmanage");
            values.add("modifyvm");
            values.add(nombreMaquina);
            values.add("--memory");
            values.add(memoria);

            proceso = new GeneradorProcesos(values);

        } catch (IOException ex) {
            Logger.getLogger(VirtualBox.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
                proceso.fin();
        }
    }

}
