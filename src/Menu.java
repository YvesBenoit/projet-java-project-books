import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    private static int level = 0;
    private static int maxLevel = 3;         // profondeur maximale de menus
    private static int[] userChoice = new int[maxLevel];       // choix de l'utilisateur (issus de stdIn)
    private static ArrayList<String> listOfFiles = new ArrayList<>();
    private static String selectedFile ="";
    private static ArrayList<String> bookInArrayList = new ArrayList<>();
    private static HashMap<String, Integer> bookInHashMap = new HashMap<>();

    public static void myProg(String[] args) {
        boolean getOut = false;  // provoque la sortie du programme si vrai
        int action = 0;           // represente l'action a effectuer (


        // recuperation des fichiers passés en arguments dans une ArrayList de String
//        ArrayList<String> listOfFiles = new ArrayList<>();
        for (int i = 0; i < args.length; ++i) {
            if (Files.exists(Path.of(args[i]))) {      // verifie si la string récupérée correspond à un fichier
                listOfFiles.add(args[i]);
            }
        }

        do {      // tant que on ne donne pas l'ordre de quitter le programme (cf booleen getOut)

            Menu.displayMenu();
            do {
                userChoice[level] = Menu.getUserChoice();
            } while (!Menu.isUserChoiceValid(userChoice[level]));

            action = (userChoice[2] * 100) + userChoice[1] * 10 + userChoice[0];
            getOut = Menu.selectedAction(action);

        } while (!getOut);

    }


    public static void displayMenu() {
        switch (level) {
            case 0:
                displayMainMenu();
                break;
            case 1:
                displayLowerMenu4();
                break;
            case 2:
                displayLowerMenu43();
                break;
            default:
                System.out.println("pb dans la profondeur de menus (level dans displayMenu) !!! " + level);
                break;
        }
    }

    public static void displayMainMenu() {
        System.out.println();
        System.out.println("*****   Menu Principal *****");
        System.out.println("[1] Lister les fichiers");
        System.out.println("[2] Ajouter un fichier");
        System.out.println("[3] Supprimer un fichier");
        System.out.println("[4] Afficher des informations sur un livre");
        System.out.println("[5] Quitter le programme");
        System.out.println();
    }

    public static void displayLowerMenu4() {
        System.out.println("***** 1er  Sous Menu Afficher des informations sur un livre *****");
        System.out.println("[1] Lister les fichiers");
        System.out.println("[2] Choisir un des fichiers");
        System.out.println("[3] Afficher des informations sur le fichier choisi");
        System.out.println("[4] Retour");
        System.out.println();
    }

    public static void displayLowerMenu43() {
        System.out.println("***** 2eme Sous Menu Afficher des informations sur le fichier choisi *****");
        System.out.println("[1] Afficher le nombre de lignes du fichier");
        System.out.println("[2] Afficher le nombre de mots du fichier");
        System.out.println("[3] Afficher le Top des mots du fichier");
        System.out.println("[4] Retour");
        System.out.println();
    }

    public static boolean isUserChoiceValid(int userChoiceInMenu) {
        boolean res = false;

        switch (level) {
            case 0:
                if (userChoiceInMenu < 1 || userChoiceInMenu > 5) {
                    System.out.println("--- Saisie invalide 1 ---");
                } else {
                    res = true;
                }
                break;
            case 1:
            case 2:
                if (userChoiceInMenu < 1 || userChoiceInMenu > 4) {
                    System.out.println("--- Saisie invalide 2 ---");
                } else {
                    res = true;
                }
                break;
            default:
                System.out.println("--- Pb de profondeur de menu (level dans isUserChoiceValid) ---");
                res = false;
                break;
        }
        return res;
    }

    public static boolean selectedAction(int action) {
        int res = -1;
//        System.out.println("action : " +action);
        switch (action) {
            case 1:
            case 14:
                Fichier.listFile(listOfFiles);
                break;
            case 2:
                Fichier.addFile(listOfFiles);
                break;
            case 3:
                Fichier.deleteFile(listOfFiles);
                break;
            case 4:     //descendre d'un niveau de menu
            case 34:
                ++level;
                break;
            case 5:   // sortir du programme
                return true;
            case 24:
                bookInHashMap.clear();
                bookInArrayList.clear();
                selectedFile = Fichier.chooseFile(listOfFiles);
                bookInArrayList = Fichier.readFileToArrayList(selectedFile);
                bookInHashMap = Fichier.readFileToHashMap(selectedFile);
                break;
            case 44:     //remonter d'un niveau de menu
            case 434:
                userChoice[level] = 0;
                --level;
                break;
            case 134:
                System.out.println(selectedFile +" contient " + bookInArrayList.size() + " lignes\n");
                break;
            case 234:
                System.out.println(selectedFile +" contient " + bookInHashMap.size() + " mots différents \n");

                break;
            case 334:
                Fichier.topMots(bookInHashMap);
                break;

            default:
                System.out.println("--- Pb d'action en entrée (action) ---");
        }
        return false;

    }

    public static int getUserChoice() {
/*----------------------------------------------------------------------
saisie clavier + return de l'int saisi
 ----------------------------------------------------------------------*/
        Scanner sc = new Scanner(System.in);
        int res = -1;
        while (true) {
            System.out.print(" --> Votre choix : ");
            try {
                res = sc.nextInt();
                sc.nextLine();
                return res;
            } catch (InputMismatchException e) {
                System.out.println("Don't be so stupid, an int is required !");
                sc.nextLine();
            }
        }

    }

}
