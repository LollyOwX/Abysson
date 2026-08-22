package main;

import java.awt.Rectangle;

// Un elemento cliccabile a schermo: un'area (bounds) più l'azione da eseguire quando ci si clicca
// dentro. Pensato per essere riusato ovunque serva un bottone — bookmark, e in futuro il contenuto
// stesso delle pagine (icone, oggetti, mostri...), non solo testo generato al volo.
public class Button {
    public Rectangle bounds;
    private final Runnable onClick;

    public Button(Runnable onClick) {
        this.onClick = onClick;
    }

    // Da richiamare a ogni frame di disegno, con la posizione/dimensione corrente a schermo —
    // cambia ogni volta perché dipende dallo scaling (finestra, libro centrato, ecc.).
    public void setBounds(int x, int y, int width, int height) {
        bounds = new Rectangle(x, y, width, height);
    }

    // Da richiamare quando arriva un click del mouse. Torna true se il click era dentro ai bounds
    // e ha eseguito l'azione — comodo per fermare il ciclo al primo bottone che risponde.
    public boolean click(int mouseX, int mouseY) {
        if (bounds != null && bounds.contains(mouseX, mouseY)) {
            onClick.run();
            return true;
        }
        return false;
    }
}