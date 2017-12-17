#!/usr/bin/env bash

mkdir /home/$USER/tool2
unzip equipments/NiCad-4.0.zip -d /home/$USER/tool2/

cd /home/$USER/tool2/NiCad-4.0
make

sudo mkdir /usr/local/lib/nicad4
sudo cp -r . /usr/local/lib/nicad4

cp nicad4 nicad4cross /usr/local/bin