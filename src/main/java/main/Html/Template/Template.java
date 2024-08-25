package main.java.main.Html.Template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SimpleTemplateEngine is a basic template engine that supports variable substitution
 * and simple looping constructs.
 */
public class Template {

    // Regular expression to match variables in the format ${variableName}
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\$\\{([^}]+)}");

    // Regular expression to match <each> blocks for looping
    private static final Pattern EACH_PATTERN = Pattern.compile("<each\\s+items=\"\\[([^}]+)]\">\\s*(.*?)\\s*</each>", Pattern.DOTALL);

    /**
     * Renders a template file with the given context.
     *
     * @param templatePath The path to the template file
     * @param context A map containing variable names and their values
     * @return The rendered template as a string
     */
    public String render(String templatePath, Map<String, Object> context) {
        String template = readFile(templatePath);
        String result = replaceVariables(template, context);
        result = processEachBlocks(result, context);
        return result;
    }

    /**
     * Reads the contents of a file into a string.
     *
     * @param filePath The path to the file to read
     * @return The contents of the file as a string
     * @throws IllegalArgumentException If there's an error reading the file
     */
    private String readFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading file: " + filePath, e);
        }
        return content.toString();
    }

    /**
     * Replaces variables in the template with their values from the context.
     *
     * @param template The template string
     * @param context A map containing variable names and their values
     * @return The template with variables replaced
     */
    private String replaceVariables(String template, Map<String, Object> context) {
        Matcher matcher = VARIABLE_PATTERN.matcher(template);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String variable = matcher.group(1);
            Object value = context.get(variable);
            matcher.appendReplacement(sb, value != null ? Matcher.quoteReplacement(value.toString()) : "");
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * Processes <each> blocks in the template, repeating the content for each item in the specified list.
     *
     * @param template The template string
     * @param context A map containing variable names and their values
     * @return The template with <each> blocks processed
     */
    private String processEachBlocks(String template, Map<String, Object> context) {
        Matcher matcher = EACH_PATTERN.matcher(template);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String itemsVar = matcher.group(1);
            String blockContent = matcher.group(2);
            List<String> items = new ArrayList<>();

            System.out.println("Items: " + itemsVar);
            System.out.println("Next");

            for (String item : itemsVar.replace("[", "").replace("]", "").split(",")) {
                items.add(item.trim());
            }

            StringBuilder replacement = new StringBuilder();
            if (!items.isEmpty()) {
                for (String item : items) {
                    replacement.append(processEachBlockItems(blockContent, item));
                }
            }
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement.toString()));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private String processEachBlockItems(String content, String itemValue) {
        String beginning = content.substring(0, content.indexOf(">") + 1);
        String middle = content.substring(content.indexOf(">") + 1, content.lastIndexOf("<"));
        String ending = content.substring(content.lastIndexOf("<"));
        if (!middle.isEmpty()) {
            return beginning + middle.replace("$[item]", itemValue) + ending;
        }
        return beginning + itemValue + ending;
    }

    /**
     * Main method to demonstrate the usage of SimpleTemplateEngine.
     */
    public static void main(String[] args) {
        Template engine = new Template();

        // Create a context with sample data
        Map<String, Object> context = new HashMap<>();
        context.put("title", "My Page");
        context.put("name", "John");
        List<String> items = new ArrayList<>();
        items.add("Apple");
        items.add("Banana");
        items.add("Cherry");
        context.put("fruits", items);

        List<String> randomStuff = new ArrayList<>();
        randomStuff.add("Shit");
        randomStuff.add("Hello");
        randomStuff.add("Minecraft");
        context.put("randomStuff", randomStuff);

        // Render the template and print the result
        String result = engine.render("C:\\Users\\Jade\\Documents\\Projects\\void\\src\\main\\java\\main\\Html\\Template\\template.html", context);
        System.out.println(result);
    }
}
