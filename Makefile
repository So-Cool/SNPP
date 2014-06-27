all: snpp

snpp:
	if [ -z "${JAVA_HOME}" ]
	then
		echo "JAVA_HOME not set"
		exit 0
	fi

	if [ ! -x "${JAVA_HOME}/bin/java" ]
	then
		echo Cannot find java executable, check JAVA_HOME
		exit 0
	fi

	LIB=esper/lib
	CLASSPATH=.
	CLASSPATH=$CLASSPATH:target/classes
	CLASSPATH=$CLASSPATH:esper-5.0.0.jar
	CLASSPATH=$CLASSPATH:$LIB/cglib-nodep-3.1.jar
	CLASSPATH=$CLASSPATH:$LIB/commons-logging-1.1.3.jar
	CLASSPATH=$CLASSPATH:$LIB/log4j-1.2.17.jar
	CLASSPATH=$CLASSPATH:$LIB/antlr-runtime-4.1.jar
	export CLASSPATH="$CLASSPATH"

	if [ ! -d "target" ]
	then
		mkdir target
	fi

	if [ ! -d "target/classes" ]
	then
		mkdir target/classes
	fi

	SOURCEPATH=src/main/java
	
	${JAVA_HOME}/bin/javac -cp ${CLASSPATH} -d ../target/classes -source 1.6 -sourcepath ${SOURCEPATH} ${SOURCEPATH}/com/espertech/esper/example/marketdatafeed/FeedSimMain.java


run:
	MEMORY_OPTIONS="-Xms512m -Xmx512m -server -XX:+UseParNewGC"
	$JAVA_HOME/bin/java $MEMORY_OPTIONS -Dlog4j.configuration=log4j.xml -cp ${CLASSPATH} com.espertech.esper.example.marketdatafeed.FeedSimMain $1 $2 $3


#clean:
#	rm -rf *o hello