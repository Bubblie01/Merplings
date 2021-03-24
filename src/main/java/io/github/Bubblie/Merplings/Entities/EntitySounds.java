package io.github.Bubblie.Merplings.Entities;



import io.github.Bubblie.Merplings.Main;
import net.minecraft.client.sound.Sound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
public class EntitySounds
{
    public static final Identifier BAO_SCREAM_ID = new Identifier("merp:bao_scream");
    public static SoundEvent BAO_SCREAM_EVENT = new SoundEvent(BAO_SCREAM_ID);

    public static final Identifier BAO_AMBIENT_ID = new Identifier("merp:bao_ambient");
    public static SoundEvent BAO_AMBIENT_EVENT = new SoundEvent(BAO_AMBIENT_ID);

    public static final Identifier BAO_DEATH_ID = new Identifier("merp:bao_death");
    public static SoundEvent BAO_DEATH_EVENT = new SoundEvent(BAO_DEATH_ID);

    public static void registerEntitySounds()
    {
        Registry.register(Registry.SOUND_EVENT, EntitySounds.BAO_SCREAM_ID, EntitySounds.BAO_SCREAM_EVENT);
        Registry.register(Registry.SOUND_EVENT, EntitySounds.BAO_AMBIENT_ID, EntitySounds.BAO_AMBIENT_EVENT);
        Registry.register(Registry.SOUND_EVENT, EntitySounds.BAO_DEATH_ID, EntitySounds.BAO_DEATH_EVENT);
    }
}
