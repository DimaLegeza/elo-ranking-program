package org.homemade.elo.util;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class FileReaderUtil {
    private ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    public BufferedReader asReader(String fileName) {
        return new BufferedReader(new InputStreamReader(this.classLoader.getResourceAsStream(fileName)));
    }


}
