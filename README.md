Utilisation du compilateur



--COMPILATION--

compilation :
	javac Compilation.java
ou	javac -cp . Compilation.java



execution :
	java Compilation fichier_a_compiler.xml [autre(s)_fichier(s)_a_compiler.xml] -t=[type] [options]
ou	java -cp . Compilation fichier_a_compiler.xml [autre(s)_fichier(s)_a_compiler.xml] -t=[type] [options]


	-obligatoire :
		fichier_a_compiler.xml
			le nom du fichier principale à compiler
		-t=[type]
			type de fichier : [type] = "plus" ou "time" (aussi acceptés "p" "t" "+")

		entrez un ou plusieurs noms de fichiers a compiler d'un même problème, séparé avec des espaces. Si plusieurs fichiers, tous doivent porter sur le même ensemble de variables.
		-t=[type], [type] pouvant prendre la valeur "plus" ou "time" (aussi acceptés "p" "t" "+"). Indique la nature additive ou multiplicative des valuations du fichier à compiler.
	-options :
		autre_fichier_a_compiler.xml
			permet de compiler un problème écrit en plusieurs fichiers
		-h=? 
			numero d'heuristique d'ordonnancement des varialbes; '1':MCF, '2':Band-Width, '3':MCS-Inv, '4':Force, '5':MCS+1, '0':ordre naturel (ordre du fichier CSP), '-1':ordre aléatoire.
		-hcon=?
			numero d'heuristique d'ordonnancement des contraintes; '0' : ordre naturel, '-1' : ordre aléatoire, '7' : complexite d'une contrainte v1
		-ff=[forme] 
			forme finale : [forme] = "AADD", "SLDDp", "SLDDt" ou "ADD"
		-save=[nom_fichier_sauvegarde.dot]
			nom du fichier de sortie (pas l'option -> pas de sortie)
		-NoSkip 
			Garder les noeuds bégayant
		-text=?
			quantité de texte à afficher. ?= de '0' à '3',  0 étant le minimum de texte affixhé possible, 3 le maximum.



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

		compilation du fichier small.xml de type additif. (par défaut : heuristique utilisé : MCS-inv; heuristique d'ordonnancement de contraintes utilisé : ordre naturel; forme finale : forme de compilation; pas de sortie; noeuds bégayants suprimmés; niveau d'affichage du texte : 1).



2/
	java Compilation medium.xml mediumPrices.xml -t=+ -h=3 -hcon=8 -ff=SLDDp -save=mediumSLDDp.dot -NoSkip -text=0
	java Read mediumSLDDp.dot -ff=ADD -text=3

		la premiere ligne compile le fichier medium.xml et le fichier associé mediumPrices.xml (qui contient les contraintes valuées). le problème est de type additif, compilé avec l'heuristique d'ordonnancement de variable MCS-inv et l'heuristique d'ordonnancement de contrainte 8. la compilation est obligatoirement faite en SLDD+, puis la fonction n'est pas traduite. La forme compilée est sauvegardé dans mediumSLDDp.dot. Les noeuds bégayants sont sauvegardés et aucun texte ne sera affiché durant la compilation.
		La 2eme ligne charge le premier fichier puis traduit la fonction en ADD mais ne la sauvegarde pas. Cependant tous les détails textuels sont affichés.






details sur les arguments : 
	-compilation
		fichier_a_compiler : le nom du fichier doit etre dans le meme dossier que le programe, ou l'adresse doit etre entree explicitement.
		autre_fichier_a_compiler : d'autres fichiers peuvent être ajoutés au premier, ils doivent porter sur un sous ensemble de variables définies dans le premier fichier.

		-t=[type] : indique si les contraintes sont de types additives (par exemple contrintes de prix) ou multiplicatives (par exemple probabilité). si elles sont de type additives, le fichier d'entree doit etre de type WCSP. si elles sont de type multiplicatif, le fichier d'entree doit etre de type BIF-XML.

		-h=? : seul un entier est accepté (à la place du '?').
 
		-hcon=? : seul un entier est accepté (à la place du '?'). 

		-ff=[forme] : la fonction sera obligatoirement compilee en SLDDp ou SLDDt (suivant le type de contraintes). il pourra cependant etre transphormé apres compilation
		
		-save=[nom_fichier_sauvegarde.dot] : ras

		-NoSkip : Les noeuds bégayant sont conservé. Toutes les variables seront rencontré une fois quelque soit le chemin.
		
		-text=? : quatres niveau d'affichage de texte : 
			-text=0 : aucun texte sauf erreure de compilation
			-text=1 : affichage de taille de la forme compilée et transformée
			-text=2 : même chose que précédament + détails et progression de la compilation 
			-text=3 : même chose que précédament + affichage des ordres des variables et des contraintes.
		
		-read=[nom_fichier_a_lire.dot] : si cette option est présente, il n'y aura pas de compilation. voir le paragraphe "format de sortie" pour plus d'info sur le format du fichier






format de fichier :
	fichier d'entree XML : voir 
		-Olivier Roussel and Christophe Lecoutre. XML Representation of Constraint Networks : Format XCSP 2.1.Technical report, CoRR abs/0902.2362, feb 2009.
		-Fabio Gagliardi Cozman. JavaBayes Version 0.347, Bayesian Networks in Java, User Manual. Technical report, dec 2002. Benchmarks at http ://sites.poli.usp.br/pmr/ltd/Software/javabayes/Home/node3.html.

	format de sortie
		le format de sortie utilise le formalisme Graphviz dot. ainsi les diagrames peuvent etre visualise grace a cette outil (deconseille pour les graphs de plusieurs milliers de neuds). Ce format etant incomplet pour nos besoin, des information sont ajoutés commentés.
		notez que seul les fichier générés par ce compilateur peuvent etre lu par ce compilateur. toute modification à la main peut gener la relecture du fichier.





	contact :
		Nicolas.Schmidt@irit.fr 


