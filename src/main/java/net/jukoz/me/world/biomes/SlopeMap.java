package net.jukoz.me.world.biomes;

import net.minecraft.block.Block;

import java.util.ArrayList;

public class SlopeMap {
    public ArrayList<SlopeData> slopeDatas;

    public SlopeMap(SlopeMap slopeMap) {
        this.slopeDatas = new ArrayList<>(slopeMap.slopeDatas);
    }

    public SlopeMap() {
        this.slopeDatas = new ArrayList<>();
    }

    public SlopeMap addSlopeData(float angle, Block block) {
        if(!slopeDatas.isEmpty()) {
            float newAngle = slopeDatas.getLast().angle;
            if (newAngle >= angle) {
                throw new ArithmeticException("Cannot add slope angle smaller than previous slope data: " + newAngle + " >= " + angle);
            } else if(newAngle < 0 || newAngle > 90) {
                throw new ArithmeticException("The new slope cannot exceed slope angle boundaries (0 to 90 degrees)");
            }
        }
        slopeDatas.add(new SlopeData(angle, block));
        return this;
    }

    public Block getBlockAtAngle(float angle) {
        for(SlopeData slopeData : slopeDatas) {
            if(angle <= slopeData.angle) {
                return slopeData.block;
            }
        }
        throw new RuntimeException("The angle exceeds the maximal allowed slope of: " + slopeDatas.getLast().angle + " degrees");
    }

    public class SlopeData {
        public float angle; // Maximal angle to apply the block
        public Block block;

        public SlopeData(float angle, Block block) {
            this.angle = angle;
            this.block = block;
        }
    }
}
