package dev.knoxy.rynox.module;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    private static ModuleManager INSTANCE;
    private final List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        // Combat
        modules.add(new Module("KillAura", Module.Category.COMBAT));
        modules.add(new Module("CrystalAura", Module.Category.COMBAT));

        // Render
        modules.add(new Module("Fullbright", Module.Category.RENDER));
        modules.add(new Module("ESP", Module.Category.RENDER));

        // Movement
        modules.add(new Module("Flight", Module.Category.MOVEMENT));
        modules.add(new Module("NoFall", Module.Category.MOVEMENT));
    }

    public static ModuleManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ModuleManager();
        }
        return INSTANCE;
    }

    public List<Module> getModules() {
        return modules;
    }

    public List<Module> getModulesInCategory(Module.Category category) {
        List<Module> categoryModules = new ArrayList<>();
        for (Module module : modules) {
            if (module.getCategory() == category) {
                categoryModules.add(module);
            }
        }
        return categoryModules;
    }
}
