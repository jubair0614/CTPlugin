#!/bin/bash

echo $line
echo "Input File Building..........."

    cd /home/$USER/tool2/NiCad-4.0

    nicad4 functions java $1

    echo "Project full path is: ${1}"

    IFS=/
    ary=($1)
    projectName=${ary[-1]}

    echo "Project is $projectName"

    echo "File copying in the output directory"
    mkdir /home/$USER/tool2/cloneResult/
	cp -r "${1}_functions-clones" /home/$USER/tool2/cloneResult/
	cp "${1}_functions.xml" /home/$USER/tool2/cloneResult/"${projectName}_functions-clones"/"${projectName}_functions.xml"

	echo "Cleaning  directory"
	rm -r "${1}_functions-clones"
	rm -f "${1}_functions.xml"
	rm -f "${1}_functions-clones-"*

echo "Successfully Done"
