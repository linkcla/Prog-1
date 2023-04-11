package wordle;

public class Wordle {

    public static void main(String[] args) {
        (new Wordle()).inici();
    }

    private void inici() {
        new Menu();
    }
}
/*
Wordle es un juego de adivinar palabras, que tiene un formato de crucigrama. 
En él, tienes que adivinar una palabra en seis intentos, en los que no se
te dan más pistas que decirte qué letras de las que has puesto están dentro 
de la palabra.
*/
