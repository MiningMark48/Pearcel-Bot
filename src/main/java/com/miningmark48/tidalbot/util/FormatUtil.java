package com.miningmark48.tidalbot.util;

public class FormatUtil {

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
                return String.format("```%s```", text);
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

    public static String formatURL(String displayText, String url){
        return "[" + displayText + "]" + "(" + url + ")";
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
