package net.jukoz.me.gui.artisantable;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.utils.LoggerUtil;

import java.util.ArrayList;
import java.util.List;

public class ArtisanTableInputsShape {
    private static List<ArtisanTableInputsShape> shapes = new ArrayList<>();
    private InputType[][] inputs;
    private String id;

    public static ArtisanTableInputsShape ANY = new ArtisanTableInputsShape("any", new InputType[][]{
            {InputType.ANY, InputType.ANY, InputType.ANY},
            {InputType.ANY, InputType.ANY, InputType.ANY},
            {InputType.ANY, InputType.ANY, InputType.ANY}});

    public static ArtisanTableInputsShape SWORD = new ArtisanTableInputsShape("sword", new InputType[][]{
            {InputType.NONE, InputType.NONE, InputType.BLADE},
            {InputType.NONE, InputType.HILT, InputType.NONE},
            {InputType.HANDLE, InputType.NONE, InputType.NONE}});

    public static ArtisanTableInputsShape AXE = new ArtisanTableInputsShape("axe", new InputType[][]{
            {InputType.NONE, InputType.NONE, InputType.AXE},
            {InputType.NONE, InputType.HANDLE, InputType.NONE},
            {InputType.HANDLE, InputType.NONE, InputType.NONE}});

    public static ArtisanTableInputsShape SPEAR = new ArtisanTableInputsShape("spear", new InputType[][]{
            {InputType.NONE, InputType.NONE, InputType.BLADE},
            {InputType.NONE, InputType.HANDLE, InputType.NONE},
            {InputType.HANDLE, InputType.NONE, InputType.NONE}});

    public static ArtisanTableInputsShape BOW = new ArtisanTableInputsShape("bow", new InputType[][]{
            {InputType.ANY, InputType.ANY, InputType.NONE},
            {InputType.ANY, InputType.NONE, InputType.ANY},
            {InputType.ANY, InputType.ANY, InputType.NONE}});

    public static ArtisanTableInputsShape PICKAXE = new ArtisanTableInputsShape("pickaxe", new InputType[][]{
            {InputType.NONE, InputType.NONE, InputType.PICKAXE},
            {InputType.NONE, InputType.HANDLE, InputType.NONE},
            {InputType.HANDLE, InputType.NONE, InputType.NONE}});

    public static ArtisanTableInputsShape SHOVEL = new ArtisanTableInputsShape("shovel", new InputType[][]{
            {InputType.NONE, InputType.NONE, InputType.SHOVEL},
            {InputType.NONE, InputType.HANDLE, InputType.NONE},
            {InputType.HANDLE, InputType.NONE, InputType.NONE}});

    public static ArtisanTableInputsShape HOE = new ArtisanTableInputsShape("hoe", new InputType[][]{
            {InputType.NONE, InputType.NONE, InputType.HOE},
            {InputType.NONE, InputType.HANDLE, InputType.NONE},
            {InputType.HANDLE, InputType.NONE, InputType.NONE}});

    public static ArtisanTableInputsShape HELMET = new ArtisanTableInputsShape("helmet", new InputType[][]{
            {InputType.ANY, InputType.ANY, InputType.ANY},
            {InputType.ANY, InputType.NONE, InputType.ANY},
            {InputType.NONE, InputType.NONE, InputType.NONE}});

    public static ArtisanTableInputsShape CHESTPLATE = new ArtisanTableInputsShape("chestplate", new InputType[][]{
            {InputType.ANY, InputType.NONE, InputType.ANY},
            {InputType.ANY, InputType.ANY, InputType.ANY},
            {InputType.ANY, InputType.ANY, InputType.ANY}});

    public static ArtisanTableInputsShape LEGGINGS = new ArtisanTableInputsShape("leggings", new InputType[][]{
            {InputType.ANY, InputType.ANY, InputType.ANY},
            {InputType.ANY, InputType.NONE, InputType.ANY},
            {InputType.ANY, InputType.NONE, InputType.ANY}});

    public static ArtisanTableInputsShape BOOTS = new ArtisanTableInputsShape("boots", new InputType[][]{
            {InputType.NONE, InputType.NONE, InputType.NONE},
            {InputType.ANY, InputType.NONE, InputType.ANY},
            {InputType.ANY, InputType.NONE, InputType.ANY}});

    public ArtisanTableInputsShape(String id, InputType[][] inputs) {
        this.id = id;
        this.inputs = inputs;
        for(ArtisanTableInputsShape shape : shapes) {
            if(shape.id.equals(id)) {
                throw new IllegalArgumentException("Cannot create a recipe shape of same id");
            }
        }
        ArtisanTableInputsShape.shapes.add(this);
    }

    public String getId() {
        return this.id;
    }

    public InputType getInputType(int x, int y) {
        if(x < 0 || y < 0 || x > 2 || y > 2) return null;
        else return this.inputs[y][x];
    }

    public static ArtisanTableInputsShape getShape(String id) {
        for(ArtisanTableInputsShape shape : shapes) {
            if(shape.id.equals(id)) {
                return shape;
            }
        }
        return null;
    }

    public void register() {
        LoggerUtil.logDebugMsg("Registering ArtisanTable Inputs Shapes for " + MiddleEarth.MOD_ID);
    }
}
