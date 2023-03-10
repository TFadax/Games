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
package org.l2jmobius.gameserver.network.serverpackets.equipmentupgrade;

import org.l2jmobius.commons.network.PacketWriter;
import org.l2jmobius.gameserver.enums.UpgradeType;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

public class ExShowUpgradeSystemNormal implements IClientOutgoingPacket
{
	
	private final UpgradeType _type;
	
	public ExShowUpgradeSystemNormal(UpgradeType type)
	{
		_type = type;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SHOW_UPGRADE_SYSTEM_NORMAL.writeId(packet);
		packet.writeH(1); // Flag
		packet.writeH(_type.ordinal());
		packet.writeH(100); // CommissionRatio
		packet.writeD(0); // MaterialItemId (array)
		packet.writeD(0); // MaterialRatio (array)
		return true;
	}
}
