# NeoForge 1.21.1 backport acceptance

This document records the upstream modules represented by the player Jar-in-Jar artifact and the behavioral checks required before publishing or deploying a backport build.

## Adopted upstream baseline

- Release: `origin/main` at `1f047d55dd1509c001596876617b4ae660eb006e`.
- Stable fixes ancestry: `origin/fixes` through `db056482e3fe10ecf15a09510d6dbc8a5cfce840`.
- Selective later fixes: `eba6b5bea287dbe8c1cbcf255e1f2dbdab898f4b`, `65450642e67cab0fb9feecb74bfa5e4dc1d63b8a`, `e83dffe9ef167edc9802b57ab2c2caf8ec564fd5`, `baf4a74c98bb49d47aee549190ca8bd5903ddc8f`, `3cf26ead8210bab2260d814cf9542bdffba856bf`, and `52519872d3ba10a0391b129ea74898cc6396249e`.
- Audit watermark: `origin/fixes` at `52519872d3ba10a0391b129ea74898cc6396249e`; this is not a fully adopted baseline.
- Localization changes: compatible keys through `ee79dc1977da654ca0276a91d93901f1b8521552`.
- `mounts-experimental`, merged `new-stuff`, wild-spawn caching, and global mob-cap lineages are excluded.

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

- Player jar: `Middle-earth-1.0.1-1.21.1-beta.jar`.
- SHA-256: `C61B7756379AFA3FC4CE393328C8CDAE7B0587ACF71CF514F919701CD04F4FDF`.
- Packaging: 67,495 entries, 356 Middle-earth structure NBT files, and exactly two nested logical-mod jars.
- Loader audit: NeoForge metadata and both Middle-earth mixin configs are present; `fabric.mod.json`, `net/fabricmc/**`, and bundled JEI/EMI API classes are absent.
- Generated forged-component audit: 18/18 base models contain exactly 23 overrides each (22 material variants plus hot state).
- Independent item-state audit: 610 effective custom state models, including 260 inventory models; 176 runtime item descriptors register 418 additional baked models. Every referenced parent, texture, and override target resolves.
- Data recipes in the player jar include 63 forge, 11 shaping-anvil, and 102 inscription definitions. Runtime viewer registration resolves 854 artisan, 63 forge, 11 shaping-anvil, 102 inscription, and 8 dynamic crafting displays.
- JEI and EMI are optional compile-time integrations and are not embedded. With EMI alone, the 8 dynamic displays are registered natively; with JEI and EMI together, JEMI imports the JEI extensions and native EMI registration is suppressed to avoid duplicate recipe IDs.

## Acceptance evidence

- Clean `build` completed 31 tasks; 87 tests in 18 suites passed with no failures or errors.
- Forged-component colors and representative component matrices: `client-20260803-024950`.
- All 176 runtime inventory descriptors, split into five visual batches: `client-20260803-024200`.
- JEI-only recipe viewer: `client-20260803-031037`.
- No-viewer optional-dependency startup: `client-20260803-032954`.
- EMI-only exploded-source check: `client-20260803-033757`.
- JEI and EMI exploded-source check: `client-20260803-034031`.
- Final packaged-jar JEI and EMI check: `client-20260803-040815`; 59/59 actions and 6/6 nonblank captures passed, both viewer registrations reported `854/102/11/63/8`, and Middle-earth dynamic duplicate IDs were zero.
- Final packaged-jar EMI-only check: `client-20260803-041017`; 59/59 actions and 6/6 nonblank captures passed, EMI reported `854/102/11/63/8`, and no JEI plugin was loaded.
- Each packaged-jar run used a hidden Win32 desktop, preserved the `Default` input desktop, restored options byte-for-byte, and used the null OpenAL backend with no output device.
- Fresh packaged-jar logs contain no fatal error and no Middle-earth missing texture or missing model report.
