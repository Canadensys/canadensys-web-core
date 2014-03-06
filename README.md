canadensys-web-core
===================
Canadensys web core library.

Artifacts:

* canadensys-web-core: jar library 
* canadensys-webapp-core: webapp folder built as war file 

Code Status
-----------
[![Build Status](https://travis-ci.org/Canadensys/canadensys-web-core.png)](https://travis-ci.org/Canadensys/canadensys-web-core)

Freemaker templates
-------------------
[Freemarker templates](https://github.com/Canadensys/canadensys-web-core/tree/master/canadensys-webapp-core/src/main/webapp/WEB-INF/view/inc) including
utility functions, Google Analytics support and paging support.

Usage
-----

The library 'canadensys-webapp-core' should be used as a WAR overlay since it includes 'webapp' related content.

* Gradle: https://github.com/scalding/gradle-waroverlay-plugin
* Maven: http://maven.apache.org/plugins/maven-war-plugin/overlays.html

