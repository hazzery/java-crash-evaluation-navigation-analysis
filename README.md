# SENG202 Team 2 JCENA
## Overview
- This application is created for users to view crash data provided by Waka Kotahi. It is made for learner/ restricted drivers and their instructors to navigate and query in order to identify areas of interest (such as places with low crashes).

## Authors
- Ben Moore
- Findlay Royds
- Harrison Parkes
- Isaac Ure
- James Lanigan
- Louis Hobson
- Matthew Lang

## Prerequisites
- JDK >= 17 [click here to get the latest stable OpenJDK release (as of writing this README)](https://jdk.java.net/18/)
- Gradle [Download](https://gradle.org/releases/) and [Install](https://gradle.org/install/)


## What's Included
This project provides a heatmap view of Waka Kotahi's crash data with a number of querying options including:
- Year sort
- Region sort
- Severity sort
- Vehicle sort

And a table view for extra information.
## Importing Project (Using IntelliJ)
IntelliJ has built-in support for Gradle. To import your project:

- Launch IntelliJ and choose `Open` from the start up window.
- Select the project and click open
- At this point in the bottom right notifications you may be prompted to 'load gradle scripts', If so, click load

**Note:** *If you run into dependency issues when running the app or the Gradle pop up doesn't appear then open the Gradle sidebar and click the Refresh icon.*

## Running bundled Jar
You can run the bundled .jar file by opening a terminal window in the project directory and executing `java -jar seng202_2023_team2_2.jar`

## Build Project 
1. Open a command line interface inside the project directory and run `./gradlew jar` to build a .jar file. The file is located at `build/libs/seng202_team2-1.0-SNAPSHOT.jar`

## Run App
- If you haven't already, Build the project.
- Open a command line interface inside the project directory and run `cd target` to change into the target directory.
- Run the command `java -jar seng202_team2-1.0-SNAPSHOT.jar` to open the application.
