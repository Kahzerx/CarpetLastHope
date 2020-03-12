package carpet.commands;

import carpet.CarpetSettings;
import carpet.utils.VectorHelper;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CommandGateway extends CommandCarpetBase
{

    @Override
    public String getName() { return "gateway"; }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/gateway <X> <Z>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!command_enabled("commandGateway", sender)) return;
        if (args.length != 2) throw new WrongUsageException("/gateway <X> <Z>");
        try
        {
            double m = Double.parseDouble(args[1]) / Double.parseDouble(args[0]);
            BlockPos min = VectorHelper.get_prolongation(Double.parseDouble(args[0]), m, 768, new BlockPos(0, 0, 0));
            BlockPos max = VectorHelper.get_prolongation(Double.parseDouble(args[0]), m, 1280, new BlockPos(0, 0, 0));
            sender.sendMessage(new TextComponentString("You have to build between " + min.getX() + "/" + min.getZ() + " and " + max.getX() + "/" + max.getZ()));
            ChunkPos tmpChunk = new ChunkPos(0, 0);
            String chunks = "";
            for (int i = 768; i <= 1280; i++) {
                BlockPos tmp = VectorHelper.get_prolongation(Double.parseDouble(args[0]), m, i, new BlockPos(0, 0, 0));
                ChunkPos tmpCh = new ChunkPos(tmp.getX() / 16, tmp.getZ() / 16);
                if (tmpChunk.x != tmpCh.x || tmpChunk.z != tmpCh.z) {
                    tmpChunk = tmpCh;
                    chunks += chunks.length() > 0 ? ", [" + tmpChunk.x + ", " + tmpChunk.z +"]" : "[" + tmpChunk.x + ", " + tmpChunk.z +"]";
                }
            }
            sender.sendMessage(new TextComponentString("Chunk Array: " + chunks));

        }
        catch (NumberFormatException nfe)
        {
            throw new WrongUsageException("<X> <Z> must be a number");
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos)
    {
        if (!CarpetSettings.commandGateway)
        {
            return Collections.<String>emptyList();
        }
        if (args.length > 0 && args.length <= 2)
        {
            return getTabCompletionCoordinateXZ(args, 0, pos);
        }
        return Collections.<String>emptyList();
    }
}