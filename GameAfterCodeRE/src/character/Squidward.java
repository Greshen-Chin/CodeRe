package character;

import enemy.Enemy;

public class Squidward extends Character {

    public Squidward() {
        super(100, 25);
    }

    @Override
    public void performAction(Enemy enemy) {
        enemy.takeDamage(25);
    }
}