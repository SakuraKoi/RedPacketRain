package ldcr.RedPacketRain;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class Util {
	  static void shootFirework(Player player,String displayname)
	  {
	    Firework firework = (Firework)player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
	    firework.setCustomName(displayname);
	    FireworkMeta fm = firework.getFireworkMeta();
	    Random r = new Random();
	    FireworkEffect.Type type = null;
	    int fType = r.nextInt(5) + 1;
	    switch (fType)
	    {
	    case 1: 
	    default: 
	      type = FireworkEffect.Type.BALL;
	      break;
	    case 2: 
	      type = FireworkEffect.Type.BALL_LARGE;
	      break;
	    case 3: 
	      type = FireworkEffect.Type.BURST;
	      break;
	    case 4: 
	      type = FireworkEffect.Type.CREEPER;
	      break;
	    case 5: 
	      type = FireworkEffect.Type.STAR;
	    }
	    int c1i = r.nextInt(16) + 1;
	    int c2i = r.nextInt(16) + 1;
	    Color c1 = getColor(c1i);
	    Color c2 = getColor(c2i);
	    FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();
	    fm.addEffect(effect);
	    int power = r.nextInt(2) + 1;
	    fm.setPower(power);
	    firework.setFireworkMeta(fm);
	  }
	  
	  static Color getColor(int c)
	  {
	    switch (c)
	    {
	    case 1: 
	    default: 
	      return Color.AQUA;
	    case 2: 
	      return Color.BLACK;
	    case 3: 
	      return Color.BLUE;
	    case 4: 
	      return Color.FUCHSIA;
	    case 5: 
	      return Color.GRAY;
	    case 6: 
	      return Color.GREEN;
	    case 7: 
	      return Color.LIME;
	    case 8: 
	      return Color.MAROON;
	    case 9: 
	      return Color.NAVY;
	    case 10: 
	      return Color.OLIVE;
	    case 11: 
	      return Color.ORANGE;
	    case 12: 
	      return Color.PURPLE;
	    case 13: 
	      return Color.RED;
	    case 14: 
	      return Color.SILVER;
	    case 15: 
	      return Color.TEAL;
	    case 16: 
	      return Color.WHITE;
	    }
	  }

	  static Material getRedPacketMaterial (int type)
	  {
		  switch (type)
		  {
		  case 1:   //掉落稀有矿物
		  {
			  Material[] list ={Material.IRON_INGOT,Material.COAL,Material.DIAMOND,Material.EMERALD,Material.GOLD_INGOT};
			  return list[new Random().nextInt(list.length)]; 
		  }
		  case 2:   //掉落食物
		  {
			  Material[] list ={Material.COOKED_CHICKEN,Material.COOKED_BEEF,Material.COOKED_FISH,Material.CAKE,Material.PUMPKIN_PIE};
			  return list[new Random().nextInt(list.length)]; 
		  }
		  default:  //掉落作物
		  {
			  Material[] list ={Material.WHEAT,Material.CARROT_ITEM,Material.POTATO_ITEM,Material.MELON};
			  return list[new Random().nextInt(list.length)];
		  }
		  }
	  }
	  static void setStackDisplayName (ItemStack stack ,String name)
	  {
		  ItemMeta meta = stack .getItemMeta();
		  meta.setDisplayName(name);
		  stack.setItemMeta(meta);
	  }
	  static double random ()
	  {
		  double value = Math.random();
		  double zf = Math.random();
		  if (zf>0.5)
		  {
			  value=-value;
		  }
		  return value;
	  }
	  static void boardcast (String str)
	  {
		  str = str.replace("&", "§");
		  Collection<? extends Player> players = Main.plugin.getServer().getOnlinePlayers();
		    for (Player player : players)
		    {
		    	player.sendMessage(str);
		    }
	  }
	  static String sswr (double v)
	  {
		  DecimalFormat d = new DecimalFormat("#.00");
		  String s=d.format(v);
		  if (s.startsWith("."))
		  {s="0"+s;}
		  return s;
	  }
	  static int random (int max)
	  {
		  return new Random().nextInt(max+1);
	  }
}
