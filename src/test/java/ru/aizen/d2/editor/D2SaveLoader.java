package ru.aizen.d2.editor;

import java.io.IOException;
import java.io.InputStream;

public final class D2SaveLoader {

    public static byte[] load(String resourceName) {
        try (InputStream is = D2SaveLoader.class.getResourceAsStream(resourceName)) {
            if (is == null) {
                throw new RuntimeException("Resource " + resourceName + "not found");
            }

            return is.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
