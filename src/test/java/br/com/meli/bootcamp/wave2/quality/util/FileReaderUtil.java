package br.com.meli.bootcamp.wave2.quality.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReaderUtil {
    public static String readResourcesString(String filePath) throws IOException {
        var fileStream = FileReaderUtil.class.getClassLoader().getResourceAsStream(filePath);
        return new String(fileStream.readAllBytes(), StandardCharsets.UTF_8);

    }
}
