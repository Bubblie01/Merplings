package io.github.Bubblie.Merplings.mixin;


import java.util.Optional;
import java.util.function.Supplier;

import com.mojang.serialization.Codec;

import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import io.github.Bubblie.Merplings.mixin.SensorTypeAccessor;
import io.github.Bubblie.Merplings.mixin.MemoryModuleTypeAccessor;

/**
 * This class provides utilities to create {@link SensorType} and {@link MemoryModuleType}.
 *
 * <p>A sensor is used to trigger brain tasks.
 * Each task can check the output value of each presented sensor in a given entity.
 *
 * <p>A memory module is used to store brain task results and intermediary data.
 * Each task can check the stored value of each presented memory module in a given entity.
 */
public final class EntityBrainHelpers {
    private EntityBrainHelpers() {
    }

    /**
     * Creates and registers a {@link SensorType}.
     *
     * @param id the id of this {@link SensorType}.
     * @param sensorFactory a supplier to provide a {@link Sensor}.
     * @return a new {@link SensorType}.
     */
    public static <U extends Sensor<?>> SensorType<U> registerSensorType(Identifier id, Supplier<U> sensorFactory) {
        return Registry.register(Registry.SENSOR_TYPE, id, SensorTypeAccessor.init(sensorFactory));
    }

    /**
     * Creates and registers a {@link MemoryModuleType}.
     *
     * @param id the id of this {@link MemoryModuleType}.
     * @return a new {@link MemoryModuleType}.
     */
    public static <U> MemoryModuleType<U> registerMemoryModuleType(Identifier id) {
        return Registry.register(Registry.MEMORY_MODULE_TYPE, id, MemoryModuleTypeAccessor.init(Optional.empty()));
    }

    /**
     * Creates and registers a {@link MemoryModuleType}.
     *
     * @param id the id of this {@link MemoryModuleType}.
     * @param codec {@link Codec} used to serialize and deserialize actual memory modules.
     * @return a new {@link MemoryModuleType}.
     */
    public static <U> MemoryModuleType<U> registerMemoryModuleType(Identifier id, Codec<U> codec) {
        return Registry.register(Registry.MEMORY_MODULE_TYPE, id, MemoryModuleTypeAccessor.init(Optional.of(codec)));
    }


}
