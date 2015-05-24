package com.jakespringer.codeday.jake;

import static com.jakespringer.engine.core.Main.destroy;
import static com.jakespringer.engine.core.Main.init;
import static com.jakespringer.engine.core.Main.run;

import com.jakespringer.codeday.combat.HealthComponent;
import com.jakespringer.codeday.enemy.Enemy;
import com.jakespringer.codeday.level.Level;
import com.jakespringer.codeday.netinterface.NetworkSystem;
import com.jakespringer.codeday.netinterface.message.PlayerJoinMessage;
import com.jakespringer.codeday.networking.Connection;
import com.jakespringer.codeday.player.Player;
import com.jakespringer.codeday.ui.CommandConsole;
import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.graphics.loading.FontContainer;
import com.jakespringer.engine.util.Vec2;

import java.awt.Font;
import java.io.File;
import java.io.IOException;

public abstract class JakeMain {

	public static void main(String[] args) {
        System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
        try {
            init();
            
        	FontContainer.add("Console-Font", "Times New Roman", Font.PLAIN, 12);

            
            new Level("lvl");
            Player p = new Player(new Vec2());
            p.getComponent(HealthComponent.class).health = 6.022e23;
            new Enemy(new Vec2(10, 200));
            new Enemy(Vec2.random(10));
            
            new CommandConsole();
            
            // NETWORKING
        	
        	NetworkSystem ns = new NetworkSystem();
        	Main.gameManager.add(ns);
        	ns.sendMessage(new PlayerJoinMessage(p.id));
            
            // END NETWORKING
            
            run();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            destroy();
        }
        System.exit(0);
    }
}
