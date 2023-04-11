package wordle;

public class Paraula {

    private final int llarginicial = 20;
    private char[] pal;
    private int ind;

    public Paraula() {
        pal = new char[llarginicial];
        ind = 0;
    }

    public Paraula(char[] p) {
        pal = new char[llarginicial];
        ind = 0;
        for (int i = 0; i < p.length; i++) {
            posar(p[i]);
        }
    }

    public void posar(char c) {
        if (ind == pal.length) {
            char[] aux = new char[pal.length + 5];
            for (int i = 0; i < pal.length; i++) {
                aux[i] = pal[i];
            }
            pal = aux;
        }
        pal[ind++] = c;
    }

    public void posarArray(char[] c) {
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 0) {
                continue;
            }
            posar(c[i]);
        }
    }

    public boolean buida() {
        return (ind == 0);
    }

    public int getTam() {
        return ind;
    }

    public boolean existAlDic(Paraula paraula, char idioma) {
        Paraula dic;
        Paraula aux = new Paraula();
        char[] lin;
        boolean exist = false;

        paraula.toMinusculas();

        switch (idioma) {
            case 'a': {
                dic = new Paraula("wordle_ang_solucions.txt".toCharArray());
                break;
            }
            case 'e': {
                dic = new Paraula("wordle_esp_solucions.txt".toCharArray());
                break;
            }
            default: {
                dic = new Paraula("wordle_cat_solucions.txt".toCharArray());
                break;
            }
        }
        FI fic = new FI(dic);

        do {

            lin = fic.llegirLinia();
            if (lin != null) {
                aux = new Paraula(lin);
            }

            if (lin != null) {
                if (paraula.igual(aux)) {
                    exist = true;
                }
            }
        } while (lin != null);
        fic.tancar();

        return exist;
    }

    public boolean igual(Paraula p) {
        if (getTam() != p.getTam()) {
            return false;
        }

        for (int i = 0; i < getTam(); i++) {
            if (pal[i] != p.pal[i]) {
                return false;
            }
        }

        return true;
    }

    public void toMayusculas() {
        char[] abecedariominusculas = "abcdefghijklmnñopqrstuvwxyzç".toCharArray();
        char[] abecedariomayusculas = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZÇ".toCharArray();

        for (int i = 0; i < ind; i++) {
            if (pal[i] == 0) {
                continue;
            }
            for (int j = 0; j < abecedariominusculas.length; j++) {
                if (pal[i] == abecedariominusculas[j]) {
                    pal[i] = abecedariomayusculas[j];
                }
            }
        }
    }

    private void toMinusculas() {
        char[] abecedariominusculas = "abcdefghijklmnñopqrstuvwxyzç".toCharArray();
        char[] abecedariomayusculas = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZÇ".toCharArray();

        for (int i = 0; i < ind; i++) {
            if (pal[i] == 0) {
                continue;
            }
            for (int j = 0; j < abecedariomayusculas.length; j++) {
                if (pal[i] == abecedariomayusculas[j]) {
                    pal[i] = abecedariominusculas[j];
                }
            }
        }
    }

    public char[] getChar() {
        return pal;
    }

    public char getEspecificChar(int posicion) {
        return pal[posicion];
    }

    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < ind; i++) {
            //Para ver que el caracter nos ea nul
            if (pal[i] == 0) {
                break;
            }
            res = res + pal[i];
        }
        return res;
    }
}
