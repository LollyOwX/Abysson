package items;

/**
 * Uno slot "componente" innestato in un'arma/armatura/gioiello — gemma, incantesimo o roccia.
 * Per ora porta SOLO un id (stringa): nessuna logica qui dentro. L'id punterà in futuro a una
 * definizione in un registry statico (stesso pattern di combat.Ability/quest.QuestRegistry —
 * uno switch su id), quando costruirai l'"add components": la funzione che legge tutti i
 * componenti equipaggiati su un oggetto e li riduce in UN blocco di bonus/effetti unico
 * (invece di controllarli uno per uno con un if per ciascuno). Quella parte l'hai detto di
 * rimandare al futuro — questa classe è solo il contenitore vuoto che la aspetta.
 *
 * null in uno slot (Weapon.gemme[i], Armor.incantesimi[i]...) = slot vuoto, non un Component.
 */
public class Component {
    public final String id; // es. "lifesteal", "ruby_shard" — riferimento a una definizione futura

    public Component(String id) {
        this.id = id;
    }
}
