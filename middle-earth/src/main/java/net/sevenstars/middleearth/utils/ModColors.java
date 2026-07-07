package net.sevenstars.middleearth.utils;

public enum ModColors {
    ALERT(0xED553B),
    WARNING(0xF6D55C),
    SUCCESS(0x00C851),
    PENDING(0x20639B),
    TEMP_1(0xff9980),
    TEMP_2(0xff704d),
    TEMP_3(0xff471a),
    TEMP_4(0xe62e00),
    TEMP_5(0xb32400);

    public final int color;
    ModColors(int color){
        this.color = color;
    }
}
