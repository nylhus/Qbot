package net.nylhus.qbot.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CommandManager extends ListenerAdapter {

    List<String> thicc = new ArrayList<>();
    HashMap thiccmap = new HashMap();
    HashMap thiccLevel = new HashMap();

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
        User user = event.getUser();
        String command = event.getName();
        if (command.equals("thiccness")) {
            if (thiccmap.get(event.getUser().getAsTag()) == null) {
                String userMention = event.getUser().getAsMention();
                Random random = new Random();
                thiccLevel.put(event.getUser().getAsTag(), thicc.get(random.nextInt(9)));
                event.reply("The thiccness level of " + userMention + " is " + thiccLevel.get(event.getUser().getAsTag())).queue();
                thiccmap.put(event.getUser().getAsTag(), false);
            } else {
                event.reply("The thiccness level of " + event.getUser().getAsMention() + " is " + thiccLevel.get(event.getUser().getAsTag())).queue();
            }
        } else if (command.equals("admin")) {
            OptionMapping optionMapping = event.getOption("message");
            Member member = event.getMember();
            if (member.getRoles().toString().contains("Admin")) {
                String message = optionMapping.getAsString();
                event.getGuildChannel().sendMessage(message).queue();
                event.reply("Sent!").setEphemeral(true).queue();
            } else {
                event.reply("You don't have the permissions for this!").queue();
            }
        }
    }


    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("thiccness", "Gets the thiccness level of users"));

        OptionData option1 = new OptionData(OptionType.STRING, "message", "Makes the bot says a message", true);
        commandData.add(Commands.slash("admin", "Makes the bot say a message").addOptions(option1));
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}

