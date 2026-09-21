package items;

/**
 * Uno slot "componente" innestato in un'arma/armatura/gioiello — gemma, incantesimo o roccia.
 * Porta SOLO un id (stringa): nessuna logica qui dentro. L'id è risolto da Item.addComponents()
 * via combat.SpecialEffectRegistry — gemme/incantesimi/rocce danno SEMPRE un effetto speciale
 * (PASSIVO o ATTIVO, vedi SpecialEffect), mai un bonus % diretto: quello lo danno solo i campi
 * grezzi di Weapon/Armor tramite MaterialRegistry, non i componenti.
 *
 * null in uno slot (Weapon.gemme[i], Armor.incantesimi[i]...) = slot vuoto, non un Component.
 */
public class Component {
    public final String id; // es. "lifesteal", "ruby_shard" — riferimento a un SpecialEffect

    public Component(String id) {
        this.id = id;
    }
}
