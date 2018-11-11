#!/bin/bash
set -ev
echo $TRAVIS_BUILD_DIR
cd $TRAVIS_BUILD_DIR/Lecture_4_Threads_1
mvn test
