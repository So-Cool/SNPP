lib=exlib/esper/lib
libe=exlib
classpath=.:target/classes:$(libe)/esper-5.0.0.jar:$(libe)/commons-collections4-4.0.jar:$(libe)/commons-io-2.4.jar:$(libe)/commons-math3-3.3.jar:$(libe)/weka.jar:$(lib)/cglib-nodep-3.1.jar:$(lib)/commons-logging-1.1.3.jar:$(lib)/log4j-1.2.17.jar:$(lib)/antlr-runtime-4.1.jar
sourcepath=src
memoryoptions=-Xms512m -Xmx512m -server -XX:+UseParNewGC

all: compile_ex
in: compile_in
run: run_ex
clean: clean_all

compile_ex:
	@if [ -z "$${JAVA_HOME}" ] ; then echo "JAVA_HOME not set" & echo "In redHat it might be: /usr/lib/jvm/java-1.7.0-openjdk.x86_64" & echo 'In OS X it is: export JAVA_HOME=$$(/usr/libexec/java_home)' & echo "To set execute: export JAVA_HOME=path/to/your/java/environment" & exit 0 ; fi
	@if [ ! -x "$${JAVA_HOME}/bin/java" ] ; then echo Cannot find java executable, check JAVA_HOME & exit 0 ; fi
	@if [ ! -d "target" ] ; then mkdir target ; fi
	@if [ ! -d "target/classes" ] ; then mkdir target/classes ; fi
	@$${JAVA_HOME}/bin/javac -cp ${classpath} -d target/classes -source 1.6 -sourcepath $(sourcepath) $(sourcepath)/engine/DriverExternal.java
	@$${JAVA_HOME}/bin/javac -cp ${classpath} -d target/classes -source 1.6 $(sourcepath)/featureExtractors/FeatureExtractor.java

compile_in:
	@if [ -z "$${JAVA_HOME}" ] ; then echo "JAVA_HOME not set" & echo "In redHat it might be: /usr/lib/jvm/java-1.7.0-openjdk.x86_64" & echo 'In OS X it is: export JAVA_HOME=$$(/usr/libexec/java_home)' & echo "To set execute: export JAVA_HOME=path/to/your/java/environment" & exit 0 ; fi
	@if [ ! -x "$${JAVA_HOME}/bin/java" ] ; then echo Cannot find java executable, check JAVA_HOME & exit 0 ; fi
	@if [ ! -d "target" ] ; then mkdir target ; fi
	@if [ ! -d "target/classes" ] ; then mkdir target/classes ; fi
#	@$${JAVA_HOME}/bin/javac -cp ${classpath} -d target/classes -source 1.6 -sourcepath $(sourcepath) $(sourcepath)/engine/Driver.java

run_ex:
	@$$JAVA_HOME/bin/java ${memoryoptions} -Dlog4j.configuration=log4j.xml -cp ${classpath} engine.DriverExternal
	
run_in:
	@$$JAVA_HOME/bin/java ${memoryoptions} -Dlog4j.configuration=log4j.xml -cp ${classpath} engine.Driver

clean_all:
	rm -rf target
