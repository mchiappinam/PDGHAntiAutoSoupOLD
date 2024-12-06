package me.mchiappinam.pdghantiautosoup;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryInteract(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (!(event.getWhoClicked() instanceof Player)) return;
        if (event.isCancelled()) return;
        if (event.getAction() != InventoryAction.MOVE_TO_OTHER_INVENTORY) return;
        if (player.hasPermission("pdgh.admin")) return;
        if (event.getSlot() == -1) return;

        long calculatedTime = System.currentTimeMillis() - Main.getInstance().getLastAttackTime(player.getUniqueId());
        int bowls = 0;

        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack == null) continue;
            if (itemStack.getType() == Material.BOWL) bowls = bowls + itemStack.getAmount();
        }

        if (calculatedTime > 99) return;
        if (bowls < 5) return;

        int level = Main.getInstance().raiseViolationLevel(player.getUniqueId());

        if (Main.getInstance().getViolation(player.getUniqueId()).shouldNotify()) {
            Utils.notifyStaff(player, level, calculatedTime);
        }

        Utils.performAction(player);
    }
}