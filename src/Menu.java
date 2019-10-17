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
    private static String selectedFile = "";
    private static ArrayList<String> bookInArrayList = new ArrayList<>();
    private static HashMap<String, Integer> bookInHashMap = new HashMap<>();

    public static void myProg(String[] args) {
        boolean getOut = false;  // provoque la sortie du programme si vrai
        int action = 0;           // represente l'action a effectuer (


        // recuperation des fichiers passés en arguments dans une ArrayList de String
//        ArrayList<String> listOfFiles = new ArrayList<>();
        for (int i = 0; i < args.length; ++i) {
            if (Files.exists(Path.of(args[i]))) {      // verifie si la string récupérée correspond à un fichier
                if (args[i].indexOf("_TRAITE")!=-1) {    //  verifie que "_TRAITE" est ans le nom du fichier
                    listOfFiles.add(args[i]);
                }
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
        System.out.println("[4] Afficher les mots uniquement dans ce fichier");
        System.out.println("[5] Afficher TODO");
        System.out.println("[6] Retour");
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
                if (userChoiceInMenu < 1 || userChoiceInMenu > 4) {
                    System.out.println("--- Saisie invalide 2 ---");
                } else {
                    res = true;
                }
                break;
            case 2:
                if (userChoiceInMenu < 1 || userChoiceInMenu > 6) {
                    System.out.println("--- Saisie invalide 3 ---");
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
        switch (action) {   // le case se lit comme suit : le nombre de digit de action donne le niveau de menu sur lequel l'action est déclenchée
            // et se lit de droite à gauche  (ie : 634 signifie choix 4 au 1er menu ; choix 3 au 2eme menu et choix 6 au 3eme menu
            case 1:
            case 14:
                Fichier.listFile(listOfFiles);
                break;
            case 2:
                Fichier.addFile(listOfFiles);
                break;
            case 3:    // supprime l'entrée de la liste (ne supprime pas physiquement le fichier)
                Fichier.deleteFile(listOfFiles);
                break;
            case 4:     //descendre d'un niveau de menu
                ++level;
                break;
            case 34:   // descendre au niveau des traitements sur un fichier ==> le fichier doit être sélectionné !
                if (selectedFile.length() != 0) {
                    ++level;
                    break;
                } else {
                    System.out.println("ATT : ------>   Vous n'avez pas selectionné de fichier !!!!!");
                    System.out.println("ATT : ------>   Merci de selectionner un fichier à traiter !");
                    action = 24;       // Rem : il n'y a pas de break dans ce cas ==> on force à l'action de selection d'un livre et on continue en séquence..
                                       // ==> Attention à l'ordre des 'case'
                }
                break;
            case 5:   // sortir du programme   (true sera récupéré dans le booleen getOut permettant de ne plus itérer indéfiniment sur des saisies utilisateur.
                return true;
            case 24:    // selection d'un book pour utilisation sur les action du 3eme menu
                bookInHashMap.clear();
                bookInArrayList.clear();
                selectedFile = Fichier.chooseFile(listOfFiles);  // selection d'un livre pour utilisation sur les action du 3eme menu
                bookInArrayList = Fichier.readFileToArrayList(selectedFile);  // chargement du livre pour utilisation brute
                bookInHashMap = Fichier.readFileToHashMap(selectedFile);      // chargement du livre avec dédoublonnage des mots et comptabilisation des occurences de chacun d'eux.
                break;
            case 44:     //remonter d'un niveau de menu
            case 634:
                userChoice[level--] = 0;   // suppression du choix utilisateur sur le menu quitté , Puis modification de l'indicateur de profondeur de menu
                break;
            case 134:
                System.out.println(selectedFile + " contient " + bookInArrayList.size() + " lignes\n");
                break;
            case 234:
                System.out.println(selectedFile + " contient " + bookInHashMap.size() + " mots différents \n");
                break;
            case 334:    // affichage des mots les plus utilisés d'un livre
                Fichier.topWords(bookInHashMap);
                break;
            case 434:    //afficher les mots qui sont présents seulement dans le livre sélectionné et dans aucun des autres livres de la liste
                System.out.println(Fichier.uniqueWords(selectedFile,listOfFiles));
                break;
            case 534:
                System.out.println("-------------->   Sorry still to do !!!!!!!!!!");
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
