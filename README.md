# Argus Camera Motion Detection

This is a video motion detection software, developed as part of argus ecosystem and used for building camera modules.

## Executing
This is a maven project

1) Generate a jar using maven

    ```mvn clean install -U dependency:copy-dependencies```
    
    After this command jar file can be found under target folder as *argus-camera-motion-detection-1.0-SNAPSHOT-shaded.jar*

2) Execute jar 
    
    Windows:
    
    ``java -jar target\argus-camera-motion-detection-1.0-SNAPSHOT-shaded.jar "c:/folder"``
    
    Linux:
    
    ``java -jar target/argus-camera-motion-detection-1.0-SNAPSHOT-shaded.jar "c:/folder"``
    
    Please pay attention in the argument, this argument is the directory where captured images will be saved. If the specified directory does not exists it will be created by the program.
