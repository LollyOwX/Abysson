package items;

import combat.ElementSystem.Element;

/**
 * Tipi di danno/azione per la durabilità (Item.tipologie): quando un'azione di un certo tipo
 * colpisce o viene fatta con un oggetto che ha quel tipo nella sua lista, l'oggetto perde
 * durabilità; un tipo non in lista non la consuma (es. VELENO su un'armatura senza VELENO).
 * Nuovo tipo = una costante qui (e, se serve, nel default di defensiveDefault()).
 */
public final class DamageTypes {
    private DamageTypes() {}

    // Elementi (stessi nomi di ElementSystem.Element)
    public static final String FISICO = "FISICO", LUCE = "LUCE", FUOCO = "FUOCO", ACQUA = "ACQUA",
                               TERRA = "TERRA", ARIA = "ARIA", FULMINE = "FULMINE";
    // Speciali
    public static final String EROSIONE = "EROSIONE", VELENO = "VELENO", PSICHICO = "PSICHICO",
                               SANGUINAMENTO = "SANGUINAMENTO", MALEDIZIONE = "MALEDIZIONE";

    /** Armature e scudi: tutti gli elementi + EROSIONE; niente VELENO/PSICHICO/SANGUINAMENTO/MALEDIZIONE. */
    public static String[] defensiveDefault() {
        return new String[]{FISICO, LUCE, FUOCO, ACQUA, TERRA, ARIA, FULMINE, EROSIONE};
    }

    /** Tipo di un'azione dal suo elemento: NONE (attacco normale) conta come FISICO. */
    public static String of(Element e) {
        return (e == null || e == Element.NONE) ? FISICO : e.name();
    }
}
