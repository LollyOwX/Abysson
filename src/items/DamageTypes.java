package items;

import combat.ElementSystem.Element;

/**
 * Tipi di danno/azione per la durabilità (Item.tipologie): quando un'azione di un certo tipo
 * colpisce o viene fatta con un oggetto che ha quel tipo nella sua lista, l'oggetto perde
 * durabilità; un tipo non in lista non la consuma (es. POISON su un'armatura senza POISON).
 * Nuovo tipo = una costante qui (e, se serve, nel default di defensiveDefault()).
 */
public final class DamageTypes {
    private DamageTypes() {}

    // Elementi (stessi nomi di ElementSystem.Element)
    public static final String PHYSICAL = "PHYSICAL", LIGHT = "LIGHT", FIRE = "FIRE", WATER = "WATER",
                               EARTH = "EARTH", WIND = "WIND", ELECTRIC = "ELECTRIC";
    // Speciali
    public static final String EROSION = "EROSION", POISON = "POISON", PSYCHIC = "PSYCHIC",
                               BLEEDING = "BLEEDING", CURSE = "CURSE";

    /** Armature e scudi: tutti gli elementi + EROSION; niente POISON/PSYCHIC/BLEEDING/CURSE. */
    public static String[] defensiveDefault() {
        return new String[]{PHYSICAL, LIGHT, FIRE, WATER, EARTH, WIND, ELECTRIC, EROSION};
    }

    /** Tipo di un'azione dal suo elemento: NONE (attacco normale) conta come PHYSICAL. */
    public static String of(Element e) {
        return (e == null || e == Element.NONE) ? PHYSICAL : e.name();
    }
}
