package carpet.commands;

import carpet.CarpetSettings;
import carpet.utils.DistanceCalculator;
import carpet.utils.Messenger;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CommandCenter extends CommandCarpetBase {

    @Override
    public String getName() { return "center"; }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/center <X1> <Z1> <X2> <Z2>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!command_enabled("commandCenter", sender)) return;
        BlockPos center = new BlockPos(0, 0, 0);
        int[] dp = new int[] {0, 0, 0};
        boolean valid = false;
        if (args.length != 4) throw new WrongUsageException("/center <X1> <Z1> <X2> <Z2>");
        try
        {
            BlockPos p1 = new BlockPos(Integer.parseInt(args[0]), 0, Integer.parseInt(args[1]));
            BlockPos p2 = new BlockPos(Integer.parseInt(args[2]), 0, Integer.parseInt(args[3]));
            center = new BlockPos((p1.getX() + p2.getX()) / 2, 0, (p1.getZ() + p2.getZ()) / 2);
            dp = DistanceCalculator.get_delta_position(p1, p2);
            valid = true;
        }
        catch (NumberFormatException nfe)
        {
            throw new WrongUsageException("<X1> <Z1> <X2> <Z2> must be a number");
        }
        //if (valid) sender.sendMessage(new TextComponentString("Center is in " + center.getX() + "/" + center.getZ() + ". Size is " + dp[0] + "/" + dp[2]));

        if (valid) msg(sender, Messenger.m(null, "w ", "c [Center is in " + center.getX() + "/" + center.getZ() + ". Size is " + dp[0] + "/" + dp[2] + "]",
                "^w Click to create the waypoint with this data.",
                "?/waypoint add <NAME> " + center.getX() + " 0 " + center.getZ() + " " + getCommandSenderAsPlayer(sender).world.provider.getDimensionType().getName().replace("the_", "") + " " + dp[0] + "/" + dp[2]));
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos)
    {
        if (!CarpetSettings.commandCenter)
        {
            return Collections.<String>emptyList();
        }
        if (args.length > 0 && args.length <= 2)
        {
            return getTabCompletionCoordinateXZ(args, 0, pos);
        }
        if (args.length > 2 && args.length <= 4)
        {
            return getTabCompletionCoordinateXZ(args, 2, pos);
        }
        return Collections.<String>emptyList();
    }
}