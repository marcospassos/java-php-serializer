# Change Log
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/)
and this project adheres to [Semantic Versioning](http://semver.org/).

## Unreleased
There are currently no unreleased changes.

## [0.7.0](https://github.com/marcospassos/java-php-serializer/releases/tag/0.7.0) (2018-02-28)

### Changed

- Remove parameter `charset` from `SerializerBuilder::registerBuiltinAdapters()` in favor of `SerializerBuilder::setCharset()`

## [0.6.0](https://github.com/marcospassos/java-php-serializer/releases/tag/0.6.0) (2018-02-28)

### Changed

- Introduce adapter for `Long` type as discussed in [#2](https://github.com/marcospassos/java-php-serializer/issues/2). 
- Fix missing builtin adapters reported in [#2](https://github.com/marcospassos/java-php-serializer/issues/2).
- Add support for string encoding, fixing cases where strings are badly encoded, as reported in [#3](https://github.com/marcospassos/java-php-serializer/issues/3).

## [0.5.2](https://github.com/marcospassos/java-php-serializer/releases/tag/0.5.2) (2017-07-12)

### Changed

- Remove unnecessary quote escaping from string serialization.


## [0.5.1](https://github.com/marcospassos/java-php-serializer/releases/tag/0.5.1) (2017-07-09)

### Changed

- Fix reference counting logic.

## [0.5.0](https://github.com/marcospassos/java-php-serializer/releases/tag/0.5.0) (2017-07-09)

### Changed

- Fix visibility of method `Writer::writeSerializableObjectEnd()`.

## [0.4.1](https://github.com/marcospassos/java-php-serializer/releases/tag/0.4.1) (2017-07-09)

### Changed

- Fix reference counting logic.
- Set pointer in sub writes to keep parent's writer count.

## [0.4.0](https://github.com/marcospassos/java-php-serializer/releases/tag/0.4.0) (2017-07-09)

### Changed

- Improve `Writer`'s API for writing serializable objects.
- Fix reference counting that breaks after writing serializable objects.

## [0.3.0](https://github.com/marcospassos/java-php-serializer/releases/tag/0.3.0) (2017-07-07)

### Changed

- Calling `SerializerBuilder::registerBuiltinAdapters()` now registers
`CollectionAdapter` for any collection and not only for `Map` and `Set`.

## [0.2.0](https://github.com/marcospassos/java-php-serializer/releases/tag/0.2.0) (2017-07-06)

### Changed

- Rename `ListMap` to `CollectionAdapter` since it supports any collection.

### Added

- Adapter for `Set` in the list of builtin adapters registered by 
`SerializerBuilder::registerBuiltinAdapters()`. 

## [0.1.0](https://github.com/marcospassos/java-php-serializer/releases/tag/0.1.0) (2017-06-29)

- First beta release
