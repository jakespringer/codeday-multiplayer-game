package com.jakespringer.engine.core;

import com.jakespringer.codeday.testgame.Red;
import com.jakespringer.engine.util.Vec2;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public abstract class Main {

    public static void main(String[] args) throws IOException {
        System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
        try {
            init();
            run();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            destroy();
        }
        System.exit(0);
    }

    public static final int speed = 60;
    public static final int LAYERS = 3;
    public static ArrayList<ArrayList<AbstractSystem>> systems;
    public static GameManager gameManager;
    public static boolean paused = false;

    public static void destroy() {
        Mouse.destroy();
        Keyboard.destroy();
        Display.destroy();
    }

    public static void init() throws LWJGLException {
        systems = new ArrayList();
        for (int i = 0; i < LAYERS; i++) {
            systems.add(new ArrayList());
        }
        gameManager = new GameManager();
        Keyboard.create();
        Mouse.create();

        new Red(new Vec2());
    }

    public static void run() {
        while (!Display.isCloseRequested() && !Keys.isPressed(Keyboard.KEY_ESCAPE)) {
            //Input
            Keys.update();
            MouseInput.update();
            //Logic
            for (ArrayList<AbstractSystem> list : systems) {
                for (int i = 0; i < list.size(); i++) {
                    if (!paused || !list.get(i).pauseable()) {
                        list.get(i).update();
                    }
                }
            }
            //Graphics
            Display.update();
            Display.sync(speed);
        }
    }
}