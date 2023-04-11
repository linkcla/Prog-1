package wordle;

import java.util.Random;

public class PalabraAleatoria {

    private final Random ran = new Random();
    private char[] obj;
    private final Paraula diccionario;
    private int linea;
    private int numeroLineas;

    //El carácter introducido ya sabemos que solo puede ser 'a', 'e' o 'c', lo hemos validado en la clase menú.
    //Posteriormente, ejecutamos pObj, subprogram que nos extraerá una palabra random del idioma elegido con el character.
    public PalabraAleatoria(char i) {
        switch (i) {
            case 'a': {
                diccionario = new Paraula("wordle_ang_solucions.txt".toCharArray());
                break;
            }
            case 'e': {
                diccionario = new Paraula("wordle_esp_solucions.txt".toCharArray());
                break;
            }
            default: {
                diccionario = new Paraula("wordle_cat_solucions.txt".toCharArray());
                break;
            }
        }

    }

    public char[] pObj(int seed) {
        FI fic = new FI(diccionario);
        //Sabiendo las líneas que tiene sacamos un random, este nos indicara la línea donde está la palabra random.
        numeroLineas = fic.obtenerLineas();
        linea = ran.nextInt(numeroLineas);

        //Si la seed es == -1 significara que el jugador ha decidido jugar en modo individual
        if (seed != -1) {
            linea = seed;
        }
        int indice = 0;

        char[] lin;
        do {
            lin = fic.llegirLinia();

            if (lin != null) {
                indice++;

                //imprimimos la palabra que está en la línea nº 'x'(el numero que haya salido con el random.
                if (indice == linea) {
                    obj = lin;
                }
            }
        } while ((lin != null) || (indice == linea));
        fic.tancar();

        return obj;
    }

    public int getSeed() {
        return linea;
    }

    public int getNumeroLineas() {
        return numeroLineas;
    }
}
