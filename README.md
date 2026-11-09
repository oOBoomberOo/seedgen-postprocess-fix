# SeedGen Post-Processing Fix

Stops Distant Horizons **SeedGen** from flooding `latest.log` with:

```text
[Trying to mark a block for post processing @ MutableBlockPos{x=.., y=-60, z=..}, but this operation is not supported.]
```

SeedGen paints chunks through a stand-in `ChunkAccess`. The vanilla base
implementation of `markPosForPostProcessing` only logs that warning and does
nothing else, so every clay disk / feature placement writes a line while
SeedGen holds its chunk lock. Logs of **2.7 GB** have been observed.

## What it does

The entire vanilla method body is:

```java
public void markPosForPostProcessing(BlockPos blockPos) {
    LOGGER.warn("Trying to mark a block for post processing @ {}, but this operation is not supported.", blockPos);
}
```

There is no work to skip — only a log event. This mod installs a Log4j filter
that drops exactly that message, so the call becomes a no-op with zero side
effects. Real chunks (`LevelChunk`, `ProtoChunk`) override the method and are
untouched.

Because the patch never references a Minecraft class, **one build works on
every supported Minecraft version and on both loaders**.

## Supported versions

- Minecraft: **1.20.1 – 26.3**
- Loaders: **Fabric** (Loader 0.15+) and **NeoForge** (47.1+ / 20.x / 21.x / 26.x)
- Java: **17+**

Pick the jar for your loader:

| File | Loader |
| --- | --- |
| `seedgen-postprocess-fix-fabric-<version>.jar` | Fabric |
| `seedgen-postprocess-fix-neoforge-<version>.jar` | NeoForge |

## Install

1. Install Fabric Loader or NeoForge for your Minecraft version.
2. Install Distant Horizons and SeedGen.
3. Drop the matching jar into `mods/`.

The mod does nothing unless the warning is actually emitted, so it is safe to
leave installed.

## Notes

- SeedGen itself still places the disks; this removes the log I/O (and the
  long lock hold that came with it). It does not change world generation.
- If you would rather cancel the method than filter the message, the Mixin
  equivalent is a few lines, but it must be built per loader and per Minecraft
  version because Fabric remaps to intermediary while NeoForge uses official
  names.

## Build

```bash
./gradlew :fabric:jar :neoforge:jar
```

Outputs land in `fabric/build/libs` and `neoforge/build/libs`.

## License

MIT — see [LICENSE](LICENSE).
