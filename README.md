GraalVM Native Image Demo

The [GraalVM](https://www.graalvm.org/) is a still relatively new technology that fascinates me and reminds me of a good bit of the history of Java the language and the Java VM.

I recall, in the very early days of Java, writing some AWT, and then Swing UI applications, which were so slow they were literally unusable but still I was amazed that I could move the byte code from one platform to another and it ran (everywhere, as we used to say).  Not pretty, not fast but no "if defs" like we had in C as we moved from one OS to another.

I also am reminded of the days when Java was "fast enough" because a Java-based application server beat the performance of a natively compile application server as announced at a JavaOne session some years back.  Both servers remained nameless (I assume the attorneys had something to say about that) but I think we know what was being referenced.  The main point being that Java and it's VM had, at that point, come a long way and performance was more than respectable.

Next the community collectively spent a good number of years ensuring that the JVM supported a very large number of languages which made cross platform, performance, and security benefits available to the masses.

So in my mind, the idea of creating native binaries (or images as we are currently calling them) which are platform specific is, at least in one respect, coming full circle.  Nowadays you might already be running such an application in a container on an orchestration platform...or planning to do so.

But, I digress.  This little project, which I imagine will relatively soon have little to no value, was created to serve as an example of moving from familiar Java byte-code to a native executable.  I believe its value will quickly diminish because of the quality and development speed of the GraalVM in general.  I believe I first heard about the GraalVM at a JavaOne but have not, until recently, had a real need to leverage it in production.  I think my timing, albeit lucky, is actually pretty good because I am finding it quite usable at least for the use cases before me.

## Setup

Given that this project leverages the GraalVM (Java 11), along with the native-image component, this is probably a good time to download both from [the Github project](https://github.com/graalvm/graalvm-ce-builds/releases) if you don't already have them installed for your platform.

I tend to keep a good number of JVM's on any machine I use on a regular basis with any one of them configured as current depending on my needs.  Ensuring that the JAVA_HOME environment variable is set to the GraalVM and that $JAVA_HOME/bin in on the PATH (before any other Java) is important.  I am assuming knowledge of such a configuration.

With the GraalVM installed, the next step is to install the native-image component.  This can be done with the following command:

```
gu install native-image
```

If you're behind a corporate firewall, the above command may not work.  Fortunately, the compoent can also be installed "locally" with this command:

```
gu install -L NAME_OF_DOWNLOADED_native-image_JAR_FILE (e.g. native-image-installable-svm-java11-linux-amd64-20.0.0.jar
)
```

I have experience with the GraalVM on Linux and the MacOS but I have read nothing that makes me believe there might be issues doing so on Windows.  The project is also Maven-based so if you're not using Maven from your favorite IDE or via the command line, you'll want to download and install Maven as well.

## Hello World Part 1

The Hello World project is a relatively simple Java application which was designed to run from the command line as a standalone jar file requiring Java to be available.  Change into the **hello-world** diectory.

```
cd hello-world
```

To build the project from the command line, execute the following maven command:

```
mvn clean install
```

If the build was successful, you should be able to run the program with this command:

```
java -jar target/hello-world-1.0.jar
```

Along with some unimpressive output about widget creations and displays, a log file, named helloworld.log, should have been created.  The fact that the program ran as a jar file and created the log file is good.

Now delete the log file:

```
rm helloworld.log
```

The maven build should have also create a native executable that we can run with this command:

```
target/helloworld
```

If this worked as is, and I suspect it will before too much longer, we could stop and move on to creating some real-world platform-specific binaries.  But for the time being, chances are there are some errors displayed and no log file created.

Specifically, the process of creating a native image is not completely working with classes which do not include a "no args" constructor.

## Hello World Part 2

Assuming you're still in the **hello-world** directory, the command below will get you to the next step

```
cd ../hello-world-2
```

The GraalVM folks created for us an agent which can assist us with the configuraiton of native image builds.  Certainly, this can be done manually, and may in fact require some manual intervention but it is a tremendous help.  You can read more about the agent [here](https://github.com/oracle/graal/blob/master/substratevm/CONFIGURE.md).  I do not know why, but I did not stumble across this page as soon as I would have liked.  On the other hand, I did create some configuration files before finding it which helped me learn a good bit about the process.

As in Part 1, we first need to build the project:

```
mvn clean install
```

If that was successful, we should have a jar file which we can run, along with the agent, to produce the needed configuration files.  The following command should yield the desired results:

```
java -agentlib:native-image-agent=config-output-dir=config -jar target/hello-world-1.0.jar
```

After running the application and agent, there should be some files of interest created in the config directory.  These files are the **reflect-config.json** and the **resource-config.json**.  As you might imagine, they will assist the native-image build process with reflection, used to determine which classes are included and how, along with which resources are to be added to the image.  For more details on the files, see the above.

In order to get the native-image maven plugin to respect these files we add two parameters to the **buildArgs** tag which are: -H:ReflectionConfigurationFiles=../config/reflect-config.json -H:ResourceConfigurationFiles=../config/resource-config.json

Now that we have the files, we can make a second attempt at creating a native image for our platform of choice.  This time, we'll use the maven command below to select the native image profile.

```
mvn clean install -Pnative-image
```

Running the resulting native image can be done with this command:

```
target/helloworld
```

The program seems to run OK, but there are warnings:

```
log4j:WARN No such property [conversionPattern] in org.apache.log4j.PatternLayout.
log4j:WARN No such property [file] in org.apache.log4j.RollingFileAppender.
log4j:WARN No such property [append] in org.apache.log4j.RollingFileAppender.
log4j:WARN No such property [maxFileSize] in org.apache.log4j.RollingFileAppender.
log4j:WARN No such property [maxBackupIndex] in org.apache.log4j.RollingFileAppender.
log4j:WARN File option not set for appender [logfile].
log4j:WARN Are you using FileAppender instead of ConsoleAppender?
log4j:ERROR No output stream or file set for the appender named [logfile].
```

## Hello World Part 3

Moving on to part 3, sssuming you're still in the **hello-world-2** directory, the command below will get you to the next step

```
cd ../hello-world-3
```

Although the output from the native-image-agent which can be found in the config directory in part 2 is very good, it had to be manually
tweaked a bit in order to get rid of the warnings we saw when running the native-image.

Build the native image by using the native-image profile.

```
mvn clean install -Pnative-image
```

Run the native image again:

```
target/helloworld
```
