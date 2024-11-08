package org.example.templates;

import java.util.List;

public class Numbers implements Templates{
    @Override
    public List<String> getTemplates() {
        return List.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    }
}
