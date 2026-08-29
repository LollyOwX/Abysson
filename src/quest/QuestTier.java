package quest;

/**
 * Rispecchia i 3 livelli del Calendar System (STATUS.md, sezione "Calendar System"): cambia
 * solo come la quest si comporta rispetto al gating temporale, non la sua logica di step
 * (quella resta identica per tutti i tier — vedi Quest/QuestStep).
 */
public enum QuestTier {
    CRITICO,  // mai davvero perdibile
    MAGGIORE, // finestre generose, spesso ciclico
    FLAVOR    // genuinamente perdibile
}
