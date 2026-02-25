package character;

import enemy.Enemy;

public abstract class Character {

    protected int hp;
    protected int maxHp;
    protected int attack;

    public Character(int hp, int attack) {
        this.hp = hp;
        this.maxHp = hp;
        this.attack = attack;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void takeDamage(int dmg) {
        hp = Math.max(0, hp - dmg);
    }

    public void heal(int amount) {
        hp = Math.min(maxHp, hp + amount);
    }

    public abstract void performAction(Enemy enemy);
}