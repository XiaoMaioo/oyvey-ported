package today.slopta.dev.features.modules.client;

import today.slopta.dev.Slopta;
import today.slopta.dev.event.impl.Render2DEvent;
import today.slopta.dev.features.modules.Module;

public class HudModule extends Module {
    public HudModule() {
        super("HUD", "Display the information of the client.", Category.CLIENT, true, false, false);
    }

    @Override public void onRender2D(Render2DEvent event) {
        event.getContext().drawTextWithShadow(
                mc.textRenderer,
                Slopta.NAME + " " + Slopta.VERSION,
                2, 2,
                -1
        );
    }
}
