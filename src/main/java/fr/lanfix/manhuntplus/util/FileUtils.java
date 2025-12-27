package fr.lanfix.manhuntplus.util;

import java.io.File;
import java.util.Objects;

public class FileUtils {

    public static boolean deleteDirectory(File file) {
        if (file.exists()) {
            for (File child : Objects.requireNonNull(file.listFiles())) {
                boolean childDeleted = child.isDirectory() ? deleteDirectory(child) : child.delete();
                if (!childDeleted) return false;
            }
        }
        return file.delete();
    }

}
