package ninja.bytecode.shuriken.bukkit.command;


import ninja.bytecode.shuriken.collections.KList;

/**
 * Represents a pawn command
 *
 * @author cyberpwn
 *
 */
public interface ICommand
{
	public KList<String> getRequiredPermissions();


	public KList<String> handleTab(ShurikenSender sender, String[] args);

	/**
	 * Get the name of this command (node)
	 *
	 * @return the node
	 */
	public String getNode();

	/**
	 * Get all (realized) nodes of this command
	 *
	 * @return the nodes
	 */
	public KList<String> getNodes();

	/**
	 * Get all (every) node in this command
	 *
	 * @return all nodes
	 */
	public KList<String> getAllNodes();

	/**
	 * Add a node to this command
	 *
	 * @param node
	 *            the node
	 */
	public void addNode(String node);

	/**
	 * Handle a command. If this is a subcommand, parameters after the subcommand
	 * will be adapted in args for you
	 *
	 * @param sender
	 *            the volume sender (pre-tagged)
	 * @param args
	 *            the arguments after this command node
	 * @return return true to mark it as handled
	 */
	public boolean handle(ShurikenSender sender, String[] args);
}
