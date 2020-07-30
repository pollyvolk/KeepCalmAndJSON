# KeepCalmAndJSON
<img src="https://raw.githubusercontent.com/pollyvolk/logos/master/KeepCalmAndJSON_logo.png?sanitize=true" width="200" height="250">

![Java CI with Maven](https://github.com/pollyvolk/KeepCalmAndJSON/workflows/Java%20CI%20with%20Maven/badge.svg)
[![Build Status](https://travis-ci.com/pollyvolk/KeepCalmAndJSON.svg?branch=master)](https://travis-ci.com/github/pollyvolk/KeepCalmAndJSON)
[![codecov](https://codecov.io/gh/pollyvolk/KeepCalmAndJSON/branch/master/graph/badge.svg)](https://codecov.io/gh/pollyvolk/KeepCalmAndJSON)

**KeepCalmAndJSON** is a library for representing JSON code as a structure of Java objects that can be easily processed in Java projects. 
The tool also includes the convenient JSON parser.

## How it works
You can load a jar file of the **KeepCalmAndJSON** and use it in your projects as a library.

## Usage examples:

### Example of parsing JSON from source String

```java
void func() throws JsonParserException {
        String data = "{\"key1\" : \"value1\", \"key2\" : 2}";
        JsonElement jsData = JsonParser.parse(data);
}
```
or
```java
void func() {
        String data = "{\"key1\" : \"value1\", \"key2\" : 2}";
        JsonElement jsData = JsonParser.parseNoThrow(data);
}
```
if you do not want exceptions to be thrown. Using this method don`t forget to avoid the NullPointerException.

### Example of wrong input case

```
void func() throws JsonParserException {
        String data = "{\"key\" : value}";
        JsonElement jsData = JsonParser.parse(data);
}
```
will cause JsonParserException:
```
org.cqfn.keepcalmandjson.parser.exceptions.ExpectedJsonElementException
	at org.cqfn.keepcalmandjson.parser.JsonParser.parseObject(JsonParser.java:320)
	at org.cqfn.keepcalmandjson.parser.JsonParser.parse(JsonParser.java:170)
	at org.cqfn.keepcalmandjson.parser.JsonParser.parse(JsonParser.java:143)
```

### Example of processing element content received after parsing

After parsing the JSON source code, the result is stored in the `JsonElement` object, 
which is an element of any possible JSON data type.
To work with the desired data type, you must typecast the abstract JsonElement element to this type.

For more information about data types, see [JSON data types](README.md#json-data-types). 

<details>
  <summary><b>Example 1 - Get value by key</b></summary><br>
   
#### Example 1 - Get value by key

For the [example - Get value by key](examples/process-after-parsing/src/main/java/org/cqfn/keepcalmandjson/example/GetValueByKey.java)
the input source JSON data is

```json
{
  "key1" : "value1",
  "key2" : "value2",
  "key3" : 123
}
```

To get the value of `key3` after parsing you should typecast JsonElement to JsonObject (as the source data includes object):

```java
JsonElement jsonData = JsonParser.parse(data);
JsonObject jsonObject = jsonData.toJsonObject();
```

After receiving an object it is possible to get the value by specified key:

```java
int value = jsonObject.getElementByKey("key3").getIntValue();
```
**Note** that the return type of `getElementByKey()` method is also the `JsonElement`, so casting methods like `getIntValue()` are required.

```java
System.out.println(value);
```

Output:
```
123
```

</details>

<details>
  <summary><b>Example 2 - Process elements of array</b></summary><br>
  
#### Example 2 - Process elements of array

For the [example - Process array content](examples/process-after-parsing/src/main/java/org/cqfn/keepcalmandjson/example/ProcessArrayContent.java)
the input source JSON data is

```json
{
  "languages" :
  [
    "Java",
    "C++",
    "Python",
    "Kotlin",
    "Go"
  ]
}
```

If parsed and typecasted to `JsonObject` data (like in [Example 1](#example-1---get-value-by-key)) is stored in the `jsonObject` variable
in the next step you can get the array:

```java
 JsonArray jsonArray = jsonObject.getElementByKey("languages").toJsonArray();
```

Then you can get all the elements as a list of `JsonElement` and process them in turn:

```java
 List<JsonElement> elements = jsonArray.getArrayElements();
```
</details>

<details>
  <summary><b>Example 3 - Add new elements to array</b></summary><br>
  
#### Example 3 - Add new elements to array

Open [example - Add array elements](examples/process-after-parsing/src/main/java/org/cqfn/keepcalmandjson/example/AddArrayElements.java)
to see a full example.

Two ways of adding elements to `JsonArray` are possible:

* Adding JsonElement created before

* Using special methods that create elements from value and add them

E.g. for the `JsonArray` from [Example 2](#example-2---process-elements-of-array)

*Method 1 - Add JsonElement created before*

```java
JsonNumber number = new JsonNumber(null, -2020);
jsonArray.addArrayElement(number);
```

*Method 2 - Create new elements*

```java
jsonArray.createStringElement("Swift");
jsonArray.createBooleanElement(true);
jsonArray.createArrayElement();
```

**Note** that you can convert the element to JSON representation *with* or *without indention*.

For

```java
jsonArray.toString();
```

Output:
```
["Java","C++","Python","Kotlin","Go",-2020,"Swift",true,[]]
```

For

```java
jsonArray.toStringWithIndents();
```

Output:
```
[
  "Java",
  "C++",
  "Python",
  "Kotlin",
  "Go",
  -2020,
  "Swift",
  true,
  [ ]
]
```

</details>

<details>
  <summary><b>Example 4 - Add new elements to object</b></summary><br>
  
#### Example 4 - Add new elements to object

Open [example - Add object elements](examples/process-after-parsing/src/main/java/org/cqfn/keepcalmandjson/example/AddObjectElements.java)
 to see a full example.

The input source JSON data

```json
{
  "name" : "John P.",
  "age" : 25,
  "score" : 82.75,
  "testPassed" : true
}
```
is stored in the `employee` variable as `JsonObject`.

Two ways of adding elements to `JsonObject` are possible:

*Method 1 - Add JsonElement content created before*

```java
JsonNumber years = new JsonNumber(null,3);
employee.addElement("years of experience", years);
```

*Method 2 - Create new elements*

E.g. creating and filling an array:

```java
JsonArray languages = employee.createJsonArray("languages");
languages.createStringElement("Java");
languages.createStringElement("Kotlin");
```

E.g. creating and filling an object:

```java
JsonObject projects = employee.createJsonObject("personal projects");
projects.createJsonNull("scientific papers");
projects.createJsonString("GitHub", "https://github.com/...");
projects.createJsonNumber("articles amount", 2);
```

Result JSON:

```json
{
  "age" : 25,
  "hasDiploma" : true,
  "languages" :
  [
    "Java",
    "Kotlin"
  ],
  "name" : "John P.",
  "personal projects" :
  {
    "GitHub" : "https://github.com/...",
    "articles amount" : 2,
    "scientific papers" : null
  },
  "score" : 82.75,
  "years of experience" : 3
}
```
**Note** that the `JsonObject` uses the natural ordering of its keys.

</details>

### Example of converting Java objects into JSON

Using Java classes mentioned above it is possible to create a whole JSON object with elements or more simple JSON types, 
e.g. strings, arrays, numbers, and convert them into JSON representation. 

<details>
  <summary><b>Example 1 - Create JSON simple types</b></summary><br>
  
#### Example 1 - Create JSON simple types

Open [example - Create simple JSON types](examples/convert-java-into-json/src/main/java/org/cqfn/keepcalmandjson/example/CreateSimpleJsonTypes.java) 
to see an example.

You can create each of the type described [here](README.md#json-data-types).

The first argument in type classes constructor is a parent element. It can be `null` for separate elements.

**Examples:**

Create `number`:

```java
JsonNumber number = new JsonNumber(null, 111);
```

Create `boolean`:

```java
JsonBoolean bool = new JsonBoolean(null, true);
```

Create `NULL`:

```java
JsonNull jsonNull = new JsonNull(null);
```

Create `string`:

```java
JsonString string = new JsonString(null, "example");
```

Create empty `array`:

```java
JsonArray array = new JsonArray(null);
```

To convert these elements into JSON representation you can use method `toString()` 
or `toStringWithIndents()` with indention:
 
E.g. for 

```java
bool.toString();
array.toString();
array.toStringWithIndents();
```

Output:
```
true
[]
[ ]
```
</details>

<details>
  <summary><b>Example 2 - Create JSON objects</b></summary><br>
  
#### Example 2 - Create JSON objects

Open [example - Create JSON object](examples/convert-java-into-json/src/main/java/org/cqfn/keepcalmandjson/example/CreateJsonObjects.java) 
to see an example.

To create a JSON object from scratch, firstly, you should create the `JsonObject` element and then fill it with
key-value pairs using special methods of `JsonObject`.

Create object:
```java
JsonObject object = new JsonObject(null);
```

Add key-value pair with string value:
```java
object.createJsonString("str", "Some string");
```

Add key-value pair with number value:
```java
object.createJsonNumber("num", 2020);
```

Methods of this type return created objects, therefore you can use it to fill them with content.

Add key-value pair with array value:
```java
JsonArray array = object.createJsonArray("arr");
array.createStringElement("value 1");
array.createStringElement("value 2");
```

Add key-value pair with object value:
```java
JsonObject nestedObject = object.createJsonObject("obj");
nestedObject.createJsonNumber("key1", 1);
nestedObject.createJsonBoolean("key2", false);
```

To convert elements of complex structure into JSON representation you can use method `toString()` or 
`toStringWithIndents()` with indention:
 
E.g. for 

```java
object.toString();
```
Output:

```
{"arr":["value 1","value 2"],"num":2020,"obj":{"key1":1,"key2":false},"str":"Some string"}
```

And for 

```java
object.toStringWithIndents();
```

Output:

```
{
  "arr" :
  [
    "value 1",
    "value 2"
  ],
  "num" : 2020,
  "obj" :
  {
    "key1" : 1,
    "key2" : false
  },
  "str" : "Some string"
}
```

**Note** that the `JsonObject` uses the natural ordering of its keys.

</details>

## JSON data types

**KeepCalmAndJSON** represents JSON structure in Java using these data types:

<details>
  <summary><b>JsonElement</b></summary><br>
  
  ``JsonElement`` - abstract class representing very JSON element, e.g. number, string, array as a value in JSON object.
  
</details> 

<details>
  <summary><b>JsonContainer</b></summary><br>
  
  ``JsonContainer`` - abstract class for a container, that can include several elements, e.g. array and object.
  
</details>

<details>
  <summary><b>JsonObject</b></summary><br>
  
  ``JsonObject`` - represents JSON object type.
  
  ```json
  {
    "name" : "John P.",
    "age" : 25,
    "score" : 82.75,
    "testPassed" : true
  }
  ```

</details> 

<details>
  <summary><b>JsonArray</b></summary><br>
  
  ``JsonArray`` - represents JSON array type.
  
  ```json
  [
    1,
    2,
    "str"
  ]
  ```
  
</details> 

<details>
  <summary><b>JsonString</b></summary><br>
  
  ``JsonString`` - represents JSON string type.
  
```json
  "Some string"
  ```

  ```json
  "\u000F"
  ```

</details> 

<details>
  <summary><b>JsonNumber</b></summary><br>
  
  ``JsonNumber`` - represents JSON numeric type.
  
  ```json
  2020
  ```

  ```json
  20.2
  ```

  ```json
  -11.23
  ```
</details> 

<details>
  <summary><b>JsonBoolean</b></summary><br>
  
  ``JsonBoolean`` - represents JSON boolean type.
 
  ```json
  true
  ```
  
</details> 
 
<details>
  <summary><b>JsonNull</b></summary><br>
  
  ``JsonNull`` - represents JSON NULL type.
  
   ```json
    null
   ```
  
</details> 

## Features of source JSON code format

### You can pass keys without quotes

```json5
{
  key1 : "value1",
  "key2" : "value2",
  key3 : 123
}
```

### Trailing commas are permitted

#### Example 1

```json5
{
  "key1" : "value1",
  "key2" : "value2",
  "key3" : 123,
}
```
#### Example 2
```json5
{
  "key1" : "value1",
  "key2" : 
  [
    1,
    2,
    3,
  ]
}
```
