package today.slopta.dev.event.impl;

import today.slopta.dev.event.Event;
import today.slopta.dev.features.Module;
import today.slopta.dev.features.settings.Setting;

public class ClientEvent extends Event {
    private Module feature;
    private Setting<?> setting;
    private int stage;

    public ClientEvent(int stage, Module feature) {
        this.stage = stage;
        this.feature = feature;
    }

    public ClientEvent(Setting<?> setting) {
        this.stage = 2;
        this.setting = setting;
    }

    public Module getFeature() {
        return this.feature;
    }

    public Setting<?> getSetting() {
        return this.setting;
    }

    public int getStage() {
        return stage;
    }
}
