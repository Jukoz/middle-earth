package net.jukoz.me.client.screens.utils.widgets.map;

import net.jukoz.me.client.screens.controllers.FactionSelectionController;
import net.jukoz.me.client.screens.utils.MapMarkerType;
import net.jukoz.me.resources.datas.factions.data.SpawnData;
import net.jukoz.me.resources.datas.factions.data.SpawnDataHandler;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.joml.Vector2d;

import java.util.List;

public class FactionSelectionMapWidget extends MapWidget {
    MapMarkerWidget[] spawnMapMarkers;
    FactionSelectionController controller;
    public FactionSelectionMapWidget(FactionSelectionController controller, int mapWidth, int mapHeight) {
        super(mapWidth, mapHeight);
        this.controller = controller;
        final int[] maxSpawnCount = {0};
        this.controller.getFactions().values().forEach(factionList -> factionList.forEach(faction -> {
            SpawnDataHandler spawnDataHandler = faction.getSpawnData();
            if(spawnDataHandler != null && spawnDataHandler.getSpawnList() != null){
                int count = spawnDataHandler.getSpawnList().size();
                if(count > maxSpawnCount[0]){
                    maxSpawnCount[0] = count;
                }
            }
        }));
        spawnMapMarkers = new MapMarkerWidget[maxSpawnCount[0]];
        for(int i = 0; i< maxSpawnCount[0]; i++){
            int finalIndex = i;
            spawnMapMarkers[i] = new MapMarkerWidget("SpawnButton_" + i, x -> selectSpawn(finalIndex));
            spawnMapMarkers[i].setType(MapMarkerType.DYNAMIC_SPAWN);
        }
    }
    public ButtonWidget[] getButtons() {
        ButtonWidget[] spawnButtonArray = new ButtonWidget[spawnMapMarkers.length];
        for(int i = 0; i < spawnMapMarkers.length; i++){
            spawnButtonArray[i] = spawnMapMarkers[i].getButton();
        }
        return spawnButtonArray;
    }

    private void selectSpawn(int index){
        LoggerUtil.logDebugMsg("Clicked on spawn #" + index);
    }

    @Override
    protected void draw(DrawContext context, int startX, int startY) {
        super.draw(context, startX, startY);
        SpawnDataHandler handler = controller.getCurrentSpawnDataHandler();
        if(handler == null || handler.getSpawnList() == null) return;
        List<SpawnData> spawns = handler.getSpawnList();
        for(int i = 0; i < spawns.size(); i++){
            SpawnData spawnData = spawns.get(i);
            Vector2d coordinates = new Vector2d(spawnData.getCoordinates().getX(), spawnData.getCoordinates().getZ());
            if(spawnData.isDynamic()){
                spawnMapMarkers[i].setType(MapMarkerType.DYNAMIC_SPAWN);
                spawnMapMarkers[i].drawFromMapPosition(context, this, coordinates);
            } else {
                spawnMapMarkers[i].setType(MapMarkerType.CUSTOM_SPAWN);
                spawnMapMarkers[i].drawFromWorldPosition(context, this, coordinates);
            }
        }
    }
}
