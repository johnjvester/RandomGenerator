package org.jjv.randomizer;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class RandomGenerator<T> {

    final static Logger logger = LogManager.getLogger(RandomGenerator.class);
    private static final Integer ZERO = new Integer("0");

    /**
     * Default Delimiter is equal to three tilde (~~~) characters.  This is the assumed default
     * delimiter when a delimiter is not specified.
     */
    public static String DEFAULT_DELIMITER = "~~~";

    /**
     * <p>If the List object includes an Integer field called "rating", this value can be used to
     * provide additional weighting to the randomization.</p>
     * <p>Consider the following two items in the list:</p>
     * <ul>
     * <li>name = Apple, rating = 1</li>
     * <li>name = Orange, rating = 5</li>
     * </ul>
     * <p>When using rating in the randomization in the example above, the Orange object will
     * have a greater number of chances than the Apple object has to be selected.  The
     * feature allows certain value to have a greater chance to be selected.</p>
     * <p>A rating scale of 1 to 5 is recommended.</p>
     */
    public static String RATING = "rating";

    /**
     * Randomizes the elements in a given tList and returns a new
     * List object of the same type.  All items in the original tList
     * will be returned.
     *
     * @param tList List object to randomize
     * @return new List object whose order has been randomized
     */
    public List<T> randomize(List<T> tList) {
        logger.debug("calling preProcessing(tList, ZERO, false)");
        return preProcessing(tList, ZERO, false);
    }

    /**
     * <p>Randomizes the elements in a given tList (which contains a field
     * named "rating"), applies weighting based upon the rating (if enabled),
     * and returns a new List object of the same type.  All items in the
     * original tList will be returned.</p>
     * <p>If the useRating Boolean is set to true and the rating field
     * for each List item contains a valid Integer (values of 1 through
     * 5 are recommended), the randomization will be weighted to favor
     * those with a higher rating field value.</p>
     *
     * @param tList     List object to randomize
     * @param useRating Boolean to indicate if rating field will be used
     * @return new List object whose order has been randomized
     */
    public List<T> randomize(List<T> tList, Boolean useRating) {
        logger.debug("calling preProcessing(tlist, ZERO, " + useRating + ")");
        return preProcessing(tList, ZERO, useRating);
    }

    /**
     * <p>Randomizes the elements in a given tList and returns a new
     * List object of the same type, limited to the number provided in
     * the maxResults object. Passing a maxResults value of zero will
     * return a List object of the same size as the original tList object.</p>
     * <p>If the maxResults value is greater than the tList size, the return
     * List object will match the size of the original tList.</p>
     *
     * @param tList      List object to randomize
     * @param maxResults the size of the return List (specify 0 to return all results)
     * @return new List object whose order has been randomized and limited to the size of the maxResults object
     */
    public List<T> randomize(List<T> tList, Integer maxResults) {
        logger.debug("calling preProcessing(tList, " + maxResults + ", false)");
        return preProcessing(tList, maxResults != null ? maxResults : ZERO, false);
    }

    /**
     * <p>Randomizes the elements in a given tList (which contains a field
     * named "rating"), applies weighting based upon the rating (if enabled),
     * and returns a new List object of the same type - limited to the number
     * provided in the maxResults object. Passing a maxResults value of zero
     * will return a List object of the same size as the original tList object.</p>
     * <p>If the maxResults value is greater than the tList size, the return
     * List object will match the size of the original tList.</p>
     * <p>If the useRating Boolean is set to true and the rating field
     * for each List item contains a valid Integer (values of 1 through
     * 5 are recommended), the randomization will be weighted to favor
     * those with a higher rating field value.</p>
     *
     * @param tList      List object to randomize
     * @param maxResults the size of the return List (specify 0 to return all results)
     * @param useRating  Boolean to indicate if rating field will be used
     * @return new List object whose order has been randomized and limited to the size of the maxResults object
     */
    public List<T> randomize(List<T> tList, Integer maxResults, Boolean useRating) {
        logger.debug("calling preProcessing(tList, " + maxResults + ", " + useRating + ")");
        return preProcessing(tList, maxResults != null ? maxResults : ZERO, useRating);
    }

    /**
     * For a given thisString object that contains multiple elements denoted
     * by a thisString separator, a new String will be returned, randomizing
     * the values from the source String object.
     *
     * @param thisString    String object to randomize
     * @param thisSeparator String value of separator for thisString object
     * @return new String object whose order has been randomized
     */
    public String randomize(String thisString, String thisSeparator) {
        logger.debug("calling convertStringToList(thisString, " + thisSeparator + ")");
        List<T> thisList = convertStringToList(thisString, thisSeparator);
        logger.debug("calling convertListToString(preProcessing(thisList, ZERO, false), " + thisSeparator + ")");
        return convertListToString(preProcessing(thisList, ZERO, false), thisSeparator);
    }

    /**
     * <p>For a given thisString object that contains multiple elements denoted
     * by a thisString separator, a new String will be returned, randomizing
     * the values from the source String object, limited to the number provided in
     * the maxResults object. Passing a maxResults value of zero will
     * return a String object of the same size as the original String object.</p>
     * <p>If the maxResults value is greater than the number of elements within the
     * thisString object, the return String object will match the size of the original
     * thisString.</p>
     *
     * @param thisString    String object to randomize
     * @param thisSeparator String value of separator for thisString object
     * @param maxResults    the size of the return String (specify 0 to return all results)
     * @return new String object whose order has been randomized and limited to the size of the maxResults object
     */
    public String randomize(String thisString, String thisSeparator, Integer maxResults) {
        logger.debug("calling convertStringToList(thisString, " + thisSeparator + ")");
        List<T> thisList = convertStringToList(thisString, thisSeparator);
        logger.debug("calling convertListToString(preProcessing(thisList, " + maxResults != null ? maxResults : ZERO + ", false), " + thisSeparator + ")");
        return convertListToString(preProcessing(thisList, maxResults != null ? maxResults : ZERO, false), thisSeparator);
    }

    private String convertListToString(List<T> thisList, String thisCat) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < thisList.size(); i++) {
            sb.append(thisList.get(i).toString());
            sb.append(thisCat);
        }

        return sb.toString();
    }

    private List<T> convertStringToList(String thisString, String thisCat) {
        List<T> thisList = new ArrayList<T>();
        int lastCatPointer = 0;

        while (lastCatPointer < thisString.length()) {
            int thisCatPointer = thisString.indexOf(thisCat, lastCatPointer);

            String thisCatValue = thisString.substring(lastCatPointer, thisCatPointer);

            if (thisCatValue != null && thisCatValue.length() > 0) {
                thisList.add((T) thisCatValue);
            }
            lastCatPointer = thisCatPointer + thisCat.length();
        }

        return thisList;
    }

    private List<T> preProcessing(List<T> tList, Integer maxResults, Boolean useRating) {
        logger.debug("begin preProcessing(tList, " + maxResults + ", " + useRating + ")");
        List<RandomListItem> returnList = new ArrayList<RandomListItem>();

        if (tList != null && tList.size() > 0) {
            logger.debug("tList.size() = " + tList.size());
            if (tList.size() == 1) {
                return tList;
            } else {
                if (maxResults.intValue() > ZERO) {
                    if (maxResults.intValue() >= tList.size()) {
                        logger.debug("maxResults (" + maxResults + ") is greater than tList.size() (" + tList.size() + ")");
                        handleRandomization(expandListForRatings(tList, useRating), returnList, ZERO);
                    } else {
                        handleRandomization(expandListForRatings(tList, useRating), returnList, maxResults);
                    }
                } else {
                    handleRandomization(expandListForRatings(tList, useRating), returnList, ZERO);
                }
            }
        }

        logger.debug("returnList.size() = " + returnList.size());
        logger.debug("end preProcessing(tList, " + maxResults + ")");
        return convertToGenericList(returnList);
    }

    private void handleRandomization(List<RandomListItem> randomListItems, List<RandomListItem> returnList, Integer maxResults) {
        logger.debug("begin handleRandomization(tList, returnList, " + maxResults + ")");
        while (randomListItems.size() > 0 && (maxResults.intValue() == ZERO || maxResults.intValue() > returnList.size())) {
            int listSize = randomListItems.size();
            logger.debug("listSize = " + listSize);

            int winner = 0;
            if (listSize != 1) {
                Random random = new Random();
                winner = random.nextInt(listSize);
            }
            logger.debug("winner = " + winner);

            RandomListItem randomListItem = randomListItems.get(winner);

            returnList.add(randomListItem);
            removeSimilarItems(randomListItems, randomListItem.originalToString);

            logger.debug("returnList.size() = " + returnList.size());
            logger.debug("randomListItems.size() = " + randomListItems.size());
        }
        logger.debug("end handleRandomization(tList, returnList, " + maxResults + ")");
    }

    private List<RandomListItem> expandListForRatings(List<T> tList, Boolean useRating) {
        logger.debug("begin expandListForRatings(tList, " + useRating + ")");

        List<RandomListItem> newList = new ArrayList<RandomListItem>();

        if (tList != null && tList.size() > 0) {

            for (T tItem : tList) {
                if (useRating) {
                    for (Field field : tItem.getClass().getDeclaredFields()) {
                        field.setAccessible(true);
                        if (field.getName().equals(RATING)) {
                            if (field.getType().equals(Integer.class)) {
                                Integer rating = 1;
                                try {
                                    Object o = field.get(tItem);
                                    rating = (Integer) o;
                                    logger.debug("Rating = " + rating);
                                } catch (Exception e) {
                                    logger.debug("Exception occurred trying to get rating");
                                }
                                copyListItem(tItem, rating, newList);
                            }
                        }
                    }
                } else {
                    copyListItem(tItem, 1, newList);
                }
            }
        }

        return newList;
    }

    private void copyListItem(T thisT, Integer rating, List<RandomListItem> newList) {
        int ratingOffset;

        switch (rating) {
        case 1:
            ratingOffset = 1;
            break;
        case 2:
            ratingOffset = 2;
            break;
        case 3:
            ratingOffset = 4;
            break;
        case 4:
            ratingOffset = 8;
            break;
        case 5:
            ratingOffset = 16;
            break;
        default:
            ratingOffset = 1;
            break;
        }

        for (int i = 0; i < ratingOffset; i++) {
            RandomListItem randomListItem = new RandomListItem(thisT.toString(), thisT);
            newList.add(randomListItem);
        }
    }

    private List<T> convertToGenericList(List<RandomListItem> randomListItems) {
        List<T> tList = new ArrayList<T>();

        for (RandomListItem randomListItem : randomListItems) {
            tList.add(randomListItem.thisT);
        }

        return tList;
    }

    private void removeSimilarItems(List<RandomListItem> randomListItems, String originalToString) {
        logger.debug("calling removeSimilarItems(randomListItems, " + originalToString + ")");
        Iterator<RandomListItem> itemIterator = randomListItems.iterator();

        int count = 0;
        while (itemIterator.hasNext()) {
            RandomListItem randomListItem = itemIterator.next();

            if (randomListItem.originalToString.equals(originalToString)) {
                itemIterator.remove();
                count++;
            }
        }
        logger.debug("Removed " + count + " instance(s) of " + originalToString);
    }

    @Data
    private class RandomListItem {
        public RandomListItem(String originalToString, T thisT) {
            this.originalToString = originalToString;
            this.thisT = thisT;
        }

        private String originalToString;
        private T thisT;
    }
}

