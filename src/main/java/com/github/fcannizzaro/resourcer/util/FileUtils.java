package com.github.fcannizzaro.resourcer.util;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Francesco Cannizzaro
 */
public class FileUtils {

    /**
     * Read file and return content as String
     */
    public static String read(String path) {

        String line;
        StringBuilder content = new StringBuilder();

        try {

            BufferedReader br = new BufferedReader(new FileReader(path));

            while ((line = br.readLine()) != null)
                content.append(line);

            return content.toString();

        } catch (IOException e) {
            return "{}";
        }

    }

    /**
     * Read xml from file
     */
    public static Document readXML(String path) {

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new File(path));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    /**
     * Check if pah is absolute or not
     */
    public static boolean isPathAbsolute(String path) {
        return path.matches("^[A-Za-z]:?(/|\\\\).*");
    }

}
