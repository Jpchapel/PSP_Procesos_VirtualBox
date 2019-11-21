/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lectorcsv;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stream
 */
public class LectorCSV {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LectorFichero lector = new LectorFichero(args[0]);
        
        String linea;
        try {
            while((linea = lector.leer()) != null){
                System.out.println(linea.replace(args[1], " "));
            }
        } catch (IOException ex) {
            Logger.getLogger(LectorCSV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
