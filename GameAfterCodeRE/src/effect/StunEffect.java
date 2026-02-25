package effect;

import enemy.Enemy;

public class StunEffect implements StatusEffect {

    private boolean used = false;

    @Override
    public void apply(Enemy enemy) {
        enemy.setStunned(true);
        used = true;
    }

    @Override
    public boolean isExpired() {
        return used;
    }
}