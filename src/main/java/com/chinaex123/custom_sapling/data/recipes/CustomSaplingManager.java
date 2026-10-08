package com.chinaex123.custom_sapling.data.recipes;

import com.chinaex123.custom_sapling.CustomSapling;
import com.chinaex123.custom_sapling.definition.CustomSaplingDefinition;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CustomSaplingManager implements ResourceManagerReloadListener {

    public static final CustomSaplingManager INSTANCE = new CustomSaplingManager();

    private static final String FOLDER = "saplings";
    private static final Gson GSON = new Gson();

    private final Map<ResourceLocation, CustomSaplingDefinition> definitions = new ConcurrentHashMap<>();

    public CustomSaplingDefinition getDefinition(ResourceLocation id) {
        return definitions.get(id);
    }

    public Map<ResourceLocation, CustomSaplingDefinition> getAll() {
        return Map.copyOf(definitions);
    }

    @Override
    public void onResourceManagerReload(ResourceManager manager) {
        definitions.clear();

        Map<ResourceLocation, List<Resource>> resources = manager.listResourceStacks(
                FOLDER, id -> id.getPath().endsWith(".json")
        );

        for (Map.Entry<ResourceLocation, List<Resource>> entry : resources.entrySet()) {
            ResourceLocation fullId = entry.getKey();
            Resource resource = entry.getValue().getFirst();

            String path = fullId.getPath();
            String name = path.substring(FOLDER.length() + 1, path.length() - 5);
            ResourceLocation saplingId = ResourceLocation.fromNamespaceAndPath(
                    fullId.getNamespace(), name);

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.open(), StandardCharsets.UTF_8))) {
                JsonObject json = GSON.fromJson(reader, JsonObject.class);

                DataResult<CustomSaplingDefinition> result =
                        CustomSaplingDefinition.CODEC.parse(JsonOps.INSTANCE, json);

                if (result.isSuccess()) {
                    CustomSaplingDefinition def = result.getOrThrow();
                    definitions.put(saplingId, def);
                } else {
                    String errorMsg = result.error()
                            .map(DataResult.Error::message)
                            .orElse("[Custom Sapling]未知错误");
                    CustomSapling.LOGGER.error("[Custom Sapling]解析自定义树苗 JSON 失败 [{}]: {}", saplingId, errorMsg);
                }
            } catch (IOException e) {
                CustomSapling.LOGGER.error("[Custom Sapling]读取自定义树苗 JSON 失败 [{}]: {}", saplingId, e.getMessage());
            }
        }
    }
}