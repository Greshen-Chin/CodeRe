package battle;

import character.Character;
import character.Grock;
import character.MC;
import character.Patrick;
import character.Saber;
import character.Spongebob;
import character.Squidward;
import player.Player;
import util.RandomUtil;

import java.util.List;
import java.util.Scanner;

public class Battle {

    private final Player player;
    private final List<Character> party;
    private final int stage;
    private final String difficulty;
    private final boolean boss;
    private final Scanner sc;

    public Battle(Player player, List<Character> party, int stage, String difficulty, boolean boss, Scanner sc) {
        this.player = player;
        this.party = party;
        this.stage = stage;
        this.difficulty = difficulty;
        this.boss = boss;
        this.sc = sc;
    }

    public boolean start() {

        int enemyHp = baseEnemyHp();
        int enemyAtk = baseEnemyAtk();

        if (boss) {
            enemyHp += 120;
        }

        boolean enemyStunned = false;
        boolean enemyBurn = false;
        int enemyBurnTurn = 0;

        while (enemyHp > 0 && player.getMcHp() > 0) {
            System.out.println("\nEnemy HP " + enemyHp);
            System.out.println(player.getName() + " HP " + player.getMcHp());

            for (Character c : party) {
                if (enemyHp <= 0) {
                    break;
                }

                if (c instanceof MC) {
                    System.out.println("1 Attack 2 Rescue");
                    int choice = sc.nextInt();
                    if (choice == 1) {
                        enemyHp -= player.getMcAtk();
                    } else if (choice == 2 && player.canUseRescue()) {
                        player.useRescue();
                    }
                } else if (c instanceof Grock) {
                    System.out.println("1 Attack 2 Heal");
                    int choice = sc.nextInt();
                    if (choice == 1) {
                        enemyHp -= 5;
                    } else if (choice == 2 && player.getGrockHealCd() == 0) {
                        player.healGrock(20);
                        player.setGrockHealCd(3);
                    }
                } else if (c instanceof Patrick) {
                    player.healMc(20);
                } else if (c instanceof Spongebob) {
                    enemyStunned = true;
                } else if (c instanceof Squidward) {
                    enemyHp -= 25;
                } else if (c instanceof Saber) {
                    System.out.println("1 Attack 2 Kill");
                    int choice = sc.nextInt();
                    if (choice == 1) {
                        enemyHp -= 40;
                    } else {
                        enemyHp = 0;
                    }
                }

                enemyHp = Math.max(enemyHp, 0);
            }

            if (enemyBurn) {
                enemyHp -= 10;
                enemyBurnTurn--;
                if (enemyBurnTurn <= 0) {
                    enemyBurn = false;
                }
                enemyHp = Math.max(enemyHp, 0);
            }

            if (enemyHp > 0 && !enemyStunned) {
                int damage = enemyAtk;

                if (boss && enemyHp < 80) {
                    damage += 15;
                }

                int roll = RandomUtil.nextInt(100);

                if (boss && stage >= 3 && roll < 25) {
                    System.out.println("Ice Blizzard");
                    damage += 25;
                    enemyStunned = true;
                }

                if (boss && stage == 4 && roll < 30) {
                    System.out.println("Fire Ball");
                    damage += 40;
                    enemyBurn = true;
                    enemyBurnTurn = 2;
                }

                player.damageMc(damage);
            }

            enemyStunned = false;
            player.tickTurnEffects();
        }

        return player.getMcHp() > 0;
    }

    private int baseEnemyHp() {
        if ("EASY".equals(difficulty)) {
            return 50;
        }
        if ("MEDIUM".equals(difficulty)) {
            return 90;
        }
        return 140;
    }

    private int baseEnemyAtk() {
        if ("EASY".equals(difficulty)) {
            return 12;
        }
        if ("MEDIUM".equals(difficulty)) {
            return 18;
        }
        return 25;
    }
}
