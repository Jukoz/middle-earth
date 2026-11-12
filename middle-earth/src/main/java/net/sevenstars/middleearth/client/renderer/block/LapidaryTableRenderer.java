package net.sevenstars.middleearth.client.renderer.block;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.lapidarytable.LapidaryTableBlock;
import net.sevenstars.middleearth.block.special.lapidarytable.LapidaryTableBlockEntity;
import net.sevenstars.middleearth.block.special.beamlamp.BeamLampBlock;
import net.sevenstars.middleearth.recipe.lapidary.LapidaryTools;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

/**
 * Draws what stands on the Lapidary Table beyond its two bare halves: the crank and the grinding wheel (turning
 * while a gem is being cut), the magnifier with its lenses, the lamp on the study half, the gem on the plinth, and
 * and a fitted pump: its foot bellows under the cutting half, its bucket or basin under the study half, and the pipes
 * from the bucket to the bellows and up beside the wheel.
 * <p>
 * Speeds follow the Blockbench "spin" animation: the handle turns 360 degrees a second and the wheel, its
 * child, 720 more. Both turn about the same axis (parallel to x, through y 10.5 / z 8 in model pixels).
 */
@Environment(EnvType.CLIENT)
public class LapidaryTableRenderer implements BlockEntityRenderer<LapidaryTableBlockEntity> {
    private static final float AXIS_Y = 10.5F / 16.0F;
    private static final float AXIS_Z = 8.0F / 16.0F;
    private static final float HANDLE_DEGREES_PER_TICK = 360.0F / 20.0F;
    private static final float WHEEL_DEGREES_PER_TICK = HANDLE_DEGREES_PER_TICK + 720.0F / 20.0F;
    /** The lamp: half size, its middle on the cutting half's back corner away from the crank (model pixels). */
    private static final float LAMP_SCALE = 0.5F;
    private static final float LAMP_X = 13, LAMP_Z = 13;
    /**
     * The pump's pipes (model pixels): on the floor at {@link #PIPE_Y}, up through the top beside the wheel slot's back
     * end on the crank side ({@link #RISER_X}, {@link #RISER_Z}) to an arm over the wheel's rim.
     */
    private static final float PIPE_Y = 1.75F, PIPE_HALF = 0.75F;
    /** The pipes' metal: a 16 texel patch at these texels of the 48 texel pump texture. */
    private static final float PIPE_U = 0, PIPE_V = 32, PUMP_TEXTURE = 48;
    private static final float RISER_X = 6, RISER_Z = 12.5F, RISER_TOP = 14.5F, ARM_TO_X = 7.9F;
    /** The pedal's lid: raised this far at rest, pressed down by up to {@link #PEDAL_PRESS} once a second in use. */
    private static final float PEDAL_UP = 20, PEDAL_PRESS = 11;
    /** The pedal lid's hinge (model pixels), and where the draw pipe meets the vessel's side wall. */
    private static final float HINGE_Y = 1, HINGE_Z = 2, INLET_Z = 6, VESSEL_WALL = 18;
    /**
     * The study gem's centre (model pixels): the pedestal's brass cup tops out at 14.25 and the gem's sprite stands
     * 2.9 px tall at its scale (0.3 here, 0.6 in its fixed display), so it rests a hair above the cup, all in view.
     */
    private static final float GEM_Y = 14.25F + 1.44F + 0.1F;
    private static final int WATER_COLOR = 0xC03F76E4;
    /**
     * The gem being cut, on the dop's wax (model pixels): centred over the wheel slot, set down into the wax. Its
     * foot (the sprite 2.9 px tall) comes to y 15.2, so the tips of the turning wheel's corners (up to y 15.45) just
     * graze it, as a gem held to the wheel would be.
     */
    private static final float DOP_GEM_X = 7.95F, DOP_GEM_Y = 16.65F, DOP_GEM_Z = 8;

    private final BlockRenderManager blocks;
    private final ItemRenderer items;

    public LapidaryTableRenderer(BlockEntityRendererFactory.Context context) {
        this.blocks = context.getRenderManager();
        this.items = context.getItemRenderer();
    }

    @Override
    public void render(LapidaryTableBlockEntity table, float tickProgress, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
        BlockState state = table.getCachedState();
        if (!state.contains(LapidaryTableBlock.PART) || state.get(LapidaryTableBlock.PART) != LapidaryTableBlock.Part.TABLE
            || table.getWorld() == null) {
            return;
        }
        boolean cutting = state.get(LapidaryTableBlock.CUTTING);
        float ticks = cutting ? (table.getWorld().getTime() % 360L) + tickProgress : 0;
        BlockState parts = state.with(LapidaryTableBlock.CUTTING, false).with(LapidaryTableBlock.FACING, Direction.NORTH);

        matrices.push();
        // Same turn as the blockstate's "y" rotation for this facing (models are drawn facing north).
        matrices.translate(0.5F, 0.0F, 0.5F);
        matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(yRotation(state.get(LapidaryTableBlock.FACING))));
        matrices.translate(-0.5F, 0.0F, -0.5F);

        LapidaryTools pump = table.pumpTier();
        if (pump != null) {
            float seconds = cutting ? (table.getWorld().getTime() % 2000L + tickProgress) / 20.0F : 0;
            renderPump(table, pump, parts, seconds, matrices, vertexConsumers, light, overlay);
        }

        renderSpinning(parts.with(LapidaryTableBlock.PART, LapidaryTableBlock.Part.HANDLE),
            ticks * HANDLE_DEGREES_PER_TICK, matrices, vertexConsumers, light, overlay);
        renderSpinning(parts.with(LapidaryTableBlock.PART, wheel(table.upgrade(LapidaryTools.Slot.WHEEL))),
            ticks * WHEEL_DEGREES_PER_TICK, matrices, vertexConsumers, light, overlay);
        LapidaryTableBlock.Part lens = lens(table.upgrade(LapidaryTools.Slot.LENSES));
        blocks.renderBlockAsEntity(parts.with(LapidaryTableBlock.PART, lens), matrices, vertexConsumers, light, overlay);
        if (lens != LapidaryTableBlock.Part.LENS) {
            // The fan lenses' tinted glass, see-through: drawn after the solid parts, so the wheel shows through it.
            BlockModelRenderer.render(matrices.peek(), vertexConsumers.getBuffer(TexturedRenderLayers.getItemEntityTranslucentCull()),
                blocks.getModel(parts.with(LapidaryTableBlock.PART, lens == LapidaryTableBlock.Part.LENS_REFINED
                    ? LapidaryTableBlock.Part.LENS_GLASS_REFINED : LapidaryTableBlock.Part.LENS_GLASS_EXQUISITE)),
                1, 1, 1, light, overlay);
        }

        boolean inUse = cutting || !table.getStudyGem().isEmpty();
        ItemStack lamp = table.upgrade(LapidaryTools.Slot.LAMP);
        if (lamp.getItem() instanceof BlockItem lampItem) {
            renderLamp(lampItem.getBlock(), inUse, matrices, vertexConsumers, light, overlay);
        }
        ItemStack cutGem = table.getCutGem();
        if (!cutGem.isEmpty()) {
            matrices.push();
            matrices.translate(DOP_GEM_X / 16, DOP_GEM_Y / 16, DOP_GEM_Z / 16);
            matrices.scale(0.3F, 0.3F, 0.3F);
            items.renderItem(cutGem, ItemDisplayContext.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers,
                table.getWorld(), (int) table.getPos().asLong() + 1);
            matrices.pop();
        }
        ItemStack gem = table.getStudyGem();
        if (!gem.isEmpty()) {
            matrices.push();
            matrices.translate(1.5F, GEM_Y / 16, 0.5F);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((table.getWorld().getTime() + tickProgress) * 2));
            matrices.scale(0.3F, 0.3F, 0.3F);
            items.renderItem(gem, ItemDisplayContext.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers,
                table.getWorld(), (int) table.getPos().asLong());
            matrices.pop();
        }
        matrices.pop();
    }

    private static LapidaryTableBlock.Part wheel(ItemStack wheel) {
        LapidaryTools tier = LapidaryTools.tierOf(wheel.getItem());
        return tier == null ? LapidaryTableBlock.Part.WHEEL
            : tier == LapidaryTools.RELIC ? LapidaryTableBlock.Part.WHEEL_KHAZAD : LapidaryTableBlock.Part.WHEEL_ENHANCED;
    }

    private static LapidaryTableBlock.Part lens(ItemStack lenses) {
        LapidaryTools tier = LapidaryTools.tierOf(lenses.getItem());
        return tier == null ? LapidaryTableBlock.Part.LENS
            : tier == LapidaryTools.RELIC ? LapidaryTableBlock.Part.LENS_EXQUISITE : LapidaryTableBlock.Part.LENS_REFINED;
    }

    /** The lamp, half size on the cutting half's back corner, shining at the wheel; lit while the table is in use. */
    private void renderLamp(Block lamp, boolean lit, MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                            int light, int overlay) {
        BlockState state = lamp.getDefaultState();
        if (state.contains(BeamLampBlock.FACING)) {
            state = state.with(BeamLampBlock.FACING, Direction.NORTH);
        }
        if (state.contains(BeamLampBlock.LIT)) {
            state = state.with(BeamLampBlock.LIT, lit);
        }
        matrices.push();
        // Turned a quarter-right so its face (north in its model) looks across the table at the wheel.
        matrices.translate(LAMP_X / 16, 12F / 16, LAMP_Z / 16);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45));
        matrices.scale(LAMP_SCALE, LAMP_SCALE, LAMP_SCALE);
        matrices.translate(-0.5F, 0, -0.5F);
        blocks.renderBlockAsEntity(state, matrices, vertexConsumers, light, overlay);
        matrices.pop();
    }

    private void renderSpinning(BlockState part, float degrees, MatrixStack matrices,
                                VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        matrices.translate(0.0F, AXIS_Y, AXIS_Z);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(degrees));
        matrices.translate(0.0F, -AXIS_Y, -AXIS_Z);
        blocks.renderBlockAsEntity(part, matrices, vertexConsumers, light, overlay);
        matrices.pop();
    }

    /**
     * The fitted pump, in the table's model frame (pixels): a small foot bellows under the cutting half, its narrow
     * hinged end toward the player and its outlet on the tall end; its lid pumping while the wheel turns. The Standard
     * Pump is plain bellows with steel fittings, the Numenorean one elven wood with mithril. A pipe from the outlet
     * runs along the floor and up beside the wheel slot's back end to the arm over the wheel; another runs from the
     * bellows' side into the side of the vessel under the study half, near its floor (the wooden bucket, or the Relic
     * mithril basin). The vessel's water shows at its level.
     */
    private void renderPump(LapidaryTableBlockEntity table, LapidaryTools tier, BlockState parts, float seconds,
                            MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        boolean relic = tier == LapidaryTools.RELIC;
        blocks.renderBlockAsEntity(parts.with(LapidaryTableBlock.PART, relic ? LapidaryTableBlock.Part.PEDAL_BASE_ELVEN
            : LapidaryTableBlock.Part.PEDAL_BASE), matrices, vertexConsumers, light, overlay);
        float press = seconds > 0 ? 0.5F - 0.5F * MathHelper.cos(seconds * MathHelper.TAU) : 0;
        float angle = PEDAL_UP - PEDAL_PRESS * press;
        matrices.push();
        matrices.translate(0, HINGE_Y / 16, HINGE_Z / 16);
        matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(angle));
        matrices.translate(0, -HINGE_Y / 16, -HINGE_Z / 16);
        blocks.renderBlockAsEntity(parts.with(LapidaryTableBlock.PART, relic ? LapidaryTableBlock.Part.PEDAL_TOP_ELVEN
            : LapidaryTableBlock.Part.PEDAL_TOP), matrices, vertexConsumers, light, overlay);
        matrices.pop();
        blocks.renderBlockAsEntity(parts.with(LapidaryTableBlock.PART, relic ? LapidaryTableBlock.Part.BASIN
            : LapidaryTableBlock.Part.BUCKET), matrices, vertexConsumers, light, overlay);

        var atlas = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
        VertexConsumer cutout = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE));
        MatrixStack.Entry e = matrices.peek();
        // The pump texture also holds the pipes' metal (a smooth patch in its hoop's colours).
        Sprite metal = atlas.apply(MiddleEarth.id(relic ? "block/foot_pump_numenorean" : "block/foot_pump_standard"));
        leather(cutout, e, metal, angle, light, overlay);

        float h = PIPE_HALF, s = 1.25F;
        // The outlet on the tall end, and the pipe on from it: back, across to the crank side, forward to the riser.
        px(cutout, e, metal, 7.5F, 0, 10, 10.5F, 3, 11.5F, light, overlay);
        // Up through the top (a flange where it comes out), the arm over the rim and its nozzle pointing down.
        pipe(cutout, e, metal, light, overlay,
            9, PIPE_Y, 11.5F,
            9, PIPE_Y, 15,
            RISER_X, PIPE_Y, 15,
            RISER_X, PIPE_Y, RISER_Z,
            RISER_X, RISER_TOP - h, RISER_Z,
            ARM_TO_X, RISER_TOP - h, RISER_Z,
            ARM_TO_X, RISER_TOP - 2 * h - 0.75F, RISER_Z);
        px(cutout, e, metal, RISER_X - s, 12, RISER_Z - s, RISER_X + s, 12.4F, RISER_Z + s, light, overlay);
        // The draw pipe: the inlet on the bellows' side, straight along the floor and through the vessel's side wall
        // (model x 18..19) near its floor, with a fitting collar outside and a short spout inside.
        px(cutout, e, metal, 12, 0.5F, 5, 13.5F, 3, 7, light, overlay);
        pipe(cutout, e, metal, light, overlay, 13.5F, PIPE_Y, INLET_Z, VESSEL_WALL + 1.5F, PIPE_Y, INLET_Z);
        px(cutout, e, metal, VESSEL_WALL - 1, PIPE_Y - s, INLET_Z - s, VESSEL_WALL, PIPE_Y + s, INLET_Z + s, light, overlay);

        // The water in the vessel, at its level: see-through, drawn after the vessel so its inside shows.
        int capacity = table.waterCapacity();
        if (table.getWater() > 0 && capacity > 0) {
            float fill = Math.min(1, table.getWater() / (float) capacity);
            float y = relic ? 1 + 4 * fill : 1 + 6 * fill;
            float x0 = 19, x1 = relic ? 27 : 25, z0 = relic ? 4 : 5, z1 = relic ? 12 : 11;
            Sprite water = atlas.apply(net.minecraft.util.Identifier.ofVanilla("block/water_still"));
            VertexConsumer translucent = vertexConsumers.getBuffer(TexturedRenderLayers.getItemEntityTranslucentCull());
            float u0 = water.getFrameU(0), u1 = water.getFrameU((x1 - x0) / 16), v0 = water.getFrameV(0), v1 = water.getFrameV((z1 - z0) / 16);
            quad(translucent, e, light, overlay, WATER_COLOR, u0, u1, v0, v1, 0, 1, 0,
                x0 / 16, y / 16, z1 / 16, x1 / 16, y / 16, z1 / 16, x1 / 16, y / 16, z0 / 16, x0 / 16, y / 16, z0 / 16);
        }
    }

    /**
     * The bellows' leather between its board and its lid: a wedge from the hinge to the tall end, as high as the lid
     * stands at {@code angle} degrees (model pixels: x 7..11, the board's top at y 1, the hinge at z 2, 8 long).
     */
    private static void leather(VertexConsumer v, MatrixStack.Entry e, Sprite s, float angle, int light, int overlay) {
        float sin = MathHelper.sin(angle * MathHelper.RADIANS_PER_DEGREE), cos = MathHelper.cos(angle * MathHelper.RADIANS_PER_DEGREE);
        float zf = (HINGE_Z + cos) / 16, yf = (HINGE_Y + sin) / 16, zb = (HINGE_Z + 8 * cos) / 16, yb = (HINGE_Y + 8 * sin) / 16;
        float x0 = 7F / 16, x1 = 11F / 16, y0 = HINGE_Y / 16, z0 = (HINGE_Z + 1) / 16;
        // The bag's leather in the bellows texture (its solid part: texels 26..34, 27..31 of 48).
        float u0 = s.getFrameU(26F / 48), u1 = s.getFrameU(34F / 48), v0 = s.getFrameV(27F / 48), v1 = s.getFrameV(31F / 48);
        quad(v, e, light, overlay, 0xFFFFFFFF, u0, u1, v0, v1, -1, 0, 0, x0, y0, z0, x0, y0, zb, x0, yb, zb, x0, yf, zf);
        quad(v, e, light, overlay, 0xFFFFFFFF, u0, u1, v0, v1, 1, 0, 0, x1, yf, zf, x1, yb, zb, x1, y0, zb, x1, y0, z0);
        quad(v, e, light, overlay, 0xFFFFFFFF, u0, u1, v0, v1, 0, 0, 1, x0, y0, zb, x1, y0, zb, x1, yb, zb, x0, yb, zb);
    }

    /**
     * A pipe through the given points (model pixels, x y z each; every leg along one axis): a straight piece per leg,
     * stopping short of each bend, where a joint block of the pipe's width fills the corner. The pieces only meet face
     * to face, so no two faces lie on one plane.
     */
    private static void pipe(VertexConsumer v, MatrixStack.Entry e, Sprite s, int light, int overlay, float... p) {
        float h = PIPE_HALF;
        int n = p.length / 3;
        for (int i = 0; i + 1 < n; i++) {
            float[] a = {p[3 * i], p[3 * i + 1], p[3 * i + 2]}, b = {p[3 * i + 3], p[3 * i + 4], p[3 * i + 5]};
            float[] lo = new float[3], hi = new float[3];
            for (int k = 0; k < 3; k++) {
                if (a[k] == b[k]) {
                    lo[k] = a[k] - h;
                    hi[k] = a[k] + h;
                } else {
                    boolean up = b[k] > a[k];
                    // Trimmed by the joint blocks at its bends (not at the pipe's two ends).
                    float from = a[k] + (i > 0 ? (up ? h : -h) : 0), to = b[k] - (i + 2 < n ? (up ? h : -h) : 0);
                    lo[k] = Math.min(from, to);
                    hi[k] = Math.max(from, to);
                }
            }
            px(v, e, s, lo[0], lo[1], lo[2], hi[0], hi[1], hi[2], light, overlay);
            if (i > 0) {
                px(v, e, s, a[0] - h, a[1] - h, a[2] - h, a[0] + h, a[1] + h, a[2] + h, light, overlay);
            }
        }
    }

    /** A box in model pixels. */
    private static void px(VertexConsumer v, MatrixStack.Entry e, Sprite s, float x0, float y0, float z0,
                           float x1, float y1, float z1, int light, int overlay) {
        box(v, e, s, x0 / 16, y0 / 16, z0 / 16, x1 / 16, y1 / 16, z1 / 16, light, overlay);
    }

    /**
     * An axis-aligned box (block units) textured one texel per model pixel, as the table's models are: each face
     * takes a piece of the pump texture's pipe patch ({@link #PIPE_U}, {@link #PIPE_V}, 16 texels square) its own size.
     */
    private static void box(VertexConsumer v, MatrixStack.Entry e, Sprite s, float x0, float y0, float z0,
                            float x1, float y1, float z1, int light, int overlay) {
        float dx = x1 - x0, dy = y1 - y0, dz = z1 - z0;
        float xu0 = u(s, dx, false), xu1 = u(s, dx, true), zu0 = u(s, dz, false), zu1 = u(s, dz, true);
        float yv0 = v(s, dy, false), yv1 = v(s, dy, true), zv0 = v(s, dz, false), zv1 = v(s, dz, true);
        quad(v, e, light, overlay, xu0, xu1, zv0, zv1, 0, -1, 0, x0, y0, z0, x1, y0, z0, x1, y0, z1, x0, y0, z1);
        quad(v, e, light, overlay, xu0, xu1, zv0, zv1, 0, 1, 0, x0, y1, z1, x1, y1, z1, x1, y1, z0, x0, y1, z0);
        quad(v, e, light, overlay, xu0, xu1, yv0, yv1, 0, 0, -1, x0, y1, z0, x1, y1, z0, x1, y0, z0, x0, y0, z0);
        quad(v, e, light, overlay, xu0, xu1, yv0, yv1, 0, 0, 1, x0, y0, z1, x1, y0, z1, x1, y1, z1, x0, y1, z1);
        quad(v, e, light, overlay, zu0, zu1, yv0, yv1, -1, 0, 0, x0, y0, z0, x0, y0, z1, x0, y1, z1, x0, y1, z0);
        quad(v, e, light, overlay, zu0, zu1, yv0, yv1, 1, 0, 0, x1, y1, z0, x1, y1, z1, x1, y0, z1, x1, y0, z0);
    }

    /** One end of a face's piece along u, {@code size} block units long, centred in the pipe patch. */
    private static float u(Sprite s, float size, boolean end) {
        float span = Math.min(1, size), from = (1 - span) / 2;
        return s.getFrameU((PIPE_U + 16 * (end ? from + span : from)) / PUMP_TEXTURE);
    }

    private static float v(Sprite s, float size, boolean end) {
        float span = Math.min(1, size), from = (1 - span) / 2;
        return s.getFrameV((PIPE_V + 16 * (end ? from + span : from)) / PUMP_TEXTURE);
    }

    private static void quad(VertexConsumer v, MatrixStack.Entry e, int light, int overlay, float u0, float u1,
                             float v0, float v1, float nx, float ny, float nz, float... p) {
        quad(v, e, light, overlay, 0xFFFFFFFF, u0, u1, v0, v1, nx, ny, nz, p);
    }

    private static void quad(VertexConsumer v, MatrixStack.Entry e, int light, int overlay, int color, float u0, float u1,
                             float v0, float v1, float nx, float ny, float nz, float... p) {
        float[][] uv = {{u0, v1}, {u1, v1}, {u1, v0}, {u0, v0}};
        for (int i = 0; i < 4; i++) {
            v.vertex(e, p[i * 3], p[i * 3 + 1], p[i * 3 + 2]).color(color).texture(uv[i][0], uv[i][1])
                .overlay(overlay).light(light).normal(e, nx, ny, nz);
        }
    }

    /** The parts reach over the study half. */
    @Override
    public boolean rendersOutsideBoundingBox() {
        return true;
    }

    private static float yRotation(Direction facing) {
        return switch (facing) {
            case EAST -> 90.0F;
            case SOUTH -> 180.0F;
            case WEST -> 270.0F;
            default -> 0.0F;
        };
    }
}
