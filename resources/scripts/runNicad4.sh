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



##!/bin/bash
#
#echo $line
#echo "Input File Building..........."
#
#    cd /home/jubair/SPL3/tools/NiCad-4.0
#
#    nicad4 functions java $1
#
#    echo "Project full path is: ${1}"
#
#    IFS=/
#    ary=($1)
#    projectName=${ary[-1]}
#
#    echo "Project is $projectName"
#
#    echo "File copying in the output directory"
#	cp -r /home/jubair/SPL3/test_projects/"${projectName}_functions-clones" /home/jubair/SPL3/test_projects/cloneResult/
#	cp /home/jubair/SPL3/test_projects/"${projectName}_functions.xml" /home/jubair/SPL3/test_projects/cloneResult/"${projectName}_functions-clones"/"${projectName}_functions.xml"
#
#	echo "Cleaning  directory"
#	rm -r /home/jubair/SPL3/test_projects/"${projectName}_functions-clones"
#	rm -f /home/jubair/SPL3/test_projects/"${projectName}_functions.xml"
#	rm -f /home/jubair/SPL3/test_projects/"${projectName}_functions-clones-"*
#
#echo "Successfully Done"
