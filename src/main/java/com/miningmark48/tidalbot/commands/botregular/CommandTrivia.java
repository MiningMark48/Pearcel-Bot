package com.miningmark48.tidalbot.commands.botregular;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import com.miningmark48.tidalbot.util.FormatUtil;
import com.miningmark48.tidalbot.util.JSON.JSONParse;
import com.miningmark48.tidalbot.util.LoggerUtil;
import com.miningmark48.tidalbot.util.MessageUtil;
import com.miningmark48.tidalbot.util.NumWordUtil;
import com.vdurmont.emoji.EmojiParser;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageReaction;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.requests.restaction.pagination.ReactionPaginationAction;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;
import sun.rmi.runtime.Log;

import java.awt.*;
import java.text.Normalizer;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CommandTrivia implements ICommand, ICommandInfo {

    private int timeToAnswer;
    private String difficulty;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        JsonObject js;

        if (args.length <= 0){
            MessageUtil.sendMessage(event, "Missing Argument!\n\n**Usage:** `" + getUsage() + "`").queue();
        } else {

            String difficulty;
            if (args[0].equalsIgnoreCase("random")) {
                Random rand = new Random();
                int num = rand.nextInt(2);
                switch (num) {
                    default:
                    case 0:
                        difficulty = "easy";
                        break;
                    case 1:
                        difficulty = "medium";
                        break;
                    case 2:
                        difficulty = "hard";
                        break;
                }
            } else {
                difficulty = args[0];
            }

            LoggerUtil.log(LoggerUtil.LogType.DEBUG, difficulty);

            switch (difficulty) {
                default:
                case "easy":
                    timeToAnswer = 15;
                    difficulty = "easy";
                    break;
                case "medium":
                    timeToAnswer = 20;
                    difficulty = "medium";
                    break;
                case "hard":
                    timeToAnswer = 25;
                    difficulty = "hard";
                    break;
            }

            if (args.length >= 2) {
                timeToAnswer = Integer.parseInt(args[1]);
                if (timeToAnswer > 60) timeToAnswer = 60;
            }

            try {
                js = JSONParse.JSONParse("https://opentdb.com/api.php?amount=1&type=multiple&difficulty=" + difficulty);
                JsonArray array = js.get("results").getAsJsonArray();
                JsonObject obj = array.get(0).getAsJsonObject();
                ArrayList<String> answers = new ArrayList<>();

                answers.add(obj.get("correct_answer").getAsString());
                obj.get("incorrect_answers").getAsJsonArray().forEach(q -> answers.add(q.getAsString()));
                Collections.shuffle(answers);

                String question = StringEscapeUtils.unescapeHtml4(obj.get("question").getAsString());

                String iconURL = "http://tw.miningmark48.xyz/img/icons/success.png";
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setColor(Color.blue);
                embedBuilder.setThumbnail(iconURL);
                embedBuilder.setFooter("Trivia!", iconURL);
                embedBuilder.setTitle("\u2754" + FormatUtil.formatText(FormatUtil.FormatType.UNDERLINE, question));


                answers.forEach(q -> embedBuilder.addField("", FormatUtil.formatText(FormatUtil.FormatType.BOLD, getChoice(answers, q) + ") ") + StringEscapeUtils.unescapeHtml4(q), false));

                embedBuilder.addBlankField(false);
                embedBuilder.addField("Category", obj.get("category").getAsString(), true);
                embedBuilder.addField("Difficulty", StringUtils.capitalize(obj.get("difficulty").getAsString()), true);
                embedBuilder.addField("Time", timeToAnswer + " second" + (timeToAnswer != 1 ? "s" : ""), true);

                MessageUtil.sendMessage(event, embedBuilder.build()).queue(msg -> MessageUtil.sendMessage(event, "You have " + NumWordUtil.convert(timeToAnswer) + " seconds to answer!").queue(msg2 -> {
                    msg2.addReaction("\uD83C\uDDE6").queue();
                    msg2.addReaction("\uD83C\uDDE7").queue();
                    msg2.addReaction("\uD83C\uDDE8").queue();
                    msg2.addReaction("\uD83C\uDDE9").queue();
                    msg2.editMessage("The correct answer was `" + getChoice(answers, obj.get("correct_answer").getAsString()) + ") " + StringEscapeUtils.unescapeHtml4(obj.get("correct_answer").getAsString()) + "`. ").queueAfter(timeToAnswer, TimeUnit.SECONDS, query -> {
                        List<User> users = query.getReactions().get(answers.indexOf(obj.get("correct_answer").getAsString())).getUsers().complete().stream().filter(u -> !u.isBot()).collect(Collectors.toList());
//                        ArrayList<User> others = new ArrayList<>();
//                        for (int i = 0; i <= 3 && i != answers.indexOf(obj.get("correct_answer").getAsString()); i++) {
//                            query.getReactions().get(i).getUsers().forEach(q -> {
//                                if (!others.contains(q) && !q.isBot()) others.add(q);
//                            });
//                        }
                        query.clearReactions().queue();
                        try {
                            if (users != null) {
//                                if (users.size() > 0) {
//                                    users.forEach(q -> {
//                                        if (others.contains(q)) {
//                                            users.remove(q);
//                                        }
//                                    });
//                                }
                                if (users.size() > 0) {
                                    StringBuilder builder = new StringBuilder();
                                    users.forEach(q -> builder.append(q.getName()).append(users.indexOf(q) + 1 != users.size() ? ", " : ""));
                                    MessageUtil.sendMessage(event, "\u2705 " + FormatUtil.formatText(FormatUtil.FormatType.BOLD, "Winner" + (users.size() != 1 ? "s" : "") + ": ") + builder.toString()).queue();
                                } else {
                                    nobodyMessage(event);
                                }
                            } else {
                                nobodyMessage(event);
                            }
                        } catch (ConcurrentModificationException e) {
                            nobodyMessage(event);
                        }

                    });
                }));

            } catch (NullPointerException e) {
                MessageUtil.sendMessage(event, "Error!").queue();
                e.printStackTrace();
            }

        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "trivia";
    }

    @Override
    public String getDesc() {
        return "Answer a trivia question!";
    }

    @Override
    public String getUsage() {
        return "trivia <easy|medium|hard|random> [int:seconds]";
    }

    @Override
    public CommandType getType() {
        return CommandType.NORMAL;
    }

    private static String getChoice(ArrayList<String> answers, String currentAnswer) {
        switch (answers.indexOf(currentAnswer)) {
            default:
            case 0:
                return "A";
            case 1:
                return "B";
            case 2:
                return "C";
            case 3:
                return "D";
        }
    }

    private static void nobodyMessage(MessageReceivedEvent event) {
        MessageUtil.sendMessage(event, "\u274C Nobody got that question correct!").queue();
    }

}
