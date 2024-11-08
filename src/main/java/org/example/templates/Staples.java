package org.example.templates;

import java.util.List;

public class Staples implements Templates{
    @Override
    public List<String> getTemplates() {
        return List.of("(", ")");
    }
}
