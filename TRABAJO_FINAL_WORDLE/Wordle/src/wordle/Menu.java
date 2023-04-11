package wordle;

public final class Menu {

    private final LT tec = new LT();

    public Menu() {
        menuPrincipal();
    }

    public void menuPrincipal() {
        System.out.println("*******************************************");
        System.out.println("*    MENU PRINCIPAL DEL JOC DEL WORDLE    *");
        System.out.println("*******************************************");
        System.out.println("*    1 Jugar                              *");
        System.out.println("*    2 Estadístiques                      *");
        System.out.println("*    s Sortir                             *");
        System.out.println("*******************************************");
        System.out.println();

        System.out.print("Opció? ");
        choose();
    }

    private void choose() {
        Character opcio = tec.llegirCaracter();
        while ((opcio == null) || ((opcio != '1') && (opcio != '2') && (opcio != 's'))) {
            System.out.print("Error, introdueix 1, 2 o 's': ");
            opcio = tec.llegirCaracter();
        }

        switch (opcio) {
            case ('1'):
                new Joc(multijugador());
                break;
            case ('2'):
                new Estadisticas().imprimir();
                menuPrincipal();
                break;
            default:
                System.out.println("Adeu.");
                break;
        }

    }

    private boolean multijugador() {
        boolean multijugador = false;
        System.out.println("Vols jugar al modo multijugador?");
        System.out.println("     0 = no");
        System.out.println("     1 = si");
        Integer aux = tec.llegirSencer();
        while ((aux == null) || ((aux != 1) && (aux != 0))) {
            System.out.println("Error, introdueix un 0 o un 1 :");
            aux = tec.llegirSencer();
        }
        if (aux == 1) {
            multijugador = true;
        }

        return multijugador;
    }
}
