import java.util.Arrays;
import java.util.Scanner;

public class Labration1 {
    private static final Scanner s = new Scanner(System.in);
    private static final int[][] elpriser = new int[24][2];
    public static void main(String[] args) {
        while (true) { //evighetsloop för att alltid komma tillbaka till menyn
            switch (showMenuAndGetUserInput()) {
                case "1" -> inmatning();
                case "2" -> minMaxMedel();
                case "3" -> sortera();
                case "4" -> bestLaddningstid();
                case "e", "E" -> System.exit(0);
                default -> System.out.println("Menyval saknas, försök igen!");
            }
        }
    }
    public static String showMenuAndGetUserInput(){
        System.out.println("Elpriser");
        System.out.println("========");
        System.out.println("1. Inmatning");
        System.out.println("2. Min, Max och Medel");
        System.out.println("3. Sortera");
        System.out.println("4. Bästa Laddningstid (4h)");
        System.out.println("e. Avsluta");

        return s.nextLine();
    }
    public static void inmatning() {
        while (true) {
            System.out.println("Skriv in elpriserna för dygnets alla timmar, separerade med \", \".");
            System.out.println("Exempel för tiderna 00-01, 01-02, 02-03, skriv \"50, 102, 680\".");
            String[] elpriserInput = s.nextLine().split(", "); //för att få in inputsen i en array men utan , och mellanslag
            boolean allInputsAreInts = true;
            if (elpriserInput.length != 24) { //om elpriser input inte innehåller 24 värden
                System.out.println("Felaktig inmatning, mata in 24 värden!");
                continue; //för att hamna tillbaka längst upp i loopen
            }
            for (String value : elpriserInput) {
                try {
                    Integer.parseInt(value); //för att kolla så att jag kan göra om alla strings till integers
                } catch (
                        NumberFormatException e) { //om något värde inte går att göra om till en int ska exception fångas och booleanen bli falsk
                    allInputsAreInts = false;
                }
            }
            if (!allInputsAreInts) {
                System.out.println("Felaktig inmatning, kostnaderna får enbart bestå av siffror!");
                continue; //för att hamna tillbaka längst upp i loopen
            }
            for (int i = 0; i < elpriserInput.length; i++) {
                elpriser[i] = new int[]{i, Integer.parseInt(elpriserInput[i])};//för att lägga in i som klockslag och inputs omgjorda till ints i arrayen elpriser
            }
            break; //för att komma ur whileloopen
        }
    }

    public static void minMaxMedel() {
        double sum = 0.0;
        int largestValue = Integer.MIN_VALUE;
        int lowestValue = Integer.MAX_VALUE;
        int mostExpensiveHour = 0;
        int leastExpensiveHour = 0;
        for (int i = 0; i < elpriser.length; i++) {
            sum += elpriser[i][1];
            if (elpriser[i][1] > largestValue) { //För att jämföra och hitta billigaste och dyraste kostnad(1an) och vilket arrayfack det är stoppat i (index representerar timman)
                largestValue = elpriser[i][1];
                mostExpensiveHour = i;
            }
            if (elpriser[i][1] < lowestValue) {
                lowestValue = elpriser[i][1];
                leastExpensiveHour = i;
            }
        }
        System.out.printf("Dygnets högsta pris är timma: %02d-%02d, då det kostar: %d öre/timma.%n", mostExpensiveHour, (mostExpensiveHour + 1), largestValue); //%02d för att lägga till två decimaler av nollor
        System.out.printf("Dygnets lägsta pris är timma: %02d-%02d då det kostar: %d öre/timma.%n", leastExpensiveHour, (leastExpensiveHour + 1), lowestValue); //vad gör %s? eftersom lowestValue är en int? %n ger ny rad
        System.out.printf("Dygnets medelpris är: %.2f öre/timma.%n", sum/ elpriser.length);
    }
    public static void sortera() {
        int [][] copyOfElpriser = Arrays.copyOf(elpriser, elpriser.length);
        System.out.println("Timpris sorterat från billigast till dyrast samt vilket klockslag det gäller:");
        for (int i = 0; i < copyOfElpriser.length - 1; i++) {
            for (int j = 0; j < copyOfElpriser.length - i - 1; j++) {
                if (copyOfElpriser[j][1] > copyOfElpriser[j +1][1]) {
                    int[] temp = copyOfElpriser[j];
                    copyOfElpriser[j] = copyOfElpriser[j + 1];
                    copyOfElpriser[j + 1] = temp;
                }
            }
        }
        for (int[] ints : copyOfElpriser) {
            System.out.printf("%02d-%02d %s öre%n", ints[0], ints[0] + 1, ints[1]);
        }
    }
    public static void bestLaddningstid() {
        int lowestAveragePrice = Integer.MAX_VALUE;
        int firstHour = 0;
        for (int i = 0; i < elpriser.length -3; i++) {
            int sum = elpriser[i][1] + elpriser[i + 1][1] + elpriser [i + 2][1] + elpriser[i +3][1];

            if (sum < lowestAveragePrice) {
                lowestAveragePrice = sum;
                firstHour = i;

            }
        }
        System.out.printf("För att få bästa möjliga laddningstid (totalt: %s öre) bör du börja ladda bilen klockan: %s. Medelpriset per timma blir då: %s öre.%n", lowestAveragePrice, firstHour, lowestAveragePrice/4);
    }
}