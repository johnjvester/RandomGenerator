package org.jjv.randomizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class Main {
    final static Logger logger = LogManager.getLogger(Main.class);

    /**
     * <h3>CLI for RandomGenerator</h3>
     * <p>Usage: java -jar random-generator-version listString [options]</p>
     * <p>where:</p>
     * <ul>
     * <li>listString = a concatenated list using either the default delimiter (~~~) or the value of the delimiterString provided.</li>
     * </ul>
     * <p>options:</p>
     * <ul>
     * <li>-delimiter = the custom delimiter to use</li>
     * <li>-returnSize = the number of elements to return</li>
     * </ul>
     * <p>examples:</p>
     * <ul>
     * <li>java -jar random-generator-version One~~~Two~~~Three~~Four~~~Five~~~<br>
     * Randomizes a list of five elements using the default delimiter (~~~)
     * </li>
     * <li>java -jar random-generator-version One^^Two^^Three^^Four^^Five^^ -delimiter ^^<br>
     * Randomizes a list of five elements using a custom delimiter (^^)
     * </li>
     * <li>java -jar random-generator-version One~~~Two~~~Three~~~Four~~~Five~~~ -returnSize 3<br>
     * Randomizes a list of five elements using the default delimiter (~~~), returning only three elements
     * </li>
     * <li>java -jar random-generator-version One^^Two^^Three^^Four^^Five^^ -delimiter ^^ -returnSize 3<br>
     * Randomizes a list of five elements using a custom delimiter (^^), returning only three elements
     * </li>
     * </ul>
     *
     * @param args requires at least the listString object, but can also include -delimiter and -returnSize run-time parameters as well
     * @throws FileNotFoundException a File Not Found Exception will be returned if the properties.yml file does not exist
     */
    public static void main(String[] args) throws FileNotFoundException {
        InputStream is = null;
        RandomGenerator randomGenerator = new RandomGenerator();

        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            is = classloader.getResourceAsStream("properties.yml");

            Yaml yaml = new Yaml();

            Map<String, Object> result = (Map<String, Object>) yaml.load(is);

            String currentVersion = result.get("version").toString();
            logger.debug("currentVersion = " + currentVersion);

            writeMessage("", false);
            writeMessage("RandomGenerator version " + currentVersion, true);

            String returnString = null;
            Integer returnListSize = null;

            if (args != null && (args.length == 1 || args.length == 3 || args.length == 5)) {
                logger.debug("args = " + args);
                logger.debug("args.length = " + args.length);

                if (args.length == 1) {
                    writeMessage("Randomizing string with default delimiter (" + randomGenerator.DEFAULT_DELIMITER + ")", false);
                    writeMessage("String to randomize:", false);
                    writeMessage(args[0], false);
                    returnString = randomGenerator.randomize(args[0], randomGenerator.DEFAULT_DELIMITER);
                } else {
                    String hasCustomDelimiter = null;
                    String hasReturnListSize = null;

                    try {
                        if (args.length >= 3) {
                            if (args[1].equals("-delimiter")) {
                                hasCustomDelimiter = args[2];
                            } else if (args[1].equals("-returnSize")) {
                                hasReturnListSize = args[2];
                            } else {
                                throw new Exception("An error has occurred: arg " + args[1] + " is not a valid option");
                            }
                        }

                        if (args.length == 5) {
                            if (args[3].equals("-delimiter")) {
                                hasCustomDelimiter = args[4];
                            } else if (args[3].equals("-returnSize")) {
                                hasReturnListSize = args[4];
                            } else {
                                throw new Exception("An error has occurred: arg " + args[3] + " is not a valid option");
                            }
                        }

                        logger.debug("hasCustomDelimiter = " + hasCustomDelimiter);
                        logger.debug("hasReturnListSize = " + hasReturnListSize);

                        if (hasReturnListSize != null) {
                            returnListSize = new Integer(hasReturnListSize);
                        }

                        if (hasCustomDelimiter != null && hasReturnListSize != null) {
                            writeMessage("Randomizing string with custom delimiter (" + hasCustomDelimiter
                                    + "), returning only " + returnListSize.intValue() + " elements", false);
                            writeMessage("String to randomize:", false);
                            writeMessage(args[0], false);
                            returnString = randomGenerator.randomize(args[0], hasCustomDelimiter, returnListSize.intValue());
                        } else if (hasCustomDelimiter != null) {
                            writeMessage("Randomizing string with custom delimiter (" + hasCustomDelimiter + ")", false);
                            writeMessage("String to randomize:", false);
                            writeMessage(args[0], false);
                            returnString = randomGenerator.randomize(args[0], hasCustomDelimiter);
                        } else if (hasReturnListSize != null) {
                            writeMessage("Randomizing string with default delimiter (" + randomGenerator.DEFAULT_DELIMITER
                                    + "), returning only " + hasReturnListSize + " elements", false);
                            writeMessage("String to randomize:", false);
                            writeMessage(args[0], false);
                            returnString = randomGenerator.randomize(args[0], randomGenerator.DEFAULT_DELIMITER, returnListSize.intValue());
                        }

                    } catch (Exception e) {
                        throw e;
                    }
                }

                logger.debug("returnString = " + returnString);

                writeMessage("Randomized string:", false);
                writeMessage(returnString, false);

            } else {
                getHelpText(randomGenerator.DEFAULT_DELIMITER);
            }
        } catch (Exception e) {
            writeMessage("An unexpected error occurred.", true);
            writeMessage(e.getMessage(), true);
            getHelpText(randomGenerator.DEFAULT_DELIMITER);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ioe) {
                    logger.error(ioe.getMessage());
                    writeMessage("An error occurred while trying to read the properties.yml file.", true);
                    writeMessage(ioe.getMessage(), true);
                }
            }
        }

    }

    private static void getHelpText(String defaultDelimiter) {
        writeMessage("Usage: java -jar random-generator-version listString [options]", true);
        writeMessage("  where:", false);
        writeMessage("    listString = a concatenated list using either the default delimiter (" + defaultDelimiter + ")", false);
        writeMessage("                 or the value of the delimiterString provided.", true);
        writeMessage("  options:", false);
        writeMessage("    -delimiter = the custom delimiter to use.", true);
        writeMessage("    -returnSize = the number of elements to return.", true);
        writeMessage("  examples:", false);
        writeMessage("    java -jar random-generator-version One~~~Two~~~Three~~Four~~~Five~~~", false);
        writeMessage("      Randomizes a list of five elements using the default delimiter (" + defaultDelimiter + ")", true);
        writeMessage("    java -jar random-generator-version One^^Two^^Three^^Four^^Five^^ -delimiter ^^", false);
        writeMessage("      Randomizes a list of five elements using a custom delimiter (^^)", true);
        writeMessage("    java -jar random-generator-version One~~~Two~~~Three~~~Four~~~Five~~~ -returnSize 3", false);
        writeMessage("      Randomizes a list of five elements using the default delimiter (" + defaultDelimiter + "), returning only three elements", true);
        writeMessage("    java -jar random-generator-version One^^Two^^Three^^Four^^Five^^ -delimiter ^^ -returnSize 3", false);
        writeMessage("      Randomizes a list of five elements using a custom delimiter (^^), returning only three elements", true);
    }

    private static void writeMessage(String message, boolean newLine) {
        System.out.println(message);
        if (newLine) {
            System.out.println("");
        }
    }
}
