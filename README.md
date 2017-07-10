Java PHP Serializer
===================
[![Build Status][travis-badge]][travis-status]
[![Coverage Status][coverall-badge]][coverall-status]
[![Java 7+][java-badge]][java]
[![License][mit-license-badge]](LICENSE)

Latest release: [![Maven Central][maven-central-badge]][maven-central-latest]

A Java library for serializing objects as PHP serialization format.

The library fully implements the PHP serialization format specification, which 
includes:

- Scalar values
- Objects
- Serializable (custom serializable objects)
- Object and variable references

The [API documentation][api-docs] is available on GitHub Pages more convenient viewing in
browser.

> **A word of notice**
>
> This library does not provide any mechanism for creating a communication
> channel between Java and PHP. For such purpose, consider using 
[Soluble Java][soluble-java].

## Use case

One of the easier way to exchange data between Java and PHP consists in
serializing the value to a data-interchanging format, such as JSON or XML,
send it through a communication channel and finally deserialize it back to the
original form on the PHP side. The problem with this approach is that the cost
of deserializing complex objects in PHP is very high.

Most of the serialization libraries in PHP use reflection for re-hydrating
objects, and it becomes an issue when you have to deserialize large structures
with hundreds of objects. Fortunately, PHP's native serialization is fast and 
the `unserialize()` function can handle such cases in a few milliseconds.

This library implements the full format specification through a friendly API 
that encapsulates the complexity of the serialization process.

## Installation

### Maven

The PHP Serializer is available in the 
[Maven Central repository][maven-central-latest].
Any Maven based project can use it directly by adding the appropriate entries
to the `dependencies` section of its `pom.xml` file:

```xml
<dependencies>
  <dependency>
    <groupId>com.marcospassos</groupId>
    <artifactId>phpserializer</artifactId>
    <version>0.5.0</version>
  </dependency>
</dependencies>
```

### Binaries

Packaged JARs can be downloaded directly from the [releases page][releases-page] 
and extracted using tar or unzip.

## Usage

### How to serialize data

The first step to serialize a Java object into a PHP serialization format
string is to create a Serializer instance according to your application domain 
model. The library ships a builder that help us with this task:

```java
Serializer serializer = new SerializerBuilder()
  // Adds a custom exclusion strategy to determine which field
  // should be serialized or not (default: no exclusion)
  .addExclusionStrategy(new MyCustomExclusionStrategy())
  // Sets the naming strategy to convert the name of classes
  // and fields from Java to PHP (default: PsrNamingStrategy)
  .setNamingStrategy(new MyCustomNamingStrategy())
  // Registers all builtin adapters (default: all built-in adapters)
  .registerBuiltinAdapters()
  // Register a custom type adapter
  .registerAdapter(CustomObject.class, new CustomObjectAdapter())
  // Creates the serialized based on the given configuration
  .build();
```

Now you have a `Serializer` instance configured according to your application
domain model. To serialize a value, just invoke `serialize(Object object)` on
the serializer instance:

```java
List list = new ArrayList();
list.add("A string");
list.add(12345);
list.add(true);

// Outputs: a:3:{i:0;s:8:"A string";i:1;i:12345;i:2;b:1;}
System.out.println(serializer.serialize(list));
```

### Naming strategies

A naming strategy allows you to translate the name of classes and fields
according to the target domain model. The library ships with a built-in adapter
that converts Java packages to PHP namespaces in accordance with PSR-1 rules.
If the PSR strategy does not fit your needs you can easily implement a custom 
naming strategy. Takes as reference the following strategy that appends an 
underscore to all private fields:

```java
public class UnderscoreNamingStrategy implements NamingStrategy
{
    public String getClassName(Class type)
    {
        return type.getName();
    }

    public String getFieldName(Field field)
    {
        if (Modifier.isPrivate(field.getModifiers())) {
            return "_" + field.getName();
        }

        return field.getName();
    }
}
```

### Type Adapters

A type adapter provides the serializer the logic for how to encode a specific 
type. The following example shows how to create a custom type adapter
to serialize Enums as string using the name of the enum constant:

```java
public class EnumTypeAdapter implements TypeAdapter<Enum>
{
    public void write(Enum value, Writer writer, Context context)
    {
        writer.writeString(value.name());
    }
}
```

Notice that circular references are handled as per case basis, once it is not
always the desirable. For instance, you may not want to serialize lists as
objects references, as array is probably the most appropriate corresponding
type in PHP. The following example shows how to handle such cases:

```java
public class MyCustomAdapter implements TypeAdapter<CustomObject>
{
    @Override
    public void write(CustomObject object, Writer writer, Context context)
    {
        int reference = context.getReference(object);

        if (reference > 0) {
            writer.writeObjectReference(reference);

            return;
        }

        context.setReference(reference, object);

        // Custom serialization logic
    }
}
```

## Change log

Please see [CHANGELOG](CHANGELOG.md) for more information what has changed 
recently.

## Contributing

Contributions to the package are always welcome!

* Report any bugs or issues you find on the [issue tracker][issue-tracker].
* You can grab the source code at the package's
[Git repository][repository].

Please see [CONTRIBUTING](CONTRIBUTING.md) and [CONDUCT](CONDUCT.md) for
details.

## Security

If you discover any security related issues, please email
marcos@marcospassos.com instead of using the issue tracker.

## Credits

* [Marcos Passos][author-page]
- [All Contributors][contributors-page]


## License

All contents of this package are licensed under the [MIT license](LICENSE).

```
Copyright (c) 2017 Marcos Passos <marcos@marcospassos.com>

Permission is hereby granted, free of charge, to any person obtaining a
copy of this software and associated documentation files (the "Software"),
to deal in the Software without restriction, including without limitation
the rights to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom the
Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
DEALINGS IN THE SOFTWARE.
```

[maven-central-badge]: https://img.shields.io/badge/maven%20central-v0.5.0-blue.svg
[maven-central-latest]: http://search.maven.org/#artifactdetails%7Ccom.marcospassos%7Cphpserializer%7C0.5.0%7Cjar
[coverall-status]: https://coveralls.io/github/marcospassos/java-php-serializer
[coverall-badge]: https://coveralls.io/repos/github/marcospassos/java-php-serializer/badge.svg
[travis-badge]: https://travis-ci.org/marcospassos/java-php-serializer.svg?branch=master
[travis-status]: https://travis-ci.org/marcospassos/java-php-serializer
[java-badge]: https://img.shields.io/badge/java-7+-4c7e9f.svg
[java]: http://java.oracle.com
[mit-license-badge]: https://img.shields.io/badge/license-MIT-blue.svg
[api-docs]: https://marcospassos.github.io/java-php-serializer/docs/api/latest/
[soluble-java]: https://github.com/belgattitude/soluble-japha
[author-page]: http://github.com/marcospassos
[contributors-page]: https://github.com/marcospassos/java-php-serializer/graphs/contributors
[issue-tracker]: https://github.com/marcospassos/java-php-serializer/issues
[repository]: https://github.com/marcospassos/java-php-serializer
[releases-page]: https://github.com/marcospassos/java-php-serializer/releases
[latest-release]: https://github.com/marcospassos/java-php-serializer/releases/tag/0.5.0
