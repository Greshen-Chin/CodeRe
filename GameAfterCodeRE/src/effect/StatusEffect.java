package effect;

import enemy.Enemy;

public interface StatusEffect {
	void apply(Enemy enemy);
    boolean isExpired();
}
