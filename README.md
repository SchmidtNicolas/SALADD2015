Utilisation du compilateur



--COMPILATION--

compilation :
	javac Compilation.java
ou	javac -cp . Compilation.java



execution :
	java Compilation fichier_a_compiler.xml -t=[type] [options]
ou	java -cp . Compilation fichier_a_compiler.xml -t=[type] [options]


	-obligatoire :
		fichier_a_compiler.xml
			le nom du ou des fichiers à compiler
			Plusieurs noms de fichiers d'un même problème à compiler peuvent être entrés, séparés par des espaces. Si plusieurs fichiers, tous doivent porter sur le même ensemble de variable.

		-t=[type]
			type de fichier : [type] = "plus" ou "time" (aussi acceptés "p" "t" "+")
			Indique la nature additive ou multiplicative des valuations du fichier à compiler.
		
	-options :
		-h=? 
			numero d'heuristique d'ordonnancement des varialbes; '1':MCF, '2':Band-Width, '3':MCS-Inv, '4':MCS+1, '5':Force, '0':ordre naturel (ordre du fichier CSP), '-1':ordre aléatoire.
		-hcon=?
			numero d'heuristique d'ordonnancement des contraintes; '1':BCF, '2':tri par difficulté, '3':tri par dureté, '0' : ordre naturel, '-1' : ordre aléatoire, '-2':ordre naturel inversé
		-ff=[forme] 
			forme finale : [forme] = "AADD", "SLDDp", "SLDDt" ou "ADD"
		-save=[nom_fichier_sauvegarde.dot]
			nom du fichier de sortie (pas l'option -> pas de sortie)
		-NoSkip 
			Garder les noeuds bégayants
		-text=?
			quantité de texte à afficher. ?= de '0' à '3',  0 étant le minimum de texte affiché possible, 3 le maximum.



--CHARGEMENT D'UN FICHIER COMPILÉ--

compilation :
	javac Read.java
ou	javac -cp . Read.java

execution :
	java Read -read=nom_fichier_a_lire.dot [options]
ou	java -cp . Read -read=nom_fichier_a_lire.dot [options] 	
	
	-obligatoire :
		-read=[nom_fichier_a_lire.dot]
			fichier à lire, extention .dot.
	-options (voir compilation):
		-ff=[forme] 
		-save=[nom_fichier_sauvegarde.dot]
		-NoSkip 
		-text=?





exemples d'utilisation :

1/
	java Compilation small.xml -t=+

		compilation du fichier small.xml de type additif. (par défaut : heuristique utilisé : MCS; heuristique d'ordonnancement de contraintes utilisé : ordre naturel; forme finale : forme de compilation; pas de sortie; noeuds bégayants suprimmés; niveau d'affichage du texte : 1).



2/
	java Compilation medium.xml mediumPrices.xml -t=+ -h=4 -hcon=2 -ff=SLDDp -save=mediumSLDDp.dot -NoSkip -text=0
	java Read mediumSLDDp.dot -ff=ADD -text=3

		la premiere ligne compile le fichier medium.xml et le fichier associé mediumPrices.xml (qui contient les contraintes valuées). le problème est de type additif, compilé avec l'heuristique d'ordonnancement de variable MCS+1 et l'heuristique d'ordonnancement de contrainte 2 (tri par difficulté). la compilation est obligatoirement faite en SLDD+, puis la fonction n'est pas traduite. La forme compilée est sauvegardée dans mediumSLDDp.dot. Les noeuds bégayants sont gardés et aucun texte ne sera affiché durant la compilation.
		La 2eme ligne charge le premier fichier puis traduit la fonction en ADD mais ne la sauvegarde pas. Cependant tous les détails textuels sont affichés.






details sur les arguments : 
	-compilation
		fichier_a_compiler : le nom du fichier doit etre dans le meme dossier que le programme, ou l'adresse doit etre entree explicitement.
		autre_fichier_a_compiler : d'autres fichiers peuvent être ajoutés au premier, ils doivent porter sur un sous ensemble de variables définies dans le premier fichier.

		-t=[type] : indique si les contraintes sont de types additives (par exemple contrintes de prix) ou multiplicatives (par exemple probabilité). si elles sont de type additives, le fichier d'entrée doit être de type WCSP. si elles sont de type multiplicatif, le fichier d'entrée doit être de type BIF-XML.

		-h=? : seul un entier est accepté (à la place du '?').
 
		-hcon=? : seul un entier est accepté (à la place du '?'). 

		-ff=[forme] : la fonction sera obligatoirement compilée en SLDDp ou SLDDt (suivant le type de contraintes). il pourra cependant etre transphormé apràs compilation
		
		-save=[nom_fichier_sauvegarde.dot] : ras

		-NoSkip : Les noeuds bégayants sont conservés. Toutes les variables seront rencontrées une fois quelque soit le chemin.
		
		-text=? : quatres niveaux d'affichage de texte : 
			-text=0 : aucun texte sauf erreure de compilation
			-text=1 : affichage de taille de la forme compilée et transformée
			-text=2 : même chose que précédemment + détails et progression de la compilation 
			-text=3 : même chose que précédemment + affichage des ordres des variables et des contraintes.





format de fichier :
	fichier d'entree XML : voir 
		-Olivier Roussel and Christophe Lecoutre. XML Representation of Constraint Networks : Format XCSP 2.1.Technical report, CoRR abs/0902.2362, feb 2009.
		-Fabio Gagliardi Cozman. JavaBayes Version 0.347, Bayesian Networks in Java, User Manual. Technical report, dec 2002. Benchmarks at http ://sites.poli.usp.br/pmr/ltd/Software/javabayes/Home/node3.html.

	format de sortie
		-le format de sortie utilise le formalisme Graphviz dot. ainsi les diagrames peuvent être visualisés grâce a cette outil (déconseillé pour les graphs de plusieurs milliers de noeuds). Ce format étant incomplet pour nos besoin, des informations sont ajoutés.
			notez que seul les fichiers générés par ce compilateur peuvent être lu par ce compilateur. toute modification à la main peut géner la relecture du fichier.




	contact :
		Nicolas Schmidt : Nac.s@free.fr
		Hélène fargier : fargier@irit.fr

