<div align="center">

<img src="https://cdn.modrinth.com/data/cached_images/878f02d73c5caa5506ec2486457b65d1eb199978.png" style="width: 50%;"><br>

-----
<h2>Welcome to this source-available project of <img src="./.idea/icon.png" width="18"> Middle-earth <img src="./.idea/icon.png" width="18"> mod.</h2>
<p>This mod is about the famous universe of the Middle Earth, from J. R. R. Tolkien's work, into Minecraft.</p> 
<p>You'll find a brand new dimension with custom blocks, items, entity, generation, etc.</p>
<a href="https://discord.gg/9yQ7UWkVUz"><img src="https://dcbadge.limes.pink/api/server/9yQ7UWkVUz?style=flat" alt="Discord"/></a>
<br>
<a href="https://github.com/Jukoz/middle-earth"><img src="https://img.shields.io/github/stars/Jukoz/middle-earth"></a>
<a href="https://modrinth.com/mod/middle-earth"><img src="https://img.shields.io/modrinth/dt/middle-earth?logo=modrinth&label=&suffix=%20&style=flat&color=242629&labelColor=5ca424&logoColor=ffffff" alt="Modrinth"></a>
<a href="https://www.curseforge.com/minecraft/mc-mods/middle-earth"><img src="https://img.shields.io/curseforge/dt/864574?logo=curseforge&label=&suffix=%20&style=flat&color=242629&labelColor=f16537&logoColor=ffffff" alt="Curseforge"></a>
<a href="https://middleearthmcmod.wiki.gg/wiki/Middle_Earth_Minecraft_Mod_Wiki"><img src="https://img.shields.io/badge/wiki-b79c80?logo=wikidotgg&&logoColor=ffffff"></a>
<br>
<img src="https://cf.way2muchnoise.eu/versions/864574.svg">
</div>

-----

## Unofficial NeoForge 1.21.1 backport

The `1.0.2-1.21.1-neoforge-backport` branch is an unofficial semantic
backport of the upstream 1.0.2 development snapshot. It includes the complete
[`d576f181`](https://github.com/Jukoz/middle-earth/commit/d576f1817b563996339d40b0170b636b4646451c)
`new-stuff` merge, every compatible change from the selected fixes through
[`52519872`](https://github.com/Jukoz/middle-earth/commit/52519872d3ba10a0391b129ea74898cc6396249e),
and localization changes through
[`ee79dc1977da654ca0276a91d93901f1b8521552`](https://github.com/Jukoz/middle-earth/commit/ee79dc1977da654ca0276a91d93901f1b8521552).
Selected source, resource, data, recipe, loot, translation, model, balance,
and gameplay changes are all in scope. Known upstream defects are corrected
while translating them to the 1.21.1 APIs. The upstream wild-spawn chunk cache
and global mob-cap implementations are not copied because their global state
and coarse rejection semantics are incompatible with this NeoForge port; the
existing bounded, dimension-aware spawning implementation is retained.
It is not an official release from the original Middle-earth mod team.

All original copyright notices, credits, trademarks and the ARR license remain
unchanged. This fork grants no additional rights; see [LICENSE](./LICENSE) and
the [upstream repository](https://github.com/Jukoz/middle-earth).

Target runtime: Minecraft 1.21.1, Java 21 and NeoForge 21.1.233 or newer within
the 21.1 line. The build currently validates against NeoForge 21.1.244.

-----

## Current state of the mod
As of now, the mod is in the Alpha development stage, meaning this project is still a prototype, and missing many core features we are planning on adding.

## Planned Features
> - <b>Brews and stews</b><br>
    <i>Features related to cooking and fancy beverage preparation.</i>
> - <b>Trading mechanics</b><br>
    <i>A new trading feature.</i>
> - <b>Next iteration on smithing & attributes system</b><br>
    <i>Mechanics related to smithing and gear upgrades.</i>
> - <b>Structures mechanics & Settlements</b><br>
    <i>Immersive structures and settlements, aiming to have a cohesive environment.</i>
> - <b>Hiring units</b><br>
    <i>Creating a mechanic so npcs can join in the player's adventures!</i>
> - <b>New factions</b><br>
    <i>Each update we will deliver more and more factions with custom content for each such as armors, weapons, structures and mounts (or mount armor)! We have a list of factions we want to offer in the team design plans.</i>


-----

## Credits
<details open>
<summary><b>Click to Fold / Unfold</b></summary>

### Developers
> - Jukoz
> - ObliviousCrab
> - Slooshyboi
> - TomSchlom

### Artists (Models/Textures)
> - Boenndal
> - Jooble
> - Jukoz
> - ObliviousCrab
> - Sindavar
> - Thijs
> - R3tt0

### Builders
> - Angmarzku
> - Arwaeneth
> - Boenndal
> - Jooble
> - Jukoz
> - ObliviousCrab
> - Slooshyboi
> - Thijs

### Contributors
> - Ag3ntCrab
> - Froosty11
> - Grandison
> - JB3
> - Khuz
> - nullBlade
> - Number_Sir
> - Python_200
> - Thorin_The_III
> - WorseNotePad

### Special Thanks
dylanhugh and Angmarzku for their ideas & arts for Gundabad and more.
</details>

-----

## Building the NeoForge 1.21.1 backport

Build all three source modules with Java 21:

```shell
./gradlew build
```

The player artifact is `middle-earth/build/libs/Middle-earth-<version>.jar`. It embeds Seven Stars API and Of Beasts and Wild Things through NeoForge Jar-in-Jar, so players should install only this outer Middle-earth jar. The standalone jars under the two subproject build directories are intermediate development artifacts and are not part of the player release.

-----

## License
All of our content is under the **ARR** license (**All Right Reserved**), meaning you cannot use our code without our written consent. If you want to use our code in any way, please write an issue using the [request template in our Github](https://github.com/Jukoz/middle-earth/issues/new?assignees=&labels=request&projects=&template=code_use_permission_request.yml).
> **Please be aware that this project is a Minecraft Parody set in the Middle-earth universe and all rights are reserved under Tolkien domain.**

-----

## Contribution
### Contributing to the source code
If you want to help us, please join our [Discord server][discord].

### Translate our mod to different languages
Current translation progress is as shown below:

<details>
<summary><b>Click to Fold / Unfold</b></summary>
<a href="https://crowdin.com/project/middle-earth-mod">
    <img src="https://badges.awesome-crowdin.com/translation-16338834-668804.png" width="50%" alt="Crowdin">
</a>
</details>

if you want to participate in the localization, please join our [Discord server][discord] or contribute directly in our [Crowdin project][crowdin].

[github]: https://github.com/Jukoz/middle-earth
[discord]: https://discord.gg/9yQ7UWkVUz
[crowdin]: https://crowdin.com/project/middle-earth-mod
