package character;

import enemy.Enemy;

public class Spongebob extends Character {

    public Spongebob() {
        super(100, 15);
    }

    @Override
    public void performAction(Enemy enemy) {
        enemy.setStunned(true);
    }
}