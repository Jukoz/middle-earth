package net.jukoz.me.utils;

public enum ModColors {
    ALERT(0xED553B),
    WARNING(0xF6D55C),
    SUCCESS(0x00C851),
    PENDING(0x20639B);

    public final int color;
    ModColors(int color){
        this.color = color;
    }
}
