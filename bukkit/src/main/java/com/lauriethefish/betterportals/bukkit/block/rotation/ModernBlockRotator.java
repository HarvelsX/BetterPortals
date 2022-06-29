package com.lauriethefish.betterportals.bukkit.block.rotation;

import com.lauriethefish.betterportals.bukkit.math.Matrix;
import org.bukkit.Axis;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.*;
import org.jetbrains.annotations.NotNull;

public class ModernBlockRotator implements IBlockRotator    {
    @Override
    public @NotNull BlockData rotateByMatrix(@NotNull Matrix matrix, @NotNull BlockData data) {
        // Blocks that can be rotated to any block face implement Rotatable
        if(data instanceof Rotatable) {
            Rotatable rotatable = (Rotatable) data.clone();

            BlockFace currentFace = rotatable.getRotation();
            BlockFace rotatedFace = BlockFaceUtil.rotateFace(currentFace, matrix);
            if(rotatedFace != null) {
                try {
                    rotatable.setRotation(rotatedFace);
                } catch (IllegalArgumentException ignored) {
                    rotatable.setRotation(currentFace);
                }

                return rotatable;
            }
        }

        // Some blocks implement Directional instead, Bukkit API, why...
        if(data instanceof Directional) {
            Directional directional = (Directional) data.clone();

            BlockFace currentFace = directional.getFacing();
            BlockFace rotatedFace = BlockFaceUtil.rotateFace(currentFace, matrix);

            if(rotatedFace != null && directional.getFaces().contains(rotatedFace)) {
                directional.setFacing(rotatedFace);
                return directional;
            }
        }

        // Some blocks implement orientable instead
        if(data instanceof Orientable) {
            Orientable orientable = (Orientable) data.clone();

            Axis currentAxis = orientable.getAxis();
            Axis rotatedAxis = AxisUtil.rotateAxis(currentAxis, matrix);
            // Only rotate if this block can actually be rotated to the new axis (most can't be rotated in all directions)
            if(rotatedAxis != null && orientable.getAxes().contains(rotatedAxis)) {
                orientable.setAxis(rotatedAxis);
                return orientable;
            }
        }

        if(data instanceof Rail) {
            Rail rail = (Rail) data.clone();

            Rail.Shape currentShape = rail.getShape();
            Rail.Shape rotatedShape = RailUtil.rotateRailShape(currentShape, matrix);
            if(rotatedShape != null && rail.getShapes().contains(rotatedShape)) {
                rail.setShape(rotatedShape);
                return rail;
            }
        }

        return data;
    }
}
