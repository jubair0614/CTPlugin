#!/bin/bash

mkdir /home/jubair/SPL3/test_projects/cloneResult/dnsjava

echo $line
echo $1

    echo "Input File Building..........."
	cd /home/jubair/SPL3/tools/SourcererCC/parser/java
	java -jar InputBuilderClassic.jar $1/$line /home/jubair/SPL3/tools/SourcererCC/input/dataset/tokens.file /home/jubair/SPL3/tools/SourcererCC/input/bookkeping/headers.file functions java 0 0 0 0 false false false 8

	echo "Indexing input.............."
	cd /home/jubair/SPL3/tools/SourcererCC/
	java -jar dist/indexbased.SearchManager.jar index 8

	echo "Searching clone.............."
	java -jar dist/indexbased.SearchManager.jar search 8

	echo "File copying in the output directory"
	mkdir /home/jubair/SPL3/test_projects/cloneResult/dnsjava/$line
	cp /home/jubair/SPL3/tools/SourcererCC/input/bookkeping/headers.file /home/jubair/SPL3/test_projects/cloneResult/dnsjava/$line
	cp /home/jubair/SPL3/tools/SourcererCC/output8.0/tokensclones_index_WITH_FILTER.txt /home/jubair/SPL3/test_projects/cloneResult/dnsjava/$line

	echo "Cleaning  directory"
	rm -r fwdindex gtpm index output output8.0
	rm -f /home/jubair/SourcererCC/input/dataset/tokens.file
	rm -f /home/jubair/SourcererCC/input/bookkeping/headers.file

	echo "Renamining Output Directory file"
	cd /home/jubair/SPL3/test_projects/cloneResult/dnsjava/$line
	mv headers.file PathID.txt
	mv tokensclones_index_WITH_FILTER.txt ClonePairID_Similarity_80.txt

	echo "Successfully Done"
