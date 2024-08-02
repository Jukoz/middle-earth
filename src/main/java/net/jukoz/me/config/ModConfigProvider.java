package net.jukoz.me.config;

import com.mojang.datafixers.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class ModConfigProvider implements SimpleConfig.DefaultConfig {
    public static final String COMMENT_PREFIX = "# ";
    public static final String SECTION_PREFIX = "# ";
    public static final String VALUE_PREFIX = "    ";

    private String configContents = "";

    public List<Pair> getConfigsList() {
        return configsList;
    }

    private final List<Pair> configsList = new ArrayList<>();

    public void addLineJump(){
        configContents += "\n";
    }

    public void addComment(String comment){
        configContents += COMMENT_PREFIX + comment + " \n";
    }

    public void addSection(String sectionName) {
        configContents += SECTION_PREFIX + sectionName + " \n";
    }

    public void addDescription(String comment){
        configContents += VALUE_PREFIX + COMMENT_PREFIX + comment + " \n";
    }
    public void addKeyValuePair(Pair<String, ?> keyValuePair, String acceptedValues) {
        configsList.add(keyValuePair);
        addDescription("Accept values: " + acceptedValues + " | Default: " + keyValuePair.getSecond());
        configContents += VALUE_PREFIX + keyValuePair.getFirst() + "=" + keyValuePair.getSecond() + "\n";
    }

    @Override
    public String get(String namespace) {
        return configContents;
    }
}