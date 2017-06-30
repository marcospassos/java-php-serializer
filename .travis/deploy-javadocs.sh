#!/bin/bash
echo -e "\nPublishing javadoc..."

cp -R target/apidocs $HOME/javadoc-latest

cd $HOME
git config --global user.email "travis@travis-ci.org"
git config --global user.name "travis-ci"

echo -e "\nCloning gh-pages..."
git clone --quiet --branch=gh-pages https://${GH_TOKEN}@github.com/marcospassos/java-php-serializer gh-pages > /dev/null

echo -e "\nReplacing with updated javadoc..."
cd gh-pages
git rm -rf ./javadoc
cp -Rf $HOME/javadoc-latest/ ./javadoc
git add -f .
git commit -m "Update Javadoc (travis build #$TRAVIS_BUILD_NUMBER)"

echo -e "\nPushing..."
git push -fq origin gh-pages > /dev/null

echo -e "\nPushed javadoc to gh-pages."