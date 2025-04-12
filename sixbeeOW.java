package dev.journey.PathSeeker.modules.utility;

import dev.journey.PathSeeker.PathSeeker;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.systems.modules.Module;
import net.minecraft.client.MinecraftClient;

public class sixbeeOW extends Module {
    private final MinecraftClient mc = MinecraftClient.getInstance();

    private final Setting<Integer> walkTime = settings.getDefaultGroup().add(new IntSetting.Builder()
            .name("walk-time")
            .description("Milliseconds to walk forward.")
            .defaultValue(1000)
            .min(100)
            .sliderMax(10000)
            .build()
    );

    private final Setting<Integer> stopTime = settings.getDefaultGroup().add(new IntSetting.Builder()
            .name("stop-time")
            .description("Milliseconds to stop walking.")
            .defaultValue(1000)
            .min(100)
            .sliderMax(10000)
            .build()
    );

    private boolean walking = true;
    private Thread loopThread;
    private volatile boolean running = false;

    public sixbeeOW() {
        super(PathSeeker.Utility, "6b6t-OW", "Forces W key held even when game is paused or GUI is open.");
    }

    @Override
    public void onActivate() {
        walking = true;
        running = true;

        loopThread = new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(walking ? walkTime.get() : stopTime.get());
                } catch (InterruptedException ignored) {}

                walking = !walking;
            }
        });

        loopThread.setDaemon(true);
        loopThread.start();
    }

    @Override
    public void onDeactivate() {
        running = false;
        walking = false;
    }

    public boolean shouldWalk() {
        return isActive() && walking;
    }
}
