package net.jukoz.me.gui.artisantable;

public class ArtisanTableInputsShape {
    private boolean[][] inputs;

    public static ArtisanTableInputsShape STRAIGHT = new ArtisanTableInputsShape(new boolean[][]{
            {false, true, false},
            {false, true, false},
            {false, true, false}});

    public static ArtisanTableInputsShape BOW = new ArtisanTableInputsShape(new boolean[][]{
            {true, true, false},
            {true, false, true},
            {true, true, false}});

    public ArtisanTableInputsShape(boolean[][] inputs) {
        this.inputs = inputs;
    }
}
