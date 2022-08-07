package net.nylhus.qbot;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.nylhus.qbot.commands.CommandManager;


import javax.security.auth.login.LoginException;

public class Qbot {
    private final Dotenv config;
    private final ShardManager shardManager;

    public Qbot() throws LoginException {
        config = Dotenv.configure().load();
        String token = config.get("TOKEN");
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.streaming("Goat Simulator 5", "https://youtu.be/dQw4w9WgXcQ"));
        shardManager = builder.build();
        shardManager.addEventListener(new CommandManager());
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public Dotenv getConfig() {
        return config;
    }

    public static void main(String[] args) {
        try {
            Qbot bot = new Qbot();
        } catch (LoginException e) {
            System.out.println("Error: Invalid Token");
        }
    }

}
