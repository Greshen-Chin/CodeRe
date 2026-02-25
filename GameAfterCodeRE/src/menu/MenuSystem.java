package menu;

import player.Player;
import character.*;
import character.Character;
import battle.Battle;
import gacha.GachaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuSystem {

    private Player player;
    private Scanner sc;
    private List<Character> owned;
    private List<Character> party;
    private GachaService gacha;

    public MenuSystem() {
        this.player = new Player();
        this.sc = new Scanner(System.in);
        this.owned = new ArrayList<>();
        this.party = new ArrayList<>();

        MC mc = new MC(player.getLevel());
        owned.add(mc);
        party.add(mc);

        this.gacha = new GachaService();
    }

    public void start() {
        System.out.print("Create Account Name: ");
        player.setName(sc.nextLine());

        player.resetCombatStats();

        while (true) {
            player.gainEnergyPerMenuTick();

            System.out.println("\n===== MENU =====");
            System.out.println(player.getName() + " Lv." + player.getLevel() + " EXP " + player.getExp() + "/" + player.getExpToLevel());
            System.out.println("HP " + player.getMcHp() + "/" + player.getMcMaxHp() + " ATK " + player.getMcAtk());
            System.out.println("Coin " + player.getCoin() + " Energy " + player.getEnergy());
            System.out.println("1 Gacha");
            System.out.println("2 Show Characters");
            System.out.println("3 Set Party");
            System.out.println("4 Play Stage");
            System.out.println("5 Exit");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    gacha();
                    break;
                case 2:
                    showCharacters();
                    break;
                case 3:
                    setParty();
                    break;
                case 4:
                    playStage();
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }

    private void gacha() {

        if (player.getCoin() < 500) {
            System.out.println("Not enough coin!");
            return;
        }

        player.spendCoin(500);

        Character newChar = gacha.roll(player.getLevel());
        owned.add(newChar);

        System.out.println("You got: " + newChar.getClass().getSimpleName());
    }

    private void showCharacters() {
        for (int i = 0; i < owned.size(); i++) {
            System.out.println(i + " " + owned.get(i).getClass().getSimpleName());
        }
    }

    private void setParty() {

        if (owned.size() == 1) {
            party.clear();
            party.add(owned.get(0));
            return;
        }

        while (true) {
            List<Character> temp = new ArrayList<>();

            System.out.print("Party size (1-3): ");
            int size = sc.nextInt();
            if (size < 1 || size > 3) {
                continue;
            }

            showCharacters();

            while (temp.size() < size) {
                int pick = sc.nextInt();
                if (pick < 0 || pick >= owned.size()) {
                    continue;
                }

                Character choice = owned.get(pick);
                if (!containsByName(temp, choice.getClass().getSimpleName())) {
                    temp.add(choice);
                }
            }

            System.out.println("Party " + partyNames(temp));
            System.out.print("Confirm Y/N: ");
            char confirm = sc.next().charAt(0);

            if (confirm == 'Y' || confirm == 'y') {
                party.clear();
                party.addAll(temp);
                break;
            }
        }
    }

    private void playStage() {
        System.out.print("Stage 1-4: ");
        int stage = sc.nextInt();
        if (stage < 1 || stage > 4) {
            return;
        }

        int energyCost = stage == 1 ? 10 : stage == 2 ? 20 : 30;
        if (!player.hasEnoughEnergy(energyCost)) {
            return;
        }

        player.spendEnergy(energyCost);
        player.resetCombatStats();

        for (int level = 1; level <= 10; level++) {
            player.healMc(30);
            boolean boss = level == 3 || level == 7 || level == 10;
            String difficulty = level <= 3 ? "EASY" : level <= 7 ? "MEDIUM" : "HARD";

            Battle battle = new Battle(player, party, stage, difficulty, boss, sc);
            boolean win = battle.start();

            if (!win) {
                System.out.println("Defeated");
                return;
            }

            player.addCoin(50);
            player.gainExp(30 + level * 5);
            System.out.println("Level cleared");
        }

        System.out.println("Stage cleared");
    }

    private String partyNames(List<Character> members) {
        List<String> names = new ArrayList<>();
        for (Character member : members) {
            names.add(member.getClass().getSimpleName());
        }
        return names.toString();
    }

    private boolean containsByName(List<Character> members, String name) {
        for (Character member : members) {
            if (member.getClass().getSimpleName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
