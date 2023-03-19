# ServerHost

## A project to make managing multiple servers (or applications) simple and in one place

### Requirements

This app was compiled with java 18.

If you have an earlier version of java (you can check by typing java -version into a command line), you will need to either download the latest version of java
or follow the extra instructions below to recompile the app.  

### To install

In this github, navigate to app/build/distributions and download app.zip.
Once downloaded, unzip the contents to a location of your choosing.  In the same place you unzipped the file, make a new text file called ServerList.
If you're on Windows, create a new batch file with the below contents

```
cd \<location where you unzipped the app\>
start /w /app/bin/app.bat commandLine
```

If you're on Linux, type the below command, then create a new file with the following contents
```
  "chmod +x \<location where you unzipped the app\>/app/bin/app" 
  (this changes the permissions on the file to allow you to run the app script)
```
```
#!/bin/sh
cd \<location where you unzipped the app\>
./app/bin/app
```

That file will need to have its permissions set too so type:
```
"chmod +x \<the name of the script you just wrote\>"
```
To run the app, just execute that script.

### Recompiling the app

Download the entire repository to a location of your choosing then open a command terminal there.

Run `graldew.bat install` if you're on Windows or `./gradlew install` if you're on Linux.

Copy the newly generated files under app/build/install to a destination of your choosing then follow the normal instructions.
