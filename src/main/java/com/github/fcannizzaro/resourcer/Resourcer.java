package com.github.fcannizzaro.resourcer;

import com.github.fcannizzaro.resourcer.annotations.*;
import com.github.fcannizzaro.resourcer.util.FileUtils;
import com.github.fcannizzaro.resourcer.util.RequestUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;

/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2016 Francesco Cannizzaro
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions
 * of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

@SuppressWarnings("unused")
public class Resourcer {

    // directory paths
    public static String PATH_BASE;
    public static String IMAGE_DIR = "images" + File.separator;
    public static String AUDIO_DIR = "audio" + File.separator;
    public static String JSON_DIR = "json" + File.separator;
    public static String VALUES_DIR = "values" + File.separator;

    // values types
    public final static String STRING = "string";
    public final static String INTEGER = "integer";
    public final static String COLOR = "color";
    public final static String LONG = "long";
    public final static String DOUBLE = "double";
    public final static String FLOAT = "float";
    public final static String BOOLEAN = "boolean";
    public final static String[] elements = {STRING, INTEGER, FLOAT, DOUBLE, LONG, BOOLEAN, COLOR};

    // values
    public static HashMap<String, HashMap<String, Object>> values = new HashMap<String, HashMap<String, Object>>();

    /**
     * Setup initial Resourcer properties
     */
    public static class Builder {

        public Builder(String pathBase) {
            PATH_BASE = pathBase + File.separator;
        }

        public Builder setImageDir(String path) {
            IMAGE_DIR = path + File.separator;
            return this;
        }

        public Builder setAudioDir(String path) {
            AUDIO_DIR = path + File.separator;
            return this;
        }

        public Builder setJsonDir(String path) {
            JSON_DIR = path + File.separator;
            return this;
        }

        public Builder setValuesDir(String path) {
            VALUES_DIR = path + File.separator;
            return this;
        }

        public void build() {
            findValues();
        }
    }

    /**
     * Bind a class
     */
    public static void bind(Object annotated) {

        for (Field field : annotated.getClass().getDeclaredFields())
            try {
                switchAnnotation(field, annotated);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
    }

    /**
     * Check the field annotation and execute its method
     *
     * @throws IllegalAccessException
     */
    private static void switchAnnotation(Field field, Object annotated) throws IllegalAccessException {

        // File
        com.github.fcannizzaro.resourcer.annotations.Image image = field.getAnnotation(com.github.fcannizzaro.resourcer.annotations.Image.class);
        Audio audio = field.getAnnotation(Audio.class);
        Json json = field.getAnnotation(Json.class);

        // Values
        StringRes stringRes = field.getAnnotation(StringRes.class);
        IntegerRes integerRes = field.getAnnotation(IntegerRes.class);
        FloatRes floatRes = field.getAnnotation(FloatRes.class);
        DoubleRes doubleRes = field.getAnnotation(DoubleRes.class);
        LongRes longRes = field.getAnnotation(LongRes.class);
        BooleanRes booleanRes = field.getAnnotation(BooleanRes.class);
        ColorRes colorRes = field.getAnnotation(ColorRes.class);

        // grant access
        field.setAccessible(true);

        if (image != null) {
            annotateImage(field, image, annotated);
            return;
        }

        if (audio != null) {
            annotateAudio(field, audio, annotated);
            return;
        }

        if (json != null) {
            annotateJson(field, json, annotated);
            return;
        }

        if (stringRes != null && is(field, String.class)) {
            annotate(field, STRING, stringRes.value(), annotated);
            return;
        }

        if (integerRes != null) {
            annotate(field, INTEGER, integerRes.value(), annotated);
            return;
        }

        if (floatRes != null) {
            annotate(field, FLOAT, floatRes.value(), annotated);
            return;
        }

        if (doubleRes != null) {
            annotate(field, DOUBLE, doubleRes.value(), annotated);
            return;
        }

        if (longRes != null) {
            annotate(field, BOOLEAN, longRes.value(), annotated);
            return;
        }

        if (booleanRes != null) {
            annotate(field, BOOLEAN, booleanRes.value(), annotated);
            return;
        }

        if (colorRes != null && is(field, Color.class))
            annotate(field, COLOR, colorRes.value(), annotated);

    }

    /* Annotation parsing */

    /**
     * Create and BufferedImage and update the field
     *
     * @throws IllegalAccessException
     */
    private static void annotateImage(Field field, com.github.fcannizzaro.resourcer.annotations.Image annotation, Object annotated) throws IllegalAccessException {
        try {

            String path = annotation.value();
            boolean pathAbsolute = FileUtils.isPathAbsolute(path);
            String res = getPath(path, IMAGE_DIR);

            if (path.startsWith("http"))
                field.set(annotated, ImageIO.read(new URL(path)));
            else
                field.set(annotated, ImageIO.read(pathAbsolute ? new File(path) : new File(res)));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create and AudioInputStream and update the field
     *
     * @throws IllegalAccessException
     */
    private static void annotateAudio(Field field, Audio annotation, Object annotated) throws IllegalAccessException {
        try {
            String res = getPath(annotation.value(), AUDIO_DIR);
            field.set(annotated, AudioSystem.getAudioInputStream(new File(res)));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a JSONObject / JSONArray from a file
     *
     * @throws IllegalAccessException
     */
    private static void annotateJson(Field field, Json annotation, Object annotated) throws IllegalAccessException {

        String url = annotation.url(),
                key = annotation.key(),
                path = annotation.value(),
                content;

        boolean isUrl = !url.equals("null") || path.startsWith("http"),
                pathAbsolute = FileUtils.isPathAbsolute(path);

        if (hasDotNotation(annotation) && !isUrl || isUrl && !key.equals("null")) {
            annotateJsonDotNotation(field, annotation, annotated);
            return;
        }

        if (!isUrl)
            content = FileUtils.read(pathAbsolute ? path + ".json" : getPath(path + ".json", JSON_DIR));
        else
            content = RequestUtils.get(url.equals("null") ? path : url);

        if (content == null)
            return;

        if (is(field, JSONArray.class))
            field.set(annotated, new JSONArray(content));

        else if (is(field, JSONObject.class))
            field.set(annotated, new JSONObject(content));

    }

    /**
     * Read key / object from a json file
     *
     * @throws IllegalAccessException
     */
    private static void annotateJsonDotNotation(Field field, Json annotation, Object annotated) throws IllegalAccessException {

        String[] values = annotation.value().split("\\.");
        boolean pathAbsolute = FileUtils.isPathAbsolute(values[0]);

        String dotNotation = annotation.key(),
                url = annotation.url(),
                content;

        if (!dotNotation.equals("null") && !url.equals("null")) {
            content = RequestUtils.get(url);
            String[] parts = dotNotation.split("\\.");
            values = new String[parts.length + 1];
            System.arraycopy(parts, 0, values, 1, parts.length);
        } else
            content = FileUtils.read((pathAbsolute ? values[0] + ".json" : getPath(values[0] + ".json", JSON_DIR)));


        if (content == null)
            return;

        // create json object
        JSONObject object = new JSONObject(content);

        for (int i = 1; i < values.length; i++) {

            String key = values[i];

            if (i == values.length - 1) {
                if (object.has(key))
                    field.set(annotated, object.get(key));
            } else
                object = object.getJSONObject(key);

        }

    }

    /**
     * Read a parsed value
     *
     * @throws IllegalAccessException
     */
    private static void annotate(Field field, String type, String name, Object annotated) throws IllegalAccessException {

        if (name.equals("null"))
            name = field.getName();

        field.set(annotated, values.get(type).get(name));

    }

    /* Util methods */

    /**
     * Parse values xml files
     */
    private static void findValues() {

        values = new HashMap<String, HashMap<String, Object>>();

        File dir = new File(PATH_BASE + VALUES_DIR);

        if (!dir.isDirectory())
            return;

        File[] files = dir.listFiles();

        if (files == null)
            return;

        for (File file : files)

            // only *.xml files
            if (file.getName().matches(".*\\.xml$")) {

                Document doc = FileUtils.readXML(file.getAbsolutePath());

                if (doc == null)
                    return;

                Element ele = doc.getDocumentElement();

                for (String element : elements) {

                    NodeList list = ele.getElementsByTagName(element);

                    if (values.get(element) == null)
                        values.put(element, new HashMap<String, Object>());

                    for (int j = 0; j < list.getLength(); j++) {

                        Element node = (Element) list.item(j);
                        String value = node.getFirstChild().getNodeValue();
                        Object valueDefined = value;

                        if (element.equals(INTEGER))
                            valueDefined = Integer.valueOf(value);

                        if (element.equals(DOUBLE))
                            valueDefined = Double.valueOf(value);

                        else if (element.equals(FLOAT))
                            valueDefined = Float.valueOf(value);

                        else if (element.equals(BOOLEAN))
                            valueDefined = Boolean.valueOf(value);

                        else if (element.equals(LONG))
                            valueDefined = Long.valueOf(value);

                        else if (element.equals(COLOR))
                            valueDefined = Color.decode(value);

                        values.get(node.getNodeName()).put(node.getAttribute("name"), valueDefined);

                    }

                }
            }
    }

    /**
     * Build path string
     */
    private static String getPath(String name, String dir) {
        return PATH_BASE + dir + name;
    }

    /**
     * Check if field class is the expected class
     */
    private static boolean is(Field field, Class expected) {
        return field.getType().equals(expected);
    }

    /**
     * Check if annotation value has a dot notation
     */
    private static boolean hasDotNotation(Json annotation) {
        return annotation.value().matches("^(?!http?)[\\w:\\\\/]+(\\.\\w+)+$");
    }

}
