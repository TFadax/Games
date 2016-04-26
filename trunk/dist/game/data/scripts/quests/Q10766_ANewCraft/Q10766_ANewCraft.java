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
package quests.Q10766_ANewCraft;

import com.l2jmobius.gameserver.enums.Race;
import com.l2jmobius.gameserver.model.actor.L2Npc;
import com.l2jmobius.gameserver.model.actor.instance.L2PcInstance;
import com.l2jmobius.gameserver.model.events.EventType;
import com.l2jmobius.gameserver.model.events.ListenerRegisterType;
import com.l2jmobius.gameserver.model.events.annotations.Id;
import com.l2jmobius.gameserver.model.events.annotations.RegisterEvent;
import com.l2jmobius.gameserver.model.events.annotations.RegisterType;
import com.l2jmobius.gameserver.model.events.impl.item.OnItemCreate;
import com.l2jmobius.gameserver.model.holders.ItemHolder;
import com.l2jmobius.gameserver.model.quest.Quest;
import com.l2jmobius.gameserver.model.quest.QuestState;
import com.l2jmobius.gameserver.network.serverpackets.TutorialShowHtml;

/**
 * A New Craft (10766)
 * @URL https://l2wiki.com/A_New_Craft
 * @author Gigi
 */
public class Q10766_ANewCraft extends Quest
{
	// NPCs
	private static final int KATALIN = 33943;
	private static final int AYANTHE = 33942;
	private static final int ZEPHYRA = 33978;
	// Items
	private static final ItemHolder WINDY_HEALING_POTION = new ItemHolder(39466, 50);
	private static final ItemHolder WINDY_QUICK_HEALING_POTION = new ItemHolder(39471, 50);
	private static final int AIR_STONE = 39461;
	private static final int WINDY_HEALING_POTION_1 = 39466;
	// Misc
	private static final int MIN_LEVEL = 40;
	// Reward
	private static final int EXP_REWARD = 168000;
	private static final int SP_REWARD = 40;
	
	public Q10766_ANewCraft()
	{
		super(10766, Q10766_ANewCraft.class.getSimpleName(), "A New Craft");
		addStartNpc(KATALIN, AYANTHE);
		addTalkId(KATALIN, AYANTHE, ZEPHYRA);
		registerQuestItems(AIR_STONE, WINDY_HEALING_POTION_1);
		addCondMinLevel(MIN_LEVEL, "noLevel.html");
		addCondRace(Race.ERTHEIA, "noErtheia.html");
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState qs = getQuestState(player, false);
		if (qs == null)
		{
			return null;
		}
		String htmltext = null;
		switch (event)
		{
			case "33943-02.htm":
			case "33943-03.htm":
			case "33942-02.htm":
			case "33942-03.htm":
			case "33978-02.html":
			{
				htmltext = event;
				break;
			}
			case "33943-04.html":
			{
				qs.startQuest();
				htmltext = event;
				break;
			}
			case "33942-04.html":
			{
				qs.startQuest();
				htmltext = event;
				break;
			}
			case "33978-03.html":
			{
				qs.setCond(2, true);
				player.sendPacket(new TutorialShowHtml(npc.getObjectId(), "..\\L2Text\\QT_026_alchemy_01.htm", TutorialShowHtml.LARGE_WINDOW));
				htmltext = event;
				break;
			}
			case "33978-05.html":
			{
				qs.setCond(3, true);
				qs.set(Integer.toString(AIR_STONE), 0);
				qs.set(Integer.toString(WINDY_HEALING_POTION_1), 0);
				htmltext = event;
				break;
			}
			case "33978-07.html":
			{
				takeItems(player, AIR_STONE, 1);
				takeItems(player, WINDY_HEALING_POTION_1, 1);
				giveItems(player, WINDY_HEALING_POTION);
				giveItems(player, WINDY_QUICK_HEALING_POTION);
				addExpAndSp(player, EXP_REWARD, SP_REWARD);
				qs.exitQuest(false, true);
				htmltext = event;
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState qs = getQuestState(player, true);
		String htmltext = getNoQuestMsg(player);
		
		switch (npc.getId())
		{
			case KATALIN:
			{
				if (qs.isCreated() && !player.isMageClass())
				{
					htmltext = "33943-01.htm";
				}
				else
				{
					htmltext = "noFighter.html";
				}
				if (qs.getCond() > 0)
				{
					htmltext = "33943-05.html";
				}
				else if (qs.isCompleted())
				{
					htmltext = getAlreadyCompletedMsg(player);
				}
				break;
			}
			case AYANTHE:
			{
				if (qs.isCreated() && player.isMageClass())
				{
					htmltext = "33942-01.htm";
				}
				else
				{
					htmltext = "noMage.html";
				}
				if (qs.getCond() > 0)
				{
					htmltext = "33942-05.html";
				}
				else if (qs.isCompleted())
				{
					htmltext = getAlreadyCompletedMsg(player);
				}
				break;
			}
			case ZEPHYRA:
			{
				if (qs.isCond(1))
				{
					htmltext = "33978-01.html";
				}
				if (qs.isCond(2))
				{
					htmltext = "33978-04.html";
				}
				if (qs.isCond(3) && ((getQuestItemsCount(player, AIR_STONE) == 0) || (getQuestItemsCount(player, WINDY_HEALING_POTION_1) == 1)))
				{
					htmltext = "33978-08.html";
				}
				if (qs.isCond(4))
				{
					htmltext = "33978-06.html";
				}
			}
		}
		return htmltext;
	}
	
	@RegisterEvent(EventType.ON_ITEM_CREATE)
	@RegisterType(ListenerRegisterType.ITEM)
	@Id(AIR_STONE)
	@Id(WINDY_HEALING_POTION_1)
	public void onItemCreate(OnItemCreate event)
	{
		final QuestState qs = getQuestState(event.getActiveChar(), false);
		if ((qs != null) && qs.isCond(3) && (getQuestItemsCount(qs.getPlayer(), AIR_STONE) >= 1) && (getQuestItemsCount(qs.getPlayer(), WINDY_HEALING_POTION_1) >= 1))
		{
			qs.setCond(4, true);
		}
	}
}