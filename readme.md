# File-Sort
File-Sort is a Java based Desktop app which helps people to arrange their pictures and videos in a separate folders by WEEK or MONTH.
It takes as input a source folder with pictures/video and gives the output as sorted folders with pictures and video.

- Run the application

![Screenshot](https://github.com/scorobogaci/file-sorting-app/blob/master/src/main/resources/screenshots/application-start.PNG)

Select source folder and destination folder :

![Screenshot](https://github.com/scorobogaci/file-sorting-app/blob/master/src/main/resources/screenshots/application-input.PNG)

Application will start to process your files and show you the progress 

![Screenshot](https://github.com/scorobogaci/file-sorting-app/blob/master/src/main/resources/screenshots/application-progress.PNG)

Once done, destination folder is opened

# Download 
Application can be downloaded here
Windows :
Linux :
IOS :

# Technologies

File sort uses the following libraries :

* Java 8 - Java 8 Development Kit!
* packr - Used for packaging the JAR, assets and a JVM for distribution on Windows, Linux and Mac OS X
* metadata-extractor - Extracts Exif, IPTC, XMP, ICC and other metadata from image and video files.
* Maven - Dependency management and build tool

# Installation

Extract zip archieve and enjoy 

# Development

This is a maven project

Build the project
```sh
$ mvn clean install
```
For packaging the project for all distributive Windows,Linux or MAC OS

```sh
$ mvn clean install -Pwindows-profile,linux-profile,mac-profile
```

# Todos

 - Write MORE Tests
 - Add capability to compress the files in zip or other format for different distributive
 - Integrate it with Travis CI
 - Upload application to cloud platform available for download
 
