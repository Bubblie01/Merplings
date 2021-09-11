package io.github.Bubblie.Merplings.Spells;

import io.github.Bubblie.Merplings.Mana.IManaManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public abstract class Spell {
    protected final int cost;
    protected final Identifier id;
    private IManaManager manager;

    public Spell(Identifier id, int cost) {
        this.id = id;
        this.cost = cost;
    }

    public void cast(ServerPlayerEntity player) {
        if(Castable(player)) {
            manager = (IManaManager) player;
            manager.getManaManager().subtractMana(this.cost);
        }
    }

    public boolean Castable(ServerPlayerEntity player)
    {
        manager = (IManaManager) player;
        return player.isCreative() || manager.getManaManager().getManaLevel() >= this.cost;
    }

    public Identifier getId()
    {
        return this.id;
    }

    public int getCost()
    {
        return this.cost;
    }

}
