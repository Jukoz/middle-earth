package net.sevenstars.middleearth.gui.utils.widgets.map;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.onboarding.onboarding_faction.OnboardingFactionScreenController;
import net.sevenstars.middleearth.gui.utils.widgets.map.types.MapMarkerType;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.joml.Vector2d;
import org.joml.Vector2i;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FactionSelectionMapWidget extends MapWidget {
    MapMarkerWidget[] spawnMapMarkers;
    List<SpawnData> spawns = new ArrayList<>();
    public FactionSelectionMapWidget(int mapWidth, int mapHeight, int maxMarkerAmount) {
        super(mapWidth, mapHeight);
        spawnMapMarkers = new MapMarkerWidget[maxMarkerAmount];
        for(int i = 0; i< maxMarkerAmount; i++){
            int finalIndex = i;
            spawnMapMarkers[i] = new MapMarkerWidget("SpawnButton_" + i, x -> selectSpawn(finalIndex),
                    new Rectangle2D.Double(0, 0, uiWidth, uiHeight - 11));
            spawnMapMarkers[i].setType(MapMarkerType.DYNAMIC_SPAWN);
        }
        MapMarkerWidget.setTitle(Text.translatable("widget.%s.spawn_tooltip_title".formatted(MiddleEarth.MOD_ID)).formatted(Formatting.UNDERLINE));
    }
    public ButtonWidget[] getButtons() {
        ButtonWidget[] spawnButtonArray = new ButtonWidget[spawnMapMarkers.length];
        for(int i = 0; i < spawnMapMarkers.length; i++){
            spawnButtonArray[i] = spawnMapMarkers[i].getButton();
        }
        return spawnButtonArray;
    }

    public void setSpawns(List<SpawnData> spawnList){
        this.spawns = spawnList;
    }

    public void selectSpawn(int index){
        addCooldown();
        if(OnboardingFactionScreenController.getInstance() != null)
            OnboardingFactionScreenController.getInstance().assignNewSpawnIndex(index);
        updateSelectedSpawn(index);
    }

    public void updateSelectedSpawn(int index){
        for(int i = 0; i < spawnMapMarkers.length; i++){
            this.spawnMapMarkers[i].setSelected(i == index);
        }
    }

    protected double getMarkerGroupUpRadius(){
        return 15;
    }

    @Override
    protected void draw(DrawContext context, int startX, int startY) {
        super.draw(context, startX, startY);
        if(spawns == null || spawns.isEmpty()) return;

        HashMap<Integer, List<Vector2i>> uniqueIndexes = new HashMap<>();
        for(int i = 0; i < spawns.size() && i < this.spawnMapMarkers.length; i++){
            SpawnData spawnData = spawns.get(i);
            Vector2d coordinates = new Vector2d(spawnData.getCoordinates().getX(), spawnData.getCoordinates().getZ());
            MapMarkerWidget mapMarker = this.spawnMapMarkers[i];
            if(spawnData.isDynamic()){
                mapMarker.setType(MapMarkerType.DYNAMIC_SPAWN);
                mapMarker.computeFromMapPosition(this, coordinates);
                mapMarker.setContent(
                        List.of(
                                Text.translatable("spawn." + spawnData.getIdentifier().toTranslationKey()).formatted(Formatting.GOLD),
                                Text.translatable("widget.%s.marker.margin_front".formatted(MiddleEarth.MOD_ID)).append(Text.translatable("spawn.%s.coordinates_base.dynamic".formatted(MiddleEarth.MOD_ID)).formatted(Formatting.GRAY)
                                        .append(Text.translatable("spawn.%s.coordinates_base_values.dynamic".formatted(MiddleEarth.MOD_ID), spawnData.getWorldCoordinates().x, spawnData.getWorldCoordinates().z).formatted(Formatting.WHITE)))
                        ));
            } else {
                mapMarker.setType(MapMarkerType.CUSTOM_SPAWN);
                mapMarker.computeFromWorldPosition(this, coordinates);
                mapMarker.setContent(
                        List.of(
                                Text.translatable("spawn." + spawnData.getIdentifier().toTranslationKey()).formatted(Formatting.GOLD),
                                Text.translatable("widget.%s.marker.margin_front".formatted(MiddleEarth.MOD_ID)).append(Text.translatable("spawn.%s.coordinates_base.custom".formatted(MiddleEarth.MOD_ID)).formatted(Formatting.GRAY)
                                        .append(Text.translatable("spawn.%s.coordinates_base_values.custom".formatted(MiddleEarth.MOD_ID), spawnData.getWorldCoordinates().x, spawnData.getWorldCoordinates().y, spawnData.getWorldCoordinates().z).formatted(Formatting.WHITE)))
                        ));
            }

            mapMarker.clearChild();
            Vector2i currentCenterCoordinate = mapMarker.getCenterCoordinates();
            boolean isSeperate = true;
            int currentUniqueIndex = 0;
            for(int j = 0; j < uniqueIndexes.size() && isSeperate; j++){
                currentUniqueIndex = uniqueIndexes.keySet().stream().toList().get(j);
                List<Vector2i> currentList = uniqueIndexes.get(currentUniqueIndex);
                for (Vector2i vector2i : currentList) {
                    double distance = currentCenterCoordinate.distance(vector2i);
                    if (Math.round(distance) <= getMarkerGroupUpRadius()) {
                        isSeperate = false;
                    }
                }
            }
            if(isSeperate){
                uniqueIndexes.put(i, List.of(currentCenterCoordinate));
            } else {
                List<Vector2i> currentList = new ArrayList<>(uniqueIndexes.get(currentUniqueIndex).stream().toList());
                currentList.add(currentCenterCoordinate);
                uniqueIndexes.put(currentUniqueIndex, currentList);
                spawnMapMarkers[currentUniqueIndex].updateMarkerType(MapMarkerType.STACKED_SPAWNS);
                spawnMapMarkers[currentUniqueIndex].addChild(mapMarker);
                mapMarker.activateButton(false);
            }
        }
        for (Integer index : uniqueIndexes.keySet()) {
            spawnMapMarkers[index].assignNewCenter(averagePosition(uniqueIndexes.get(index)));
            spawnMapMarkers[index].draw(context);
        }
    }

    private Vector2i averagePosition(List<Vector2i> listOfPositions){
        Vector2i average = new Vector2i();
        for(Vector2i position : listOfPositions){
            average.x += position.x;
            average.y += position.y;
        }
        average.x /= listOfPositions.size();
        average.y /= listOfPositions.size();
        return average;
    }
}
