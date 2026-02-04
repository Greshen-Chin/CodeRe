package main;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {

    static Scanner sc = new Scanner(System.in);
    static Random rand = new Random();

    static String mcName;
    static int coin = 350;
    static int energy = 100;
    static int maxEnergy = 100;

    static int mcLevel = 1;
    static int mcExp = 0;
    static int expToLevel = 100;

    static int mcHp;
    static int mcMaxHp;
    static int mcAtk;
    static int mcBuffTurn;

    static int grockHp;
    static int grockHealCd;

    static boolean enemyStunned;
    static boolean enemyBurn;
    static int enemyBurnTurn;

    static ArrayList<String> owned = new ArrayList<>();
    static ArrayList<String> party = new ArrayList<>();

    public static void main(String[] args) {

        System.out.print("Create Account Name: ");
        mcName = sc.nextLine();

        owned.add("MC");
        party.add("MC");

        resetPlayer();

        while (true) {
            energy = Math.min(maxEnergy, energy + 5);

            System.out.println("\n===== MENU =====");
            System.out.println(mcName + " Lv." + mcLevel + " EXP " + mcExp + "/" + expToLevel);
            System.out.println("HP " + mcHp + "/" + mcMaxHp + " ATK " + mcAtk);
            System.out.println("Coin " + coin + " Energy " + energy);
            System.out.println("1 Gacha");
            System.out.println("2 Show Characters");
            System.out.println("3 Set Party");
            System.out.println("4 Play Stage");
            System.out.println("5 Exit");

            int c = sc.nextInt();

            if (c == 1) gacha();
            else if (c == 2) showCharacters();
            else if (c == 3) setParty();
            else if (c == 4) playStage();
            else System.exit(0);
        }
    }

    static void gacha() {
        if (coin < 500) return;

        coin -= 500;
        int r = rand.nextInt(100);
        String res;

        if (r < 20) res = "Saber";
        else if (r < 40) res = "Grock";
        else if (r < 60) res = "Spongebob";
        else if (r < 80) res = "Patrick";
        else res = "Squidward";

        owned.add(res);
        System.out.println("You got " + res);
    }

    static void showCharacters() {
        for (int i = 0; i < owned.size(); i++) {
            System.out.println(i + " " + owned.get(i));
        }
    }

    static void setParty() {

        if (owned.size() == 1) {
            party.clear();
            party.add("MC");
            return;
        }

        while (true) {
            ArrayList<String> temp = new ArrayList<>();

            System.out.print("Party size (1-3): ");
            int s = sc.nextInt();
            if (s < 1 || s > 3) continue;

            showCharacters();

            while (temp.size() < s) {
                int p = sc.nextInt();
                if (p < 0 || p >= owned.size()) continue;
                String ch = owned.get(p);
                if (!temp.contains(ch)) temp.add(ch);
            }

            System.out.println("Party " + temp);
            System.out.print("Confirm Y/N: ");
            char y = sc.next().charAt(0);

            if (y == 'Y' || y == 'y') {
                party.clear();
                party.addAll(temp);
                break;
            }
        }
    }

    static void playStage() {

        System.out.print("Stage 1-4: ");
        int stage = sc.nextInt();
        if (stage < 1 || stage > 4) return;

        int cost = stage == 1 ? 10 : stage == 2 ? 20 : 30;
        if (energy < cost) return;
        energy -= cost;

        resetPlayer();

        for (int level = 1; level <= 10; level++) {

            mcHp = Math.min(mcMaxHp, mcHp + 30);

            boolean boss = level == 3 || level == 7 || level == 10;
            String diff = level <= 3 ? "EASY" : level <= 7 ? "MEDIUM" : "HARD";

            boolean win = fight(stage, diff, boss);

            if (!win) {
                System.out.println("Defeated");
                return;
            }

            coin += 50;
            gainExp(30 + level * 5);
            System.out.println("Level cleared");
        }

        System.out.println("Stage cleared");
    }

    static void resetPlayer() {
        mcMaxHp = 100 + mcLevel * 10;
        mcHp = mcMaxHp;
        mcAtk = 20 + mcLevel * 2;
        mcBuffTurn = 0;

        grockHp = 100;
        grockHealCd = 0;
    }

    static boolean fight(int stage, String diff, boolean boss) {

        int enemyHp = diff.equals("EASY") ? 50 : diff.equals("MEDIUM") ? 90 : 140;
        int enemyAtk = diff.equals("EASY") ? 12 : diff.equals("MEDIUM") ? 18 : 25;

        if (boss) enemyHp += 120;

        enemyStunned = false;
        enemyBurn = false;
        enemyBurnTurn = 0;

        while (enemyHp > 0 && mcHp > 0) {

            System.out.println("\nEnemy HP " + enemyHp);
            System.out.println(mcName + " HP " + mcHp);

            for (String c : party) {
                if (enemyHp <= 0) break;

                if (c.equals("MC")) {
                    System.out.println("1 Attack 2 Rescue");
                    int m = sc.nextInt();
                    if (m == 1) enemyHp -= mcAtk;
                    else if (m == 2 && mcBuffTurn == 0) {
                        mcAtk += 10;
                        mcHp += 30;
                        mcHp = Math.min(mcHp, mcMaxHp);
                        mcBuffTurn = 3;
                    }
                }

                else if (c.equals("Grock")) {
                    System.out.println("1 Attack 2 Heal");
                    int g = sc.nextInt();
                    if (g == 1) enemyHp -= 5;
                    else if (g == 2 && grockHealCd == 0) {
                        grockHp += 20;
                        grockHealCd = 3;
                    }
                }

                else if (c.equals("Patrick")) {
                    mcHp = Math.min(mcMaxHp, mcHp + 20);
                }

                else if (c.equals("Spongebob")) {
                    enemyStunned = true;
                }

                else if (c.equals("Squidward")) {
                    enemyHp -= 25;
                }

                else if (c.equals("Saber")) {
                    System.out.println("1 Attack 2 Kill");
                    int s = sc.nextInt();
                    if (s == 1) enemyHp -= 40;
                    else enemyHp = 0;
                }

                enemyHp = Math.max(enemyHp, 0);
            }

            if (enemyBurn) {
                enemyHp -= 10;
                enemyBurnTurn--;
                if (enemyBurnTurn <= 0) enemyBurn = false;
                enemyHp = Math.max(enemyHp, 0);
            }

            if (enemyHp > 0 && !enemyStunned) {

                int dmg = enemyAtk;

                boolean rage = boss && enemyHp < 80;

                if (rage) dmg += 15;

                int roll = rand.nextInt(100);

                if (boss && stage >= 3 && roll < 25) {
                    System.out.println("Ice Blizzard");
                    dmg += 25;
                    enemyStunned = true;
                }

                if (boss && stage == 4 && roll < 30) {
                    System.out.println("Fire Ball");
                    dmg += 40;
                    enemyBurn = true;
                    enemyBurnTurn = 2;
                }

                mcHp -= dmg;
                mcHp = Math.max(mcHp, 0);
            }

            enemyStunned = false;

            if (mcBuffTurn > 0) {
                mcBuffTurn--;
                if (mcBuffTurn == 0) mcAtk -= 10;
            }

            if (grockHealCd > 0) grockHealCd--;
        }

        return mcHp > 0;
    }

    static void gainExp(int amt) {
        mcExp += amt;
        while (mcExp >= expToLevel) {
            mcExp -= expToLevel;
            mcLevel++;
            expToLevel += 50;
            System.out.println("LEVEL UP " + mcLevel);
        }
    }
}
