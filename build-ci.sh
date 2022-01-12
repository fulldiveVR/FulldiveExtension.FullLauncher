#!/bin/bash

#
#     Copyright (C) 2021 Lawnchair Team.
#
#     This file is part of Lawnchair Launcher.
#
#     Lawnchair Launcher is free software: you can redistribute it and/or modify
#     it under the terms of the GNU General Public License as published by
#     the Free Software Foundation, either version 3 of the License, or
#     (at your option) any later version.
#
#     Lawnchair Launcher is distributed in the hope that it will be useful,
#     but WITHOUT ANY WARRANTY; without even the implied warranty of
#     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#     GNU General Public License for more details.
#
#     You should have received a copy of the GNU General Public License
#     along with Lawnchair Launcher.  If not, see <https://www.gnu.org/licenses/>.
#

TRACK=$DRONE_BRANCH

if [[ $TAG =~ ([0-9]+).([0-9]+)(-([a-z]+).([0-9]+))? ]]; then
    export TAG_MAJOR=${BASH_REMATCH[1]}
    export TAG_MINOR=${BASH_REMATCH[2]}
    TRACK=${BASH_REMATCH[4]}
    if [[ ! -z $TRACK ]]; then
        TRACK_NAME="$(tr '[:lower:]' '[:upper:]' <<< ${TRACK:0:1})${TRACK:1}"
        RELEASE_NO=${BASH_REMATCH[5]}
        export TAG_VERSION_NAME="${TAG_MAJOR}.${TAG_MINOR} ${TRACK_NAME} ${RELEASE_NO}"
    else
        TRACK="stable"
        export TAG_VERSION_NAME="${TAG_MAJOR}.${TAG_MINOR}"
    fi
else
    if [[ ! -z "$TAG" ]]; then
        echo "Invalid tag: $TAG"
        exit 1
    fi
fi

if [ $DRONE_BRANCH = "beta" ] || [ $DRONE_BRANCH = "stable" ]; then
    BUILD_COMMAND="assembleQuickstepLawnchairPlahRelease"
else
    BUILD_COMMAND="assembleQuickstepLawnchairCiOptimized"
fi

echo "Running $BUILD_COMMAND"
bash ./gradlew $BUILD_COMMAND
