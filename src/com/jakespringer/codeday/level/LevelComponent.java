package com.jakespringer.codeday.level;

import com.jakespringer.engine.core.AbstractComponent;
import com.jakespringer.engine.graphics.Graphics2D;
import static com.jakespringer.engine.graphics.Graphics2D.drawSpriteFast;
import com.jakespringer.engine.graphics.data.Texture;
import static com.jakespringer.engine.graphics.loading.SpriteContainer.loadSprite;
import static com.jakespringer.engine.util.Color4d.WHITE;
import com.jakespringer.engine.util.Vec2;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import static org.lwjgl.opengl.GL11.*;

public class LevelComponent extends AbstractComponent {

    public String fileName;
    public Tile[][] tileGrid;
    public int width;
    public int height;
    public int list;
    private static String path = "levels/";
    private static String type = ".png";

    public LevelComponent(String fileName) {
        this.fileName = fileName;
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path + fileName + type));
        } catch (IOException ex) {
            throw new RuntimeException("Level " + fileName + " doesn't exist");
        }
        width = image.getWidth();
        height = image.getHeight();
        tileGrid = new Tile[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tileGrid[x][y] = createTile(x, y, image.getRGB(x, height - y - 1));
            }
        }

        //List
        list = glGenLists(1);
        glNewList(list, GL_COMPILE);
        //Grid
        for (int i = 0; i < width; i++) {
            Graphics2D.drawLine(new Vec2(i, 0), new Vec2(i, height));
        }
        for (int i = 0; i < height; i++) {
            Graphics2D.drawLine(new Vec2(0, i), new Vec2(width, i));
        }
        glEnable(GL_TEXTURE_2D);
        Texture[] texList = {loadSprite("floor"), loadSprite("wall")};
        WHITE.glColor();
        //Draw
        for (Texture tex : texList) {
            tex.bind();
            glBegin(GL_QUADS);

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    Tile t = tileGrid[i][j];
                    if (t.tex != tex) {
                        continue;
                    }
                    drawSpriteFast(tex, t.LL(), t.LR(), t.UR(), t.UL());
                }
            }
            glEnd();
        }
        glEndList();
    }

    public Tile createTile(int x, int y, int color) {
        switch (color) {
            case 0xFF000000: //0 0 0
                return new Tile(x, y, true);
            default:
                return new Tile(x, y, false);
        }
    }

    public Tile tileAt(Vec2 pos) {
        if (pos.x >= 0 && pos.y >= 0 && pos.x < width * Tile.SIZE && pos.y < height * Tile.SIZE) {
            return tileGrid[(int) pos.x / Tile.SIZE][(int) pos.y / Tile.SIZE];
        }
        return null;
    }
}
