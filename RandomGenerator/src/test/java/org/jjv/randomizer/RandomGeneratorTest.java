package org.jjv.randomizer;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class RandomGeneratorTest {

    final static Logger logger = LogManager.getLogger(RandomGeneratorTest.class);
    private static final Integer ONE = 1;
    private static final Integer TWO = 2;
    private static final Integer THREE = 3;
    private static final Integer FOUR = 4;
    private static final Integer FIVE = 5;

    @Test
    public void standardTest() {
        logger.debug("begin standardTest()");
        List<TestObject> testList = new ArrayList<TestObject>();
        generateTestData(testList, 25, false);

        for (TestObject testObject : testList) {
            logger.debug("testObject.getValue() = " + testObject.getValue());
        }

        RandomGenerator randomGenerator = new RandomGenerator();

        List<TestObject> resultList = randomGenerator.randomize(testList);

        for (TestObject resultObject : resultList) {
            logger.debug("resultObject.getValue() = " + resultObject.getValue());
        }

        assertNotSame("Objects should be in a different order", testList, resultList);
        logger.debug("end standardTest()");
    }

    @Test
    public void standardTestWithRatingType() {
        logger.debug("begin standardTestWithRatingType()");
        List<TestObject> testList = new ArrayList<TestObject>();
        generateTestData(testList, 25, true);

        for (TestObject testObject : testList) {
            logger.debug("testObject.getValue() = " + testObject.getValue());
        }

        RandomGenerator randomGenerator = new RandomGenerator();

        List<TestObject> resultList = randomGenerator.randomize(testList, true);

        for (TestObject resultObject : resultList) {
            logger.debug("resultObject.getValue() = " + resultObject.getValue());
        }

        assertNotSame("Objects should be in a different order", testList, resultList);
        logger.debug("end standardTestWithRatingType()");
    }

    @Test
    public void fiveResultsTest() {
        logger.debug("begin fiveResultsTest()");
        List<TestObject> testList = new ArrayList<TestObject>();
        generateTestData(testList, 25, false);

        for (TestObject testObject : testList) {
            logger.debug("testObject.getValue() = " + testObject.getValue());
        }

        RandomGenerator randomGenerator = new RandomGenerator();

        List<TestObject> resultList = randomGenerator.randomize(testList, 5);

        for (TestObject resultObject : resultList) {
            logger.debug("resultObject.getValue() = " + resultObject.getValue());
        }

        assertTrue(resultList.size() == 5);

        assertNotSame("Objects should be in a different order", testList, resultList);
        logger.debug("end fiveResultsTest()");
    }

    @Test
    public void fiveResultsTestWithRatingType() {
        logger.debug("begin fiveResultsTestWithRatingType()");
        List<TestObject> testList = new ArrayList<TestObject>();
        generateTestData(testList, 25, true);

        for (TestObject testObject : testList) {
            logger.debug("testObject.getValue() = " + testObject.getValue());
        }

        RandomGenerator randomGenerator = new RandomGenerator();

        List<TestObject> resultList = randomGenerator.randomize(testList, 5, true);

        for (TestObject resultObject : resultList) {
            logger.debug("resultObject.getValue() = " + resultObject.getValue());
        }

        assertTrue(resultList.size() == 5);

        assertNotSame("Objects should be in a different order", testList, resultList);
        logger.debug("end fiveResultsTestWithRatingType()");
    }

    @Test
    public void oneResultTest() {
        logger.debug("begin oneResultTest()");
        List<TestObject> testList = new ArrayList<TestObject>();
        generateTestData(testList, 1, false);

        for (TestObject testObject : testList) {
            logger.debug("testObject.getValue() = " + testObject.getValue());
        }

        RandomGenerator randomGenerator = new RandomGenerator();

        List<TestObject> resultList = randomGenerator.randomize(testList);

        for (TestObject resultObject : resultList) {
            logger.debug("resultObject.getValue() = " + resultObject.getValue());
        }

        assertSame("Single size objects should be in the same order", testList, resultList);
        logger.debug("end oneResultTest()");
    }

    @Test
    public void oneResultTestWithRatingType() {
        logger.debug("begin oneResultTestWithRatingType()");
        List<TestObject> testList = new ArrayList<TestObject>();
        generateTestData(testList, 1, true);

        for (TestObject testObject : testList) {
            logger.debug("testObject.getValue() = " + testObject.getValue());
        }

        RandomGenerator randomGenerator = new RandomGenerator();

        List<TestObject> resultList = randomGenerator.randomize(testList, true);

        for (TestObject resultObject : resultList) {
            logger.debug("resultObject.getValue() = " + resultObject.getValue());
        }

        assertSame("Single size objects should be in the same order", testList, resultList);
        logger.debug("end oneResultTestWithRatingType()");
    }

    @Test
    public void tooManyMaxResultsTest() {
        logger.debug("begin standardTest()");
        List<TestObject> testList = new ArrayList<TestObject>();
        generateTestData(testList, 25, false);

        for (TestObject testObject : testList) {
            logger.debug("testObject.getValue() = " + testObject.getValue());
        }

        RandomGenerator randomGenerator = new RandomGenerator();

        List<TestObject> resultList = randomGenerator.randomize(testList, 50);

        for (TestObject resultObject : resultList) {
            logger.debug("resultObject.getValue() = " + resultObject.getValue());
        }

        assertNotSame("Objects should be in a different order", testList, resultList);
        logger.debug("end standardTest()");
    }

    @Test
    public void standardStringTest() {
        logger.debug("begin standardStringTest()");
        RandomGenerator randomGenerator = new RandomGenerator();

        String testString = generateTestStringData(randomGenerator.DEFAULT_DELIMITER, 25);
        logger.debug("testString = " + testString);

        String resultString = randomGenerator.randomize(testString, randomGenerator.DEFAULT_DELIMITER);
        logger.debug("resultString = " + resultString);

        assertNotSame("Objects should be in a different order", testString, resultString);
        logger.debug("end standardStringTest()");
    }

    @Test
    public void standardStringTestOnlySeven() {
        logger.debug("begin standardStringTestOnlySeven()");
        RandomGenerator randomGenerator = new RandomGenerator();

        String testString = generateTestStringData(randomGenerator.DEFAULT_DELIMITER, 25);
        logger.debug("testString = " + testString);

        String resultString = randomGenerator.randomize(testString, randomGenerator.DEFAULT_DELIMITER, 7);
        logger.debug("resultString = " + resultString);

        assertNotSame("Objects should be in a different order", testString, resultString);
        logger.debug("end standardStringTestOnlySevel()");
    }

    @Data
    private static class TestObject {
        private String value;
        private Integer rating;

        public TestObject() {

        }
    }

    private static void generateTestData(List<TestObject> testList, int listSize, Boolean useRating) {
        for (int i = 0; i < listSize; i++) {
            TestObject testObject = new TestObject();

            switch (i) {
            case 0:
                testObject.setValue("ONE");
                if (useRating) {
                    testObject.setRating(FIVE);
                }
                break;
            case 1:
                testObject.setValue("TWO");
                if (useRating) {
                    testObject.setRating(FOUR);
                }
                break;
            case 2:
                testObject.setValue("THREE");
                if (useRating) {
                    testObject.setRating(THREE);
                }
                break;
            case 3:
                testObject.setValue("FOUR");
                if (useRating) {
                    testObject.setRating(TWO);
                }
                break;
            case 4:
                testObject.setValue("FIVE");
                if (useRating) {
                    testObject.setRating(ONE);
                }
                break;
            case 5:
                testObject.setValue("SIX");
                if (useRating) {
                    testObject.setRating(TWO);
                }
                break;
            case 6:
                testObject.setValue("SEVEN");
                if (useRating) {
                    testObject.setRating(THREE);
                }
                break;
            case 7:
                testObject.setValue("EIGHT");
                if (useRating) {
                    testObject.setRating(FOUR);
                }
                break;
            case 8:
                testObject.setValue("NINE");
                if (useRating) {
                    testObject.setRating(FIVE);
                }
                break;
            case 9:
                testObject.setValue("TEN");
                if (useRating) {
                    testObject.setRating(FOUR);
                }
                break;
            case 10:
                testObject.setValue("ELEVEN");
                if (useRating) {
                    testObject.setRating(THREE);
                }
                break;
            case 11:
                testObject.setValue("TWELVE");
                if (useRating) {
                    testObject.setRating(TWO);
                }
                break;
            case 12:
                testObject.setValue("THIRTEEN");
                if (useRating) {
                    testObject.setRating(ONE);
                }
                break;
            case 13:
                testObject.setValue("FOURTEEN");
                if (useRating) {
                    testObject.setRating(TWO);
                }
                break;
            case 14:
                testObject.setValue("FIFTEEN");
                if (useRating) {
                    testObject.setRating(THREE);
                }
                break;
            case 15:
                testObject.setValue("SIXTEEN");
                if (useRating) {
                    testObject.setRating(FOUR);
                }
                break;
            case 16:
                testObject.setValue("SEVENTEEN");
                if (useRating) {
                    testObject.setRating(FIVE);
                }
                break;
            case 17:
                testObject.setValue("EIGHTEEN");
                if (useRating) {
                    testObject.setRating(FOUR);
                }
                break;
            case 18:
                testObject.setValue("NINETEEN");
                if (useRating) {
                    testObject.setRating(THREE);
                }
                break;
            case 19:
                testObject.setValue("TWENTY");
                if (useRating) {
                    testObject.setRating(TWO);
                }
                break;
            case 20:
                testObject.setValue("TWENTY-ONE");
                if (useRating) {
                    testObject.setRating(ONE);
                }
                break;
            case 21:
                testObject.setValue("TWENTY-TWO");
                if (useRating) {
                    testObject.setRating(TWO);
                }
                break;
            case 22:
                testObject.setValue("TWENTY-THREE");
                if (useRating) {
                    testObject.setRating(THREE);
                }
                break;
            case 23:
                testObject.setValue("TWENTY-FOUR");
                if (useRating) {
                    testObject.setRating(FOUR);
                }
                break;
            case 24:
                testObject.setValue("TWENTY-FIVE");
                if (useRating) {
                    testObject.setRating(FIVE);
                }
                break;
            }

            testList.add(testObject);
        }
    }

    private static String generateTestStringData(String thisCat, int listSize) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < listSize; i++) {
            switch (i) {
            case 0:
                sb.append("ONE");
                sb.append(thisCat);
                break;
            case 1:
                sb.append("TWO");
                sb.append(thisCat);
                break;
            case 2:
                sb.append("THREE");
                sb.append(thisCat);
                break;
            case 3:
                sb.append("FOUR");
                sb.append(thisCat);
                break;
            case 4:
                sb.append("FIVE");
                sb.append(thisCat);
                break;
            case 5:
                sb.append("SIX");
                sb.append(thisCat);
                break;
            case 6:
                sb.append("SEVEN");
                sb.append(thisCat);
                break;
            case 7:
                sb.append("EIGHT");
                sb.append(thisCat);
                break;
            case 8:
                sb.append("NINE");
                sb.append(thisCat);
                break;
            case 9:
                sb.append("TEN");
                sb.append(thisCat);
                break;
            case 10:
                sb.append("ELEVEN");
                sb.append(thisCat);
                break;
            case 11:
                sb.append("TWELVE");
                sb.append(thisCat);
                break;
            case 12:
                sb.append("THIRTEEN");
                sb.append(thisCat);
                break;
            case 13:
                sb.append("FOURTEEN");
                sb.append(thisCat);
                break;
            case 14:
                sb.append("FIFTEEN");
                sb.append(thisCat);
                break;
            case 15:
                sb.append("SIXTEEN");
                sb.append(thisCat);
                break;
            case 16:
                sb.append("SEVENTEEN");
                sb.append(thisCat);
                break;
            case 17:
                sb.append("EIGHTEEN");
                sb.append(thisCat);
                break;
            case 18:
                sb.append("NINETEEN");
                sb.append(thisCat);
                break;
            case 19:
                sb.append("TWENTY");
                sb.append(thisCat);
                break;
            case 20:
                sb.append("TWENTY-ONE");
                sb.append(thisCat);
                break;
            case 21:
                sb.append("TWENTY-TWO");
                sb.append(thisCat);
                break;
            case 22:
                sb.append("TWENTY-THREE");
                sb.append(thisCat);
                break;
            case 23:
                sb.append("TWENTY-FOUR");
                sb.append(thisCat);
                break;
            case 24:
                sb.append("TWENTY-FIVE");
                sb.append(thisCat);
                break;
            }
        }

        return sb.toString();
    }
}