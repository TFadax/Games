/*
 * This file is part of the L2J Mobius project.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jmobius.gameserver.model.zone.type;

import com.l2jmobius.gameserver.model.actor.L2Character;
import com.l2jmobius.gameserver.model.zone.L2ZoneType;
import com.l2jmobius.gameserver.model.zone.ZoneId;

/**
 * A Town zone
 * @author durgus
 */
public class L2TownZone extends L2ZoneType
{
	private int _townId;
	private int _taxById;
	
	public L2TownZone(int id)
	{
		super(id);
		
		_taxById = 0;
	}
	
	@Override
	public void setParameter(String name, String value)
	{
		if (name.equals("townId"))
		{
			_townId = Integer.parseInt(value);
		}
		else if (name.equals("taxById"))
		{
			_taxById = Integer.parseInt(value);
		}
		else
		{
			super.setParameter(name, value);
		}
	}
	
	@Override
	protected void onEnter(L2Character character)
	{
		character.setInsideZone(ZoneId.TOWN, true);
	}
	
	@Override
	protected void onExit(L2Character character)
	{
		character.setInsideZone(ZoneId.TOWN, false);
	}
	
	/**
	 * Returns this zones town id (if any)
	 * @return
	 */
	public int getTownId()
	{
		return _townId;
	}
	
	/**
	 * Returns this town zones castle id
	 * @return
	 */
	public final int getTaxById()
	{
		return _taxById;
	}
}
