/*
 * SoftLimit
 * http://kitteh.org
 *
 * Copyright 2012-2014 Matt Baxter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kitteh.softlimit;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.java.JavaPlugin;

public final class SoftLimit extends JavaPlugin implements Listener {
    private String kickMessage;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.kickMessage = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("kickmsg", "&cServer full! &f:(")).replace("&&", "&");
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(PlayerLoginEvent event) {
        if (event.getResult() == Result.KICK_FULL) {
            if (event.getPlayer().hasPermission("softlimit.bypass")) {
                event.allow();
            } else {
                event.setKickMessage(this.kickMessage);
            }
        }
    }
}