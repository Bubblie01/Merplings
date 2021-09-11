package io.github.Bubblie.Merplings.Spells;

import net.minecraft.util.Identifier;

import java.util.HashMap;

public class SpellRegistry
{
    private HashMap<String, Spell> storedSpellSentences = new HashMap<>();
    private HashMap<Identifier, Spell> storedSpells = new HashMap<>();

    public Spell registerSpellsAndSentence(Spell spell, String sentence, Identifier identifier)
    {
        storedSpellSentences.put(sentence, spell);
        storedSpells.put(identifier, spell);
        return spell;
    }

    public Spell getSpell(String sentence)
    {
        return storedSpellSentences.get(sentence);
    }

    public Spell getSpell(Identifier identifier)
    {
        return storedSpells.get(identifier);
    }




}
