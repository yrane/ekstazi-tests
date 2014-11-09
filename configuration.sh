#!/usr/bin/env bash
#Source this file for environmental variables to be populated


ekstazi_version=4.0.1
junit_stuff=$HOME/.m2/repository/junit/junit/4.11/junit-4.11.jar
junit_stuff=$junit_stuff:$HOME/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar

export ekstazi_version=$ekstazi_version
export junit_stuff=$junit_stuff
