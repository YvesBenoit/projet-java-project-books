import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;


public class ToolBox {

    public static void main(String[] args) {

        // affichage pour saisie utilisateur du nom de fichier à lire
        String fileName = getStringOnStdIn("nom du fichier input");

        //lecture du fichier et stockage dans la table de hachage
        HashMap<String, Integer> myHM = readFileToHashMap(fileName);

        // tri de la table
        HashMap<String, Integer> myHMSorted = sortWithVDecrease(myHM);

        // saisie du nombre d'éléments de la hash table à afficher
        int max = getIntOnStdIn("profondeur du Top");

        // itération pour n'afficher que le nombre d'éléments demandés
        Iterator itV = myHMSorted.values().iterator();
        Iterator itK = myHMSorted.keySet().iterator();
        int i = 0;
        while (itV.hasNext()) {
            System.out.println("Top" + ++i + " \"" + itK.next() + "\" apparait " + itV.next() + " fois");
            if (i == max) {
                break;
            }
        }
    }


    // Lecteur de fichier vers Hash Map
    public static HashMap readFileToHashMap(String nameFile) {
        HashMap<String, Integer> listReadedLines = new HashMap<>();
        //Scanner scFile;
        int nbReadedLines = 0;
        while (true) {
            try ( Scanner scFile = new Scanner(new File(nameFile))) {
                for (int i = 0; (scFile.hasNextLine()); ++i) {     // boucle tant qu'il y a une ligne suivante
                    // Pour chaque mot, incrémenter sa valeur
                    String readedLine = scFile.nextLine();      // lecture d'une ligne
                    if (readedLine.length() > 0) {
                        // si dans la liste de lignes déja lues, je retrouve la ligne en cours (<==> la valeur associée à la clef est <> de 0), je recupere la valeur associée
                        nbReadedLines = (listReadedLines.get(readedLine) != null) ? listReadedLines.get(readedLine) : 0;
                        // et je la modifie en l'incrémentant   (<=> je compte les occurences de chaque clef
                        listReadedLines.put(readedLine, nbReadedLines + 1);
                    }
                }
                return listReadedLines;
            } catch (FileNotFoundException e) {
                System.out.println("Une erreur est survenue !\n" + e.getLocalizedMessage());
          //      scFile.nextLine();
            }
        }
    }

    public static HashMap<String, Integer> sortWithVDecrease(HashMap<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        HashMap<String, Integer> map_apres = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list)
            map_apres.put(entry.getKey(), entry.getValue());
        return map_apres;
    }

    public static HashMap<String, Integer> sortWithVIncrease(HashMap<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        HashMap<String, Integer> map_apres = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list)
            map_apres.put(entry.getKey(), entry.getValue());
        return map_apres;
    }

    public static String getStringOnStdIn(String userGuide) {
/*----------------------------------------------------------------------
Saisie d'une string sur StdIn et return du résultat
 ----------------------------------------------------------------------*/
        Scanner sc = new Scanner(System.in);
        String res;
        System.out.print(userGuide + "--> please enter a string : ");
        res = sc.nextLine();
        return res;


    }

    public static int getIntOnStdIn(String userGuide) {
/*----------------------------------------------------------------------
affichage text utilisateur + saisie clavier + return de l'int saisi
 ----------------------------------------------------------------------*/
        Scanner sc = new Scanner(System.in);
        int res = 0;
        while (true) {
            System.out.print(userGuide + " --> Please, enter an int : ");
            try {
                res = sc.nextInt();
                return res;
            } catch (InputMismatchException e) {
                System.out.println("Don't be so stupid, an int is required !");
                sc.nextLine();
            }
        }

    }


}

