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
backport of the official upstream
[`1.0.2-1.21.8-beta`](https://github.com/Jukoz/middle-earth/releases/tag/1.0.2-1.21.8-beta)
tag at
[`fe655f713`](https://github.com/Jukoz/middle-earth/commit/fe655f71374db31629c228e1435e64636698b56c).
That tagged tree is identical to the fully merged upstream `dev` tree at
[`b92dbab45`](https://github.com/Jukoz/middle-earth/commit/b92dbab458da78a36378e18b7afa216288631f14).
All compatible source, resource, data, recipe, loot, translation, model,
balance, and gameplay changes in that release are represented either directly
or by a 1.21.1/NeoForge semantic adaptation. Known upstream defects are
corrected while translating them to the older APIs. The upstream wild-spawn
chunk cache and global mob-cap implementations are not copied because their
global state and coarse rejection semantics are incompatible with this port;
the existing bounded, dimension-aware spawning implementation is retained.
It is not an official release from the original Middle-earth mod team.

All original copyright notices, credits, trademarks and the ARR license remain
unchanged. This fork grants no additional rights; see [LICENSE](./LICENSE) and
the [upstream repository](https://github.com/Jukoz/middle-earth).

Target runtime: Minecraft 1.21.1, Java 21 and NeoForge 21.1.233 or newer within
the 21.1 line. The build currently validates against NeoForge 21.1.244.

The current maintenance snapshot also incorporates the upstream NeoForge fixes
through [`7497d9e02`](https://github.com/Jukoz/middle-earth/commit/7497d9e02b99ef76c3c5991f9639774b26b7fc5b).
The content baseline remains 1.0.2; this is not the unreleased Fabric 1.0.3 line.

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

Use the `dev-neoforge` or `1.0.2-1.21.1-neoforge-backport` branch for this port.
The historical `main` branch in the fork contains Fabric sources. Select the
NeoForge branch before downloading a source ZIP, or clone it explicitly:

```shell
git clone --branch dev-neoforge https://github.com/Campione01/middle-earth.git
cd middle-earth
```

Extract the complete repository before building. Run the wrapper from the root
directory containing `settings.gradle` and all three module directories. A
Git checkout is not required; the source ZIP builds the same way.

Install a **JDK 21**, then confirm `java -version` reports 21. If multiple JDKs
are installed, point `JAVA_HOME` at JDK 21. This wrapper uses Gradle 8.10;
running it with Java 23 or newer is unsupported. No global Gradle installation
or separately downloaded mod dependency JARs are required.

Windows PowerShell:

```powershell
.\gradlew.bat build --console=plain
```

Linux and macOS:

```shell
bash ./gradlew build --console=plain
```

The player artifact is `middle-earth/build/libs/Middle-earth-<version>.jar`. It embeds Seven Stars API and Of Beasts and Wild Things through NeoForge Jar-in-Jar, so players should install only this outer Middle-earth jar. The standalone jars under the two subproject build directories are intermediate development artifacts and are not part of the player release.

The build generates required resources and runs tests automatically. Initial
dependency downloads need network access to Gradle, Mojang, NeoForged and the
recipe-viewer Maven repositories. For download/SSL failures, configure any
required proxy in your user Gradle configuration and retry; do not disable TLS
verification. Report the first failure and `java -version` output when a build
fails. Windows and Linux CI builds use a fresh source archive without
local generated files or Git metadata.

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
