package effect;

import enemy.Enemy;

public class BurnEffect implements StatusEffect {

    private int turn = 2;

    @Override
    public void apply(Enemy enemy) {
        enemy.takeDamage(10);
        turn--;
    }

    @Override
    public boolean isExpired() {
        return turn <= 0;
    }
}