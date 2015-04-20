package br4cp;

/*   (C) Copyright 2015, Pierre-François Gimenez
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

public class VarPoidIdElement {

	public int[] var;
	public Structure poid;
	public Integer id;
	
	public VarPoidIdElement(Integer id, int[] var, Structure poids)
	{
		this.var = var;
		this.poid = poids;
		this.id = id;
	}
	
}
