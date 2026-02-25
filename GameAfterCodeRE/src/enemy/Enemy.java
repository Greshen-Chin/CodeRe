package enemy;

import character.Character;
import effect.StatusEffect;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Enemy {

    private int hp;
    private int attack;
    private boolean stunned;
    private List<StatusEffect> effects = new ArrayList<>();

    public Enemy(int hp, int attack) {
        this.hp = hp;
        this.attack = attack;
    }

    public void takeDamage(int dmg) {
        hp = Math.max(0, hp - dmg);
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void attack(Character target) {
        if (!stunned) {
            target.takeDamage(attack);
        }
        stunned = false;
    }

    public void setStunned(boolean value) {
        this.stunned = value;
    }

    public void addEffect(StatusEffect effect) {
        effects.add(effect);
    }

    public void applyEffects() {
        Iterator<StatusEffect> it = effects.iterator();
        while (it.hasNext()) {
            StatusEffect effect = it.next();
            effect.apply(this);
            if (effect.isExpired()) {
                it.remove();
            }
        }
    }

    public int getHp() {
        return hp;
    }
}