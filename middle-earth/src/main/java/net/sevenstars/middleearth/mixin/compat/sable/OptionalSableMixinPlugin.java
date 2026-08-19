package net.sevenstars.middleearth.mixin.compat.sable;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.service.MixinService;

public final class OptionalSableMixinPlugin implements IMixinConfigPlugin {
    private static final String SABLE_EVENTS_CLASS = "dev.ryanhcode.sable.SableCommonEvents";

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        try {
            MixinService.getService().getBytecodeProvider().getClassNode(SABLE_EVENTS_CLASS);
            return List.of("SableCommonEventsMixin");
        } catch (ClassNotFoundException | IOException ignored) {
            return List.of();
        }
    }

    @Override
    public void preApply(
            String targetClassName,
            ClassNode targetClass,
            String mixinClassName,
            IMixinInfo mixinInfo
    ) {
    }

    @Override
    public void postApply(
            String targetClassName,
            ClassNode targetClass,
            String mixinClassName,
            IMixinInfo mixinInfo
    ) {
    }
}
