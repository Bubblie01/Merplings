package io.github.Bubblie.Merplings.Entities;



import io.github.Bubblie.Merplings.Main;
import net.minecraft.client.sound.Sound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
public class ModSounds
{
    public static final Identifier BAO_SCREAM_ID = new Identifier("merp:bao_scream");
    public static SoundEvent BAO_SCREAM_EVENT = new SoundEvent(BAO_SCREAM_ID);

    public static final Identifier BAO_AMBIENT_ID = new Identifier("merp:bao_ambient");
    public static SoundEvent BAO_AMBIENT_EVENT = new SoundEvent(BAO_AMBIENT_ID);

    public static final Identifier BAO_DEATH_ID = new Identifier("merp:bao_death");
    public static SoundEvent BAO_DEATH_EVENT = new SoundEvent(BAO_DEATH_ID);

    public static final Identifier SCRIBBLE_SOUND_ID = new Identifier("merp:scribble");
    public static SoundEvent SCRIBBLE_SOUND_EVENT = new SoundEvent(SCRIBBLE_SOUND_ID);

    public static void registerSounds()
    {
        Registry.register(Registry.SOUND_EVENT, ModSounds.BAO_SCREAM_ID, ModSounds.BAO_SCREAM_EVENT);
        Registry.register(Registry.SOUND_EVENT, ModSounds.BAO_AMBIENT_ID, ModSounds.BAO_AMBIENT_EVENT);
        Registry.register(Registry.SOUND_EVENT, ModSounds.BAO_DEATH_ID, ModSounds.BAO_DEATH_EVENT);
        Registry.register(Registry.SOUND_EVENT, ModSounds.SCRIBBLE_SOUND_ID, ModSounds.SCRIBBLE_SOUND_EVENT);
    }
}
