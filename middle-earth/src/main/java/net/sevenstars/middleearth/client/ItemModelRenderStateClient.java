package net.sevenstars.middleearth.client;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.datageneration.content.models.SimpleArtefactModels;
import net.sevenstars.middleearth.datageneration.content.models.SimpleBigItemModel;
import net.sevenstars.middleearth.datageneration.content.models.SimpleSpearModel;
import net.sevenstars.middleearth.item.DecorativeItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.items.PipeItem;
import net.sevenstars.middleearth.item.items.weapons.CustomDaggerWeaponItem;
import net.sevenstars.middleearth.item.items.weapons.CustomLongswordWeaponItem;
import net.sevenstars.middleearth.item.items.weapons.artefacts.ArtefactCustomGlowingDaggerWeaponItem;
import net.sevenstars.middleearth.item.items.weapons.artefacts.ArtefactCustomGlowingLongswordWeaponItem;

import java.util.Collections;
import java.util.EnumMap;
import java.util.IdentityHashMap;
import java.util.Map;

public final class ItemModelRenderStateClient {
    private static volatile Map<Item, ModelDescriptor> DESCRIPTORS = Collections.emptyMap();
    private static volatile Map<Item, BakedModels> BAKED_MODELS = Collections.emptyMap();

    private ItemModelRenderStateClient() {
    }

    public static void registerAdditionalModels(ModelEvent.RegisterAdditional event) {
        IdentityHashMap<Item, ModelDescriptor> descriptors = buildDescriptors();
        DESCRIPTORS = Collections.unmodifiableMap(descriptors);
        for (ModelDescriptor descriptor : descriptors.values()) {
            descriptor.register(event);
        }
    }

    public static void cacheBakedModels(ModelEvent.BakingCompleted event) {
        IdentityHashMap<Item, BakedModels> bakedModels = new IdentityHashMap<>();
        BakedModel missingModel = event.getModelManager().getMissingModel();
        for (Map.Entry<Item, ModelDescriptor> entry : DESCRIPTORS.entrySet()) {
            bakedModels.put(entry.getKey(), entry.getValue().bake(event.getModels(), missingModel));
        }
        BAKED_MODELS = Collections.unmodifiableMap(bakedModels);
    }

    public static BakedModel resolve(
            BakedModel original,
            ItemStack stack,
            ItemDisplayContext displayContext,
            Level level,
            LivingEntity entity
    ) {
        BakedModels models = BAKED_MODELS.get(stack.getItem());
        if (models == null) {
            return original;
        }

        boolean flat = isFlat(displayContext);
        return switch (models.kind) {
            case FLAT_ITEM -> flat ? models.orElse(ModelRole.INVENTORY, original) : original;
            case BIG_ITEM -> flat
                    ? models.orElse(ModelRole.INVENTORY, original)
                    : isUsing(stack, entity) && models.has(ModelRole.USING_HAND)
                    ? models.get(ModelRole.USING_HAND)
                    : original;
            case PIPE -> flat
                    ? models.orElse(ModelRole.INVENTORY, original)
                    : isUsing(stack, entity)
                    ? models.orElse(ModelRole.USING_HAND, original)
                    : original;
            case BIG_BOW -> resolveBigBow(original, models, stack, entity, flat);
            case ARTEFACT_DAGGER -> resolveArtefactDagger(original, models, stack, level, entity);
            case ARTEFACT_SIMPLE -> isBroken(stack)
                    ? models.orElse(ModelRole.BROKEN_HAND, original)
                    : original;
            case ARTEFACT_DUAL -> resolveDualArtefact(original, models, stack, level, entity, flat);
            case EXPLICIT_HAND -> flat
                    ? models.orElse(ModelRole.INVENTORY, original)
                    : models.orElse(ModelRole.HAND, original);
            case EXPLICIT_USING_HAND -> flat
                    ? models.orElse(ModelRole.INVENTORY, original)
                    : isUsing(stack, entity)
                    ? models.orElse(
                            ModelRole.USING_HAND,
                            models.orElse(ModelRole.HAND, original)
                    )
                    : models.orElse(ModelRole.HAND, original);
        };
    }

    private static BakedModel resolveBigBow(
            BakedModel original,
            BakedModels models,
            ItemStack stack,
            LivingEntity entity,
            boolean flat
    ) {
        if (!flat) {
            return original;
        }
        if (!isUsing(stack, entity)) {
            return models.orElse(ModelRole.INVENTORY, original);
        }

        float pull = (float) (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0F;
        if (pull >= 0.9F) {
            return models.orElse(ModelRole.PULLING_2_INVENTORY, original);
        }
        if (pull >= 0.65F) {
            return models.orElse(ModelRole.PULLING_1_INVENTORY, original);
        }
        return models.orElse(ModelRole.PULLING_0_INVENTORY, original);
    }

    private static BakedModel resolveArtefactDagger(
            BakedModel original,
            BakedModels models,
            ItemStack stack,
            Level level,
            LivingEntity entity
    ) {
        if (CustomDaggerWeaponItem.canSneakAttack(stack)) {
            return models.orElse(ModelRole.STRIKE_HAND, original);
        }
        if (isBroken(stack)) {
            return models.orElse(ModelRole.BROKEN_HAND, original);
        }
        if (models.has(ModelRole.GLOWING_HAND) && isGlowing(stack.getItem(), level, entity)) {
            return models.get(ModelRole.GLOWING_HAND);
        }
        return original;
    }

    private static BakedModel resolveDualArtefact(
            BakedModel original,
            BakedModels models,
            ItemStack stack,
            Level level,
            LivingEntity entity,
            boolean flat
    ) {
        boolean using = isUsing(stack, entity);
        if (isBroken(stack)) {
            if (flat) {
                return models.orElse(
                        ModelRole.BROKEN_INVENTORY,
                        models.orElse(ModelRole.INVENTORY, original)
                );
            }
            return using
                    ? models.orElse(
                            ModelRole.BROKEN_USING_HAND,
                            models.orElse(ModelRole.BROKEN_HAND, original)
                    )
                    : models.orElse(ModelRole.BROKEN_HAND, original);
        }
        if (models.has(ModelRole.GLOWING_HAND) && isGlowing(stack.getItem(), level, entity)) {
            if (flat) {
                return models.orElse(
                        ModelRole.GLOWING_INVENTORY,
                        models.orElse(ModelRole.INVENTORY, original)
                );
            }
            return using
                    ? models.orElse(ModelRole.GLOWING_USING_HAND, models.get(ModelRole.GLOWING_HAND))
                    : models.get(ModelRole.GLOWING_HAND);
        }
        if (flat) {
            return models.orElse(ModelRole.INVENTORY, original);
        }
        return using ? models.orElse(ModelRole.USING_HAND, original) : original;
    }

    private static boolean isFlat(ItemDisplayContext displayContext) {
        return displayContext == ItemDisplayContext.GUI
                || displayContext == ItemDisplayContext.GROUND
                || displayContext == ItemDisplayContext.FIXED;
    }

    private static boolean isUsing(ItemStack stack, LivingEntity entity) {
        return entity != null && entity.isUsingItem() && entity.getUseItem() == stack;
    }

    private static boolean isBroken(ItemStack stack) {
        return stack.isDamageableItem() && stack.getDamageValue() >= stack.getMaxDamage() - 1;
    }

    private static boolean isGlowing(Item item, Level level, LivingEntity entity) {
        if (item instanceof ArtefactCustomGlowingLongswordWeaponItem) {
            return ArtefactCustomGlowingLongswordWeaponItem.shouldBeGlowing(level, entity);
        }
        if (item instanceof ArtefactCustomGlowingDaggerWeaponItem) {
            return ArtefactCustomGlowingDaggerWeaponItem.shouldBeGlowing(level, entity);
        }
        return false;
    }

    private static IdentityHashMap<Item, ModelDescriptor> buildDescriptors() {
        IdentityHashMap<Item, ModelDescriptor> descriptors = new IdentityHashMap<>();

        for (Item item : SimpleBigItemModel.items) {
            EnumMap<ModelRole, ModelResourceLocation> models = itemModels(item, ModelRole.INVENTORY);
            if (item instanceof CustomLongswordWeaponItem) {
                models.put(ModelRole.USING_HAND, itemModel(item, "_blocking"));
            }
            put(descriptors, item, new ModelDescriptor(ModelKind.BIG_ITEM, models));
        }
        for (Item item : SimpleBigItemModel.bigBows) {
            EnumMap<ModelRole, ModelResourceLocation> models = itemModels(
                    item,
                    ModelRole.INVENTORY,
                    ModelRole.PULLING_0,
                    ModelRole.PULLING_1,
                    ModelRole.PULLING_2,
                    ModelRole.PULLING_0_INVENTORY,
                    ModelRole.PULLING_1_INVENTORY,
                    ModelRole.PULLING_2_INVENTORY
            );
            put(descriptors, item, new ModelDescriptor(ModelKind.BIG_BOW, models));
        }
        for (Item item : SimpleBigItemModel.genericItems) {
            EnumMap<ModelRole, ModelResourceLocation> models = itemModels(item, ModelRole.INVENTORY);
            ModelKind kind = ModelKind.FLAT_ITEM;
            if (item instanceof PipeItem) {
                ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
                models.put(
                        ModelRole.USING_HAND,
                        standalone(MiddleEarth.of("item/smoking_" + id.getPath()))
                );
                kind = ModelKind.PIPE;
            }
            put(descriptors, item, new ModelDescriptor(kind, models));
        }
        for (Item item : SimpleSpearModel.items) {
            put(
                    descriptors,
                    item,
                    new ModelDescriptor(ModelKind.FLAT_ITEM, itemModels(item, ModelRole.INVENTORY))
            );
        }
        for (SimpleArtefactModels.Artefact artefact : SimpleArtefactModels.artefacts) {
            put(descriptors, artefact.artefact(), artefactDescriptor(artefact));
        }

        put(
                descriptors,
                WeaponItemsME.TROLL_MACE,
                new ModelDescriptor(
                        ModelKind.FLAT_ITEM,
                        explicitModels(ModelRole.INVENTORY, MiddleEarth.of("item/troll_mace_inventory"))
                )
        );
        put(
                descriptors,
                ModDecorativeBlocks.CANDLE_HOLDER.asItem(),
                new ModelDescriptor(
                        ModelKind.EXPLICIT_HAND,
                        explicitModels(
                                ModelRole.INVENTORY, MiddleEarth.of("item/candle_holder"),
                                ModelRole.HAND, MiddleEarth.of("block/candle_holder")
                        )
                )
        );
        put(
                descriptors,
                DecorativeItemsME.WATERING_CAN,
                new ModelDescriptor(
                        ModelKind.EXPLICIT_USING_HAND,
                        explicitModels(
                                ModelRole.INVENTORY, MiddleEarth.of("item/watering_can"),
                                ModelRole.HAND, MiddleEarth.of("block/watering_can"),
                                ModelRole.USING_HAND, MiddleEarth.of("block/watering_can_sprinkling")
                        )
                )
        );
        return descriptors;
    }

    private static ModelDescriptor artefactDescriptor(SimpleArtefactModels.Artefact artefact) {
        Item item = artefact.artefact();
        if (item instanceof CustomDaggerWeaponItem) {
            EnumMap<ModelRole, ModelResourceLocation> models = itemModels(
                    item,
                    ModelRole.BROKEN_HAND,
                    ModelRole.STRIKE_HAND
            );
            if (item instanceof ArtefactCustomGlowingDaggerWeaponItem) {
                models.put(ModelRole.GLOWING_HAND, itemModel(item, "_glowing"));
            }
            return new ModelDescriptor(ModelKind.ARTEFACT_DAGGER, models);
        }

        if (artefact.dualModel()) {
            EnumMap<ModelRole, ModelResourceLocation> models = itemModels(
                    item,
                    ModelRole.INVENTORY,
                    ModelRole.USING_HAND,
                    ModelRole.BROKEN_HAND,
                    ModelRole.BROKEN_INVENTORY,
                    ModelRole.BROKEN_USING_HAND
            );
            if (item instanceof ArtefactCustomGlowingLongswordWeaponItem) {
                models.put(ModelRole.GLOWING_HAND, itemModel(item, "_glowing"));
                models.put(ModelRole.GLOWING_INVENTORY, itemModel(item, "_glowing_inventory"));
                models.put(ModelRole.GLOWING_USING_HAND, itemModel(item, "_glowing_blocking"));
            }
            return new ModelDescriptor(ModelKind.ARTEFACT_DUAL, models);
        }

        return new ModelDescriptor(
                ModelKind.ARTEFACT_SIMPLE,
                itemModels(item, ModelRole.BROKEN_HAND)
        );
    }

    private static EnumMap<ModelRole, ModelResourceLocation> itemModels(Item item, ModelRole... roles) {
        EnumMap<ModelRole, ModelResourceLocation> models = new EnumMap<>(ModelRole.class);
        for (ModelRole role : roles) {
            models.put(role, itemModel(item, role.suffix));
        }
        return models;
    }

    private static EnumMap<ModelRole, ModelResourceLocation> explicitModels(Object... roleAndIds) {
        EnumMap<ModelRole, ModelResourceLocation> models = new EnumMap<>(ModelRole.class);
        for (int index = 0; index < roleAndIds.length; index += 2) {
            models.put(
                    (ModelRole) roleAndIds[index],
                    standalone((ResourceLocation) roleAndIds[index + 1])
            );
        }
        return models;
    }

    private static ModelResourceLocation itemModel(Item item, String suffix) {
        return standalone(BuiltInRegistries.ITEM.getKey(item).withPrefix("item/").withSuffix(suffix));
    }

    private static ModelResourceLocation standalone(ResourceLocation id) {
        return ModelResourceLocation.standalone(id);
    }

    private static void put(
            IdentityHashMap<Item, ModelDescriptor> descriptors,
            Item item,
            ModelDescriptor descriptor
    ) {
        if (descriptors.put(item, descriptor) != null) {
            throw new IllegalStateException("Duplicate item render descriptor for " + BuiltInRegistries.ITEM.getKey(item));
        }
    }

    private enum ModelKind {
        FLAT_ITEM,
        BIG_ITEM,
        PIPE,
        BIG_BOW,
        ARTEFACT_DAGGER,
        ARTEFACT_SIMPLE,
        ARTEFACT_DUAL,
        EXPLICIT_HAND,
        EXPLICIT_USING_HAND
    }

    private enum ModelRole {
        INVENTORY("_inventory"),
        HAND(""),
        USING_HAND("_blocking"),
        BROKEN_HAND("_broken"),
        BROKEN_INVENTORY("_broken_inventory"),
        BROKEN_USING_HAND("_broken_blocking"),
        GLOWING_HAND("_glowing"),
        GLOWING_INVENTORY("_glowing_inventory"),
        GLOWING_USING_HAND("_glowing_blocking"),
        STRIKE_HAND("_strike"),
        PULLING_0("_pulling_0"),
        PULLING_1("_pulling_1"),
        PULLING_2("_pulling_2"),
        PULLING_0_INVENTORY("_pulling_0_inventory"),
        PULLING_1_INVENTORY("_pulling_1_inventory"),
        PULLING_2_INVENTORY("_pulling_2_inventory");

        private final String suffix;

        ModelRole(String suffix) {
            this.suffix = suffix;
        }
    }

    private record ModelDescriptor(
            ModelKind kind,
            Map<ModelRole, ModelResourceLocation> models
    ) {
        private ModelDescriptor {
            models = Collections.unmodifiableMap(new EnumMap<>(models));
        }

        private void register(ModelEvent.RegisterAdditional event) {
            for (ModelResourceLocation location : models.values()) {
                event.register(location);
            }
        }

        private BakedModels bake(
                Map<ModelResourceLocation, BakedModel> availableModels,
                BakedModel missingModel
        ) {
            BakedModel[] baked = new BakedModel[ModelRole.values().length];
            for (Map.Entry<ModelRole, ModelResourceLocation> entry : models.entrySet()) {
                BakedModel model = availableModels.get(entry.getValue());
                if (model != null && model != missingModel) {
                    baked[entry.getKey().ordinal()] = model;
                }
            }
            return new BakedModels(kind, baked);
        }
    }

    private static final class BakedModels {
        private final ModelKind kind;
        private final BakedModel[] models;

        private BakedModels(ModelKind kind, BakedModel[] models) {
            this.kind = kind;
            this.models = models;
        }

        private boolean has(ModelRole role) {
            return models[role.ordinal()] != null;
        }

        private BakedModel get(ModelRole role) {
            return models[role.ordinal()];
        }

        private BakedModel orElse(ModelRole role, BakedModel fallback) {
            BakedModel model = models[role.ordinal()];
            return model != null ? model : fallback;
        }
    }
}
