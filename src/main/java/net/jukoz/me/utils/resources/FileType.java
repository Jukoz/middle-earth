package net.jukoz.me.utils.resources;

public enum FileType {
    Png ("png"),
    Jpg("jpg");

    public final String extension;
    FileType(String extension){
        this.extension = extension;
    }
}