ekstazi_version=4.1.0
junit_stuff=$HOME/.m2/repository/junit/junit/4.11/junit-4.11.jar:$HOME/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar

javac -cp ${junit_stuff} T.java
javac -cp $HOME/.m2/repository/org/ekstazi/org.ekstazi.core/${ekstazi_version}/org.ekstazi.core-${ekstazi_version}.jar:.:${junit_stuff} Main.java
java -javaagent:$HOME/.m2/repository/org/ekstazi/org.ekstazi.core/${ekstazi_version}/org.ekstazi.core-${ekstazi_version}.jar=mode=multi -cp .:${junit_stuff} Main

# java -javaagent:$HOME/.m2/repository/org/ekstazi/org.ekstazi.core/${ekstazi_version}/org.ekstazi.core-${ekstazi_version}.jar=mode=junit -cp .:${junit_stuff} org.junit.runner.JUnitCore T
