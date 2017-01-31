package ldcr.RedPacketRain;

import org.bukkit.Sound;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FireworkExplodeEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class EventListener 
implements Listener
{
	@EventHandler
	public void onFireworkExplode(FireworkExplodeEvent e)
	{
		try
		{
			Firework fw = e.getEntity();
			if (fw==null)
			{return;}
			if (fw.getCustomName()==null)
			{return;}
			String[] info =fw.getCustomName().split("&#\\|\\|#&");;
			if (info.length!=3)
			{return;}
			int count = (int) (Double.parseDouble(info[1]));
			Double money = Double.parseDouble(info[0])/count;
			int type =Util.random(2);
			for (int i =0;i<count;i++)
	    	{
				double temp =(money-(Math.random()*Config.Multiply));
				if (temp<0)
				{temp=0;}
				ItemStack stack = new ItemStack(Util.getRedPacketMaterial(type),1) ;
				Util.setStackDisplayName(stack, "§6RedPakcet&#||#&"+temp+"&#||#&"+info[2]);
	    		Item item =fw.getLocation().getWorld().dropItem(fw.getLocation(),stack);
	    		item.setVelocity(new Vector(Util.random(),Util.random(),Util.random()));    		
	    	}
		}
		catch(NullPointerException exc){}
	}
	
	
	  @EventHandler(priority=EventPriority.HIGH)
	  public void onPlayerPickUpItem(PlayerPickupItemEvent e)
	  {
		  if (e.getItem().getItemStack().hasItemMeta())
		  {
			  ItemStack item = e.getItem().getItemStack();
			  if (item==null)
			  {return;}
			  if (!item.hasItemMeta())
			  {return;}
			  ItemMeta meta = item.getItemMeta();
			  if (!meta.hasDisplayName())
			  {return;}
			  if (meta.getDisplayName().startsWith("§6RedPakcet&#||#&")) 
			  {
				  
				  e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
				  String[] info =meta.getDisplayName().split("&#\\|\\|#&");
				  if (info.length!=3)
				  {return;}
				  e.setCancelled(true);  
				  Double money = Double.parseDouble(info[1])*e.getItem().getItemStack().getAmount();
				  String zf = info[2].replace("&", "§");
				  VaultUtil.eco.depositPlayer(e.getPlayer(), money);
				  e.getItem().remove();
				  e.getPlayer().sendMessage( "§b"+zf+" §6你捡到一个红包,获得§a "+Util.sswr(money)+" §6元");  	
			  }
		  }
	  }
	  
	  @EventHandler(priority=EventPriority.HIGH)
	  public void onItemPickup(InventoryPickupItemEvent e)
	  { 
		  if (e.getInventory().getType().equals(InventoryType.HOPPER))
		  {
			  ItemStack item = e.getItem().getItemStack();
			  if (item==null)
			  {return;}
			  if (!item.hasItemMeta())
			  {return;}
			  if (!item.getItemMeta().hasDisplayName())
			  {return;}
			  if (item.getItemMeta().getDisplayName().startsWith("§6RedPakcet&#||#&"))
			  {
				  e.setCancelled(true);
			  }
		  }
	  }
	  
}
