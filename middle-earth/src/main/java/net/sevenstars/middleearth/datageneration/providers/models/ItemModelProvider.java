package net.sevenstars.middleearth.datageneration.providers.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.datageneration.content.CustomItemModels;
import net.sevenstars.middleearth.datageneration.content.models.HotMetalsModel;
import net.sevenstars.middleearth.datageneration.content.models.SimpleArtefactModels;
import net.sevenstars.middleearth.datageneration.content.models.SimpleBigItemModel;
import net.sevenstars.middleearth.datageneration.content.models.SimpleBowItemModel;
import net.sevenstars.middleearth.datageneration.content.models.SimpleCrossbowItemModel;
import net.sevenstars.middleearth.datageneration.content.models.SimpleDyeableItemModel;
import net.sevenstars.middleearth.datageneration.content.models.SimpleHandheldItemModel;
import net.sevenstars.middleearth.datageneration.content.models.SimpleItemModel;
import net.sevenstars.middleearth.datageneration.content.models.SimpleSpearModel;
import net.sevenstars.middleearth.item.EggItemsME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.items.PipeItem;
import net.sevenstars.middleearth.item.items.weapons.CustomDaggerWeaponItem;
import net.sevenstars.middleearth.item.items.weapons.artefacts.ArtefactCustomGlowingDaggerWeaponItem;
import net.sevenstars.middleearth.item.items.weapons.artefacts.ArtefactCustomGlowingLongswordWeaponItem;
import net.sevenstars.middleearth.registries.content.npctypes.NpcRegistry;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ItemModelProvider implements DataProvider {
    private static final ResourceLocation PULLING = ResourceLocation.withDefaultNamespace("pulling");
    private static final ResourceLocation PULL = ResourceLocation.withDefaultNamespace("pull");
    private static final ResourceLocation CHARGED = ResourceLocation.withDefaultNamespace("charged");
    private static final ResourceLocation FIREWORK = ResourceLocation.withDefaultNamespace("firework");
    private static final ResourceLocation BLOCKING = ResourceLocation.withDefaultNamespace("blocking");
    private static final ResourceLocation TRIM_TYPE = ResourceLocation.withDefaultNamespace("trim_type");
    private static final ResourceLocation BROKEN = ResourceLocation.withDefaultNamespace("broken");
    private static final ResourceLocation GLOWING = ResourceLocation.withDefaultNamespace("glowing");
    private static final ResourceLocation HOT = MiddleEarth.of("hot_component");
    private static final ResourceLocation SNEAK_ATTACK = MiddleEarth.of("sneak_attack");

    private static final List<TrimMaterialData> TRIM_MATERIALS = List.of(
            new TrimMaterialData("jade", 0.001F),
            new TrimMaterialData("tin", 0.002F),
            new TrimMaterialData("lead", 0.003F),
            new TrimMaterialData("silver", 0.004F),
            new TrimMaterialData("bronze", 0.005F),
            new TrimMaterialData("steel", 0.006F),
            new TrimMaterialData("crude", 0.007F),
            new TrimMaterialData("burzum_steel", 0.008F),
            new TrimMaterialData("edhel_steel", 0.009F),
            new TrimMaterialData("khazad_steel", 0.011F),
            new TrimMaterialData("mithril", 0.012F),
            new TrimMaterialData("quartz", 0.1F),
            new TrimMaterialData("iron", 0.2F),
            new TrimMaterialData("netherite", 0.3F),
            new TrimMaterialData("redstone", 0.4F),
            new TrimMaterialData("copper", 0.5F),
            new TrimMaterialData("gold", 0.6F),
            new TrimMaterialData("emerald", 0.7F),
            new TrimMaterialData("diamond", 0.8F),
            new TrimMaterialData("lapis", 0.9F),
            new TrimMaterialData("amethyst", 1.0F),
            new TrimMaterialData("resin", 1.1F)
    );

    private final PackOutput.PathProvider modelPathProvider;
    private final Path authoredModelsRoot;
    private BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput;

    public ItemModelProvider(PackOutput output) {
        this.modelPathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models");
        this.authoredModelsRoot = output.getOutputFolder()
                .resolveSibling("resources")
                .resolve("assets")
                .resolve(MiddleEarth.MOD_ID)
                .resolve("models");
    }

    @Override
    public String getName() {
        return "Item model definitions";
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        Map<ResourceLocation, Supplier<JsonElement>> models = new HashMap<>();
        Set<ResourceLocation> definedModels = new HashSet<>();
        modelOutput = (id, supplier) -> {
            if (!definedModels.add(id)) {
                throw new IllegalStateException("Duplicate model definition for " + id);
            }
            if (!hasAuthoredItemModel(id)) {
                models.put(id, supplier);
            }
        };

        generateItemModels();

        CompletableFuture<?>[] futures = models.entrySet().stream()
                .map(entry -> DataProvider.saveStable(
                        cachedOutput,
                        entry.getValue().get(),
                        modelPathProvider.json(entry.getKey())
                ))
                .toArray(CompletableFuture[]::new);
        return CompletableFuture.allOf(futures);
    }

    private boolean hasAuthoredItemModel(ResourceLocation id) {
        return id.getNamespace().equals(MiddleEarth.MOD_ID)
                && id.getPath().startsWith("item/")
                && Files.isRegularFile(authoredModelsRoot.resolve(id.getPath() + ".json"));
    }

    private void generateItemModels() {
        SimpleItemModel.items.forEach(item -> createFlatItem(item, ModelTemplates.FLAT_ITEM));
        SimpleHandheldItemModel.items.forEach(item -> createFlatItem(item, ModelTemplates.FLAT_HANDHELD_ITEM));
        SimpleHandheldItemModel.daggers.forEach(this::registerDaggerItemModels);
        SimpleBigItemModel.items.forEach(this::registerWeaponBigItemModels);
        SimpleBigItemModel.bigBows.forEach(this::registerBigBowItemModels);
        SimpleBowItemModel.items.forEach(this::registerBow);
        SimpleCrossbowItemModel.items.forEach(this::registerCrossbow);
        SimpleSpearModel.items.forEach(this::registerSpearModels);
        SimpleBigItemModel.genericItems.forEach(this::registerGenericBigModels);
        WeaponItemsME.shields.forEach(this::registerShield);

        for (SimpleArtefactModels.Artefact artefact : SimpleArtefactModels.artefacts) {
            registerArtefact(artefact.artefact(), artefact.dualModel());
        }

        HotMetalsModel.ingots.forEach(this::registerHotIngotItem);
        HotMetalsModel.nuggets.forEach(this::registerHotNuggetItem);
        HotMetalsModel.nuggies.forEach(this::registerHotItem);
        SimpleDyeableItemModel.items.forEach(this::registerDyeableArmor);

        registerPalettedItem(ResourceItemsME.ROD);
        registerPalettedItem(ResourceItemsME.LARGE_ROD);
        registerPalettedItem(ResourceItemsME.PICKAXE_HEAD);
        registerPalettedItem(ResourceItemsME.AXE_HEAD);
        registerPalettedItem(ResourceItemsME.SHOVEL_HEAD);
        registerPalettedItem(ResourceItemsME.HOE_HEAD);
        registerPalettedItem(ResourceItemsME.BLADE);
        registerPalettedItem(ResourceItemsME.SHORT_BLADE);
        registerPalettedItem(ResourceItemsME.LONG_BLADE);
        registerPalettedItem(ResourceItemsME.SWORD_HILT);
        registerPalettedItem(ResourceItemsME.MAIL_RING);
        registerPalettedItem(ResourceItemsME.MAIL);
        registerPalettedItem(ResourceItemsME.SCALE);
        registerPalettedItem(ResourceItemsME.SCALE_MAIL);
        registerPalettedItem(ResourceItemsME.ARMOR_PLATE);
        registerPalettedItem(ResourceItemsME.HELMET_PLATE);
        registerPalettedItem(ResourceItemsME.SHIELD_BORDER);
        registerPalettedItem(ResourceItemsME.SHIELD_PLATE);

        createFlatItem(EggItemsME.NPC_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
        for (var npcType : NpcRegistry.allNpcTypes) {
            String id = npcType.location().getPath()
                    .replace("npc_data.middle-earth.", "")
                    .replace('.', '_') + "_spawn_egg";
            createFlatModel(MiddleEarth.ofPath("item", id), MiddleEarth.ofPath("item", id), ModelTemplates.FLAT_ITEM);
        }
        createFlatModel(
                MiddleEarth.ofPath("item", "npc_random_spawn_egg"),
                MiddleEarth.ofPath("item", "npc_random_spawn_egg"),
                ModelTemplates.FLAT_ITEM
        );
    }

    private void registerDaggerItemModels(Item item) {
        ResourceLocation strike = createFlatModel(
                ModelLocationUtils.getModelLocation(item, "_strike"),
                TextureMapping.getItemTexture(item),
                CustomItemModels.DAGGER_STRIKE
        );
        createFlatItem(
                item,
                ModelTemplates.FLAT_HANDHELD_ITEM,
                List.of(override(strike, SNEAK_ATTACK, 1.0F))
        );
    }

    private void registerWeaponBigItemModels(Item item) {
        ModelTemplate handModel = BuiltInRegistries.ITEM.getKey(item).getPath().contains("staff")
                ? CustomItemModels.BIG_WEAPON_STAFF
                : CustomItemModels.BIG_WEAPON;
        createFlatItem(item, "_inventory", ModelTemplates.FLAT_ITEM);

        createFlatItem(item, handModel, List.of());
    }

    private void registerGenericBigModels(Item item) {
        createFlatItem(item, "_inventory", ModelTemplates.FLAT_ITEM);
        if (item instanceof PipeItem) {
            createFlatModel(
                    MiddleEarth.ofPath("item", "smoking_" + BuiltInRegistries.ITEM.getKey(item).getPath()),
                    MiddleEarth.ofPath("item", "smoking_" + BuiltInRegistries.ITEM.getKey(item).getPath()),
                    ModelTemplates.FLAT_HANDHELD_ITEM
            );
        }
    }

    private void registerArtefact(Item item, boolean dualModel) {
        if (item instanceof CustomDaggerWeaponItem) {
            ResourceLocation strike = createFlatModel(
                    ModelLocationUtils.getModelLocation(item, "_strike"),
                    TextureMapping.getItemTexture(item),
                    CustomItemModels.DAGGER_STRIKE
            );
            ResourceLocation broken = createFlatItem(item, "_broken", ModelTemplates.FLAT_HANDHELD_ITEM);
            List<ModelOverride> overrides = new ArrayList<>();
            overrides.add(override(broken, BROKEN, 1.0F));
            if (item instanceof ArtefactCustomGlowingDaggerWeaponItem) {
                ResourceLocation glowing = createFlatItem(item, "_glowing", ModelTemplates.FLAT_HANDHELD_ITEM);
                overrides.add(override(glowing, GLOWING, 1.0F));
            }
            overrides.add(override(strike, SNEAK_ATTACK, 1.0F));
            createFlatItem(
                    item,
                    ModelTemplates.FLAT_HANDHELD_ITEM,
                    overrides
            );
            return;
        }

        if (dualModel) {
            ResourceLocation broken = createFlatItem(item, "_broken", CustomItemModels.BIG_WEAPON);
            createFlatItem(item, "_inventory", ModelTemplates.FLAT_ITEM);
            createFlatItem(item, "_broken_inventory", ModelTemplates.FLAT_ITEM);
            List<ModelOverride> overrides = new ArrayList<>();
            overrides.add(override(broken, BROKEN, 1.0F));
            if (item instanceof ArtefactCustomGlowingLongswordWeaponItem) {
                ResourceLocation glowing = createFlatItem(item, "_glowing", CustomItemModels.BIG_WEAPON);
                createFlatItem(item, "_glowing_inventory", ModelTemplates.FLAT_ITEM);
                overrides.add(override(glowing, GLOWING, 1.0F));
            }
            createFlatItem(
                    item,
                    CustomItemModels.BIG_WEAPON,
                    overrides
            );
        } else {
            ResourceLocation broken = createFlatItem(item, "_broken", ModelTemplates.FLAT_HANDHELD_ITEM);
            createFlatItem(
                    item,
                    ModelTemplates.FLAT_HANDHELD_ITEM,
                    List.of(override(broken, BROKEN, 1.0F))
            );
        }
    }

    private void registerSpearModels(Item item) {
        createFlatItem(item, "_inventory", ModelTemplates.FLAT_ITEM);
    }

    private void registerShield(Item item) {
        ResourceLocation blocking = ModelLocationUtils.getModelLocation(item, "_blocking");
        createFlatItem(
                item,
                ModelTemplates.FLAT_HANDHELD_ITEM,
                List.of(override(blocking, BLOCKING, 1.0F))
        );
    }

    private void registerBigBowItemModels(Item item) {
        ResourceLocation pulling0 = createFlatItem(item, "_pulling_0", CustomItemModels.LONGBOW);
        ResourceLocation pulling1 = createFlatItem(item, "_pulling_1", CustomItemModels.LONGBOW);
        ResourceLocation pulling2 = createFlatItem(item, "_pulling_2", CustomItemModels.LONGBOW);
        createFlatItem(item, "_inventory", ModelTemplates.FLAT_ITEM);
        createFlatItem(item, "_pulling_0_inventory", ModelTemplates.FLAT_ITEM);
        createFlatItem(item, "_pulling_1_inventory", ModelTemplates.FLAT_ITEM);
        createFlatItem(item, "_pulling_2_inventory", ModelTemplates.FLAT_ITEM);
        createFlatItem(item, CustomItemModels.LONGBOW, bowOverrides(pulling0, pulling1, pulling2));
    }

    private void registerBow(Item item) {
        ResourceLocation pulling0 = createFlatItem(item, "_pulling_0", CustomItemModels.BOW);
        ResourceLocation pulling1 = createFlatItem(item, "_pulling_1", CustomItemModels.BOW);
        ResourceLocation pulling2 = createFlatItem(item, "_pulling_2", CustomItemModels.BOW);
        createFlatItem(item, CustomItemModels.BOW, bowOverrides(pulling0, pulling1, pulling2));
    }

    private List<ModelOverride> bowOverrides(
            ResourceLocation pulling0,
            ResourceLocation pulling1,
            ResourceLocation pulling2
    ) {
        return List.of(
                override(pulling0, PULLING, 1.0F),
                override(pulling1, PULLING, 1.0F, PULL, 0.65F),
                override(pulling2, PULLING, 1.0F, PULL, 0.9F)
        );
    }

    private void registerCrossbow(Item item) {
        ResourceLocation pulling0 = createFlatItem(item, "_pulling_0", CustomItemModels.CROSSBOW);
        ResourceLocation pulling1 = createFlatItem(item, "_pulling_1", CustomItemModels.CROSSBOW);
        ResourceLocation pulling2 = createFlatItem(item, "_pulling_2", CustomItemModels.CROSSBOW);
        ResourceLocation arrow = createFlatItem(item, "_arrow", CustomItemModels.CROSSBOW);
        ResourceLocation firework = createFlatModel(
                ModelLocationUtils.getModelLocation(item, "_firework"),
                TextureMapping.getItemTexture(item, "_arrow"),
                CustomItemModels.CROSSBOW
        );
        createFlatItem(
                item,
                CustomItemModels.CROSSBOW,
                List.of(
                        override(pulling0, PULLING, 1.0F),
                        override(pulling1, PULLING, 1.0F, PULL, 0.58F),
                        override(pulling2, PULLING, 1.0F, PULL, 1.0F),
                        override(arrow, CHARGED, 1.0F),
                        override(firework, CHARGED, 1.0F, FIREWORK, 1.0F)
                )
        );
    }

    private void registerDyeableArmor(Item item) {
        ModelTemplates.TWO_LAYERED_ITEM.create(
                ModelLocationUtils.getModelLocation(item),
                TextureMapping.layered(
                        TextureMapping.getItemTexture(item),
                        TextureMapping.getItemTexture(item, "_overlay")
                ),
                modelOutput
        );
    }

    private void registerPalettedItem(Item item) {
        ResourceLocation baseModel = ModelLocationUtils.getModelLocation(item);
        ResourceLocation baseTexture = TextureMapping.getItemTexture(item);
        List<ModelOverride> overrides = new ArrayList<>();

        for (TrimMaterialData material : TRIM_MATERIALS) {
            ResourceLocation trimModel = baseModel.withSuffix("_" + material.name() + "_trim");
            ResourceLocation trimTexture = MiddleEarth.of(
                    "trims/items/" + BuiltInRegistries.ITEM.getKey(item).getPath()
                            + "_trim_" + material.name()
            );
            ModelTemplates.TWO_LAYERED_ITEM.create(
                    trimModel,
                    TextureMapping.layered(baseTexture, trimTexture),
                    modelOutput
            );
            overrides.add(override(trimModel, TRIM_TYPE, material.modelIndex()));
        }

        ResourceLocation hotModel = createFlatItem(item, "_hot", ModelTemplates.FLAT_ITEM);
        overrides.add(override(hotModel, HOT, 1.0F));
        createFlatItem(item, ModelTemplates.FLAT_ITEM, overrides);
    }

    private void registerHotIngotItem(Item item) {
        String textureName = "ingot_hot";
        if (item == ResourceItemsME.BRONZE_INGOT) {
            textureName = "medium_ingot_hot";
        } else if (item == ResourceItemsME.TIN_INGOT) {
            textureName = "cube_ingot_hot";
        } else if (item == ResourceItemsME.LEAD_INGOT) {
            textureName = "tall_small_ingot_hot";
        } else if (item == ResourceItemsME.EDHEL_STEEL_INGOT || item == ResourceItemsME.MITHRIL_INGOT) {
            textureName = "small_ingot_hot";
        } else if (item == ResourceItemsME.KHAZAD_STEEL_INGOT) {
            textureName = "tall_ingot_hot";
        } else if (item == ResourceItemsME.BURZUM_STEEL_INGOT) {
            textureName = "thick_ingot_hot";
        }

        ResourceLocation hotModel = createFlatModel(
                ModelLocationUtils.getModelLocation(item, "_hot"),
                MiddleEarth.ofPath("item", textureName),
                ModelTemplates.FLAT_ITEM
        );
        createFlatItem(item, ModelTemplates.FLAT_ITEM, List.of(override(hotModel, HOT, 1.0F)));
    }

    private void registerHotNuggetItem(Item item) {
        ResourceLocation hotModel = createFlatModel(
                ModelLocationUtils.getModelLocation(item, "_hot"),
                MiddleEarth.ofPath("item", "nugget_hot"),
                ModelTemplates.FLAT_ITEM
        );
        createFlatItem(item, ModelTemplates.FLAT_ITEM, List.of(override(hotModel, HOT, 1.0F)));
    }

    private void registerHotItem(Item item) {
        ResourceLocation hotModel = createFlatItem(item, "_hot", ModelTemplates.FLAT_ITEM);
        createFlatItem(item, ModelTemplates.FLAT_ITEM, List.of(override(hotModel, HOT, 1.0F)));
    }

    private ResourceLocation createFlatItem(Item item, ModelTemplate template) {
        return createFlatItem(item, template, List.of());
    }

    private ResourceLocation createFlatItem(Item item, String suffix, ModelTemplate template) {
        return createFlatModel(
                ModelLocationUtils.getModelLocation(item, suffix),
                TextureMapping.getItemTexture(item, suffix),
                template
        );
    }

    private ResourceLocation createFlatItem(
            Item item,
            ModelTemplate template,
            List<ModelOverride> overrides
    ) {
        ResourceLocation id = ModelLocationUtils.getModelLocation(item);
        TextureMapping textures = TextureMapping.layer0(TextureMapping.getItemTexture(item));
        if (overrides.isEmpty()) {
            return template.create(id, textures, modelOutput);
        }
        return template.create(id, textures, modelOutput, (modelId, textureMap) -> {
            JsonObject json = template.createBaseTemplate(modelId, textureMap);
            JsonArray overrideArray = new JsonArray();
            overrides.forEach(override -> overrideArray.add(override.toJson()));
            json.add("overrides", overrideArray);
            return json;
        });
    }

    private ResourceLocation createFlatModel(
            ResourceLocation id,
            ResourceLocation texture,
            ModelTemplate template
    ) {
        return template.create(id, TextureMapping.layer0(texture), modelOutput);
    }

    private static ModelOverride override(ResourceLocation model, Object... predicatePairs) {
        Map<ResourceLocation, Float> predicates = new LinkedHashMap<>();
        for (int i = 0; i < predicatePairs.length; i += 2) {
            predicates.put((ResourceLocation) predicatePairs[i], (Float) predicatePairs[i + 1]);
        }
        return new ModelOverride(predicates, model);
    }

    private record TrimMaterialData(String name, float modelIndex) {
    }

    private record ModelOverride(Map<ResourceLocation, Float> predicates, ResourceLocation model) {
        private JsonObject toJson() {
            JsonObject json = new JsonObject();
            JsonObject predicateJson = new JsonObject();
            predicates.forEach((id, value) -> predicateJson.addProperty(id.toString(), value));
            json.add("predicate", predicateJson);
            json.addProperty("model", model.toString());
            return json;
        }
    }
}
