package me.yannisgerlach.easysleep;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	int nowsleeping = 0;
	long sperre = 0;
	
	
	@Override
	public void onEnable() {
		System.out.println("EsaySleep is Enabled.");
		this.getServer().getPluginManager().registerEvents(this, this);

		this.getConfig();
		this.reloadConfig();
	}
	
	@EventHandler
	public void onsleep(PlayerBedEnterEvent e)
	{		
		int i = e.getPlayer().getWorld().getPlayers().size();
		nowsleeping ++;
		float percent = (nowsleeping / i);
		e.getPlayer().getServer().broadcastMessage(ChatColor.GREEN + e.getPlayer().getDisplayName() +  ChatColor.GOLD + this.getConfig().getString("lang.enter_bed") + nowsleeping + "/" + i  + " ( " + percent*100 +"% / " + this.getConfig().getInt("options.percent_of_sleeping_persons") + "% )" + this.getConfig().getString("lang.in_dimesion") + e.getPlayer().getWorld().getName());
		
		if( percent*100 >= this.getConfig().getInt("options.percent_of_sleeping_persons") )
		{
			e.getPlayer().getWorld().setTime(0L);
			nowsleeping = 0;
			sperre = System.currentTimeMillis();
		}
	}
	
	
	@EventHandler
	public void onbreak(PlayerBedLeaveEvent e)
	{
		if(sperre + 500 <= System.currentTimeMillis())
		{
			nowsleeping --;

			e.getPlayer().getServer().broadcastMessage(ChatColor.GREEN + e.getPlayer().getDisplayName() +  ChatColor.GOLD + " ist aufgestanden.");
			System.out.println(e.getPlayer().getDisplayName() + this.getConfig().getString("lang.leave_bed"));
		}
	}
	
}
