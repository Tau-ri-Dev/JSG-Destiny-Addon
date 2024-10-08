package dev.tauri.jsgdestinyaddon.client.listener;

import dev.tauri.jsgdestinyaddon.JSGDestinyAddon;
import dev.tauri.jsgdestinyaddon.client.loader.LoadersHolder;
import net.minecraft.util.Unit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.StartupMessageManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.progress.ProgressMeter;

@Mod.EventBusSubscriber(modid = JSGDestinyAddon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModListener {
    @SubscribeEvent
    public static void onResourcesReload(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener((pPreparationBarrier, pResourceManager, pPreparationsProfiler, pReloadProfiler, pBackgroundExecutor, pGameExecutor) ->
                pPreparationBarrier.wait(Unit.INSTANCE).thenRunAsync(() -> {
                    pReloadProfiler.startTick();
                    pReloadProfiler.push("jsg_destiny_resources");
                    ProgressMeter progress = StartupMessageManager.addProgressBar("JSG: Destiny Addon - TESR loading", 2);
                    LoadersHolder.TEXTURE_LOADER.loadResources();
                    progress.increment();
                    LoadersHolder.MODEL_LOADER.loadResources();
                    progress.increment();
                    progress.complete();
                    pReloadProfiler.pop();
                    pReloadProfiler.endTick();
                }, pBackgroundExecutor));
    }
}
