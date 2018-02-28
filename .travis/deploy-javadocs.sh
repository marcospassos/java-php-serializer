#!/usr/bin/env bash

set -e

if [ -z ${GH_TOKEN} ]; then
    echo "\$GH_TOKEN is unset"
    exit 1
fi

if [ -z ${TRAVIS_REPO_SLUG} ]; then
    echo "\$TRAVIS_REPO_SLUG is unset"
    exit 1
fi

# Build parameters
REPOSITORY=${REPOSITORY:-"https://${GH_TOKEN}@github.com/${TRAVIS_REPO_SLUG}.git"}
BRANCH=${BRANCH:-"gh-pages"}
POM_VERSION=$(xmllint --xpath "//*[local-name()='project']/*[local-name()='version']/text()" pom.xml)
VERSION_MAJOR="$(echo "${POM_VERSION}" | cut -d '.' -f 1)"
VERSION_MINOR="$(echo "${POM_VERSION}" | cut -d '.' -f 2)"
VERSION_SHORT="${VERSION_MAJOR}.${VERSION_MINOR}"

# Commit settings
COMMIT_AUTHOR_NAME="Travis"
COMMIT_AUTHOR_EMAIL="travis@travis-ci.org"
COMMIT_MESSAGE="Update documentation to version ${VERSION_SHORT}"

# Documentation paths
API_DOCS_SRC="${TRAVIS_BUILD_DIR}/target/apidocs"
API_DOCS="docs/api"
API_LATEST="${API_DOCS}/latest"
API_DIR=VERSION_SHORT
API_VERSION="${API_DOCS}/${API_DIR}"

echo "Updating javadocs to version ${VERSION_SHORT}"

# Import repository
echo "Cloning repository..."
git clone "${REPOSITORY}" gh-pages --branch "${BRANCH}" --depth 1

# Change working directory
cd "gh-pages"

# Set identity
git config user.name "${COMMIT_AUTHOR_NAME}"
git config user.email "${COMMIT_AUTHOR_EMAIL}"

rm -Rf "${API_VERSION}"
mkdir -p "${API_VERSION}"
cp -Rf "${API_DOCS_SRC}/." "${API_VERSION}"
# Create a latest link which points to the last version
rm -f "${API_LATEST}"
ln -sf "${API_DIR}" "${API_LATEST}"	

# Record changes to the repository
git add .
git commit -m "${COMMIT_MESSAGE}"

# Push changes to remote server
git push origin HEAD

echo "Documentation successfully updated to version ${VERSION_SHORT}"