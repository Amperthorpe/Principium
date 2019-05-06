package com.alleluid.principium;

import com.google.common.base.Predicates;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class JavaUtils {
    @Nullable
    public static RayTraceResult getEntityIntercept(World world, EntityPlayer player,
                                            Vec3d start, Vec3d look, Vec3d end,
                                             @Nullable RayTraceResult mop)
    {
        double distance = end.distanceTo(start);

        if (mop != null)
        {
            distance = mop.hitVec.distanceTo(start);
        }

        Vec3d direction = new Vec3d(
                look.x * distance,
                look.y * distance,
                look.z * distance);

        end = start.add(direction);

        Vec3d hitPosition = null;
        List<Entity> list = world.getEntitiesInAABBexcluding(player,
                player.getEntityBoundingBox()
                        .expand(direction.x, direction.y, direction.z)
                        .grow(1.0, 1.0, 1.0), Predicates.and(EntitySelectors.NOT_SPECTATING,
                        Entity::canBeCollidedWith));

        double distanceToEntity = distance;
        Entity pointedEntity = null;
        for (Entity entity : list)
        {
            double border = entity.getCollisionBorderSize();
            AxisAlignedBB bounds = entity.getEntityBoundingBox().expand(border, border, border);
            RayTraceResult intercept = bounds.calculateIntercept(start, end);

            if (bounds.contains(start))
            {
                if (distanceToEntity >= 0.0D)
                {
                    pointedEntity = entity;
                    hitPosition = intercept == null ? start : intercept.hitVec;
                    distanceToEntity = 0.0D;
                }
            }
            else if (intercept != null)
            {
                double interceptDistance = start.distanceTo(intercept.hitVec);

                if (interceptDistance < distanceToEntity || distanceToEntity == 0.0D)
                {
                    if (entity == player.getRidingEntity() && !player.canRiderInteract())
                    {
                        if (distanceToEntity == 0.0D)
                        {
                            pointedEntity = entity;
                            hitPosition = intercept.hitVec;
                        }
                    }
                    else
                    {
                        pointedEntity = entity;
                        hitPosition = intercept.hitVec;
                        distanceToEntity = interceptDistance;
                    }
                }
            }
        }

        if (pointedEntity != null)
        {
            if (distanceToEntity < distance)
            {
                if (start.distanceTo(hitPosition) < distance)
                {
                    return new RayTraceResult(pointedEntity, hitPosition);
                }
            }
        }

        return mop;
}
}
