package com.miningmark48.pearcelbot.util;

import com.google.common.collect.Lists;
import com.sun.istack.internal.Nullable;

import java.util.*;

public class Tools {

    //TODO: Include formatting

    public static final String SEPERATOR = System.lineSeparator();

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue (Map<K, V> map, boolean invert) {

        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, Comparator.comparing(Map.Entry<K,V>::getValue));

        if (invert)
            list = Lists.reverse(list);

        final Map<K, V> result = new LinkedHashMap<>();
        for (final Map.Entry<K, V> entry : list)
            result.put(entry.getKey(), entry.getValue());
        return result;
    }

    public static String formatText(FormatType type, String text){
        switch (type){
            case BOLD:
                return String.format("**%s**", text);
            case ITALIC:
                return String.format("*%s*", text);
            case UNDERLINE:
                return String.format("__%s__", text);
            case BOLD_ITALIC:
                return String.format("***%s***", text);
            case BOLD_UNDERLINE:
                return String.format("__**%s**__", text);
            case ITALIC_UNDERLINE:
                return String.format("__*%s*__", text);
            case BOLD_ITALIC_UNDERLINE:
                return String.format("__***%s***__", text);
            case STRIKEOUT:
                return String.format("~~%s~~", text);
            case CODE_BLOCK:
                return String.format("`%s`", text);
            case MULTILINE_CODE_BLOCK:
                return String.format("```%s%s```", text);
        }

        return text;
    }

    public static String formatText(FormatType type, String text, String code_type){
        switch (type){
            case MULTILINE_CODE_BLOCK:
                return String.format("```%s%s```", code_type, text);
        }

        return text;
    }

    public enum FormatType{
        BOLD,
        ITALIC,
        UNDERLINE,
        BOLD_ITALIC,
        BOLD_UNDERLINE,
        ITALIC_UNDERLINE,
        BOLD_ITALIC_UNDERLINE,
        STRIKEOUT,
        CODE_BLOCK,
        MULTILINE_CODE_BLOCK
    }

}
