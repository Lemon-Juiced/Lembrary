package lemon_juice;

import lemon_juice.event.MobDropEvents;
import lemon_juice.tag.TagRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(Lembrary.MOD_ID)
public class Lembrary {
    public static final String MOD_ID = "lembrary";

    public Lembrary(IEventBus modEventBus) {
        modEventBus.addListener(this::commonSetup);

        // Register Tags
        TagRegistry.init();

        NeoForge.EVENT_BUS.register(this);
    }

    /**
     * Registers mob drops from <code>BeheadingSwordItem</code>s
     * @param event The <code>FMLCommonSetupEvent</code> that will set up the drops
     */
    private void commonSetup(final FMLCommonSetupEvent event) {
        NeoForge.EVENT_BUS.register(new MobDropEvents());
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {}
    }
}
