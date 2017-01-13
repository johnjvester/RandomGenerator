package org.jjv.randomizer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MainTest {
    private final java.io.ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    private String currentVersion;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        InputStream is = null;
        RandomGenerator randomGenerator = new RandomGenerator();

        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            is = classloader.getResourceAsStream("properties.yml");

            Yaml yaml = new Yaml();

            Map<String, Object> result = (Map<String, Object>) yaml.load(is);

            currentVersion = result.get("version").toString();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ioe) {
                    System.out.println(ioe.getMessage());
                }
            }
        }
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void testWithoutAnyArgs() {
        try {
            String expectedResult = "\n"
                    + "RandomGenerator version " + currentVersion + "\n"
                    + "\n"
                    + "Usage: java -jar random-generator-version listString [options]\n"
                    + "\n"
                    + "  where:\n"
                    + "    listString = a concatenated list using either the default delimiter (~~~)\n"
                    + "                 or the value of the delimiterString provided.\n"
                    + "\n"
                    + "  options:\n"
                    + "    -delimiter = the custom delimiter to use.\n"
                    + "\n"
                    + "    -returnSize = the number of elements to return.\n"
                    + "\n"
                    + "  examples:\n"
                    + "    java -jar random-generator-version One~~~Two~~~Three~~Four~~~Five~~~\n"
                    + "      Randomizes a list of five elements using the default delimiter (~~~)\n"
                    + "\n"
                    + "    java -jar random-generator-version One^^Two^^Three^^Four^^Five^^ -delimiter ^^\n"
                    + "      Randomizes a list of five elements using a custom delimiter (^^)\n"
                    + "\n"
                    + "    java -jar random-generator-version One~~~Two~~~Three~~~Four~~~Five~~~ -returnSize 3\n"
                    + "      Randomizes a list of five elements using the default delimiter (~~~), returning only three elements\n"
                    + "\n"
                    + "    java -jar random-generator-version One^^Two^^Three^^Four^^Five^^ -delimiter ^^ -returnSize 3\n"
                    + "      Randomizes a list of five elements using a custom delimiter (^^), returning only three elements\n"
                    + "\n";

            String[] args = null;
            Main.main(args);
            String result = outContent.toString();

            assertEquals(expectedResult, result);
        } catch (FileNotFoundException e) {
            fail();
        }
    }

    @Test
    public void testWithDefaultDelimiter() {
        try {
            String expectedResult = "\n"
                    + "RandomGenerator version " + currentVersion + "\n"
                    + "\n"
                    + "Randomizing string with default delimiter (~~~)\n"
                    + "String to randomize:\n"
                    + "One~~~Two~~~Three~~~Four~~~Five~~~\n"
                    + "Randomized string:\n";

            String[] args = { "One~~~Two~~~Three~~~Four~~~Five~~~" };
            Main.main(args);
            String result = outContent.toString();

            assertEquals(expectedResult, result.substring(0, 155));
        } catch (FileNotFoundException e) {
            fail();
        }
    }

    @Test
    public void testWithAlternateDelimiter() {
        try {
            String expectedResult = "\n"
                    + "RandomGenerator version " + currentVersion + "\n"
                    + "\n"
                    + "Randomizing string with custom delimiter (^^^)\n"
                    + "String to randomize:\n"
                    + "One^^^Two^^^Three^^^Four^^^Five^^^\n"
                    + "Randomized string:\n";

            String[] args = { "One^^^Two^^^Three^^^Four^^^Five^^^", "-delimiter", "^^^" };
            Main.main(args);
            String result = outContent.toString();

            assertEquals(expectedResult, result.substring(0, 154));
        } catch (FileNotFoundException e) {
            fail();
        }
    }

    @Test
    public void testWithAlternateDelimiterAndReturnSize() {
        try {
            String expectedResult = "\n"
                    + "RandomGenerator version " + currentVersion + "\n"
                    + "\n"
                    + "Randomizing string with custom delimiter (^^^), returning only 3 elements\n"
                    + "String to randomize:\n"
                    + "One^^^Two^^^Three^^^Four^^^Five^^^\n"
                    + "Randomized string:\n";

            String[] args = { "One^^^Two^^^Three^^^Four^^^Five^^^", "-delimiter", "^^^", "-returnSize", "3" };
            Main.main(args);
            String result = outContent.toString();

            assertEquals(expectedResult, result.substring(0, 181));
        } catch (FileNotFoundException e) {
            fail();
        }
    }

    @Test
    public void testWithReturnSizeAndAlternateDelimiter() {
        try {
            String expectedResult = "\n"
                    + "RandomGenerator version " + currentVersion + "\n"
                    + "\n"
                    + "Randomizing string with custom delimiter (^^^), returning only 3 elements\n"
                    + "String to randomize:\n"
                    + "One^^^Two^^^Three^^^Four^^^Five^^^\n"
                    + "Randomized string:\n";

            String[] args = { "One^^^Two^^^Three^^^Four^^^Five^^^", "-returnSize", "3", "-delimiter", "^^^" };
            Main.main(args);
            String result = outContent.toString();

            assertEquals(expectedResult, result.substring(0, 181));
        } catch (FileNotFoundException e) {
            fail();
        }
    }

    @Test
    public void testWithReturnSize() {
        try {
            String expectedResult = "\n"
                    + "RandomGenerator version " + currentVersion + "\n"
                    + "\n"
                    + "Randomizing string with default delimiter (~~~), returning only 3 elements\n"
                    + "String to randomize:\n"
                    + "One~~~Two~~~Three~~~Four~~~Five~~~\n"
                    + "Randomized string:\n";

            String[] args = { "One~~~Two~~~Three~~~Four~~~Five~~~", "-returnSize", "3" };
            Main.main(args);
            String result = outContent.toString();

            assertEquals(expectedResult, result.substring(0, 182));
        } catch (FileNotFoundException e) {
            fail();
        }
    }

    @Test
    public void testWithInvalidArg() {
        try {
            String expectedResult = "\n"
                    + "RandomGenerator version " + currentVersion + "\n"
                    + "\n"
                    + "An unexpected error occurred.\n"
                    + "\n"
                    + "An error has occurred: arg -invalidArg is not a valid option\n"
                    + "\n"
                    + "Usage: java -jar random-generator-version listString [options]\n"
                    + "\n"
                    + "  where:\n"
                    + "    listString = a concatenated list using either the default delimiter (~~~)\n"
                    + "                 or the value of the delimiterString provided.\n"
                    + "\n"
                    + "  options:\n"
                    + "    -delimiter = the custom delimiter to use.\n"
                    + "\n"
                    + "    -returnSize = the number of elements to return.\n"
                    + "\n"
                    + "  examples:\n"
                    + "    java -jar random-generator-version One~~~Two~~~Three~~Four~~~Five~~~\n"
                    + "      Randomizes a list of five elements using the default delimiter (~~~)\n"
                    + "\n"
                    + "    java -jar random-generator-version One^^Two^^Three^^Four^^Five^^ -delimiter ^^\n"
                    + "      Randomizes a list of five elements using a custom delimiter (^^)\n"
                    + "\n"
                    + "    java -jar random-generator-version One~~~Two~~~Three~~~Four~~~Five~~~ -returnSize 3\n"
                    + "      Randomizes a list of five elements using the default delimiter (~~~), returning only three elements\n"
                    + "\n"
                    + "    java -jar random-generator-version One^^Two^^Three^^Four^^Five^^ -delimiter ^^ -returnSize 3\n"
                    + "      Randomizes a list of five elements using a custom delimiter (^^), returning only three elements\n"
                    + "\n";

            String[] args = { "One~~~Two~~~Three~~~Four~~~Five~~~", "-invalidArg", "hello" };
            Main.main(args);
            String result = outContent.toString();

            assertEquals(expectedResult, result);
        } catch (FileNotFoundException e) {
            fail();
        }
    }

    @Test
    public void testWithOneValidAndOneInvalidArg() {
        try {
            String expectedResult = "\n"
                    + "RandomGenerator version " + currentVersion + "\n"
                    + "\n"
                    + "An unexpected error occurred.\n"
                    + "\n"
                    + "An error has occurred: arg -invalidArg is not a valid option\n"
                    + "\n"
                    + "Usage: java -jar random-generator-version listString [options]\n"
                    + "\n"
                    + "  where:\n"
                    + "    listString = a concatenated list using either the default delimiter (~~~)\n"
                    + "                 or the value of the delimiterString provided.\n"
                    + "\n"
                    + "  options:\n"
                    + "    -delimiter = the custom delimiter to use.\n"
                    + "\n"
                    + "    -returnSize = the number of elements to return.\n"
                    + "\n"
                    + "  examples:\n"
                    + "    java -jar random-generator-version One~~~Two~~~Three~~Four~~~Five~~~\n"
                    + "      Randomizes a list of five elements using the default delimiter (~~~)\n"
                    + "\n"
                    + "    java -jar random-generator-version One^^Two^^Three^^Four^^Five^^ -delimiter ^^\n"
                    + "      Randomizes a list of five elements using a custom delimiter (^^)\n"
                    + "\n"
                    + "    java -jar random-generator-version One~~~Two~~~Three~~~Four~~~Five~~~ -returnSize 3\n"
                    + "      Randomizes a list of five elements using the default delimiter (~~~), returning only three elements\n"
                    + "\n"
                    + "    java -jar random-generator-version One^^Two^^Three^^Four^^Five^^ -delimiter ^^ -returnSize 3\n"
                    + "      Randomizes a list of five elements using a custom delimiter (^^), returning only three elements\n"
                    + "\n";

            String[] args = { "One~~~Two~~~Three~~~Four~~~Five~~~", "-returnSize", "3", "-invalidArg", "hello" };
            Main.main(args);
            String result = outContent.toString();

            assertEquals(expectedResult, result);
        } catch (FileNotFoundException e) {
            fail();
        }
    }
}
