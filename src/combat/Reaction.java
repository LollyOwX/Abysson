package combat;
import combat.ElementSystem.*;

public class Reaction {
    public final String name;
    public final int bonusDamage;              // extra immediate damage (0 if none)
    public final Element bonusDamageElement;   // element of the bonus damage (to trigger other reactions)
    public final StatusEffect effect;          // main status effect applied
    public final int effectDuration;           // turns of duration (0 = this turn only, -1 = until end of combat)
    public final double damageMultiplier;      // multiplier on the damage that triggered the reaction (1.0 = none)
    public final StatusEffect secondaryEffect; // second effect, for reactions that apply two (e.g. Explosion)
    public final int secondaryDuration;
    public final Element reapplyElement;       // element to assign to lastElementHit (NONE = the incoming one, default)
    public final boolean disarms;              // Flooding: unequips the target's weapon

    private Reaction(Builder b) {
        this.name               = b.name;
        this.bonusDamage        = b.bonusDamage;
        this.bonusDamageElement = b.bonusDamageElement;
        this.effect              = b.effect;
        this.effectDuration      = b.effectDuration;
        this.damageMultiplier    = b.damageMultiplier;
        this.secondaryEffect     = b.secondaryEffect;
        this.secondaryDuration   = b.secondaryDuration;
        this.reapplyElement      = b.reapplyElement;
        this.disarms             = b.disarms;
    }

    /** No reaction */
    public static final Reaction NONE = new Builder("", StatusEffect.NONE).build();

    /**
     * Builder instead of a constructor with 9 positional parameters: each reaction in
     * ElementSystem.getReaction() sets only the fields it really needs, the rest
     * stays at the defaults (no bonus damage, no second effect, no disarm...).
     */
    public static class Builder {
        private final String name;
        private final StatusEffect effect;
        private int bonusDamage = 0;
        private Element bonusDamageElement = Element.NONE;
        private int effectDuration = 0;
        private double damageMultiplier = 1.0;
        private StatusEffect secondaryEffect = StatusEffect.NONE;
        private int secondaryDuration = 0;
        private Element reapplyElement = Element.NONE;
        private boolean disarms = false;

        public Builder(String name, StatusEffect effect) {
            this.name = name;
            this.effect = effect;
        }

        public Builder bonus(int dmg, Element el)         { bonusDamage = dmg; bonusDamageElement = el; return this; }
        public Builder duration(int d)                    { effectDuration = d; return this; }
        public Builder multiplier(double m)                { damageMultiplier = m; return this; }
        public Builder also(StatusEffect e, int d)         { secondaryEffect = e; secondaryDuration = d; return this; }
        public Builder reapply(Element e)                  { reapplyElement = e; return this; }
        public Builder disarm()                             { disarms = true; return this; }

        public Reaction build() { return new Reaction(this); }
    }
}
