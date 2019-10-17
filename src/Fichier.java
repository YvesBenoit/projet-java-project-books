import javax.management.ListenerNotFoundException;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class Fichier {

    // Lecteur de fichier vers Hash Map
    public static HashMap readFileToHashMap(String nameFile) {
        HashMap<String, Integer> listLinesRead = new HashMap<>();
        //Scanner scFile;
        int nbLinesRead = 0;

        try (Scanner scFile = new Scanner(new java.io.File(nameFile))) {
            for (int i = 0; (scFile.hasNextLine()); ++i) {                              // boucle tant qu'il y a une ligne suivante
                String lineRead = scFile.nextLine();                                         // lecture d'une ligne
                if (lineRead.length() > 0) {                                                 // si ligne vide , rien n'est fait ==> itération sur ligne suivante
                    nbLinesRead = (listLinesRead.get(lineRead) != null) ? listLinesRead.get(lineRead) : 0;    // Dans la liste de lignes lues, si je trouve la ligne en cours
                    // (<==> la valeur associée à la clef est <> de 0),
                    // alors, je recupere la valeur associée
                    listLinesRead.put(lineRead, nbLinesRead + 1);                                             // <---  et je la modifie en l'incrémentant
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Une erreur est survenue !\n" + e.getLocalizedMessage());
            //      scFile.nextLine();
        }
        return listLinesRead;
    }

    // Lecteur de fichier vers ArrayList
    public static ArrayList readFileToArrayList(String nameFile) {
        ArrayList<String> bookInArrayList = new ArrayList<>();
        //Scanner scFile;
        int nbLinesRead = 0;

        try (Scanner scFile = new Scanner(new java.io.File(nameFile))) {
            for (int i = 0; (scFile.hasNextLine()); ++i) {                              // boucle tant qu'il y a une ligne suivante
                String lineRead = scFile.nextLine();                                         // lecture d'une ligne
                if (lineRead.length() > 0) {                                                 // si ligne vide , rien n'est fait ==> itération sur ligne suivante
                    bookInArrayList.add(lineRead);   // ajout de la ligne lue sur SstdIn dans la ArrayList
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Une erreur est survenue !\n" + e.getLocalizedMessage());
            //      scFile.nextLine();
        }
        return bookInArrayList;
    }

    public static boolean isFileExist(String fileName) {
        File f = new File(fileName);
        return f.exists();
    }

    public static void listFile(ArrayList<String> listOfFiles) {
        for (int i = 0; i < listOfFiles.size(); ++i) {
            System.out.println(i + " : " + listOfFiles.get(i));
        }

    }

    public static void addFile(ArrayList<String> listOfFiles) {

        listOfFiles.add(ToolBox.getStringOnStdIn("Fichier à ajouter"));
    }

    public static void deleteFile(ArrayList<String> listOfFiles) {
        listFile(listOfFiles);
        int i = 0;
        while (i < 1 || i > listOfFiles.size()) {
            i = ToolBox.getIntOnStdIn("Numero du fichier à supprimer");
        }
        listOfFiles.remove(i);
    }

    public static String chooseFile(ArrayList<String> listOfFiles) {
        listFile(listOfFiles);
        int i = 0;
        while (i < 1 || i > listOfFiles.size()) {
            i = ToolBox.getIntOnStdIn("Numero du fichier à selectionner");
        }
        return listOfFiles.get(i);
    }


    public static void topMots(HashMap<String,Integer> myHM) {

         // tri de la table
        HashMap<String, Integer> myHMSorted = ToolBox.sortWithVDecrease(myHM);

        // saisie du nombre d'éléments de la hash table à afficher
        int max = ToolBox.getIntOnStdIn("profondeur du Top");

        // itération pour n'afficher que le nombre d'éléments demandés
        Iterator itV = myHMSorted.values().iterator();
        Iterator itK = myHMSorted.keySet().iterator();
        int i = 0;
        while (itV.hasNext()) {
            System.out.println("Top" + ++i + ": \"" + itK.next() + "\" apparait " + itV.next() + " fois");
            if (i == max) {
                break;
            }
        }
    }


}