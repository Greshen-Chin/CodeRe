package character;

import enemy.Enemy;

public class Saber extends Character {

    public Saber() {
        super(120, 40);
    }

    @Override
    public void performAction(Enemy enemy) {
        enemy.takeDamage(40);
    }
    
    public void instantKill(Enemy enemy) {
        enemy.takeDamage(9999);
    }
}