package wordle;

import com.diogonunes.CC;
import java.util.Random;

public class Joc {

    //Longitud máxima de una palabra
    private final int LONGITUD_PALABRAS = 5;
    //Número máximos de intentos por ronda
    private final int LONGITUD_RONDAS = 6;
    //Clase LT para la lectura
    private final LT lt = new LT();
    private final Estadisticas estadisticas = new Estadisticas();

    private final Paraula[] palabrasMetidas = new Paraula[LONGITUD_RONDAS];
    private Paraula nom; //Nombre del jugador

    private Paraula palabracomparar;
    private Paraula palabraobjetivo;
    private char idioma;
    private Integer seed = -1;

    public Joc(boolean multijugador) {
        jugar(multijugador);
    }

    public void jugar(boolean multijugador) {

        //---------- Nombre e idioma--------------------------------------------------
        System.out.print("Nom del jugador? ");
        char[] aux = lt.llegirLiniaC();
        while (aux == null) {
            System.out.println("Error, introdueix el teu nom: ");
            aux = lt.llegirLiniaC();
        }
        nom = new Paraula(aux);
        idioma = idioma();
        //----------------------------------------------------------------------------

        //---------- Relacionado con la palbra objetivo ------------------------------
        PalabraAleatoria palabraRandom = new PalabraAleatoria(idioma);
        //----------

        //---------- Multijugador -----------------------------------------------------
        if (multijugador) {
            System.out.println("Has entrat al mode multijugador");
            System.out.println("Quina es la seva seed? ");
            seed = lt.llegirSencer();
            while ((seed == null) || (seed == 0)) {
                System.out.println("Error, introdueix una seed valida: ");
                seed = lt.llegirSencer();
            }
        }
        //-----------------------------------------------------------------------------

        //---------- Relacionado con la palbra objetivo -------------------------------
        palabraobjetivo = new Paraula(palabraRandom.pObj(seed));
        palabraobjetivo.toMayusculas();
        System.out.println("La seva seed es :" + palabraRandom.getSeed());
        System.out.println();
        //-----------------------------------------------------------------------------

        //---------- Pista -------------------------------------------------------------
        System.out.println("Vol tenir una pista?");
        System.out.println("Aquesta pista li dirà un caracter que està present a la paraula objectiu.");
        System.out.println("     0 = no");
        System.out.println("     1 = si");
        Integer opciopista = lt.llegirSencer();
        while ((opciopista == null) || ((opciopista != 1) && (opciopista != 0))) {
            System.out.println("Error, introdueix un 0 o un 1 :");
            opciopista = lt.llegirSencer();
        }

        //Creamos un random de la longitud de las palabras y extraemos un caracter random de la palabra objetivo.
        Random ran = new Random();
        if (opciopista == 1) {
            System.out.println("El caracter pista es: " + palabraobjetivo.getEspecificChar(ran.nextInt(LONGITUD_PALABRAS)));
            System.out.println();//estetica
        }
        //--------------------------------------------
        
        System.out.println("Ha d'introduir paraules de 5 caràcters.");
        for (int i = 1; i <= LONGITUD_RONDAS; i++) {
            if (i != 1) {
                System.out.println("*                                         *");
            }
            System.out.println("*******************************************");
            System.out.println("    Jugador: " + nom + "  ||  Idioma: " + printIdioma(idioma));
            System.out.println("*******************************************");
            System.out.print("Jugada? ");

            //Ya tenemos validada la palabra y sabemos si es igual o no.
            pedirYValPal(i);

            //Imprime todas las palabras introducidas anteriormente
            for (int j = 0; j < LONGITUD_RONDAS; j++) {
                //Hemos encontrado la última palabra a leer
                if (palabrasMetidas[j] == null) {
                    break;
                }
                imprimir(palabrasMetidas[j], palabraobjetivo);
            }

            //Las palabras son iguales, paramos de ejecutar el main
            if (palabracomparar.igual(palabraobjetivo)) {
                System.out.println();//estetica
                System.out.println("CORRECTE!! Ha encertat amb " + i + " intents.");
                estadisticas.guardar(printIdioma(idioma), nom, palabrasMetidas, palabraobjetivo);
                new Menu();
                break;
            }

            //Se han acabado los intentos permitidos
            if (i == LONGITUD_RONDAS) {
                System.out.println();//estética
                System.out.println("Numero maxim de jugades, la paraula objectiu era: " + palabraobjetivo);
                estadisticas.guardar(printIdioma(idioma), nom, palabrasMetidas, palabraobjetivo);
                new Menu();
                break;
            }

        }
    }

//---------------------------------------------- VALIDAR PALABRA ------------------
    private void pedirYValPal(int indice) {
        boolean cond = false;

        while (!cond) {
            palabracomparar = new Paraula(lt.llegirLiniaC());
            palabracomparar.toMayusculas();

            while (palabracomparar.getTam() != 5) {
                System.out.println("Error, la paraula ha de tenir 5 caracters.");
                System.out.println("Jugada? ");
                palabracomparar = new Paraula(lt.llegirLiniaC());
                palabracomparar.toMayusculas();
            }

            cond = palabracomparar.existAlDic(palabracomparar, idioma);
            palabracomparar.toMayusculas();
            if (!cond) {
                System.out.println("Error! La paraula no existeix en el diccionari: ");
                System.out.println("Jugada? ");
            }
        }

        //Añadimos la palabra a la lista de todas las palabras introducidas durante la ronda
        palabrasMetidas[indice - 1] = palabracomparar;
    }

//---------------------------------------------------------------------------------  
//----------------------------------------------- IDIOMA --------------------------
    private char idioma() { //conseguimos y validamos el caracter que significará el idioma que ha elegido el jugador
        System.out.println("Idioma del joc? ");
        System.out.println("e = Espanyol");
        System.out.println("a = Anglés");
        System.out.println("c = Català");

        Character aux = lt.llegirCaracter();
        while ((aux == null) || ((aux != 'e') && (aux != 'a') && (aux != 'c'))) {
            System.out.println("Error, introdueix un caracter vàlid: ");
            System.out.println("e = espanyol");
            System.out.println("a = anglés");
            System.out.println("c = català");
            aux = lt.llegirCaracter();
        }
        return aux;
    }

//--------------------------------------------------------------------------------- 
//------------------------------------------- PRINT IDIOMA ------------------------
    //Para no imprimir el caracter lo que hacemos es un swuitch para imprimir la palabra entera.
    private Paraula printIdioma(char i) {
        Paraula res;

        switch (i) {
            case 'e':
                res = new Paraula("Espanyol".toCharArray());
                break;
            case 'a':
                res = new Paraula("Anglés".toCharArray());
                break;
            default:
                res = new Paraula("Català".toCharArray());
                break;
        }

        return res;
    }
//---------------------------------------------------------------------------------

//---------------------------- IMPRIMIR COLORES ------------------------------------
    public void imprimir(Paraula intento, Paraula objetivo) {
        char[] objetivoChar = objetivo.getChar();
        char[] intentoChar = intento.getChar();

        // Para mirar si ya la hemos usado o no
        boolean[] usadaObjetivo = new boolean[5];
        boolean[] usadaIntroducida = new boolean[5];
        // Una array que te indica de que color ha de pintatar despues cada caracter (1 = verde; 2 = amarillo; 3 = gris)
        int[] colores = new int[]{3, 3, 3, 3, 3};

        // Miramos si las letras de la objetivo y de la introducida estan en la misma posición
        for (int i = 0; i < objetivo.getTam(); i++) {
            if (objetivoChar[i] == intentoChar[i]) {
                colores[i] = 1;
                usadaObjetivo[i] = true;
                usadaIntroducida[i] = true;
            }
        }

        // Miramos si las letras de la objetivo estan en la introducida pero en un sitio distinto
        for (int i = 0; i < objetivo.getTam(); i++) {
            if (usadaObjetivo[i]) {
                continue;
            }

            for (int j = 0; j < objetivo.getTam(); j++) {
                if (usadaIntroducida[j]) {
                    continue;
                }
                if (intentoChar[i] == objetivoChar[j]) {
                    colores[i] = 2;
                    usadaObjetivo[i] = true;
                    usadaIntroducida[j] = true;
                    break;
                }
            }
        }

        printPalabra(intentoChar, colores);
    }

    private void printPalabra(char[] palabraimprimir, int[] col) { //va imprimirendo cada caracter de la introducida de color dependiendo del numero que hay en la array colores. 

        //Estética
        System.out.print("*              ");//estetica

        for (int i = 0; i < LONGITUD_PALABRAS; i++) {
            switch (col[i]) {
                case 1:
                    CC.impr((palabraimprimir[i] + " "), CC.FVerd, CC.TNegre);
                    break;
                case 2:
                    CC.impr((palabraimprimir[i] + " "), CC.FGroc, CC.TNegre);
                    break;
                default:
                    System.out.print(palabraimprimir[i] + " ");
                    break;
            }
        }
        System.out.print("                 *");//estetica
        System.out.println("");
    }

//---------------------------------------------------------------------------------
}
