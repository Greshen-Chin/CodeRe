package character;

import enemy.Enemy;
import java.util.List;

public class Patrick extends Character {

    public Patrick() {
        super(110, 10);
    }

    @Override
    public void performAction(Enemy enemy) {
        enemy.takeDamage(10);
    }

    public void healTeam(List<Character> party) {
        for (Character c : party) {
            c.heal(20);
        }
    }
}