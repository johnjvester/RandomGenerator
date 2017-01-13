## RandomGenerator

[![build status](https://gitlab.com/johnjvester/RandomGenerator/badges/master/build.svg)](https://gitlab.com/johnjvester/RandomGenerator/commits/master)

> The RandomGenerator project provides the ability to randomly sort a given `java.util.List` object, with 
> the option to return a subset of the original list contents.  A delimited String object can 
> also be sorted, with the ability to return a subset of the original contents.
>
> When using RandomGenerator with a List object, it is possible to introduced weighted value 
> to the randomization process by including a `java.lang.Integer` field called "rating" in the List items 
> being processed.  
>
> The project builds to a JAR file, which can be utilized by other Java projects, and also 
> includes a command-line interface (CLI) which can be used to randomly sort a delimited String.

### Getting Started

#### Using the JAR 

Until RandomGenerator is in Maven Central, follow the steps below to use RandomGenerator in your Java project:

1. Clone this project and execute the `mvn package` command
2. Optionally, navigate to the target folder of the RandomGenerator project
3. Optionally, copy the random-generator-version.jar into a location where your Java project can reference the jar
4. Within your Java project, include the random-generator-version.jar as part of your project

#### Using the CLI

RandomGenerator includes a command-line interface (CLI) that can be used with Java.

Follow the steps listed below to use the command-line interface (CLI):

1. Clone this project and execute the `mvn package` command
2. Make sure Java is in your classpath
3. Navigate to the target folder of the RandomGenerator project
4. Execute `java -jar random-generator-version.jar`, which will provide on-line help regarding the current version.

### JavaDoc

[http://johnjvester.gitlab.io/RandomGenerator-JavaDoc/](http://johnjvester.gitlab.io/RandomGenerator-JavaDoc/)

### About Weighted Values

When randomizing `java.util.List` objects with RandomGenerator, the items in the list can be weighted so that 
there is a better chance for one item to appear over another.  In the case of an example where the list being processed 
is centered around an end-user's preferences.  While the list will still be random, they may prefer some items over 
others.  This can be accomplished using the rating field within each item in the list being processed.

Consider the following list items of vacation destinations, with a 1 - 5 rating by an end-user:

| Destination   | Rating |
|:--------------|-------:|
| Boston, MA    | 2      |
| Las Vegas, NV | 3      |
| Maui, HI      | 5      |
| Miami, FL     | 4      |
| New York, NY  | 1      |
| San Diego, CA | 4      |
| Tampa, FL     | 5      |

When using RandomGenerator to randomize the list of destinations without the rating field, the list would be purely
random - giving each destination an equal chance of being first on the list.  However, if the rating field is included
in the processing, then there will be a better chance of Maui (HI) being selected over New York (NY).

It is recommended that the rating field (which has to be an `java.lang.Integer` type) have values between 1 to 5.

### Coming Soon

The following enhancement(s)/tasks(s) are planned:

* Add build process to GitLab
* Integrate Maven Release Plugin (if needed)
* Store binaries by version online
* Integrate with Maven Central

Created by [John Vester](https://www.linkedin.com/in/johnjvester), because I truly enjoy writing code.
