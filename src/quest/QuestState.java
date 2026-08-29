package quest;

public enum QuestState {
    NOT_STARTED,
    ACTIVE,
    COMPLETED,
    FAILED // stub: per design nessuna quest CRITICO dovrebbe mai finire qui (vedi Calendar System
           // in STATUS.md) — non ancora agganciato a nessuna logica, resta un valore inutilizzato per ora.
}
