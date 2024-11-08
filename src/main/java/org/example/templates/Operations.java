package org.example.templates;

import java.util.List;

public class Operations implements Templates{
    @Override
    public List<String> getTemplates() {
        return List.of("^", "/", "*", "+", "-");
    }
}
