package ninja.bytecode.shuriken.bukkit.world;

import org.bukkit.Material;

/**
 * Blast resistance
 *
 * @author cyberpwn
 */
public enum BlastResistance
{
	BARRIER(18000003),
	BEDROCK(18000000),
	COMMAND(18000000),
	ENDER_PORTAL(18000000),
	ENDER_PORTAL_FRAME(18000000),
	ANVIL(6000),
	ENCHANTMENT_TABLE(6000),
	OBSIDIAN(6000),
	ENDER_CHEST(3000),
	WATER(500),
	LAVA(500),
	STATIONARY_WATER(500),
	DRAGON_EGG(45),
	ENDER_STONE(45),
	COAL_BLOCK(30),
	DIAMOND_BLOCK(30),
	EMERALD_BLOCK(30),
	GOLD_BLOCK(30),
	IRON_BLOCK(30),
	REDSTONE_BLOCK(30),
	BRICK(30),
	BRICK_STAIRS(30),
	HARD_CLAY(30),
	STONE(30),
	SMOOTH_BRICK(30),
	SMOOTH_STAIRS(30),
	DOUBLE_STONE_SLAB2(30),
	STONE_SLAB2(30),
	IRON_DOOR(30),
	IRON_DOOR_BLOCK(25),
	IRON_TRAPDOOR(25),
	MOB_SPAWNER(25),
	WEB(20),
	DISPENSER(17),
	DROPPER(17),
	FURNACE(17),
	BURNING_FURNACE(17),
	BEACON(15),
	COAL_ORE(15),
	COCOA(15),
	DIAMOND_ORE(15),
	EMERALD_ORE(15),
	FENCE(15),
	FENCE_GATE(15),
	ACACIA_FENCE_GATE(15),
	BIRCH_FENCE_GATE(15),
	DARK_OAK_FENCE_GATE(15),
	SPRUCE_FENCE_GATE(15),
	JUNGLE_FENCE_GATE(15),
	ACACIA_FENCE(15),
	BIRCH_FENCE(15),
	DARK_OAK_FENCE(15),
	SPRUCE_FENCE(15),
	JUNGLE_FENCE(15),
	ACACIA_WOOD_STAIRS(15),
	BIRCH_WOOD_STAIRS(15),
	DARK_OAK_WOOD_STAIRS(15),
	SPRUCE_WOOD_STAIRS(15),
	JUNGLE_WOOD_STAIRS(15),
	QUARTZ_STAIRS(15),
	COBBLESTONE_STAIRS(15),
	SANDSTONE_STAIRS(15),
	NETHER_BRICK_STAIRS(15),
	WOOD_STAIRS(15),
	RED_SANDSTONE_STAIRS(15),
	ACACIA_DOOR(15),
	BIRCH_DOOR(15),
	DARK_OAK_DOOR(15),
	SPRUCE_DOOR(15),
	JUNGLE_DOOR(15),
	GOLD_ORE(15),
	HOPPER(15),
	IRON_ORE(15),
	LAPIS_BLOCK(15),
	LAPIS_ORE(15),
	QUARTZ_ORE(15),
	REDSTONE_ORE(15),
	TRAP_DOOR(15),
	WOOD(15),
	CHEST(12),
	TRAPPED_CHEST(12),
	WORKBENCH(12),
	CAULDRON(12),
	LOG(10),
	LOG_2(10),
	BOOKSHELF(7),
	BANNER(5),
	STANDING_BANNER(5),
	WALL_BANNER(5),
	JACK_O_LANTERN(5),
	MELON_BLOCK(5),
	SKULL(5),
	PUMPKIN(5),
	SIGN(5),
	SIGN_POST(5),
	WALL_SIGN(5),
	QUARTZ_BLOCK(4),
	NOTE_BLOCK(4),
	RED_SANDSTONE(4),
	SANDSTONE(4),
	WOOL(4),
	MONSTER_EGG(4),
	RAILS(3),
	ACTIVATOR_RAIL(3),
	DETECTOR_RAIL(3),
	POWERED_RAIL(3),
	CLAY(3),
	DIRT(3),
	GRASS(3),
	GRAVEL(3),
	SPONGE(3),
	BREWING_STAND(3),
	STONE_BUTTON(3),
	WOOD_BUTTON(3),
	CAKE_BLOCK(3),
	ICE(3),
	PACKED_ICE(3),
	LEVER(3),
	MYCEL(3),
	PISTON_BASE(3),
	PISTON_EXTENSION(3),
	PISTON_MOVING_PIECE(3),
	PISTON_STICKY_BASE(3),
	GOLD_PLATE(3),
	IRON_PLATE(3),
	STONE_PLATE(3),
	WOOD_PLATE(3),
	SAND(3),
	SOUL_SAND(3),
	CACTUS(2),
	LADDER(2),
	NETHERRACK(2),
	GLASS(1),
	THIN_GLASS(1),
	STAINED_GLASS(1),
	STAINED_GLASS_PANE(1),
	GLOWSTONE(1),
	REDSTONE_LAMP_OFF(1),
	REDSTONE_LAMP_ON(1),
	SEA_LANTERN(1),
	BED_BLOCK(1),
	DAYLIGHT_DETECTOR(1),
	DAYLIGHT_DETECTOR_INVERTED(1),
	HUGE_MUSHROOM_1(1),
	HUGE_MUSHROOM_2(1),
	LEAVES(1),
	LEAVES_2(1),
	SNOW(1);

	private int br;

	private BlastResistance(int br)
	{
		this.br = br;
	}

	public int getBlastResistance()
	{
		return br;
	}

	public static int get(Material m)
	{
		if(!m.isBlock())
		{
			return 0;
		}

		try
		{
			return valueOf(m.name()).getBlastResistance();
		}

		catch(Exception e)
		{

		}

		return 0;
	}
}