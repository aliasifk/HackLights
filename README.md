# HackLights

Simple framebuffer based lighting engine for libGDX.

![banner2](https://user-images.githubusercontent.com/55298434/156891579-94c284dd-815c-4404-89fb-a0d3f2e42181.png)


![banner](https://user-images.githubusercontent.com/55298434/156891489-15757313-dd4f-47cf-8033-d1caff05fb10.png)


[![License](https://img.shields.io/github/license/aliasifk/HackLights)](https://github.com/aliasifk/HackLights/blob/main/LICENSE)
[![Jitpack](https://jitpack.io/v/aliasifk/HackLights.svg)](https://jitpack.io/#aliasifk/HackLights)

# Example

See:
[Sample Code](https://github.com/aliasifk/HackLights/blob/master/src/test/java/com/aliasifkhan/hackLights/lwjgl/tests/BasicTest.java)

Sample Light Images:
![Sample Light Textures](https://user-images.githubusercontent.com/55298434/156891366-d28edc23-1c89-40d9-8ac1-0d0c7b061d72.png)


# Installation

1. Open or create `gradle.properties` in the root folder of your project, add the following line:

```properties
hackLightsVersion=VERSION
```

Check [Jitpack](https://jitpack.io/#aliasifk/HackLights/) for the latest version and replace `VERSION` with that.

2. Add the jitpack repo to your build file.

```groovy
allprojects {
    repositories {
        // ...
        maven { url 'https://jitpack.io' }
    }
}
```

3. Add that to your core modules dependencies inside your root `build.gradle`

```groovy
project(":core") {
    // ...

    dependencies {
        // ...
        implementation "com.github.aliasifk:HackLights:$hackLightsVersion"
    }
}
```

## Html/Gwt project

1. Gradle dependency:

```groovy
implementation "com.github.aliasifk:HackLights:$hackLightsVersion:sources"
```

2. In your application's `.gwt.xml` file add (Normally `GdxDefinition.gwt.xml`):

```xml

<inherits name="com.aliasifkhan.hackLights"/>
```

## How to test

Run `./gradlew test` to run lwjgl3 tests and examples.

Set environment variable `SLEEPY` to a millisecond number to sleep between each test. (For example: SLEEPY=3000 would wait 3 seconds after every test.)
