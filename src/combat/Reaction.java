package combat;
import combat.ElementSystem.*;

public class Reaction {
    public final String name;
    public final int bonusDamage;              // danno immediato extra (0 se nessuno)
    public final Element bonusDamageElement;   // elemento del danno bonus (per triggerare altre reazioni)
    public final StatusEffect effect;          // effetto di stato principale applicato
    public final int effectDuration;           // turni di durata (0 = solo questo turno, -1 = fino a fine combattimento)
    public final double damageMultiplier;      // moltiplicatore sul danno che ha triggerato la reazione (1.0 = nessuno)
    public final StatusEffect secondaryEffect; // secondo effetto, per reazioni che ne applicano due (es. Esplosione)
    public final int secondaryDuration;
    public final Element reapplyElement;       // elemento da assegnare a lastElementHit (NONE = quello in arrivo, default)
    public final boolean disarms;              // Inondazione: disequipaggia l'arma del bersaglio

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

    /** Nessuna reazione */
    public static final Reaction NONE = new Builder("", StatusEffect.NONE).build();

    /**
     * Builder al posto di un costruttore con 9 parametri posizionali: ogni reazione in
     * ElementSystem.getReaction() imposta solo i campi che le servono davvero, il resto
     * resta ai default (nessun bonus danno, nessun secondo effetto, nessun disarmo...).
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
