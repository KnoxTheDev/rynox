package dev.knoxy.rynox.client.module;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager {

    private static ModuleManager INSTANCE;

    private final List<Module> modules = new ArrayList<>();

    private ModuleManager() {
        // Add modules here
        addModules();
    }

    public static ModuleManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ModuleManager();
        }
        return INSTANCE;
    }

    public void addModules() {
        modules.add(new Module("AutoCrystal", "Automatically places and breaks crystals.", Category.COMBAT));
        modules.add(new TestModule());
    }

    public List<Module> getModules() {
        return modules;
    }

    public List<Module> getModulesInCategory(Category category) {
        return modules.stream()
                .filter(module -> module.getCategory() == category)
                .collect(Collectors.toList());
    }
}
