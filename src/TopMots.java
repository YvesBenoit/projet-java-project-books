import java.util.HashMap;
import java.util.Iterator;

public class TopMots {

    public static void main(String[] args) {

        // affichage pour saisie utilisateur du nom de fichier à lire
        String fileName  = ToolBox.getStringOnStdIn("nom du fichier input");

        //lecture du fichier et stockage dans la table de hachage
        HashMap<String, Integer> myHM = ToolBox.readFileToHashMap(fileName);

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
