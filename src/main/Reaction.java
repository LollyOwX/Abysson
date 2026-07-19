package main;
import main.ElementSystem.*;


public class Reaction {
    public final String name;
    public final int bonusDamage; //danno immediato extra (0 se nessuno)
    public final Element bonusDamageElement; //elemento del danno bonus (per triggerare altre reazioni)
    public final StatusEffect effect; //effetto di stato persistente applicato
    public final int effectDuration; //turni di durata (0 = solo questo turno, -1 = fino a fine combattimento)
    public final double damageMultiplier; //moltiplicatore sul danno che ha triggerato la reazione (1.0 = nessuno)

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
