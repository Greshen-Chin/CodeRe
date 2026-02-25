package character;

import enemy.Enemy;

public class MC extends Character {

    public MC(int level) {
        super(100 + level * 10, 20 + level * 2);
    }

    @Override
    public void performAction(Enemy enemy) {
        enemy.takeDamage(attack);
    }

    public void rescue() {
        this.attack += 10;
        this.heal(30);
    }
}