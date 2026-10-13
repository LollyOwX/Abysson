package items;

import java.util.ArrayList;
import java.util.List;

public class Resource {
    public String name, itemPath;
    public int value, quantity = 1;
    public String description;                 // breve descrizione e come si ottiene
    public String processExtra;                // icona facoltativa sopra la catena (es. combustibile)
    public String processPrimary, processTool, processRefined; // icone: materiale primario -> strumento -> materiale rifinito
    public final List<String> usages = new ArrayList<>();      // icone dei materiali FINITI, uno per cella della tabella

    public Resource(String name, int value, int quantity, String description) {
        this.name = name;
        this.value = value;
        this.quantity = quantity;
        this.description = description;
    }
}
