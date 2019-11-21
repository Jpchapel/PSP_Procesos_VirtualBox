package lectorcsv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Clase que lee un fichero plano. Se proporciona la ruta en el constructor.
 *
 */
public class LectorFichero {

    private File archivo;
    private FileReader fr = null;
    private BufferedReader br;

    /**
     *
     * @param fichero Fichero que se pretende leer. Se puede indicar junto con
     * su ruta.
     */
    public LectorFichero(String fichero) {
        try {
            // Apertura del fichero y creacion de BufferedReader para poder 
            // procesarlo por líneas con el método readLine(). 
            archivo = new File(fichero);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
        } catch (FileNotFoundException e) {
            System.out.println("No existe el fichero " + fichero);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Método que retorna una línea del fichero
     * @return Siguiente línea
     * @throws IOException 
     */
    public String leer() throws IOException {
        String línea = "";
        try {
            línea = br.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (línea == null){
            if (null != fr) {
                fr.close();
            }
            br.close();
        }
        return línea;
    }

    /**
     * Se utiliza el método leerTodo() para obtener el contenido del fichero.
     *
     * @return Contenido del fichero plano
     * @throws IOException Devuelve una excepción de tipo entrada/salida en caso
     * de no poder leer el fichero
     */
    public String leerTodo() throws IOException {
        // Se utiliza la clase StringBuilder para concatenar cadenas con el 
        // objetivo de acelerar el proceso en el caso de que el ficheor sea
        // muy extenso.
        StringBuilder contenido = new StringBuilder();
        String línea;
        // Varible utilizada para insertar saltos de línea excepto en la última
        boolean yaHayLínea = false;

        try {
            while ((línea = br.readLine()) != null) {
                // Si ya se imprimió alguna línea, se concatena un salto
                // para imprimir la nueva en una nueva línea
                // No se concatena después de imprimir todas las líneas
                // para evitar que haya un salto después de la última.
                if (yaHayLínea) {
                    contenido.append("\n");
                }
                contenido.append(línea);
                yaHayLínea = true;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (null != fr) {
                fr.close();
            }
            br.close();
        }

        return contenido.toString();
    }
}
