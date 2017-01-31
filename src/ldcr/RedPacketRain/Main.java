package ldcr.RedPacketRain;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main 
extends JavaPlugin
{
	static Plugin plugin ;
	public void onEnable()
	  {
		  PluginManager pm = Bukkit.getServer().getPluginManager();
		  pm.registerEvents(new EventListener(), this);
		  plugin=this;
		  if (pm.getPlugin("Vault")!=null)
		  {
			  VaultUtil.loadeco();
			  getLogger().info("找到Vault! 使用"+VaultUtil.eco.getName()+"金钱插件!"); 
			  
			  getLogger().info("正在载入配置文件...");
			  if(!getDataFolder().exists())
			  {
				  getLogger().info("插件目录不存在,正在创建....");
				  getDataFolder().mkdir();
			  }
			  File cfile=new File(getDataFolder(),"config.yml");
			  if (!(cfile.exists()))
			  {
				  getLogger().info("配置文件不存在,正在创建....");
				  saveDefaultConfig();
			  }
			  reloadConfig();
			  Config.config = YamlConfiguration.loadConfiguration(cfile);
			  
			  Config.Multiply=Config.config.getInt("Multiply");
			  Config.MinTotal=Config.config.getDouble("MinTotal");
			  Config.MinPerPacket=Config.config.getDouble("MinPerPacket");
			  Config.MaxRedPacketCount=Config.config.getDouble("MaxRedPacketCount");
			  Config.Cost=Config.config.getDouble("Cost");
			  
			  getLogger().info("RedPacketRain红包雨插件 已加载  By.Ldcr~");  
		  }
		  else
		  {
			  getLogger().log(Level.WARNING,"未找到Vault,插件自动禁用!"); 
			  pm.disablePlugin(this);
		  }
	  }
	  public void onDisable()
	  {
		  getLogger().info("RedPacketRain红包雨插件 已卸载  By.Ldcr~");
	  }
	  
	  public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args)
	  {
	    if (lable.equalsIgnoreCase("redpacketrain") || lable.equalsIgnoreCase("rpr")|| lable.equalsIgnoreCase("redpacket"))
	    {
	    	if (!(sender instanceof Player))
	    	{
	    		Bukkit.getServer().getLogger().info("控制台无法使用此指令~");
	    	}
	    	else if (args.length == 0)
	    	{
	    		//发送帮助信息
	    		String[] helpstrArray={"§c███████████","§c██§6█§c█████§6█§c██","§c███§6█§c███§6█§c███  §b§l/redpacketrain §a参数如下:","§c████§6█§c█§6█§c████","§c█████§6█§c█§c████  §e<金钱数> §5发出去的钱的数量","§c█████§6█§c█████  §e<红包数> §5红包数量","§c███§6█████§c███  §e<祝福语> §5玩家捡到红包所看见的祝福语 ","§c█████§6█§c█████","§c█████§6█§c█████             §b§lRedPacketRain 红包雨插件","§c██§a红§c██§6包§c██§a雨§c██                                  §6§lBy.Ldcr","§c███████████"};
	    		sender.sendMessage(helpstrArray);	
	    	}
	    	else if ( args[0].equalsIgnoreCase("make") && sender.hasPermission("redpacketrain.make"))
	    	{
	    		if (args.length ==3 )
	    		{
	    			Player p =(Player) sender;
	    			if (p.getInventory().getItemInMainHand()!=null)
	    			{
	    				Util.setStackDisplayName(p.getInventory().getItemInMainHand(), "§6RedPakcet&#||#&"+args[1]+"&#||#&"+args[2]);
	    			}
	    			else
	    			{
	    				sender.sendMessage("§6[红包雨] §c请将一个物品放在主手制作为红包!");
	    			}
	    		}
	    		else
	    		{
	    			sender.sendMessage("§6[红包雨] §c使用方法: /redpacketrain make 红包钱数 祝福语");
	    		}
	    	}
	    	else if (sender.hasPermission("redpacketrain.use"))
	    	{
	    		// 参数 发送的钱数(不能低于1) 发送红包总数(不能低于1,必须为整数,不能高于钱数,不能高于30) 祝福语
	    		if (args.length != 3)
	    		{
	    			//发送帮助信息
	    			String[] helpstrArray={"§c███████████","§c██§6█§c█████§6█§c██","§c███§6█§c███§6█§c███  §b§l/redpacketrain §a参数如下:","§c████§6█§c█§6█§c████","§c█████§6█§c█§c████  §e<金钱数> §5发出去的钱的数量","§c█████§6█§c█████  §e<红包数> §5红包数量","§c███§6█████§c███  §e<祝福语> §5玩家捡到红包所看见的祝福语 ","§c█████§6█§c█████","§c█████§6█§c█████             §b§lRedPacketRain 红包雨插件","§c██§a红§c██§6包§c██§a雨§c██                                  §6§lBy.Ldcr","§c███████████"};
	    			sender.sendMessage(helpstrArray);	
	    		}
	    		else
	    		{
	    			Double money = Double.parseDouble(args[0]);
	    			int count = (int) (Double.parseDouble(args[1]));
	    			if (args[2].equalsIgnoreCase("no"))
	    			{args[2]="&f";}
	    			if (money >=Config.MinTotal)
	    			{
	    				if (count>=1)
	    				{  
	    					if (count<=Config.MaxRedPacketCount)
	    					{
	    						if ((money/count)>=Config.MinPerPacket)
	    						{
	    							Player p =(Player) sender;
	    							if (VaultUtil.eco.has(p, money+Config.Cost))
	    							{
	    								VaultUtil.eco.withdrawPlayer(p.getPlayer(), money+Config.Cost);
	    								if (Config.Cost!=0)
	    								{
	    									sender.sendMessage("§6[红包雨] §a你成功发出 §6"+count+" §a个红包,扣除 §6"+money+" §a元! §c(额外扣除手续费"+Config.Cost+"元)");
	    								}
	    								else
	    								{
	    									
	    									sender.sendMessage("§6[红包雨] §a你成功发出 §6"+count+" §a个红包,扣除 §6"+money+" §a元!");
	    								}
	    								Util.shootFirework(p, money+"&#||#&"+count+"&#||#&"+args[2]);
	    								Util.boardcast("&6&l土豪 &a&l"+p.getName()+"&6&l 在坐标(&c&l"+p.getWorld().getName()+" "+(int)p.getLocation().getX()+","+(int)p.getLocation().getY()+","+(int)p.getLocation().getZ()+"&6&l)发了一个 &b&l"+money+"&6&l 元红包啦~");
	    							}
	    							else
	    							{
	    								sender.sendMessage("§6[红包雨] §c你没有那么多钱来发红包!");
	    							}
	    						}
	    						else
	    						{
	    							sender.sendMessage("§6[红包雨] §c每个红包内的金钱不能低于"+Config.MinPerPacket+"块,请尝试增加钱或减少红包数量!");
	    						}
	    					}	
	    					else
	    					{
	    						sender.sendMessage("§6[红包雨] §c红包数不能高于"+Config.MaxRedPacketCount+"!");
	    					}
	    				}
	    				else
	    				{
	    					sender.sendMessage("§6[红包雨] §c你至少要发出一个红包!");
	    				}
	    			}
	    			else
	    			{
	    				sender.sendMessage("§6[红包雨] §c你至少需要向红包内塞入"+Config.MinTotal+"元钱!");
	    			}	
	    		}
	    	}
	    	return true;
	    }
	    return false;
	  }	
}		
