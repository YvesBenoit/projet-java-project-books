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
            System.out.println((i + 1) + " : " + listOfFiles.get(i));
        }

    }

    public static void addFile(ArrayList<String> listOfFiles) {

        String fileName = ToolBox.getStringOnStdIn("Fichier à ajouter");

        if (Files.exists(Path.of(fileName))) {      // verifie si la string récupérée correspond à un fichier
            if (fileName.indexOf("_TRAITE") != -1) {    //  verifie que "_TRAITE" est ans le nom du fichier
                listOfFiles.add(fileName);
                System.out.println("Fichier : "+fileName+" pris en compte.");
            }
        }
        System.out.println("Att : --> Fichier : "+fileName+" non pris en compte.  (n'existe pas ou n'a pas été pré-traité !) ");

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
        System.out.println("vous avez choisi :" + listOfFiles.get(i - 1));
        return listOfFiles.get(i - 1);
    }


    public static void topWords(HashMap<String, Integer> myHM) {

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

    public static ArrayList<String> uniqueWords(String selectedFile, ArrayList<String> listOfFiles) {

        // load du tous les fichiers de la liste dans un tableau de HashMaps sauf le fichier sélectionne (<==> à comparer au autres) que je charge dans une HMap à part.
        HashMap<String, Integer>[] listHMsToCompare = new HashMap[listOfFiles.size() - 1];   //tableau de HMaps d'un élément de moins que le nombre de fichiers contenus dans la liste de fichiers
        HashMap<String, Integer> selectedHM = new HashMap<String, Integer>();     // HMap du fichier sélectionné.

        ArrayList<String> listUniqueWords = new ArrayList<>();

        int j = 0;    // compteur pour gérer le tableau de HMaps à comparer. (augmente comme l'indice de boucle (i) sauf lorsque je traite le fichier selectionné)
        for (int i = 0; i < listOfFiles.size(); i++) {
            if (selectedFile.equals(listOfFiles.get(i))) {    // si on s'apprete à traiter le fichier sélectionné
                selectedHM = readFileToHashMap(listOfFiles.get(i));  // je le charge dans la HMap sélectionnée
            } else {                                          // sinon  (on s'apprete à traiter un des fichiers à comparer au fichier sélectionné
                listHMsToCompare[j++] = readFileToHashMap(listOfFiles.get(i));    // je charge la HMap du fichier en cours dans le tableau de HMaps
            }
        }

        // itération pour n'afficher que le nombre d'éléments demandés
        Iterator itV = selectedHM.values().iterator();
        Iterator itK = selectedHM.keySet().iterator();

        while (itV.hasNext()) {
            for (int i = 0; i < listHMsToCompare.length; ++i) {
                if (!listHMsToCompare[i].containsKey(itK.next())) {    // si je ne retrouve pas la clef issue du fichier à comparer dans la HM courante des HM non sélectionnées
                    listUniqueWords.add((String) itK.next());                   // je stocke cette clef unique  (sinon je ne fais rien )
                }

            }
        }
        return listUniqueWords;
    }


}