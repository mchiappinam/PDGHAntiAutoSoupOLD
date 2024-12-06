package me.mchiappinam.pdghantiautosoup;

public class Violation {
	/**private Main plugin;
	
	public Violation(Main main) {
		plugin=main;
	}*/

    private int violationLevel = 0;
    private long lastNotified = 0;

    public void raiseViolationLevel() {
        violationLevel++;
    }

    public int getViolationLevel() {
        return violationLevel;
    }

    public void updateNotify() {
        lastNotified = System.currentTimeMillis();
    }

    public boolean shouldNotify() {
        return (System.currentTimeMillis() - lastNotified) >= 5000;
    }
}