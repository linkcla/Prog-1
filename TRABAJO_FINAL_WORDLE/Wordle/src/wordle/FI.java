package wordle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FI {

    private Paraula nom;
    private FileReader fic;
    private BufferedReader reader;

    public FI(Paraula n) {
        nom = n;

        try {
            fic = new FileReader(nom.toString());
            reader = new BufferedReader(fic);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public char[] llegirLinia() {
        char[] res = null;

        try {
            String fr = reader.readLine();
            if (fr != null) {
                res = fr.toCharArray();
            }
        } catch (IOException ex) {
            Logger.getLogger(FI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    public int obtenerLineas() {
        final FI leerLineas = new FI(nom);
        int lineas = 0;
        char[] linea;

        do {
            linea = leerLineas.llegirLinia();
            if (linea != null) {
                lineas++;
            }
        } while (linea != null);

        leerLineas.tancar();
        return lineas;
    }

    public void tancar() {
        try {
            reader.close();
            fic.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fic.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
