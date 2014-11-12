JUNIT=4.8.1
javac -cp $HOME/.m2/repository/junit/junit/4.8.1/junit-4.8.1.jar:$HOME/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar JunitRunTest2.java
java JunitRunTest2 dummy 

#java -cp .:$HOME/.m2/repository/junit/junit/4.8.1/junit-4.8.1.jar:$HOME/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar org.junit.runner.JUnitCore JunitRunTest2
