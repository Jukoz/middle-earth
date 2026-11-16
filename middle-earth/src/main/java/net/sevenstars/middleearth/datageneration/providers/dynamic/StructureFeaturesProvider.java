package net.sevenstars.middleearth.datageneration.providers.dynamic;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.registry.RegistryWrapper;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.structurefeatures.StructureFeaturesRegistry;
import net.sevenstars.middleearth.registries.content.structurefeatures.StructureFeaturesReport;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class StructureFeaturesProvider extends FabricDynamicRegistryProvider {
    private static final String REPORT_PATH = "../../../build/reports/structure_features.md";

    private final FabricDataOutput output;

    public StructureFeaturesProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
        this.output = output;
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        entries.addAll(registries.getOrThrow(DynamicRegistriesME.STRUCTURE_FEATURES));
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        return super.run(writer).thenRun(() -> {
            // The generated data goes in the module's source folder and the report goes in its build folder
            Path report = output.getPath().resolve(REPORT_PATH).normalize();
            try {
                MiddleEarth.LOGGER.logInfoMsg(StructureFeaturesReport.write(StructureFeaturesRegistry.scan(), report) + ", report in " + report);
            } catch (IOException e) {
                throw new IllegalStateException("Could not write the structure features report", e);
            }
        });
    }

    @Override
    public String getName() {
        return DynamicRegistriesME.STRUCTURE_FEATURES.getValue().getPath();
    }
}
