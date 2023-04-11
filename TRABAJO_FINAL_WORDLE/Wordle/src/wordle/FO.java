package wordle;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class FO {

    private Paraula nom;
    private FileWriter fic;
    private BufferedWriter writer;

    public FO(Paraula n, boolean append) {
        nom = n;

        try {
            fic = new FileWriter(nom.toString(), append);
            writer = new BufferedWriter(fic);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void GravarLinia(Paraula p) {
        try {
            writer.write(p.toString());
            writer.newLine();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void tancar() {
        try {
            writer.close();
            fic.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                fic.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
