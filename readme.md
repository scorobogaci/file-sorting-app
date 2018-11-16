# File-Sort
File-Sort is a Java based Desktop app which helps people to arrange their pictures and videos in a separate folders by WEEK or MONTH.
It takes as input a source folder with pictures/video and gives the output as sorted folders with pictures and video.

- Run the application

![Screenshot](https://github.com/scorobogaci/file-sorting-app/blob/master/src/main/resources/screenshots/application-start.PNG)

- Select source folder and destination folder :

![Screenshot](https://github.com/scorobogaci/file-sorting-app/blob/master/src/main/resources/screenshots/application-input.PNG)

- Application will start to process your files and show you the progress 

![Screenshot](https://github.com/scorobogaci/file-sorting-app/blob/master/src/main/resources/screenshots/application-progress.PNG)

- Once done, destination folder is opened

# Download 
Application can be downloaded here
Windows :
Linux :
IOS :

# Technologies

File sort uses the folowing libraries :

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

   [dill]: <https://github.com/joemccann/dillinger>
   [git-repo-url]: <https://github.com/joemccann/dillinger.git>
   [john gruber]: <http://daringfireball.net>
   [df1]: <http://daringfireball.net/projects/markdown/>
   [markdown-it]: <https://github.com/markdown-it/markdown-it>
   [Ace Editor]: <http://ace.ajax.org>
   [node.js]: <http://nodejs.org>
   [Twitter Bootstrap]: <http://twitter.github.com/bootstrap/>
   [jQuery]: <http://jquery.com>
   [@tjholowaychuk]: <http://twitter.com/tjholowaychuk>
   [express]: <http://expressjs.com>
   [AngularJS]: <http://angularjs.org>
   [Gulp]: <http://gulpjs.com>

   [PlDb]: <https://github.com/joemccann/dillinger/tree/master/plugins/dropbox/README.md>
   [PlGh]: <https://github.com/joemccann/dillinger/tree/master/plugins/github/README.md>
   [PlGd]: <https://github.com/joemccann/dillinger/tree/master/plugins/googledrive/README.md>
   [PlOd]: <https://github.com/joemccann/dillinger/tree/master/plugins/onedrive/README.md>
   [PlMe]: <https://github.com/joemccann/dillinger/tree/master/plugins/medium/README.md>
   [PlGa]: <https://github.com/RahulHP/dillinger/blob/master/plugins/googleanalytics/README.md>
