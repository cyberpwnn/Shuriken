package ninja.bytecode.shuriken.bukkit.scm;

import java.util.UUID;

import ninja.bytecode.shuriken.collections.KList;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.util.Vector;



public interface Multiblock
{
	public IVolume getVolume();

	public IMappedVolume getMappedVolume();

	public void destroy();

	public void save();

	public UUID getUUID();

	public void reconstruct();

	public void onTick();

	public void onAsyncTick();

	public void onConstructed(Player player, Block block, Event event);

	public void onConstructed(Block block, Event event);

	public void onInteract(Player player, Block block, Vector vector, Action action, Event event);

	public void onPlacedAgainst(Player player, Block block, Vector vector, Vector placedOn, BlockFace against, Event event);

	public void onDestroyed(Player player, Block block, Vector vector, Event event);

	public void onDestroyed(KList<Block> changed);
}
