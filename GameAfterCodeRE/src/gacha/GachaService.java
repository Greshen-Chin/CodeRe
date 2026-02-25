package gacha;

import character.*;
import character.Character;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class GachaService {

    private final List<Supplier<Character>> pool = List.of(
            Saber::new,
            Grock::new,
            Spongebob::new,
            Patrick::new,
            Squidward::new
    );

    private final Random random = new Random();

    public Character roll(int level) {
        int index = random.nextInt(pool.size());
        return pool.get(index).get();
    }
}