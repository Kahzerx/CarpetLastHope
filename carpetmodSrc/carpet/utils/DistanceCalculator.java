package carpet.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DistanceCalculator
{
    public static final HashMap<EntityPlayer, BlockPos> dist_pos = new HashMap<>();

    public static int[] get_delta_position(BlockPos pos1, BlockPos pos2) {
        return new int[] {MathHelper.abs(pos1.getX()-pos2.getX()), MathHelper.abs(pos1.getY()-pos2.getY()), MathHelper.abs(pos1.getZ()-pos2.getZ())};
    }

    public static enum DISTANCE_ALGORITHM {MANHATTAN, SPHERICAL, CYLINDRICAL};

    public static double get_distance(DISTANCE_ALGORITHM alg, int[] dp) {
        if (alg == DISTANCE_ALGORITHM.MANHATTAN) {
            return dp[0]+dp[1]+dp[2];
        } else if (alg == DISTANCE_ALGORITHM.SPHERICAL) {
            return MathHelper.sqrt(dp[0]*dp[0] + dp[1]*dp[1] + dp[2]*dp[2]);
        } else if (alg == DISTANCE_ALGORITHM.CYLINDRICAL) {
            return MathHelper.sqrt(dp[0]*dp[0] + dp[2]*dp[2]);
        }
        return -1;
    }

    public static List<ITextComponent> print_distance_two_points(BlockPos pos1, BlockPos pos2)
    {
        int[] dp = get_delta_position(pos1, pos2);
        int manhattan = (int)get_distance(DISTANCE_ALGORITHM.MANHATTAN, dp);
        double spherical = get_distance(DISTANCE_ALGORITHM.SPHERICAL, dp);
        double cylindrical = get_distance(DISTANCE_ALGORITHM.CYLINDRICAL, dp);
        List<ITextComponent> res = new ArrayList<>();
        res.add(Messenger.m(null,
                "w Distance between ",Messenger.tp("b",pos1),"w and ",Messenger.tp("b",pos2),"w :"));
        res.add(Messenger.m(null, "w  - Manhattan: ", String.format("wb %d", manhattan)));
        res.add(Messenger.m(null, "w  - Spherical: ", String.format("wb %.2f", spherical)));
        res.add(Messenger.m(null, "w  - Cylindrical: ", String.format("wb %.2f", cylindrical)));
        return res;
    }

    public static void report_distance(EntityPlayer player, BlockPos pos)
    {
        if ( !(dist_pos.containsKey(player) ) )
        {
            dist_pos.put(player, pos);
            Messenger.m(player,"gi Set initial point to: ", Messenger.tp("gi",pos));
            return;
        }
        Messenger.send(player, print_distance_two_points( dist_pos.get(player), pos));
        dist_pos.remove(player);
    }
}