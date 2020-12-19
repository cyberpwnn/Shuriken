/*
 *
 * Copyright (c) 2020 Xenondevs
 *
 * The MIT License (MIT)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package ninja.bytecode.shuriken.bukkit.particle.api;

import ninja.bytecode.shuriken.bukkit.particle.api.utils.ReflectionUtils;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Constants for particles.
 *
 * @author ByteZ
 * @since 10.06.2019
 */
public class ParticleConstants {

    /* ---------------- Classes ---------------- */

    /**
     * Represents the ItemStack class.
     */
    public static final Class ITEM_STACK_CLASS;
    /**
     * Represents the Packet class.
     */
    public static final Class PACKET_CLASS;
    /**
     * Represents the PacketPlayOutWorldParticles class.
     */
    public static final Class PACKET_PLAY_OUT_WORLD_PARTICLES_CLASS;
    /**
     * Represents the EnumParticle enum.
     */
    public static final Class PARTICLE_ENUM;
    /**
     * Represents the Particle class.
     */
    public static final Class PARTICLE_CLASS;
    /**
     * Represents the MiencraftKey class.
     */
    public static final Class MINECRAFT_KEY_CLASS;
    /**
     * Represents the abstract IRegistry class.
     */
    public static final Class REGISTRY_CLASS;
    /**
     * Represents the Block class.
     */
    public static final Class BLOCK_CLASS;
    /**
     * Represents the IBLockData interface.
     */
    public static final Class BLOCK_DATA_INTERFACE;
    /**
     * Represents the Blocks class.
     */
    public static final Class BLOCKS_CLASS;
    /**
     * Represents the EntityPlayer class.
     */
    public static final Class ENTITY_PLAYER_CLASS;
    /**
     * Represents the PlayerConnection class.
     */
    public static final Class PLAYER_CONNECTION_CLASS;
    /**
     * Represents the CraftPlayer class.
     */
    public static final Class CRAFT_PLAYER_CLASS;
    /**
     * Represents the CraftItemStack class.
     */
    public static final Class CRAFT_ITEM_STACK_CLASS;
    /**
     * Represents the ParticleParam class.
     */
    public static final Class PARTICLE_PARAM_CLASS;
    /**
     * Represents the ParticleParamRedstone class.
     */
    public static final Class PARTICLE_PARAM_REDSTONE_CLASS;
    /**
     * Represents the ParticleParamBlock class.
     */
    public static final Class PARTICLE_PARAM_BLOCK_CLASS;
    /**
     * Represents the ParticleParamItem class.
     */
    public static final Class PARTICLE_PARAM_ITEM_CLASS;

    /* ---------------- Methods ---------------- */

    /**
     * Represents the IRegistry#get(MinecraftKey) method.
     */
    public static final Method REGISTRY_GET_METHOD;
    /**
     * Represents the PlayerConnection#sendPacket(); method.
     */
    public static final Method PLAYER_CONNECTION_SEND_PACKET_METHOD;
    /**
     * Represents the CraftPlayer#getHandle(); method.
     */
    public static final Method CRAFT_PLAYER_GET_HANDLE_METHOD;
    /**
     * Represents the Block#getBlockData(); method.
     */
    public static final Method BLOCK_GET_BLOCK_DATA_METHOD;
    /**
     * Represents the CraftItemStack#asNMSCopy(); method.
     */
    public static final Method CRAFT_ITEM_STACK_AS_NMS_COPY_METHOD;

    /* ---------------- Fields ---------------- */

    /**
     * Represents the EntityPlayer#playerConnection field.
     */
    public static final Field ENTITY_PLAYER_PLAYER_CONNECTION_FIELD;

    /* ---------------- Constructor ---------------- */

    /**
     * Represents the PacketPlayOutWorldParticles constructor.
     */
    public static final Constructor PACKET_PLAY_OUT_WORLD_PARTICLES_CONSTRUCTOR;
    /**
     * Represents the MinecraftKey constructor.
     */
    public static final Constructor MINECRAFT_KEY_CONSTRUCTOR;
    /**
     * Represents the ParticleParamRedstone constructor.
     */
    public static final Constructor PARTICLE_PARAM_REDSTONE_CONSTRUCTOR;
    /**
     * Represents the ParticleParamBlock constructor.
     */
    public static final Constructor PARTICLE_PARAM_BLOCK_CONSTRUCTOR;
    /**
     * Represents the ParticleParamItem constructor.
     */
    public static final Constructor PARTICLE_PARAM_ITEM_CONSTRUCTOR;

    /* ---------------- Object constants ---------------- */

    /**
     * Represents the ParticleType Registry.
     */
    public static final Object PARTICLE_TYPE_REGISTRY;

    /* ---------------- INIT ---------------- */

    static {
        int version = ReflectionUtils.MINECRAFT_VERSION;
        // Classes
        ITEM_STACK_CLASS = ReflectionUtils.getNMSClass("ItemStack");
        PACKET_CLASS = ReflectionUtils.getNMSClass("Packet");
        PACKET_PLAY_OUT_WORLD_PARTICLES_CLASS = ReflectionUtils.getNMSClass("PacketPlayOutWorldParticles");
        PARTICLE_ENUM = version < 13 ? ReflectionUtils.getNMSClass("EnumParticle") : null;
        PARTICLE_CLASS = version < 13 ? null : ReflectionUtils.getNMSClass("Particle");
        MINECRAFT_KEY_CLASS = ReflectionUtils.getNMSClass("MinecraftKey");
        REGISTRY_CLASS = version < 13 ? null : ReflectionUtils.getNMSClass("IRegistry");
        BLOCK_CLASS = ReflectionUtils.getNMSClass("Block");
        BLOCK_DATA_INTERFACE = ReflectionUtils.getNMSClass("IBlockData");
        BLOCKS_CLASS = version < 13 ? null : ReflectionUtils.getNMSClass("Blocks");
        ENTITY_PLAYER_CLASS = ReflectionUtils.getNMSClass("EntityPlayer");
        PLAYER_CONNECTION_CLASS = ReflectionUtils.getNMSClass("PlayerConnection");
        CRAFT_PLAYER_CLASS = ReflectionUtils.getCraftBukkitClass("entity.CraftPlayer");
        CRAFT_ITEM_STACK_CLASS = ReflectionUtils.getCraftBukkitClass("inventory.CraftItemStack");
        PARTICLE_PARAM_CLASS = version < 13 ? null : ReflectionUtils.getNMSClass("ParticleParam");
        PARTICLE_PARAM_REDSTONE_CLASS = version < 13 ? null : ReflectionUtils.getNMSClass("ParticleParamRedstone");
        PARTICLE_PARAM_BLOCK_CLASS = version < 13 ? null : ReflectionUtils.getNMSClass("ParticleParamBlock");
        PARTICLE_PARAM_ITEM_CLASS = version < 13 ? null : ReflectionUtils.getNMSClass("ParticleParamItem");
        // Methods
        REGISTRY_GET_METHOD = version < 13 ? null : ReflectionUtils.getMethodOrNull(REGISTRY_CLASS, "get", MINECRAFT_KEY_CLASS);
        PLAYER_CONNECTION_SEND_PACKET_METHOD = ReflectionUtils.getMethodOrNull(PLAYER_CONNECTION_CLASS, "sendPacket", PACKET_CLASS);
        CRAFT_PLAYER_GET_HANDLE_METHOD = ReflectionUtils.getMethodOrNull(CRAFT_PLAYER_CLASS, "getHandle");
        BLOCK_GET_BLOCK_DATA_METHOD = ReflectionUtils.getMethodOrNull(BLOCK_CLASS, "getBlockData");
        CRAFT_ITEM_STACK_AS_NMS_COPY_METHOD = ReflectionUtils.getMethodOrNull(CRAFT_ITEM_STACK_CLASS, "asNMSCopy", ItemStack.class);
        //Fields
        ENTITY_PLAYER_PLAYER_CONNECTION_FIELD = ReflectionUtils.getFieldOrNull(ENTITY_PLAYER_CLASS, "playerConnection", false);
        //Constructors
        if (version < 13)
            PACKET_PLAY_OUT_WORLD_PARTICLES_CONSTRUCTOR = ReflectionUtils.getConstructorOrNull(PACKET_PLAY_OUT_WORLD_PARTICLES_CLASS, PARTICLE_ENUM, boolean.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class, int[].class);
        else if (version < 15)
            PACKET_PLAY_OUT_WORLD_PARTICLES_CONSTRUCTOR = ReflectionUtils.getConstructorOrNull(PACKET_PLAY_OUT_WORLD_PARTICLES_CLASS, PARTICLE_PARAM_CLASS, boolean.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class);
        else
            PACKET_PLAY_OUT_WORLD_PARTICLES_CONSTRUCTOR = ReflectionUtils.getConstructorOrNull(PACKET_PLAY_OUT_WORLD_PARTICLES_CLASS, PARTICLE_PARAM_CLASS, boolean.class, double.class, double.class, double.class, float.class, float.class, float.class, float.class, int.class);
        MINECRAFT_KEY_CONSTRUCTOR = ReflectionUtils.getConstructorOrNull(MINECRAFT_KEY_CLASS, String.class);
        PARTICLE_PARAM_REDSTONE_CONSTRUCTOR = version < 13 ? null : ReflectionUtils.getConstructorOrNull(PARTICLE_PARAM_REDSTONE_CLASS, float.class, float.class, float.class, float.class);
        PARTICLE_PARAM_BLOCK_CONSTRUCTOR = version < 13 ? null : ReflectionUtils.getConstructorOrNull(PARTICLE_PARAM_BLOCK_CLASS, PARTICLE_CLASS, BLOCK_DATA_INTERFACE);
        PARTICLE_PARAM_ITEM_CONSTRUCTOR = version < 13 ? null : ReflectionUtils.getConstructorOrNull(PARTICLE_PARAM_ITEM_CLASS, PARTICLE_CLASS, ITEM_STACK_CLASS);
        // Constants
        PARTICLE_TYPE_REGISTRY = version < 13 ? null : ReflectionUtils.readField(ReflectionUtils.getFieldOrNull(REGISTRY_CLASS, "PARTICLE_TYPE", false), null);
    }

}
