package character;

import enemy.Enemy;

public class Grock extends Character {

    public Grock() {
        super(150, 5);
    }

    @Override
    public void performAction(Enemy enemy) {
        enemy.takeDamage(5);
    }

    public void healSelf() {
        heal(20);
    }
}