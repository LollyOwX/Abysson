package tile;

public enum SpecialTile {

    // Casa 1
    vaso_casa1(true),
    vaso_casa1_rotto(true),

    // Casa 2
    fiore_casa2(false),
    fiore_casa2_appassito(false);

    public final boolean collision;

    SpecialTile(boolean collision) {
        this.collision = collision;
    }
}