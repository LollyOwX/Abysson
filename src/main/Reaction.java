package main;
import main.ElementSystem.*;

/**
 * Descrive l'effetto di una reazione tra due elementi.
 *
 * bonusDamage      → danno immediato extra (0 se nessuno)
 * bonusDamageElement → elemento del danno bonus (per triggerare altre reazioni)
 * effect           → effetto di stato persistente applicato
 * effectDuration   → turni di durata (0 = solo questo turno, -1 = fino a fine combattimento)
 * damageMultiplier → moltiplicatore sul danno che ha triggerato la reazione (1.0 = nessuno)
 */
public class Reaction {
    public final String name;
    public final int bonusDamage;
    public final Element bonusDamageElement;
    public final StatusEffect effect;
    public final int effectDuration;
    public final double damageMultiplier;

    public Reaction(String name, int bonusDamage, Element bonusDamageElement,
                    StatusEffect effect, int effectDuration, double damageMultiplier) {
        this.name = name;
        this.bonusDamage = bonusDamage;
        this.bonusDamageElement = bonusDamageElement;
        this.effect = effect;
        this.effectDuration = effectDuration;
        this.damageMultiplier = damageMultiplier;
    }

    /** Nessuna reazione */
    public static final Reaction NONE = new Reaction("", 0, Element.NONE, StatusEffect.NONE, 0, 1.0);
}
