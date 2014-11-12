ekstazi_version=4.1.0
junit_stuff=$HOME/.m2/repository/junit/junit/4.11/junit-4.11.jar:$HOME/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar

mkdir -p v1 v2
cp T.java v1/
cp T.java v2/
javac -cp ${junit_stuff} Main.java v1/T.java
javac -cp ${junit_stuff} v2/T.java

echo running without Ekstazi
java -cp .:${junit_stuff} Main

echo running with Ekstazi does not clean the cache\!
rm -rf .ekstazi
java -javaagent:$HOME/.m2/repository/org/ekstazi/org.ekstazi.core/${ekstazi_version}/org.ekstazi.core-${ekstazi_version}.jar=mode=junit,dependencies.format=txt -cp .:${junit_stuff} Main
