package com.spikespaz.radialmenu.gui;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

public class RenderHelper {
    public static void drawPoly(double[][] points, int color) {
        float alpha = (float) (color >> 24 & 255) / 255.0F;
        float red = (float) (color >> 16 & 255) / 255.0F;
        float green = (float) (color >> 8 & 255) / 255.0F;
        float blue = (float) (color & 255) / 255.0F;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(red, green, blue, alpha);

        bufferBuilder.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION);

        for (double[] point : points)
            bufferBuilder.pos(point[0], point[1], 0.0D).endVertex();

        tessellator.draw();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawCircle(double cx, double cy, double radius, int resolution, int color) {
        double[][] vertices = new double[resolution][2];

        for (int i = 0; i < vertices.length; i++) {
            double a = Math.PI * 2 * i / vertices.length;

            vertices[i] = new double[] {
                    cx + Math.sin(a) * radius,
                    cy + Math.cos(a) * radius
            };
        }

        drawPoly(vertices, color);
    }

    public static int colorToInt(Color color) {
        return (color.getAlpha() << 24) & 255 | (color.getRed() << 16) & 255 | (color.getGreen() << 8) & 255 | color.getBlue() & 255;
    }
}
