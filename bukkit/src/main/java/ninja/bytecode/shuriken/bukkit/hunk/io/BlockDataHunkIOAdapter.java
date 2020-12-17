package ninja.bytecode.shuriken.bukkit.hunk.io;

import ninja.bytecode.shuriken.bukkit.block.B;
import ninja.bytecode.shuriken.collections.hunk.io.PaletteHunkIOAdapter;
import org.bukkit.block.data.BlockData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BlockDataHunkIOAdapter extends PaletteHunkIOAdapter<BlockData> {

    @Override
    public void write(BlockData blockData, DataOutputStream dos) throws IOException {
        dos.writeUTF(blockData.getAsString(true));
    }

    @Override
    public BlockData read(DataInputStream din) throws IOException {
        return B.get(din.readUTF());
    }
}
