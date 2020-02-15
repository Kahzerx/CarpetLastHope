package carpet.utils;

import net.minecraft.util.math.BlockPos;

public class VectorHelper {

    public static BlockPos get_prolongation(double gx, double m, float module, BlockPos origin) {
        double term1 = 1 + Math.pow(m, 2);
        double x = module * Math.sqrt(term1) / term1;
        if (gx < 0) x = -x;
        double z = m * x;
        return new BlockPos(x, 0, z);
    }

}