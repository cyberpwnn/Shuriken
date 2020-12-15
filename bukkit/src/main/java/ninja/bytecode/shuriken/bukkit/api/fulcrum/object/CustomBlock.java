package ninja.bytecode.shuriken.bukkit.api.fulcrum.object;

import ninja.bytecode.shuriken.bukkit.bukkit.compatibility.MaterialEnum;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.BlockStand12;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.FulcrumInstance;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.FulcrumRegistry;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.resourcepack.ModelType;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.resourcepack.TextureType;
import ninja.bytecode.shuriken.bukkit.lang.collection.GMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;

import ninja.bytecode.shuriken.bukkit.api.fulcrum.util.BlockCollision;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.util.BlockHardness;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.util.BlockSoundCategory;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.util.DefaultBlockModel;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.util.IBlock;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.util.IResource;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.util.PotentialDropList;

public class CustomBlock extends CustomItem implements IBlock
{
	private String toolType;
	private double hardness;
	private int minimumToolLevel;
	private BlockCollision collisionMode;
	private GMap<BlockSoundCategory, CustomSound> sounds;

	public CustomBlock(String id)
	{
		super(id);
		sounds = new GMap<>();
		textures = new GMap<>();
		setTexture(getID(), "assets/textures/blocks/unknown.png");
		setCollisionMode(BlockCollision.FULL);
		setHardnessLike(MaterialEnum.STONE.bukkitMaterial());
		setEffectiveToolLike(MaterialEnum.STONE.bukkitMaterial());
		setModel(DefaultBlockModel.CUBE_ALL);
		model.rewrite("$id", getID());
	}

	public void setModel(DefaultBlockModel model)
	{
		setModel(model.getPath());
	}

	@Override
	public void setModel(String modelResource)
	{
		setModel(new CustomModel(getID(), FulcrumInstance.instance.getResource(modelResource), ModelType.BLOCK));
	}

	@Override
	public void setModel(IResource modelResource)
	{
		setModel(new CustomModel(getID(), modelResource, ModelType.BLOCK));
	}

	@Override
	public void registerResources(FulcrumRegistry registry)
	{
		super.registerResources(registry);

		for(CustomSound i : sounds.v())
		{
			registry.sound().register(i);
		}
	}

	public void playSound(Block block, BlockSoundCategory category)
	{
		CustomSound sound = getSound(category);

		if(sound != null)
		{
			sound.constructAudible().play(block.getLocation().clone().add(0.5, 0.5, 0.5));
		}
	}

	public CustomSound getSound(BlockSoundCategory category)
	{
		return sounds.get(category);
	}

	public void setSound(BlockSoundCategory category, CustomSound sound)
	{
		sounds.put(category, sound);
	}

	@Override
	public void setHardnessLike(Material m)
	{
		setHardness(BlockHardness.getHardness(m));
	}

	@Override
	public void setEffectiveToolLike(Material m)
	{
		setEffectiveToolType(BlockHardness.getEffectiveTool(m));
	}

	@Override
	public BlockCollision getCollisionMode()
	{
		return collisionMode;
	}

	@Override
	public void setCollisionMode(BlockCollision collisionMode)
	{
		this.collisionMode = collisionMode;
	}

	@Override
	public void setTexture(String name, IResource resource)
	{
		textures.put(name, new CustomTexture(name, resource, TextureType.BLOCKS));
	}

	@Override
	public void setModel(String name, IResource resource)
	{
		model = new CustomModel(name, resource, ModelType.BLOCK);
	}

	@Override
	public String getAllocatedModel()
	{
		return "block/" + model.getModelName();
	}

	@Override
	public CustomBlock block()
	{
		return (CustomBlock) this;
	}

	@Override
	public boolean isBlock()
	{
		return true;
	}

	@Override
	public void removeAt(Block at)
	{
		at.setType(MaterialEnum.AIR.bukkitMaterial());

		for(Entity i : at.getWorld().getNearbyEntities(at.getLocation().clone().add(0.5, 0.5, 0.5), 0.25, 0.25, 0.25))
		{
			if(i instanceof ArmorStand)
			{
				if(((ArmorStand) i).getHelmet().getType().equals(toItemStack(1).getType()))
				{
					BlockStand12.allowDelete.add(i.getEntityId());
					i.remove();
				}
			}
		}
	}

	private ArmorStand constructArmorStand(Block at)
	{
		Location l = at.getLocation().clone().add(0.5, 0.5, 0.5);
		BlockStand12 entity = new BlockStand12(((CraftWorld) l.getWorld()).getHandle(), l.getX(), l.getY(), l.getZ());
		entity.yaw = 0;
		entity.lastYaw = 0;
		entity.pitch = 0;
		entity.lastPitch = 0;
		entity.positionChanged = false;
		ArmorStand a = (ArmorStand) entity.getBukkitEntity();
		a.setAI(false);
		a.setGravity(false);
		a.setBasePlate(false);
		a.setArms(false);
		a.setSmall(true);
		a.setMarker(true);
		a.setInvulnerable(true);
		a.setVisible(false);
		a.setCanPickupItems(false);
		a.setCollidable(false);
		a.setRemoveWhenFarAway(false);
		a.setSilent(true);
		a.setHelmet(toItemStack(1));
		entity.world.addEntity(entity, SpawnReason.CUSTOM);

		return a;
	}

	public ArmorStand replaceAt(Block at)
	{
		return constructArmorStand(at);
	}

	@Override
	public ArmorStand placeAt(Block at)
	{
		ArmorStand as = constructArmorStand(at);

		if(as != null)
		{
			if(getCollisionMode().equals(BlockCollision.FULL))
			{
				at.setType(MaterialEnum.BARRIER.bukkitMaterial());
			}
		}

		return as;
	}

	@Override
	public void getDrops(Block at, Player who, ItemStack tool, PotentialDropList drops)
	{
		drops.add(toItemStack(1));
	}

	@Override
	public String getEffectiveToolType()
	{
		return toolType;
	}

	@Override
	public void setEffectiveToolType(String toolType)
	{
		this.toolType = toolType;
	}

	@Override
	public double getHardness()
	{
		return hardness;
	}

	@Override
	public void setHardness(double hardness)
	{
		this.hardness = hardness;
	}

	@Override
	public int getMinimumToolLevel()
	{
		return minimumToolLevel;
	}

	@Override
	public void setMinimumToolLevel(int minimumToolLevel)
	{
		this.minimumToolLevel = minimumToolLevel;
	}
}
