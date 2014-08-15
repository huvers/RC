package com.hbar.finance.neuralnetwork.reservoir.tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Lukas Lozovski on 8/13/14.
 */
public class RCProperties {
    public static void getSystemProperties(String propertiesPath) throws IOException {
        Properties runtimeProperties = new Properties();
        runtimeProperties = System.getProperties();

        FileInputStream in = new FileInputStream(propertiesPath);
        runtimeProperties.load(in);
        in.close();
        System.setProperties(runtimeProperties);
    }
}
