package com.bleak.graphics.framework;

import java.awt.image.BufferedImage;

public class SpriteSheet {

    private final BufferedImage image;
    private final int width = 8;
    private final int height = 8;

    public SpriteSheet(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage grabImage(int col, int row, int width, int height) {
        return image.getSubimage(
            (col * this.width) - this.width,
            (row * this.height) - this.height,
            width,
            height
        );
    }

}
