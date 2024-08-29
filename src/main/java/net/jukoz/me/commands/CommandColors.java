package net.jukoz.me.commands;

public enum CommandColors {
    ALERT(15553851), //ED553B;
    WARNING(16176476), //F6D55C;
    SUCCESS(51281), //00C851;
    PENDING(2122651); //20639B;

    public final int color;
    CommandColors(int color){
        this.color = color;
    }
}
