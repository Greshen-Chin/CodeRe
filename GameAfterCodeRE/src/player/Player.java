package player;

public class Player {

    private String name;
    private int level = 1;
    private int exp = 0;
    private int expToLevel = 100;
    private int coin = 350;
    private int energy = 100;
    private int maxEnergy = 100;

    private int mcHp;
    private int mcMaxHp;
    private int mcAtk;
    private int mcBuffTurn;

    private int grockHp;
    private int grockHealCd;

    public void setName(String name) {
        this.name = name;
    }

    public void gainExp(int amount) {
        exp += amount;
        while (exp >= expToLevel) {
            exp -= expToLevel;
            level++;
            expToLevel += 50;
            System.out.println("LEVEL UP " + level);
        }
    }

    public String getName() { return name; }
    public int getLevel() { return level; }
    public int getExp() { return exp; }
    public int getExpToLevel() { return expToLevel; }
    public int getCoin() { return coin; }
    public int getEnergy() { return energy; }
    public int getMaxEnergy() { return maxEnergy; }
    public int getMcHp() { return mcHp; }
    public int getMcMaxHp() { return mcMaxHp; }
    public int getMcAtk() { return mcAtk; }
    public int getGrockHp() { return grockHp; }
    public int getGrockHealCd() { return grockHealCd; }
    public int getMcBuffTurn() { return mcBuffTurn; }

    public void addCoin(int amount) {
        coin += amount;
    }

    public void spendCoin(int amount) {
        coin -= amount;
    }

    public void gainEnergyPerMenuTick() {
        energy = Math.min(maxEnergy, energy + 5);
    }

    public boolean hasEnoughEnergy(int amount) {
        return energy >= amount;
    }

    public void spendEnergy(int amount) {
        energy -= amount;
    }

    public void resetCombatStats() {
        mcMaxHp = 100 + level * 10;
        mcHp = mcMaxHp;
        mcAtk = 20 + level * 2;
        mcBuffTurn = 0;
        grockHp = 100;
        grockHealCd = 0;
    }

    public void healMc(int amount) {
        mcHp = Math.min(mcMaxHp, mcHp + amount);
    }

    public void damageMc(int amount) {
        mcHp = Math.max(0, mcHp - amount);
    }

    public boolean canUseRescue() {
        return mcBuffTurn == 0;
    }

    public void useRescue() {
        mcAtk += 10;
        healMc(30);
        mcBuffTurn = 3;
    }

    public void setGrockHealCd(int turns) {
        grockHealCd = turns;
    }

    public void healGrock(int amount) {
        grockHp += amount;
    }

    public void tickTurnEffects() {
        if (mcBuffTurn > 0) {
            mcBuffTurn--;
            if (mcBuffTurn == 0) {
                mcAtk -= 10;
            }
        }

        if (grockHealCd > 0) {
            grockHealCd--;
        }
    }
}
