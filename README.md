PHP Serializer for Java
=======================
[![Build Status](https://travis-ci.org/marcospassos/java-php-serializer.svg?branch=master)](https://travis-ci.org/marcospassos/java-php-serializer)
[![Coverage Status](https://coveralls.io/repos/github/marcospassos/java-php-serializer/badge.svg)](https://coveralls.io/github/marcospassos/java-php-serializer)

A Java library for serializing objects into PHP serialization format.

The library fully implements the format specification, which includes:

- Scalar values
- Objects
- Serializable (custom serializable objects)
- Object and variable references

> **A word of notice**
>
> This library does not provide any mechanism for creating a communication
> channel between Java and PHP. For such purpose consider using [Soluble Java]().

## Use case

This library turns out useful when your need to send complex data structures
from Java to PHP, but performance is a concern.

One of the easier way to transmit data between Java and PHP consists in
serializing the value to a data-interchanging format, such as JSON or XML,
send it through a communication channel, and finally deserialize it back to the
original form on the PHP side. The problem with this approach is that the cost
of deserializing complex objects in PHP very high.

Most of the serialization libraries in PHP use Reflection for re-hydrating
objects, and it becomes an issue when you have to deserialize large structures
with hundreds of objects. Deserializing data from PHP serialization format, on
the other hand, is much faster.

This library implements the full format specification through a friendly API
that encapsulates the complexity of the serialization process.


## Installation

### Maven

The PHP Serializer is in the standard [Maven Central repository]().
Any Maven based project can use it directly by adding the appropriate entries
to the `dependencies` section of its `pom.xml` file:

```xml
<dependencies>
  <dependency>
    <groupId>com.foundationdb</groupId>
    <artifactId>fdb-sql-parser</artifactId>
    <version>1.5.0</version>
  </dependency>
</dependencies>
```

### Binaries

Packaged JARs can be downloaded directly from the [releases page]() and
extracted using tar or unzip.

## Usage

### How to serialize data

The first step to serialize a Java object into a PHP serialization format
string is to create a Serializer instance. The library ships a serializer
builder that help us with this task:

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
domain model. To serialize a value, just call the `serialize(Object object)` on
the `serializer` instance:

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
However,

If the PSR strategy does not fit your needs, implementing a custom naming
strategy is straightforward. Takes as reference the following strategy that
appends an underscore to all private fields:

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

A type adapter provides the serializer the logic for how to serialize a
specific type. The following example shows how to create a custom type adapter
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

Notice that circular references are handled as per case basis, once it not
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