package org.homemade.elo.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.stereotype.Component;

@Component
public class FileReaderUtil {
    private ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    public BufferedReader asReader(String fileName) {
        return new BufferedReader(new InputStreamReader(this.classLoader.getResourceAsStream(fileName)));
    }

}
