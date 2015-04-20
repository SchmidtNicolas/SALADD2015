
# SALADD

## Utilisation du compilateur :

Compilation :    javac Main.java
ou                           javac -cp . Main.java                


Usage :         java Main [args]
ou                          java -cp . Main [args]   

        arg1 : nom_fichier.xml (le nom du fichier a compiler)
        arg2 : type de fichier : "plus" ou "time" (aussi acceptés "p" "t" "+")
        arg3 : numero d'heuristique, avec 1:MCF, 2:Band-Width, 3:MCS-Inv, 4:Force, 5:MCS+1
        arg4 : forme finale : "AADD", "SLDDp", "SLDDt"
        (arg5 : nom_fichier_sauvegarde.dot)
        (arg6 : Garder les noeuds bégayant : "NoSkip")


## Exemples d'utilisation :
   java Main small + 3 AADD smallAADD NoSkip
   
    Cette commande compile le fichier small.xml de type additif avec l'heuristique MCS-inv. la compilation est obligatoirement faite en SLDD+, puis la fonction est traduite en AADD et est sauvegardé aux formats smallAADD.dot et smallAADD.xml. Les Noeuds bégayant (i.e. les noeuds normalement supprimés lors de la normalisation car non contraints) sont conservés

 

## Details sur les arguments :
    Compilation :
        arg1 : le nom du fichier doit etre dans le meme dossier que le programe, ou l'adresse doit etre entree explicitement.
        arg2 : indique si les contraintes sont de types additives (par exemple contrintes de prix) ou multiplicatives (par exemple probabilité). si elles sont de type additives, le fichier d'entree doit etre de type WCSP. si elles sont de type multiplicatif, le fichier d'entree doit etre de type BIF-XML.
        arg3 : seul un entier est accepté.
        arg4 : la fonction sera obligatoirement compilee en SLDDp ou SLDDt (suivant le type de contraintes). il pourra cependant etre transphormé apres compilation
        arg5 : ras
        arg6 : Les noeuds bégayant sont conservé. Toutes les variables seront rencontré une fois quelque soit le chemin.

format de fichier :
   Fichier d'entrée XML : voir
        -Olivier Roussel and Christophe Lecoutre. XML Representation of Constraint Networks : Format XCSP 2.1.Technical report, CoRR abs/0902.2362, feb 2009.
        -Fabio Gagliardi Cozman. JavaBayes Version 0.347, Bayesian Networks in Java, User Manual. Technical report, dec 2002. Benchmarks at http ://sites.poli.usp.br/pmr/ltd/Software/javabayes/Home/node3.html.

    Format de sortie :
        
        .dot : le format de sortie utilise le formalisme Graphviz dot. ainsi les diagrames peuvent etre visualise grace a cette outil (deconseille pour les graphs de plusieurs milliers de neuds). Ce format etant incomplet pour nos besoin, des information sont ajouté commenté.
        .xml : représentation xml du graphe.


## Contact :
                Nicolas.Schmidt@irit.fr

