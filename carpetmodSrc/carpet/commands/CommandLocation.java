package carpet.commands;

import carpet.CarpetSettings;
import carpet.utils.Waypoint;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DimensionType;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CommandLocation extends CommandCarpetBase
{
    @Override
    public String getName()
    {
        return "location";
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "location [player]";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (!command_enabled("commandLocation", sender)) return;
        EntityPlayerMP player = getCommandSenderAsPlayer(sender);
        if (args.length > 0)
        {
            final EntityPlayerMP name = server.getPlayerList().getPlayerByUsername(args[0]);
            if (name != null)
            {
                player = name;
            }
        }
        int player_x = player.getPosition().getX();
        int player_y = player.getPosition().getY();
        int player_z = player.getPosition().getZ();

        sender.sendMessage(new TextComponentString(getDimensionWithColor(player) + " " + TextFormatting.WHITE + player.getName() + " está en " + player_x + " " + player_y + " " + player_z + " " + calculaPosicion(player)));
        PotionEffect potioneffect = new PotionEffect(Potion.getPotionFromResourceLocation("glowing"), 100, 0, false, false);
        player.addPotionEffect(potioneffect);
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        if (!CarpetSettings.commandLocation)
        {
            return Collections.<String>emptyList();
        }
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.emptyList();
    }

    public String getDimensionWithColor(EntityPlayerMP player)
    {
        DimensionType dimension = player.world.provider.getDimensionType();
        String message = player.world.provider.getDimensionType().getName();
        if (dimension == DimensionType.OVERWORLD)
            message = TextFormatting.GREEN + "[Overworld]";
        else if (dimension == DimensionType.NETHER)
            message = TextFormatting.RED + "[Nether]";
        else if (dimension == DimensionType.THE_END)
            message = TextFormatting.DARK_PURPLE + "[End]";

        return message;
    }

    public String calculaPosicion(EntityPlayerMP player){
        Map<String, Waypoint> wps = player.getServerWorld().waypoints;
        final String[] message = {""};
        wps.forEach((name, wp) -> {
            if (wp.inRange(Waypoint.RANGE_ALGORITHM.AUTO, player)) message[0] = TextFormatting.GRAY + "[" + wp.name.split(":")[0] + "]";
        });
        return message[0];
    }
}