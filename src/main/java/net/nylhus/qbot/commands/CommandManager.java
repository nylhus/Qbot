package net.nylhus.qbot.commands;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CommandManager extends ListenerAdapter {

    List<String> thicc = new ArrayList<>();
    HashMap thiccmap = new HashMap();

    public CommandManager() {
        thicc.add("null");
        thicc.add("horrible");
        thicc.add("sub-par");
        thicc.add("average");
        thicc.add("good");
        thicc.add("great");
        thicc.add("incredible");
        thicc.add("amazing");
        thicc.add("godly");
        thicc.add("infinitely thicc");
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("thiccness")) {
            String userMention = event.getUser().getAsMention();
            Random random = new Random();
            thiccmap.put(event.getUser().getAsTag(), random.nextInt(9));
            final String thiccLevel = String.valueOf(thiccmap.get(event.getUser().getAsTag()));
            event.reply("The thiccness level of " + userMention + " is " + thiccLevel).queue();
        }
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("thiccness", "Gets the thiccness level of users"));
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}

