package methode_oubli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import test_independance.TestIndependance;
import br4cp.LecteurXML;
import br4cp.SALADD;
import br4cp.VDD;
import br4cp.Var;

/*   (C) Copyright 2015, Gimenez Pierre-François
 * 
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Méthode d'oubli par d-sépration par réseau bayésien naïf augmenté par arbre
 * @author pgimenez
 *
 */

public class OubliParDSeparationTree extends MethodeOubliRestauration {

	public OubliParDSeparationTree(int seuil, TestIndependance test)
	{
		super(seuil, test);
	}
	
	@Override
	public void learn(SALADD saladd, String prefix_file_name)
	{
		super.learn(saladd, prefix_file_name);
		xml=new LecteurXML();
		for(Var v: saladd.getAllVar())
			reseaux.put(v.name, xml.lectureReseauBayesien("bn_tree_"+v.name+"_"+nbIter+".xml"));
	}
	
	private int nbOubli;
	private LecteurXML xml;

	private HashMap<String,HashMap<String, ArrayList<String>>[]> reseaux = new HashMap<String,HashMap<String, ArrayList<String>>[]>();
	private ArrayList<String> done = new ArrayList<String>();
	private ArrayList<Var> dejavu = new ArrayList<Var>();
	private ArrayList<String> dejavuVal = new ArrayList<String>();

	private static final int parents = 0;
	private static final int enfants = 1;

	private void rechercheEnProfondeur(HashMap<String, ArrayList<String>>[] reseau, ArrayList<String> connues, String v, boolean vientDeParent)
	{
		done.add(v);
		ArrayList<String> listeParents = reseau[parents].get(v);
		ArrayList<String> listeEnfants = reseau[enfants].get(v);

		/**
		 * D'abord, on étend la recherche aux enfants.
		 * Il n'y a pas de problème de collision.
		 * Si v est connu, on bloque la recherche.
		 * On ne vérifie pas qu'ils sont "done" parce que:
		 * - par acyclicité, il n'y aura pas de boucle infinie
		 * - c'est nécessaire dans le cas d'une V-structure qui peut être "done" et pourtant
		 * peut avoir des parents à explorer
		 */
		if(!connues.contains(v))
			for(String enf: listeEnfants)
				rechercheEnProfondeur(reseau, connues, enf, true);
		
		boolean aUnEnfantConnu = false;
		for(String enf: listeEnfants)
			if(connues.contains(enf))
			{
				aUnEnfantConnu = true;
				break;
			}
		
		/**
		 * La recherche des parents est un peu plus complexe, et prend compte des V-structure
		 */
		if(!(connues.contains(v) || aUnEnfantConnu) && vientDeParent)
		{
			/**
			 * Si on n'est pas connu (et qu'aucun de nos enfants ne l'est)
			 * et qu'on provient d'un parent, on a une V-structure: on s'arrête là.
			 */
			return;
		}
		else if(!vientDeParent)
		{
			/**
			 * Si on vient d'un fils, alors il n'y a pas de V-structure.
			 * On peut propager à conditionner de ne pas être connu.
			 */
			if(!connues.contains(v))
				for(String par: listeParents)
					if(!done.contains(par))
						rechercheEnProfondeur(reseau, connues, par, false);
		}
		else
		{
			/**
			 * Dernière possibilité: on vient d'un parent et on (ou un de nos fils) est connu.
			 * Dans ce cas, la V-structure est ignoré.
			 */
			for(String par: listeParents)
				if(!done.contains(par))
					rechercheEnProfondeur(reseau, connues, par, false);
		}
	}
	
	@Override
	public Map<String, Double> recommandation(Var v, HashMap<String, String> historiqueOperations, VDD vdd, ArrayList<String> possibles)
	{
		dejavu.clear();
		dejavuVal.clear();
		nbOubli = 0;
		ArrayList<String> connues = new ArrayList<String>();
		Map<String, Double> m;
		done.clear();
		
		for(String s: historiqueOperations.keySet())
			connues.add(vdd.getVar(s).name);

		HashMap<String, ArrayList<String>>[] reseau = reseaux.get(v.name);
		
		rechercheEnProfondeur(reseau, connues, v.name, false);
		
		for(String s: historiqueOperations.keySet())
		{
			Var connue = vdd.getVar(s);
			if(!done.contains(connue.name))
			{
	    		dejavu.add(connue);
	    		dejavuVal.add(historiqueOperations.get(s));
	    		vdd.deconditioner(connue);
	    		nbOubli++;
			}
		}
//		System.out.println("Oubli d-sep: "+nbOubli);
//		int nbOubliSauv = nbOubli;
		
		/**
		 * Dans le cas où il faut différencier deux cas, on fait un test de comparaison entre une moyenne
		 * (la probabilité estimée) et une moyenne théorique, 1/2
		 */
/*		if(possibles.size() == 2)
		{
			Var varInteret = possibles.get(0);
			int n = vdd.countingpondere()
			// cas de "grands" échantillons
			if(n >= 30)
			{
				m = vdd.countingpondereOnPossibleDomain(v, possibles);
				if((m.get(varInteret)-0.5)/())
			}
			else
			{
				
			}
		}*/
		
		super.restaure(historiqueOperations, vdd, v);

    	m = vdd.countingpondereOnPossibleDomain(v, possibles);
    	
    	super.reconditionne(vdd);
    	
    	return m;
	}

	@Override
	public int getNbOublis() {
		return nbOubli;
	}
	
}
