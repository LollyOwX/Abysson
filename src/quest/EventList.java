package quest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import main.GamePanel;

public class EventList {
    private interface Event { void run(Object... args); }

    private final Map<String, Event> events = new HashMap<>();

    public EventList(GamePanel gp) {
        events.put("zoom", args -> gp.zoomInOut(num(args, 0)));

        events.put("damagePit", args -> {
            gp.gameState = num(args, 0);
            gp.ui.currentDialogue = "You fell into a Pit!";
            gp.player.life -= 1;
        });

        events.put("healingPool", args -> {
            if (gp.KeyH.enterPressed) {
                gp.gameState = num(args, 0);
                gp.ui.currentDialogue = "Drinking from the Lake,\nyour vital energy got restored";
                gp.player.life = gp.player.maxLife;
            }
        });
    }

    public void run(String name, Object... args) {
        Event event = events.get(name);
        if (event == null) {
            System.err.println("EventList: unknown event " + name);
            return;
        }
        Object[] given = args == null ? new Object[]{null} : args;
        try {
            event.run(given);
        } catch (ArrayIndexOutOfBoundsException | ClassCastException e) {
            System.err.println("EventList: bad arguments for '" + name + "' " + Arrays.toString(given) + ": " + e);
        }
    }

    private static int num(Object[] args, int i) {
        if (!(args[i] instanceof Number n)) throw new ClassCastException("argument " + i + " must be a number, got " + args[i]);
        return n.intValue();
    }
}