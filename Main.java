import java.util.Scanner;
public class Main {
    public static long longOf(String s) {
        return Long.parseLong(s);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library lib = new Library();
        while(scanner.hasNext()){
            String[] parameters = scanner.nextLine().split(" ");
            switch (parameters[0]){
                case "arrive":
                    lib.arrive(parameters[1], longOf(parameters[2]));
                    break;
                case "exit":
                    lib.exit(parameters[1], longOf(parameters[2]));
                    break;
                case "isInLib":
                    System.out.println(lib.isInLib(parameters[1]) ? "YES" : "NO");
                    break;
                case "returnBook":
                    lib.returnBook(parameters[1], parameters[2]);
                    break;
                case "totalTimeInLib":
                    System.out.println(lib.totalTimeInLib(parameters[1], longOf(parameters[2]), longOf(parameters[3])));;
                    break;
                case "addNewBook":
                    lib.addNewBook(parameters[1], longOf(parameters[2]));
                    break;
                case "shouldBring":
                    lib.shouldBringBook(parameters[1], parameters[2]);
                    break;
                case "allPersonCurrentBook":
                    lib.allPersonCurrentBooks(parameters[1]);
                    break;
                case "allPersonHave":
                    lib.allPersonsHaveThisBook(parameters[1]);
                    break;
            }
        }
    }
}