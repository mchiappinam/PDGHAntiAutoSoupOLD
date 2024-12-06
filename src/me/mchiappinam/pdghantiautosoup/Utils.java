package me.mchiappinam.pdghantiautosoup;

import org.bukkit.entity.Player;

public class Utils {
	private static Main plugin;
	
	public Utils(Main main) {
		plugin=main;
	}

    public static void notifyStaff(Player player, int violationLevel, long time) {
        Violation violation = Main.getInstance().getViolation(player.getUniqueId());

        violation.updateNotify();
        Main.getInstance().violations.put(player.getUniqueId(), violation);

        for (Player staff : plugin.getServer().getOnlinePlayers()) {
            if (staff.hasPermission("pdgh.admin")) {
                staff.sendMessage("§b§l[PDGHAntiAutoSoup] §9"+player.getName()+" §eestá suspeito de usar auto soup §c(MS="+time+" | LVL="+violationLevel+"/"+plugin.getConfig().getInt("maximoLevelViolacao")+")");
            }
        }

        plugin.getServer().getConsoleSender().sendMessage("§b§l[PDGHAntiAutoSoup] §9"+player.getName()+" §eestá suspeito de usar auto soup §c(MS="+time+" | LVL="+violationLevel+"/"+plugin.getConfig().getInt("maximoLevelViolacao")+")");
    }

    public static void performAction(Player player) {
        int violationLevel = Main.getInstance().getViolation(player.getUniqueId()).getViolationLevel();
        int maxAllowedViolationLevel = plugin.getConfig().getInt("maximoLevelViolacao");

        if (maxAllowedViolationLevel == 0) return;
        if (violationLevel < maxAllowedViolationLevel) return;

        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), plugin.getConfig().getString("comando").replace("@player", player.getName()));
        plugin.getServer().broadcastMessage("§4§l[PDGH] §9"+player.getName()+" §ausou auto soup e foi banido!");
    }

    
}