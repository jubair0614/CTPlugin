#!/bin/bash

echo $line
echo "Input File Building..........."

echo $1
    cd /home/jubair/SPL3/tools/NiCad-4.0

    nicad4 functions java $1

    echo "File copying in the output directory"
	cp -r /home/jubair/SPL3/test_projects/JHotDraw54b1_functions-clones /home/jubair/SPL3/test_projects/cloneResult/
	cp /home/jubair/SPL3/test_projects/JHotDraw54b1_functions.xml /home/jubair/SPL3/test_projects/cloneResult/JHotDraw54b1_functions-clones/JHotDraw54b1_functions.xml

	echo "Cleaning  directory"
	rm -r /home/jubair/SPL3/test_projects/JHotDraw54b1_functions-clones
	rm -f /home/jubair/SPL3/test_projects/JHotDraw54b1_functions.xml
	rm -f /home/jubair/SPL3/test_projects/JHotDraw54b1_functions-clones-*

echo "Successfully Done"
