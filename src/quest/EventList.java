package quest;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntConsumer;
import main.GamePanel;

public class EventList {
    private final Map<String, IntConsumer> events = new HashMap<>();

    public EventList(GamePanel gp) {
        events.put("zoom", gp::zoomInOut);
    }

    public void run(String name, int value) {
        IntConsumer event = events.get(name);
        if (event == null) System.err.println("EventList: unknown event " + name);
        else event.accept(value);
    }
}
