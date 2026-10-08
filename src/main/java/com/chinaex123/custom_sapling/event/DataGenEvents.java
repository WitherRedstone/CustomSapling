package com.chinaex123.custom_sapling.event;

import com.chinaex123.custom_sapling.data.provider.CustomSaplingDefinitionProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber
public class DataGenEvents {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        if (event.includeServer()) {
            event.getGenerator().addProvider(
                    true,
                    new CustomSaplingDefinitionProvider(
                            event.getGenerator().getPackOutput(),
                            event.getLookupProvider(),
                            event.getExistingFileHelper()
                    )
            );
        }
    }
}