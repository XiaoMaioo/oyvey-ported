package today.slopta.dev.event.impl;

import today.slopta.dev.event.Event;
import net.minecraft.client.gui.DrawContext;

public class Render2DEvent extends Event {
    private final DrawContext context;
    private final float delta;

    public Render2DEvent(DrawContext context, float delta) {
        this.context = context;
        this.delta = delta;
    }

    public DrawContext getContext() {
        return context;
    }

    public float getDelta() {
        return delta;
    }
}
