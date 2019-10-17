- [Contexte](#org2602fda)
- [MVP](#org067d6e3)
- [Objectif](#orgeb6e119)
- [Prétraitements](#org906660d)



<a id="org2602fda"></a>

# Contexte

Le programme doit permettre de travailler sur un ensemble de fichiers texte correspondant à des livres. On voudra pouvoir avoir des informations sur le contenu des ces livres.


<a id="org067d6e3"></a>

# MVP

Le programme doit accepter un nombre quelconque d'arguments qui sont des noms de fichiers. Ensuite, il doit présenter le menu suivant :

<div class="VERBATIM">
1.  Lister les fichiers
2.  Ajouter un fichier
3.  Supprimer un fichier
4.  Afficher des informations sur un livre
5.  Quitter le programme

</div>

Pendant toute l'exécution du programme, celui-ci maintient une liste des noms de fichiers, initialisée par les arguments du programme, qu'il est possible de consulter et modifier avec les trois premières options.

Le quatrième choix :

1.  affiche la liste des fichiers
2.  propose de choisir d'un de ces fichiers
3.  propose le sous-menu suivant :

<div class="VERBATIM">
1.  Afficher le nombre de lignes du fichier
2.  Afficher le nombre de mots du fichier

</div>


<a id="orgeb6e119"></a>

# Objectif

Parmi les informations proposées pour un fichier, ajouter :

-   afficher les 50 mots les plus fréquents et leur nombre d'occurrences
-   afficher les mots qui sont présents seulement dans ce fichier et aucun des autres fichiers
-   Afficher pour chacun des autres fichiers le pourcentage de mots de l'autre fichier qui sont présents dans le fichier sélectionnés, par ordre décroissant de ce pourcentage.


<a id="org906660d"></a>

# Prétraitements

Les fichiers de texte contiennent, en plus des mots, des signes, notamment de ponctuation, qu'on voudra éliminer. Pour faciliter le mini-projet, on peut le faire avec un prétraitement, par exemple en utilisant le programme suivant pour générer des fichiers ne contenant que les mots (un par ligne), d'un fichier texte :

```java
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BooksToWords {
    public static void main(String[] args)throws FileNotFoundException {
        Pattern p = Pattern.compile("\\w+", Pattern.UNICODE_CHARACTER_CLASS);
        try(Scanner sc = new Scanner(new File(args[0]));
            PrintStream fileOut = new PrintStream(new FileOutputStream(args[1]))){
            for(int i=0; sc.hasNextLine(); ++i){
                for(Matcher m1 = p.matcher(sc.nextLine()); m1.find();) {
                    fileOut.println(m1.group());
                }
            }
        }
    }
}
```

On peut aussi réaliser ce programme en python :

```python
import sys
import re
with open(sys.argv[1]) as input, open(sys.argv[2], "w") as output:
    for line in input:
        for word in re.findall(r'\w+', line):
            output.write(word.lower())
            output.write('\n')
```

Si vous le voulez, vous pouvez vous inspirer de ce code pour que votre programme puisse traiter directement les fichiers de texte en réalisant vous-même l'extraction des mots.
