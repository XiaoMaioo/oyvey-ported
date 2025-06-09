package today.slopta.dev.manager;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import today.slopta.dev.event.impl.Render2DEvent;
import today.slopta.dev.event.impl.Render3DEvent;
import today.slopta.dev.features.Module;
import today.slopta.dev.features.modules.client.ClickGui;
import today.slopta.dev.features.modules.client.HudModule;
import today.slopta.dev.features.modules.combat.Criticals;
import today.slopta.dev.features.modules.misc.MCF;
import today.slopta.dev.features.modules.movement.ReverseStep;
import today.slopta.dev.features.modules.movement.Step;
import today.slopta.dev.features.modules.player.FastPlace;
import today.slopta.dev.features.modules.player.NoFall;
import today.slopta.dev.features.modules.player.Velocity;
import today.slopta.dev.features.modules.render.BlockHighlight;
import today.slopta.dev.util.traits.Jsonable;
import today.slopta.dev.util.traits.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager implements Jsonable, Util {
    public List<today.slopta.dev.features.modules.Module> modules = new ArrayList<>();
    public List<today.slopta.dev.features.modules.Module> sortedModules = new ArrayList<>();
    public List<String> sortedModulesABC = new ArrayList<>();

    public void init() {
        modules.add(new HudModule());
        modules.add(new ClickGui());
        modules.add(new Criticals());
        modules.add(new MCF());
        modules.add(new Step());
        modules.add(new ReverseStep());
        modules.add(new FastPlace());
        modules.add(new Velocity());
        modules.add(new BlockHighlight());
        modules.add(new NoFall());
    }

    public today.slopta.dev.features.modules.Module getModuleByName(String name) {
        for (today.slopta.dev.features.modules.Module module : this.modules) {
            if (!module.getName().equalsIgnoreCase(name)) continue;
            return module;
        }
        return null;
    }

    public <T extends today.slopta.dev.features.modules.Module> T getModuleByClass(Class<T> clazz) {
        for (today.slopta.dev.features.modules.Module module : this.modules) {
            if (!clazz.isInstance(module)) continue;
            return (T) module;
        }
        return null;
    }

    public void enableModule(Class<today.slopta.dev.features.modules.Module> clazz) {
        today.slopta.dev.features.modules.Module module = this.getModuleByClass(clazz);
        if (module != null) {
            module.enable();
        }
    }

    public void disableModule(Class<today.slopta.dev.features.modules.Module> clazz) {
        today.slopta.dev.features.modules.Module module = this.getModuleByClass(clazz);
        if (module != null) {
            module.disable();
        }
    }

    public void enableModule(String name) {
        today.slopta.dev.features.modules.Module module = this.getModuleByName(name);
        if (module != null) {
            module.enable();
        }
    }

    public void disableModule(String name) {
        today.slopta.dev.features.modules.Module module = this.getModuleByName(name);
        if (module != null) {
            module.disable();
        }
    }

    public boolean isModuleEnabled(String name) {
        today.slopta.dev.features.modules.Module module = this.getModuleByName(name);
        return module != null && module.isOn();
    }

    public boolean isModuleEnabled(Class<today.slopta.dev.features.modules.Module> clazz) {
        today.slopta.dev.features.modules.Module module = this.getModuleByClass(clazz);
        return module != null && module.isOn();
    }

    public today.slopta.dev.features.modules.Module getModuleByDisplayName(String displayName) {
        for (today.slopta.dev.features.modules.Module module : this.modules) {
            if (!module.getDisplayName().equalsIgnoreCase(displayName)) continue;
            return module;
        }
        return null;
    }

    public ArrayList<today.slopta.dev.features.modules.Module> getEnabledModules() {
        ArrayList<today.slopta.dev.features.modules.Module> enabledModules = new ArrayList<>();
        for (today.slopta.dev.features.modules.Module module : this.modules) {
            if (!module.isEnabled()) continue;
            enabledModules.add(module);
        }
        return enabledModules;
    }

    public ArrayList<String> getEnabledModulesName() {
        ArrayList<String> enabledModules = new ArrayList<>();
        for (today.slopta.dev.features.modules.Module module : this.modules) {
            if (!module.isEnabled() || !module.isDrawn()) continue;
            enabledModules.add(module.getFullArrayString());
        }
        return enabledModules;
    }

    public ArrayList<today.slopta.dev.features.modules.Module> getModulesByCategory(today.slopta.dev.features.modules.Module.Category category) {
        ArrayList<today.slopta.dev.features.modules.Module> modulesCategory = new ArrayList<today.slopta.dev.features.modules.Module>();
        this.modules.forEach(module -> {
            if (module.getCategory() == category) {
                modulesCategory.add(module);
            }
        });
        return modulesCategory;
    }

    public List<today.slopta.dev.features.modules.Module.Category> getCategories() {
        return Arrays.asList(today.slopta.dev.features.modules.Module.Category.values());
    }

    public void onLoad() {
        this.modules.stream().filter(today.slopta.dev.features.modules.Module::listening).forEach(EVENT_BUS::register);
        this.modules.forEach(today.slopta.dev.features.modules.Module::onLoad);
    }

    public void onUpdate() {
        this.modules.stream().filter(Module::isEnabled).forEach(today.slopta.dev.features.modules.Module::onUpdate);
    }

    public void onTick() {
        this.modules.stream().filter(Module::isEnabled).forEach(today.slopta.dev.features.modules.Module::onTick);
    }

    public void onRender2D(Render2DEvent event) {
        this.modules.stream().filter(Module::isEnabled).forEach(module -> module.onRender2D(event));
    }

    public void onRender3D(Render3DEvent event) {
        this.modules.stream().filter(Module::isEnabled).forEach(module -> module.onRender3D(event));
    }

    public void sortModules(boolean reverse) {
        this.sortedModules = this.getEnabledModules().stream().filter(today.slopta.dev.features.modules.Module::isDrawn)
                .sorted(Comparator.comparing(module -> mc.textRenderer.getWidth(module.getFullArrayString()) * (reverse ? -1 : 1)))
                .collect(Collectors.toList());
    }

    public void sortModulesABC() {
        this.sortedModulesABC = new ArrayList<>(this.getEnabledModulesName());
        this.sortedModulesABC.sort(String.CASE_INSENSITIVE_ORDER);
    }

    public void onUnload() {
        this.modules.forEach(EVENT_BUS::unregister);
        this.modules.forEach(today.slopta.dev.features.modules.Module::onUnload);
    }

    public void onUnloadPost() {
        for (today.slopta.dev.features.modules.Module module : this.modules) {
            module.enabled.setValue(false);
        }
    }

    public void onKeyPressed(int eventKey) {
        if (eventKey <= 0) return;
        this.modules.forEach(module -> {
            if (module.getBind().getKey() == eventKey) {
                module.toggle();
            }
        });
    }

    @Override public JsonElement toJson() {
        JsonObject object = new JsonObject();
        for (today.slopta.dev.features.modules.Module module : modules) {
            object.add(module.getName(), module.toJson());
        }
        return object;
    }

    @Override public void fromJson(JsonElement element) {
        for (today.slopta.dev.features.modules.Module module : modules) {
            module.fromJson(element.getAsJsonObject().get(module.getName()));
        }
    }

    @Override public String getFileName() {
        return "modules.json";
    }
}
