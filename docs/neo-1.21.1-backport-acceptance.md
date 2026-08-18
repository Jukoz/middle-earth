# NeoForge 1.21.1 backport acceptance

This document records the upstream modules represented by the player Jar-in-Jar artifact and the behavioral checks required before publishing or deploying a backport build.

## Adopted upstream baseline

- Released ancestor: `origin/main` at `1f047d55dd1509c001596876617b4ae660eb006e`.
- Official release baseline: tag `1.0.2-1.21.8-beta` at `fe655f71374db31629c228e1435e64636698b56c`.
- The fully merged upstream `dev` commit `b92dbab458da78a36378e18b7afa216288631f14` and the official tag share tree `531effb8d0d4e2b63b7fb0e7b2631c13b0028a0b`; there is no main/dev content split in this baseline.
- Every compatible change from the released ancestor through that tag is represented directly or by an explicit Minecraft 1.21.1/NeoForge adaptation. This includes the complete `d576f1817b563996339d40b0170b636b4646451c` content merge, fixes through `52519872d3ba10a0391b129ea74898cc6396249e`, and inscription/localization changes through `ee79dc1977da654ca0276a91d93901f1b8521552`.
- Fabric metadata is replaced by NeoForge metadata, and 1.21.8 item-definition resources are expressed through the 1.21.1 `models/item` contract.
- Upstream static forge and layered-pile loot JSONs are emitted by the NeoForge data provider and verified at the generated-resource boundary. The Fabric `ServerWorldMixin` sleep hook maps to NeoForge's native sleeping-level advance plus a guarded Overworld advance; its entity UUID tracker is deliberately absent with the global mob-cap implementation it served.
- Upstream removes its saddle recipe after Minecraft 1.21.8 adds an equivalent vanilla recipe. Minecraft 1.21.1 has no vanilla replacement, so this backport deliberately continues to generate `middle-earth:saddle` with the original leather-and-iron pattern.
- Wild-spawn chunk caching and the global mob-cap lineage are documented incompatibilities rather than silent omissions. Their process-global mutable state and coarse rejection semantics are replaced by the existing bounded, dimension-aware NeoForge spawn implementation.

## Source modules

### Middle-earth

The main mod owns world generation, structures, NPCs, gameplay screens, recipes, items, blocks, entities, models, textures, sounds, data generation, and the outer player jar.

Required rendering checks:

- Inscription-table catalyst and chisel placeholders resolve from the 1.21.1 atlas contract.
- All 18 forged-component base models retain 22 material permutations plus the hot-state override; authored models must not shadow the generated override sets.
- Big weapons, longswords, longbows, spears, artefacts, pipes, the troll mace, the candle holder, and the watering can select the same inventory, hand, using, broken, and glowing model states as the upstream item definitions.
- Grass-tinted items, dyeable equipment, beech leaves, and colored bundles retain their upstream item colors.
- Shields and held banners continue to use their special item renderers.

Required functional checks:

- Inscription word selection stays synchronized with the server when a full three-word selection is replaced.
- Catalyst and chisel durability ends on the configured final use; the mithril chisel remains infinite.
- Longbow pulling, longsword blocking, artefact broken state, pipe smoking, and watering-can sprinkling switch only under their intended use conditions.
- Structure generation, structure NPC placement, natural spawning, dimension travel, respawn persistence, plate interaction, and the previously repaired animal/NPC render paths remain regression-free.
- Old skull rendering and persistence, all nine skeleton poses, and all four chandelier variants survive place/use/save/reload cycles.
- New wearable models and dye layers render on players and NPCs; the beekeeper mask preserves the upstream hive-protection behavior.
- Existing and new Dol Guldur shields retain the corrected medium/heavy types and blocking models.
- Hewing and Tree Feller secondary drops honor the real tool, Silk Touch, Fortune, break-event cancellation, and block-entity loot.
- Holly and Mallorn leaf bone-meal interactions preserve required block-state properties.
- Sleeping in the Middle-earth dimension advances both that level and the Overworld without double-advancing an Overworld sleep.
- The forge drops only from its top part, while skeletal-pile and waste-pile drops preserve their placed layer counts from one through eight.
- Saddle craftability remains available on Minecraft 1.21.1 with the upstream pre-1.21.8 recipe semantics.

### Seven Stars API

This embedded dependency owns shared registration, networking, AI, animation, and utility infrastructure. Its common upstream image resources are byte-identical in the backport.

Required checks:

- Its logical mod is discovered from the nested jar and reaches common/client initialization once.
- Shared registry and networking helpers resolve without Fabric runtime classes.
- No API resource or mixin load failure appears in a fresh client log.

### Of Beasts and Wild Things

This embedded dependency owns farm-animal variants and its own entities, models, textures, and sounds. Common PNG and OGG resources are byte-identical to upstream; the backport additionally carries the required 1.21.1 vanilla animal fallback textures.

Required checks:

- Pig, cow, and chicken base and variant textures render correctly.
- Chicken and cow rendering does not recurse when another mod injects renderer bridge methods.
- Entity attributes, AI, spawning, sounds, persistence, and client render-layer registration remain functional.

## Automated gates

1. `:middle-earth:test` and all module compilation tasks pass.
2. `:middle-earth:runData` completes and the model/resource audit reports zero missing references.
3. A clean player jar contains all three logical mods, generated resources, NeoForge metadata, and no Fabric runtime classes or `fabric.mod.json`.
4. A muted client runs on an isolated Win32 desktop without switching the user's input desktop.
5. Fresh screenshots and logs demonstrate the target GUI and representative item, block, entity, NPC, and submodule renders with no Middle-earth missing-texture/model/atlas error.
6. Publishing and live-jar replacement happen only from the exact source and artifact that passed these gates.

## Accepted 1.21.1 build

- Player jar: `Middle-earth-1.0.2-1.21.1-beta-backport.1.jar`.
- SHA-256: `0B4EB219764F315CACF3FBDB5C82639C17B58A73C770FE308C3DF0EEE8EDA70D`.
- Packaging: 68,126 entries, 356 Middle-earth structure NBT files, and exactly two nested logical-mod jars.
- Loader audit: NeoForge metadata and both Middle-earth mixin configs are present; `fabric.mod.json`, `net/fabricmc/**`, and bundled JEI/EMI API classes are absent.
- Generated forged-component audit: 18/18 base models contain exactly 23 overrides each (22 material variants plus hot state).
- Independent item-state audit: 615 effective custom state models, including 260 inventory models. The descriptor contract still registers 176 descriptors and 418 additional baked models; every referenced parent, texture, and override target resolves.
- Data recipes in the player jar include 63 forge, 11 shaping-anvil, and 102 inscription definitions. Runtime viewer registration resolves 863 artisan, 63 forge, 11 shaping-anvil, 102 inscription, and 8 dynamic crafting displays.
- The player jar also contains the version-specific `middle-earth:saddle` crafting recipe and its unlock advancement; the generated recipe uses the 1.21.1 ingredient-object schema.
- Every inscription recipe has a unique matching recipe-book advancement; the former five shared advancement paths can no longer overwrite each other during data generation.
- All 71 pale-oak block models use explicit Minecraft 1.21.1 birch or dark-oak texture fallbacks, and faction NPC ranks serialize in deterministic enum order.
- JEI and EMI are optional compile-time integrations and are not embedded. With EMI alone, the 8 dynamic displays are registered natively; with JEI and EMI together, JEMI imports the JEI extensions and native EMI registration is suppressed to avoid duplicate recipe IDs.

## Acceptance evidence

- Final `clean build` completed 37 actionable tasks. Across the three modules, 116 tests in 26 suites passed with no failure, error, or skip.
- The final Jar-in-Jar audit found 68,126 entries, exactly two nested logical mods, zero duplicate ZIP paths, 10,304 recipe JSON files, 8,092 item-model JSON files, and 270 biome-event definitions.
- A second consecutive `:middle-earth:runData` wrote zero files, confirming deterministic generated resources after canonical faction-rank ordering.
- Current 1.0.2 JEI+EMI client check: `client-20260815-151821`; 135/135 actions and 17/17 nonblank captures passed. It covered the six new decorative blocks, all nine skeleton poses, six wearables, five new shields, representative blocking models, both leaf bone-meal transformations, and the starlight-phial return flow.
- The starlight-phial check opened the return screen while the integrated server was paused, observed the 3-second countdown and enabled button, returned from `middle-earth:middle_earth` to the configured Overworld point, and consumed the survival-mode phial. All positive markers were present and no failure marker appeared.
- In the JEI+EMI run, both integrations reported `863/102/11/63/8`; JEMI skipped the four categories already owned by native EMI. The generic JEMI tag-ingredient duplicate notices did not involve Middle-earth recipe IDs.
- Current 1.0.2 EMI-only client check: `client-20260815-152552`; 59/59 actions and 6/6 nonblank captures passed. Artisan (863 pages), forge (63), shaping anvil (11), and inscription (102) categories opened and rendered correctly, and the `@middle-earth` item search populated.
- Both current runs used isolated Win32 desktops, preserved the `Default` input desktop for all 230 samples, restored options byte-for-byte, set master volume to zero, and confirmed OpenAL's null backend with no output device.
- Fresh logs contain no fatal, Mixin-apply failure, Middle-earth missing texture/model, invalid resource path, uncaught exception, or crash report.
- The 2026-08-03 evidence remains the exhaustive 1.0.1 renderer baseline; the two 2026-08-15 runs above are the publication gates for this 1.0.2 artifact.
