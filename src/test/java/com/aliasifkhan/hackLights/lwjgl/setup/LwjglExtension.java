package com.aliasifkhan.hackLights.lwjgl.setup;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.junit.jupiter.api.Assertions;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LwjglExtension extends BaseLwjglExtension {
    private static final CountDownLatch lock = new CountDownLatch(1);

    private static Lwjgl3Application application;
    private static ApplicationAdapterWrapper wrapper;

    public static Lwjgl3Application getApplication() {
        return LwjglExtension.application;
    }

    public static ApplicationAdapterWrapper getWrapper() {
        return LwjglExtension.wrapper;
    }

    @Override
    void setup() {
        System.out.println("Setup");
        wrapper = new ApplicationAdapterWrapper(new ApplicationAdapter() {
            @Override
            public void create() {
                System.out.println("lock down created");
                lock.countDown();
            }
        });

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setBackBufferConfig(8, 8, 8, 8, 16, 2, 0);

        new Thread(() -> application = new Lwjgl3Application(wrapper, config)).start();

        try {
            Assertions.assertTrue(lock.await(5, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        System.out.println("Close");
        if (application != null)
            application.exit();
    }
}
