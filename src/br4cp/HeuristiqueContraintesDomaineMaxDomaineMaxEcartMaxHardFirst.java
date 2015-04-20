package br4cp;

import java.util.ArrayList;

/*   (C) Copyright 2013, Schmidt Nicolas
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

public class HeuristiqueContraintesDomaineMaxDomaineMaxEcartMaxHardFirst implements HeuristiqueContraintes {

	public ArrayList<Integer> reorganiseContraintes(LecteurXML l)
	{
		ArrayList<Integer> reorga=new ArrayList<Integer>();

		int ecart;
		int domain;
		//int sumdomain;
		int[] score=new int[l.getNbConstraints()];
		for(int i=0; i<l.getNbConstraints(); i++)
			score[i]=-1;
		int max=-1;
		int maxVal=-1;
	
			
		for(int i=0; i<l.getNbConstraints(); i++){
			if(l.cons[i]!=null){
				int ecartmax=0;
				int domainmax=0;
				int domainmax2=1;
				score[i]=0;
				for(int j=0; j<l.cons[i].scopeID.length; j++){
					for(int k=j+1; k<l.cons[i].scopeID.length; k++){
						ecart=Math.abs(l.cons[i].scopeID[j]-l.cons[i].scopeID[k]);
						if(ecart>ecartmax)
							ecartmax=ecart;
					}
					domain=l.var.get(l.cons[i].scopeID[j]).domain;
					if(domain>domainmax){
						domainmax2=domainmax;
						domainmax=domain;
					}else{
						if(domain>domainmax2)
							domainmax2=domain;
					}
				}
				score[i]=domainmax*domainmax2*ecartmax;
			}
		}
		
		max=-1;
		maxVal=-1;
		int j=0;
		for(j=0; j<l.getNbConstraints(); j++){
			for(int i=0; i<l.getNbConstraints(); i++){
				if(l.cons[i]!=null && !l.cons[i].relation.softConstraint){
					if(score[i]>max){
						max=score[i];
						maxVal=i;
					}
				}
			}
			if(maxVal!=-1){
				reorga.add(maxVal);
				score[maxVal]=-1;
				max=-1;
				maxVal=-1;
			}else{			//reste plus que des contraintes souples
				max=-1;
				maxVal=-1;
				break;
			}
		}
		
		for(;j<l.getNbConstraints(); j++){
			for(int i=0; i<l.getNbConstraints(); i++){
				if(l.cons[i]!=null && l.cons[i].relation.softConstraint){
					if(score[i]>max){
						max=score[i];
						maxVal=i;
					}
				}
			}
			if(maxVal!=-1){
				reorga.add(maxVal);
				score[maxVal]=-1;
				max=-1;
				maxVal=-1;
			}else{			//reste plus que des contraintes nulles
				for(int i=0; i<l.getNbConstraints(); i++){
					if(l.cons[i]==null){
						reorga.add(i);
					}
				}
				break;
			}
			
		}
		return reorga;
	}		
	
}
