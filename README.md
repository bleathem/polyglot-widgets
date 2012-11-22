


The significant achievement here is a single application written in multiple languages/frameworks with a uniform L&F.
Polyglot-widgets: Components for a polyglot world!
=======================================================
Author: Brian Leathem
Technologies: JSF, RichFaces, Push, Errai, CDI, GWT
Summary: Demo of the PickList demo used in multiple frameworks

What is it?
-----------

A demo application demonstrating the use of RichFaces standalone widgets to create a "polyglot" application using both RichFaces/JSF, Errai/GWT, and HTML/REST.


System requirements
-------------------

All you need to build this project is Java 6.0 (Java SDK 1.6) or better, Maven 3.0 or better.

The application this project produces is designed to be run on JBoss Enterprise Application Platform 6 or JBoss AS 7. 

 
Configure Maven
---------------

If you have not yet done so, you must [Configure Maven](../README.md#mavenconfiguration) before running this demo.


Start JBoss Enterprise Application Platform 6 or JBoss AS 7 with the Web Profile
-------------------------

1. Open a command line and navigate to the root of the JBoss server directory.
2. The following shows the command line to start the server with the web profile:

        For Linux:   JBOSS_HOME/bin/standalone.sh
        For Windows: JBOSS_HOME\bin\standalone.bat

 
Build and Deploy the Quickstart
-------------------------

_NOTE: The following build command assumes you have configured your Maven user settings. If you have not, you must include Maven setting arguments on the command line. See [Build and Deploy the Quickstarts](../README.md#buildanddeploy) for complete instructions and additional options._

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. Type this command to build and deploy the archive:

        mvn clean package jboss-as:deploy

4. This will deploy `target/polyglot-widgets.war` to the running instance of the server.


Access the application 
---------------------

The application will be running at the following URL:  <http://localhost:8080/polyglot-widgets/>.


Undeploy the Archive
--------------------

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. When you are finished testing, type this command to undeploy the archive:

        mvn jboss-as:undeploy


Run the Application in GWT Dev Mode
---------------------------------------

GWT Dev Mode provides an edit-save-refresh development experience. If you plan to modify this demo, we recommend you start the application in Dev Mode so you don't have to repackage the entire application every time you change it.

1. Deploy the WAR file and start the JBoss Server as described above.
2. Open a command line and navigate to the polyglot-widgets directory
3. Execute the following command:

        mvn gwt:run


Run the Quickstart in JBoss Developer Studio or Eclipse
-------------------------------------
You can also start the server and deploy the quickstarts from Eclipse using JBoss tools. For more information, see [Use JBoss Developer Studio or Eclipse to Run the Quickstarts](../README.md#useeclipse) 


Debug the Application
------------------------------------

If you want to debug the source code or look at the Javadocs of any library in the project, run either of the following commands to pull them into your local repository. The IDE should then detect them.

        mvn dependency:sources
        mvn dependency:resolve -Dclassifier=javadoc
