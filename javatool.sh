#!/bin/bash

# پروژه فعلی: e-commerce
JAR_FILE="target/e-commerce.jar"
MODULE_NAME="com.mtyn.ecommerce"
CUSTOM_RUNTIME="runtime-ecommerce"
JFR_FILE="recording.jfr"

echo "🔍 Running jdeps analysis..."
jdeps -s $JAR_FILE > jdeps-output.txt
echo "✅ jdeps output saved to jdeps-output.txt"

echo "⚙️ Creating custom JDK runtime with jlink..."
jlink --module-path $JAVA_HOME/jmods \
      --add-modules java.base,java.logging \
      --output $CUSTOM_RUNTIME
echo "✅ Custom runtime created at ./$CUSTOM_RUNTIME"

echo "📈 Starting Java Flight Recorder (JFR) recording..."
java -XX:StartFlightRecording=duration=20s,filename=$JFR_FILE -jar $JAR_FILE
echo "✅ JFR recording saved to $JFR_FILE"

echo "🧠 Listing running Java processes for jcmd..."
jcmd > jcmd-processes.txt
echo "✅ jcmd output saved to jcmd-processes.txt"

echo "Done ✅"
