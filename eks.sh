wget mir.cs.illinois.edu/gliga/projects/ekstazi/release/org.ekstazi.core-4.3.1.jar
wget mir.cs.illinois.edu/gliga/projects/ekstazi/release/org.ekstazi.ant-4.3.1.jar
wget mir.cs.illinois.edu/gliga/projects/ekstazi/release/ekstazi-maven-plugin-4.3.1.jar

mvn install:install-file -Dfile=org.ekstazi.core-4.3.1.jar -DgroupId=org.ekstazi -DartifactId=org.ekstazi.core -Dversion=4.3.1 -Dpackaging=jar -DlocalRepositoryPath=$HOME/.m2/repository/
mvn install:install-file -Dfile=ekstazi-maven-plugin-4.3.1.jar -DgroupId=org.ekstazi -DartifactId=ekstazi-maven-plugin -Dversion=4.3.1 -Dpackaging=jar -DlocalRepositoryPath=$HOME/.m2/repository/
