# Resourcer
Java Resources Annotations

[![](https://jitpack.io/v/fcannizzaro/resourcer.svg)](https://jitpack.io/#fcannizzaro/resourcer)
[![Build Status](https://travis-ci.org/fcannizzaro/resourcer.svg?branch=master)](https://travis-ci.org/fcannizzaro/resourcer)

# Install

### Gradle
```gradle
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    compile 'com.github.fcannizzaro:resourcer:1.0.5'
}
```

### Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.fcannizzaro</groupId>
        <artifactId>resourcer</artifactId>
        <version>1.0.5</version>
    </dependency>
</dependencies>
```

###  Download JAR
[Release 1.0.5](https://github.com/fcannizzaro/resourcer/releases/tag/1.0.5)

# Project Structure
```
Project
|-- res
    |-- images
        |-- test.png
    |-- audio
    |-- json
        |-- array.json
        |-- object.json
    |-- values
        |-- colors.xml
        |-- dimensions.xml
        |-- strings.xml
        |-- booleans.xml
```

each file in values directory will be read and parsed.

# Init
```java
public static void main(String[] args) {

  Main instance = new Main();

  // only first time setup path
  new Resourcer.Builder("src\\main\\res")  // required
               .setImageDir("images")      // default
               .setAudioDir("audio")       // default
               .setJsonDir("json")         // default
               .setValuesDir("values")     // default
               .build();

  // then bind class
  Resourcer.bind(instance);

}
```
another class (without main)

```java
public class Another{

  // bind class in constructor
  public Another(){
    Resourcer.bind(this);
  }

}
```

# Sample
See [Sample](https://github.com/FrancisCan/Resourcer/tree/master/Sample)

# File

## @Json("filename")
```java

// relative path
@Json("filename")
JSONObject count;

// relative path (array)
@Json("array")
JSONArray array;

// absolute path
@Json("D:/Dropbox/Public/json/turing")
JSONObject turing;

// url
@Json("https://db.tt/WO7TLF2h")
JSONObject turing;
```

## @Json("filename.body.key")
use the dot notation to get a single value or object
```java

// relative path + nested object
@Json("filename.nested.object")
JSONObject nested;

// relative path + nested field
@Json("filename.counters.count")
int count;

// absolute path + dot notation
@Json("D:/Dropbox/Public/webanimex.dialog.title")
String title;

// url + dot notation
@Json(url = "https://db.tt/WO7TLF2h", key = "data")
JSONArray machines;
```

## @Image("filename")
```java

// local resource
@Image("name.png")
BufferedImage image;

// url
@Image("https://db.tt/G5a5HqnI")
BufferedImage image;
```

## @Audio("filename")
```java
@Audio("name.wav")
AudioInputStream audio;
```

# Values

```xml
<?xml version="1.0"?>
<resources>
    <string  name="string1">test</string>
    <integer name="number1">150</integer>
    <float   name="number2">150.50</float>
    <double  name="number3">150</double>
    <long    name="number4">150000</long>
    <color   name="color01">#000</color>
</resources>
```

## @StringRes()
```java
@StringRes()
String name;

// or use another variable name
@StringRes("name")
String anotherName;
```
## @IntegerRes()
Integer / int type

## @BooleanRes()
Boolean / boolean type

## @DoubleRes()
Double / double type

## @FloatRes()
Float / float type

## @LongRes()
Long / long type

## @ColorRes()
Color type
