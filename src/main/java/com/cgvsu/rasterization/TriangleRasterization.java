package com.cgvsu.rasterization;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class TriangleRasterization {
    private static Comparator<Vector2f> COMP_BY_Y = new Comparator<Vector2f>() {
        @Override
        public int compare(Vector2f v1, Vector2f v2) {
            int cmp;
            return (cmp = (Float.compare(v1.y(), v2.y()))) != 0 ? cmp : Float.compare(v1.x(), v2.x());
        }
    };

    public static void drawTriangle(
            final GraphicsContext graphicsContext,
            final int x1, final int y1,
            final int x2, final int y2,
            final int x3, final int y3,
            final Color c1, final Color c2, final Color c3) {
        drawTriangle(graphicsContext,
                new Vector2f(x1, y1), new Vector2f(x2, y2), new Vector2f(x3, y3),
                c1, c2, c3);
    }

    public static void drawTriangle(
            final GraphicsContext graphicsContext,
            final Triangle tr) {
        drawTriangle(graphicsContext, tr.v1(), tr.v2(), tr.v3(), tr.c1(), tr.c2(), tr.c3());
    }

    public static void drawTriangle(
            final GraphicsContext gContext,
            final Vector2f v1, final Vector2f v2, final Vector2f v3,
            final Color c1, final Color c2, final Color c3) {

        Vector2f[] vArr = new Vector2f[]{v1, v2, v3};
        Arrays.sort(vArr, COMP_BY_Y);

        int x1 = (int) vArr[0].x();
        int y1 = (int) vArr[0].y();
        int x2 = (int) vArr[1].x();
        int y2 = (int) vArr[1].y();
        int x3 = (int) vArr[2].x();
        int y3 = (int) vArr[2].y();

        drawTopTriangle(gContext.getPixelWriter(), x1, y1, x2, y2, x3, y3, v1, c1, v2, c2, v3, c3);
        drawBottomTriangle(gContext.getPixelWriter(), x1, y1, x2, y2, x3, y3, v1, c1, v2, c2, v3, c3);
    }

    private static void drawTopTriangle(
            final PixelWriter pw,
            final int x1, final int y1,
            final int x2, final int y2,
            final int x3, final int y3,
            final Vector2f v1, final Color c1,
            final Vector2f v2, final Color c2,
            final Vector2f v3, final Color c3) {

        int x2x1 = x2 - x1;
        int x3x1 = x3 - x1;
        int y2y1 = y2 - y1;
        int y3y1 = y3 - y1;

        for (int y = y1; y < y2; y++) {
            int xL = x1 + (y - y1) * x2x1 / y2y1;
            int xR = x1 + (y - y1) * x3x1 / y3y1;

            if (xL > xR) {
                int tmp = xL;
                xL = xR;
                xR = tmp;
            }

            for (int x = xL; x <= xR; x++) {
                pw.setColor(x, y, interpolateColor(x, y, v1, c1, v2, c2, v3, c3));
            }
        }
    }

    private static void drawBottomTriangle(
            final PixelWriter pw,
            final int x1, final int y1,
            final int x2, final int y2,
            final int x3, final int y3,
            final Vector2f v1, final Color c1,
            final Vector2f v2, final Color c2,
            final Vector2f v3, final Color c3) {
        int x3x2 = x3 - x2;
        int x3x1 = x3 - x1;
        int y3y2 = y3 - y2;
        int y3y1 = y3 - y1;

        for (int y = y2; y < y3; y++) {
            int xL = x2 + (y - y2) * x3x2 / y3y2;
            int xR = x1 + (y - y1) * x3x1 / y3y1;

            if (xL > xR) {
                int tmp = xL;
                xL = xR;
                xR = tmp;
            }

            for (int x = xL; x <= xR; x++) {
                pw.setColor(x, y, interpolateColor(x, y, v1, c1, v2, c2, v3, c3));
            }
        }
    }

    public static Color interpolateColor(
            final int x, final int y,
            final Vector2f v1, final Color c1,
            final Vector2f v2, final Color c2,
            final Vector2f v3, final Color c3) {
        final float x1x3 = v1.x() - v3.x();
        final float x3x2 = v3.x() - v2.x();
        final float y1y3 = v1.y() - v3.y();
        final float y3y1 = v3.y() - v1.y();
        final float y2y3 = v2.y() - v3.y();

        float xx3 = x - v3.x();
        float yy3 = y - v3.y();


        float alpha = (y2y3 * xx3 + x3x2 * yy3) / (y2y3 * x1x3 + x3x2 * y1y3);
        float betta = (y3y1 * xx3 + x1x3 * yy3) / (y2y3 * x1x3 + x3x2 * y1y3);
        float gamma = 1f - betta - alpha;

        // На этом моменте барицентрики можно передавать для наложения текстур, вместо интерполяции цвета

        float red = (float) (alpha * c1.getRed() + betta * c2.getRed() + gamma * c3.getRed());
        float green = (float) (alpha * c1.getGreen() + betta * c2.getGreen() + gamma * c3.getGreen());
        float blue = (float) (alpha * c1.getBlue() + betta * c2.getBlue() + gamma * c3.getBlue());

        red = Math.max(0f, Math.min(1f, red));
        green = Math.max(0f, Math.min(1f, green));
        blue = Math.max(0f, Math.min(1f, blue));
        return Color.color(red, green, blue);
    }


}
