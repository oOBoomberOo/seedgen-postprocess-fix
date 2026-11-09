# SeedGen Post-Processing Fix

Two fixes for Distant Horizons SeedGen, mostly noticeable with worldgen mods like WWOO:

- Hides the `Trying to mark a block for post processing ... not supported` warning that SeedGen can spam into `latest.log` (I've seen it hit several GB).
- Makes LOD generation fast again with Trees enabled. SeedGen's Trees option also paints ground features like clay disks and vegetation patches, and that part is slow. This mod skips it but keeps the trees.

Distant terrain won't show those ground patches anymore. If you want them back, add `-Dseedgenfix.skipFeaturePainting=false` to your JVM arguments.

## Install

Works on Minecraft 1.20.1 to 26.3, Fabric or NeoForge, Java 17+.

Grab the jar for your loader from [Releases](https://github.com/oOBoomberOo/seedgen-postprocess-fix/releases) and put it in `mods/` next to Distant Horizons and SeedGen.

## Building

```bash
./gradlew :fabric:jar :neoforge:jar
```

## License

MIT
