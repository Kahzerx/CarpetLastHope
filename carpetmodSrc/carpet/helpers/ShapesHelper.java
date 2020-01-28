package carpet.helpers;

import net.minecraft.util.math.BlockPos;

public class ShapesHelper {

    public static boolean inSquare(BlockPos pos, BlockPos origin, double side_length) {
        boolean x = (origin.getX() - side_length / 2) < pos.getX() && pos.getX() < (origin.getX() + side_length / 2);
        boolean z = (origin.getZ() - side_length / 2) < pos.getZ() && pos.getZ() < (origin.getZ() + side_length / 2);
        return x && z;
    }

    public static boolean inRectangle(BlockPos pos, BlockPos origin, double[] side_length) {
        boolean x = (origin.getX() - side_length[0] / 2) < pos.getX() && pos.getX() < (origin.getX() + side_length[0] / 2);
        boolean z = (origin.getZ() - side_length[1] / 2) < pos.getZ() && pos.getZ() < (origin.getZ() + side_length[1] / 2);
        return x && z;
    }

}