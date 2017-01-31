package ldcr.RedPacketRain;

import org.bukkit.plugin.RegisteredServiceProvider;
import net.milkbowl.vault.economy.Economy;

public class VaultUtil 
{
	static Economy eco;
	static boolean loadeco()
	{
		RegisteredServiceProvider<Economy> economy = Main.plugin.getServer().getServicesManager().getRegistrations(net.milkbowl.vault.economy.Economy.class).iterator().next();
		if (economy!=null)
		{
			eco=economy.getProvider();
		}
		return (economy!=null);
	}
}
