#!/bin/bash
# indique au système que l'argument qui suit est le programme utilisé pour exécuter ce fichier.
# En cas général les "#" servent à faire des commentaires comme ici

tableau=''
count=0
cd ./pedigree
for i in $(ls *)
do
   tableau[$count]=$i
   (( count=$count+1 ))
done
cd ..

echo done

cd ./src
for i in ${!tableau[@]}; 
	do 
		echo java Main ${tableau[i]} +
		java Main ./../pedigree/${tableau[i]} +
	done

exit 0