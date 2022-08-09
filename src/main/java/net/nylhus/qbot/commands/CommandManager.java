package net.nylhus.qbot.commands;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import javax.swing.plaf.metal.MetalMenuBarUI;
import javax.swing.text.html.Option;
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
                thiccLevel.put(event.getUser().getAsTag(), thicc.get(random.nextInt(10)));
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
        } else if (command.equals("mute")) {
            OptionMapping optionMapping = event.getOption("user");
            Member member  = event.getMember();
            if (member.getRoles().toString().contains("Admin")) {
                Member mutedMember = optionMapping.getAsMember();
                String mutedUser = optionMapping.getAsUser().getAsMention();
                if (!mutedMember.getRoles().toString().contains("Muted")) {
                    Guild guild = mutedMember.getGuild();
                    List<Role> roles = new ArrayList<>(mutedMember.getRoles());
                    List<Role> mutedRoles = guild.getRolesByName("muted", true);
                    roles.addAll(mutedRoles);
                    guild.modifyMemberRoles(mutedMember, roles).queue();
                    event.reply("Muted " + mutedUser).setEphemeral(true).queue();
                    event.getGuildChannel().sendMessage(mutedUser + " was muted by Quandavious Bingleton").queue();
                    event.getGuildChannel().sendMessage("https://tenor.com/view/among-us-twerk-thicc-among-us-twerk-funny-among-us-gif-20511920").queue();
                } else {
                    event.reply("This user is already muted").setEphemeral(true).queue();
                }
            }
        } else if (command.equals("unmute")) {
            OptionMapping optionMapping = event.getOption("user");
            Member member = event.getMember();
            String unmutedMember = optionMapping.getAsUser().getAsMention();
            if (member.getRoles().toString().contains("Admin")) {
                Member unmutedUser = optionMapping.getAsMember();
                if (unmutedUser.getRoles().toString().contains("Muted")) {
                    Guild guild = unmutedUser.getGuild();
                    List<Role> roles = new ArrayList<>(unmutedUser.getRoles());
                    List<Role> mutedRoles = guild.getRolesByName("muted", true);
                    roles.removeAll(mutedRoles);
                    guild.modifyMemberRoles(unmutedUser, roles).queue();
                    event.reply("Unmuted " + unmutedMember).setEphemeral(true).queue();
                } else {
                    event.reply("This user is not muted").setEphemeral(true).queue();
                }
            }
        } else if (command.equals("birthday")) {
            OptionMapping optionMapping = event.getOption("birthday-boy");
            Member member = event.getMember();
            String birthdayBoy = optionMapping.getAsUser().getAsMention();
            if (member.getRoles().toString().contains("Admin")) {
                Member birthdayBoi = optionMapping.getAsMember();
                Guild guild = birthdayBoi.getGuild();
                List<Role> roles = new ArrayList<>(birthdayBoi.getRoles());
                List<Role> bRoles = guild.getRolesByName("Birthday Boy", true);
                roles.addAll(bRoles);
                guild.modifyMemberRoles(birthdayBoi, roles).queue();
                event.reply("Happy Birthday has been wished!").setEphemeral(true).queue();
                event.getGuildChannel().sendMessage("HAPPY BIRTHDAY " + birthdayBoy + " !!!!").queue();
                event.getGuildChannel().sendMessage("https://tenor.com/view/happy-birthday-the-office-dwight-dwight-schrute-birthday-gif-25725679").queue();
                event.getGuildChannel().sendMessage(birthdayBoy + " has been given the Birthday Boy role for his legendary contribution to living another year!!!!!").queue();
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

        OptionData option2 = new OptionData(OptionType.USER, "user", "Mutes a user", false);
        commandData.add(Commands.slash("mute", "Mutes a user").addOptions(option2));
        event.getGuild().updateCommands().addCommands(commandData).queue();

        OptionData option3 = new OptionData(OptionType.USER, "user", "Unmutes a muted user");
        commandData.add(Commands.slash("unmute", "Unnmutes a muted user").addOptions(option3));
        event.getGuild().updateCommands().addCommands(commandData).queue();

        OptionData option4 = new OptionData(OptionType.USER, "birthday-boy", "Wishes a special someone happy birthday");
        commandData.add(Commands.slash("birthday", "Wishes a special someone a happy birthday").addOptions(option4));
        event.getGuild().updateCommands().addCommands(commandData).queue();

    }
}

