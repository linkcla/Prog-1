package wordle;

import java.util.Date;

public class Estadisticas {

    public void imprimir() {
        char[] lin;

        FI fic = new FI(new Paraula("estadisticas.txt".toCharArray()));
        System.out.println();//estetica

        do {
            lin = fic.llegirLinia();
            if (lin != null) {
                System.out.println(lin);
            }
        } while (lin != null);

        fic.tancar();
        System.out.println();//estetica
    }

    public void guardar(Paraula idioma, Paraula nombre, Paraula[] palabrasMetidas, Paraula palabraObjetivo) {
        final FO fico = new FO(new Paraula("estadisticas.txt".toCharArray()), true);
        final Paraula paraula = new Paraula();

        //Vamos añadiendo la estructura de las estadísticas: FECHA#IDIOMA#NOMBRE#PALABRAS INTRODUCIDAS##PALABRA OBJETIVO
        paraula.posarArray(((new Date() + "#")).toCharArray());
        paraula.posarArray((idioma + "#").toCharArray());
        paraula.posarArray(((nombre + "#")).toCharArray());

        for (int i = 0; i < palabrasMetidas.length; i++) {
            //Hemos encontrado la última palabra introducida, por ende hacemos el break
            if (palabrasMetidas[i] == null) {
                break;
            }
            paraula.posarArray(palabrasMetidas[i].getChar());
            paraula.posar('#');
        }
        paraula.posar('#');
        paraula.posarArray(palabraObjetivo.getChar());
        fico.GravarLinia(paraula);
        fico.tancar();
    }
}
