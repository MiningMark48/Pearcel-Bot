package com.miningmark48.tidalbot.commands.regular;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.util.UtilFormat;
import com.miningmark48.tidalbot.util.JSON.JSONParse;
import com.miningmark48.tidalbot.util.UtilLogger;
import com.miningmark48.tidalbot.util.UtilMessage;
import com.miningmark48.tidalbot.util.UtilNumWord;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CommandTrivia implements ICommand {

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
            UtilMessage.sendMessage(event, "Missing Argument!\n\n**Usage:** `" + getUsage() + "`").queue();
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

            UtilLogger.log(UtilLogger.LogType.DEBUG, difficulty);

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
                embedBuilder.setTitle("\u2754" + UtilFormat.formatText(UtilFormat.FormatType.UNDERLINE, question));


                answers.forEach(q -> embedBuilder.addField("", UtilFormat.formatText(UtilFormat.FormatType.BOLD, getChoice(answers, q) + ") ") + StringEscapeUtils.unescapeHtml4(q), false));

                embedBuilder.addBlankField(false);
                embedBuilder.addField("Category", obj.get("category").getAsString(), true);
                embedBuilder.addField("Difficulty", StringUtils.capitalize(obj.get("difficulty").getAsString()), true);
                embedBuilder.addField("Time", timeToAnswer + " second" + (timeToAnswer != 1 ? "s" : ""), true);

                UtilMessage.sendMessage(event, embedBuilder.build()).queue(msg -> UtilMessage.sendMessage(event, "You have " + UtilNumWord.convert(timeToAnswer) + " seconds to answer!").queue(msg2 -> {
                    msg2.addReaction("\uD83C\uDDE6").queue();
                    msg2.addReaction("\uD83C\uDDE7").queue();
                    msg2.addReaction("\uD83C\uDDE8").queue();
                    msg2.addReaction("\uD83C\uDDE9").queue();
                    msg2.editMessage("The correct answer was `" + getChoice(answers, obj.get("correct_answer").getAsString()) + ") " + StringEscapeUtils.unescapeHtml4(obj.get("correct_answer").getAsString()) + "`. ").queueAfter(timeToAnswer, TimeUnit.SECONDS, query -> {
                        List<User> users = query.getReactions().get(answers.indexOf(obj.get("correct_answer").getAsString())).retrieveUsers().complete().stream().filter(u -> !u.isBot()).collect(Collectors.toList());
                        ArrayList<User> others = new ArrayList<>();
                        for (int i = 0; i <= 3 && i != answers.indexOf(obj.get("correct_answer").getAsString()); i++) {
                            query.getReactions().get(i).retrieveUsers().forEach(q -> {
                                if (!others.contains(q) && !q.isBot()) others.add(q);
                            });
                        }
                        query.getReactions().clear();
//                        query.clearReactions();
                        try {
                            if (!users.isEmpty()) {
                                users.size();
                                users.forEach(q -> {
                                    if (others.contains(q)) {
                                        users.remove(q);
                                    }
                                });
                                if (users.size() > 0) {
                                    StringBuilder builder = new StringBuilder();
                                    users.forEach(q -> builder.append(q.getName()).append(users.indexOf(q) + 1 != users.size() ? ", " : ""));
                                    UtilMessage.sendMessage(event, "\u2705 " + UtilFormat.formatText(UtilFormat.FormatType.BOLD, "Winner" + (users.size() != 1 ? "s" : "") + ": ") + builder.toString()).queue();
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
                UtilMessage.sendMessage(event, "Error!").queue();
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
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.REGULAR;
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
        UtilMessage.sendMessage(event, "\u274C Nobody got that question correct!").queue();
    }

}
