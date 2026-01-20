package tutorial.numbers;

public class Main {

    public static void main(String[] args) {

        double num1 = 123.42d;
        long giorni = 100;
        long giorni2 = 70;
        long giorniTot = giorni + giorni2;

        System.out.println((num1 / giorniTot) * giorni);
        System.out.println((num1 / giorniTot) * giorni2);


        double n = 0.35d;

        Integer f = (int) Math.ceil(n);

        System.out.println("ceil: " + f);

        Double op = ((1 * 35d) / 100d);
        System.out.println("OP: " + op);
        Integer numPlacementEff = (int) Math.ceil(op);
        System.out.println("CELL: " + numPlacementEff);

        System.out.println("CELL2: " + ((1 * 35d) / 100d));


        String[] steps = {"datiGenerali", "budget", "fornitori", "placement", "dettaglioRicavi", "fatture", "pianoEconomicoCommessa"};

        System.out.println(StringPosition(steps, "dettaglioRicavi"));
    }


    public static Integer StringPosition(String[] array, String search) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(search))
                return i;
        }
        return null;
    }

}
