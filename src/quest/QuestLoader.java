package quest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class QuestLoader {
    private static final String FOLDER = "/quests/";

    // Ogni chiamata legge il file e crea un'istanza NUOVA (stato mutabile)
    public static Quest load(String id) {
        String path = FOLDER + id + ".txt";
        try (InputStream is = QuestLoader.class.getResourceAsStream(path)) {
            if (is == null) {
                System.err.println("Quest non trovata: " + path);
                return null;
            }
            return parse(id, new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Errore nel file quest " + path + ": " + e.getMessage());
            return null;
        }
    }

    // ── Parsing ──────────────────────────────────────────────────

    static Quest parse(String id, BufferedReader in) throws IOException {
        String title = null, description = "", giver = null;
        QuestTier tier = QuestTier.FLAVOR;
        Quest.Step step = null;
        QuestBranch branch = null;
        java.util.List<Quest.Step> steps = new java.util.ArrayList<>();

        String raw;
        int lineNo = 0;
        while ((raw = in.readLine()) != null) {
            lineNo++;
            String line = raw.trim();
            if (line.isEmpty() || line.startsWith("#")) continue;
            try {
                if (line.startsWith("title:"))            title = value(line);
                else if (line.startsWith("description:")) description = value(line);
                else if (line.startsWith("giver:"))       giver = value(line);
                else if (line.startsWith("tier:"))        tier = QuestTier.valueOf(value(line).toUpperCase());
                else if (line.startsWith("step ")) {
                    int colon = line.indexOf(':');
                    if (colon < 0) throw new IllegalArgumentException("manca ':' dopo il numero dello step");
                    String[] xy = line.substring(5, colon).trim().split("\\.");
                    int x = Integer.parseInt(xy[0]);
                    int y = (xy.length > 1) ? Integer.parseInt(xy[1]) : 0;
                    step = new Quest.Step(x, y, line.substring(colon + 1).trim());
                    steps.add(step);
                    branch = null;
                } else if (line.startsWith("branch ")) {
                    if (step == null) throw new IllegalArgumentException("branch fuori da uno step");
                    branch = parseBranch(line);
                    step.branches.add(branch);
                } else if (line.startsWith("require ")) {
                    if (branch == null) throw new IllegalArgumentException("require fuori da un branch");
                    branch.requirements.add(parseCondition(line));
                } else if (line.startsWith("continue ")) {
                    if (branch == null) throw new IllegalArgumentException("continue fuori da un branch");
                    if (branch.continuation != null) throw new IllegalArgumentException("continue già presente nel branch " + branch.id);
                    branch.continuation = parseCondition(line);
                } else {
                    throw new IllegalArgumentException("riga non riconosciuta");
                }
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("riga " + lineNo + ": " + e.getMessage());
            }
        }

        if (title == null) throw new IllegalArgumentException("manca 'title:'");
        if (steps.isEmpty()) throw new IllegalArgumentException("nessuno step definito");

        Quest quest = new Quest(id, title, description, tier, giver);
        for (Quest.Step s : steps) {
            if (quest.steps.put(Quest.key(s.x, s.y), s) != null) throw new IllegalArgumentException("step " + s.x + "." + s.y + " definito due volte");
        }
        validate(quest);
        return quest;
    }

    private static String value(String line) {
        return line.substring(line.indexOf(':') + 1).trim();
    }

    // branch <id> <FAST|IMPORTANT|COINCIDENT> [AND|OR] -> X[.Y]
    private static QuestBranch parseBranch(String line) {
        String[] t = line.split("\\s+");
        int arrow = java.util.Arrays.asList(t).indexOf("->");
        if (arrow < 3 || arrow > 4 || arrow == t.length - 1) {
            throw new IllegalArgumentException("formato: branch <id> <TIPO> [AND|OR] -> X[.Y]");
        }
        QuestBranchType type = QuestBranchType.valueOf(t[2].toUpperCase());
        boolean all = true;
        if (arrow == 4) {
            String gate = t[3].toUpperCase();
            if (!gate.equals("AND") && !gate.equals("OR")) throw new IllegalArgumentException("operatore '" + t[3] + "': usa AND oppure OR");
            all = gate.equals("AND");
        }
        String[] xy = t[arrow + 1].split("\\.");
        int x = Integer.parseInt(xy[0]);
        int y = (xy.length > 1) ? Integer.parseInt(xy[1]) : 0;
        return new QuestBranch(t[1], type, all, x, y);
    }

    // require|continue <EVENTO> <target> [xN]
    private static QuestCondition parseCondition(String line) {
        String[] t = line.split("\\s+");
        if (t.length < 3) throw new IllegalArgumentException("formato: require <EVENTO> <target> [xN]");
        QuestEventType type = QuestEventType.valueOf(t[1].toUpperCase());
        int goal = 1;
        for (int i = 3; i < t.length; i++) {
            if (t[i].matches("x\\d+")) {
                goal = Integer.parseInt(t[i].substring(1));
            } else {
                throw new IllegalArgumentException("parametro non riconosciuto: " + t[i]);
            }
        }
        return new QuestCondition(type, t[2], goal);
    }

    // ── Controlli di coerenza ────────────────────────────────────

    private static void validate(Quest quest) {
        for (Quest.Step s : quest.steps.values()) {
            for (QuestBranch b : s.branches) {
                String where = quest.id + " step " + s.x + "." + s.y + " branch " + b.id;
                if (b.requirements.isEmpty()) throw new IllegalArgumentException(where + ": nessun require");
                if (b.type != QuestBranchType.IMPORTANT && b.continuation == null) throw new IllegalArgumentException(where + ": " + b.type + " richiede 'continue'");
                if (b.type == QuestBranchType.IMPORTANT && b.continuation != null) System.err.println("AVVISO " + where + ": 'continue' ignorato (IMPORTANT prende priorità subito)");
                if (b.type != QuestBranchType.COINCIDENT && b.requirements.size() > 1) System.err.println("AVVISO " + where + ": più require, usa COINCIDENT");
                if (!quest.steps.containsKey(Quest.key(b.targetStep, b.targetMicro))) System.err.println("AVVISO " + where + ": situazione di arrivo " + b.targetStep + "." + b.targetMicro + " non definita");
                if (b.targetStep < s.x || (b.targetStep == s.x && b.targetMicro <= s.y)) System.err.println("AVVISO " + where + ": porta a una situazione non successiva (" + b.targetStep + "." + b.targetMicro + ")");
            }
        }
    }
}
