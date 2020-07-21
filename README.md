# KeepCalmAndJSON
<img src="https://raw.githubusercontent.com/pollyvolk/logos/master/KeepCalmAndJSON_logo.png?sanitize=true" width="200" height="250">


**KeepCalmAndJSON** is a library for representing JSON code as a structure of Java objects that can be easily processed in Java projects. 
The tool also includes the convenient JSON parser.

## How it works
You can load a jar file of the **KeepCalmAndJSON** and use it in your projects as a library.

## JSON data types
**KeepCalmAndJSON** represents JSON structure in Java using these data types:
* ``JsonElement`` - abstract class representing very JSON element, e.g. number, string, array as a value in JSON object.

* ``JsonContainer`` - abstract class for a container, that can include several elements, e.g. array and object.

* ``JsonObject`` - represents JSON object type.

* ``JsonArray`` - represents JSON array type.

* ``JsonString`` - represents JSON string type.

* ``JsonNumber`` - represents JSON numeric type.

* ``JsonBoolean`` - represents JSON boolean type.

* `JsonNull`- represents JSON NULL type.

## Features of source JSON code format

#### Keys can be without quotes
```
{
  key1 : "value1",
  "key2" : "value2",
  key3 : 123
}
```

#### Trailing commas permitted

**Example 1**

```
{
  "key1" : "value1",
  "key2" : "value2",
  "key3" : 123,
}
```
**Example 2**
```
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

## Examples of using

#### Example of parsing JSON from source String

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

#### Example of wrong input case

```
void func() throws JsonParserException {
        String data = "{\"key\" : value}";
        JsonElement jsData = JsonParser.parse(data);
}
```
will cause JsonParserException:
```
org.pollyvolk.keepcalmandjson.parser.exceptions.ExpectedJsonElementException
	at org.pollyvolk.keepcalmandjson.parser.JsonParser.parseObject(JsonParser.java:320)
	at org.pollyvolk.keepcalmandjson.parser.JsonParser.parse(JsonParser.java:170)
	at org.pollyvolk.keepcalmandjson.parser.JsonParser.parse(JsonParser.java:143)
```

#### Example of processing element content received after parsing

**Example 1 - Getting value by key**

```java
public class Example {
    public void example1() throws JsonParserException {
        int value = 0;
        String data = "{\n" +
                "\tkey1 : \"value1\",\n" +
                "\tkey2 : \"value2\",\n" +
                "\tkey3 : 123\n" +
                "}";
        JsonElement jsonData = JsonParser.parse(data);
        // Convert abstract element to JSON object instance
        JsonObject jsonObject = jsonData.toJsonObject();
        // Check if some key and value exist
        if (jsonObject.containsKey("key3"))
            // Get JsonElement by specified key and convert it to the type expected
            value = jsonObject.getElementByKey("key3").getIntValue();
        System.out.println(value);
    }
}
```
Output:
```
123
```

**Example 2 - Processing elements of array**

```java
public class Example {
    public void example2() throws JsonParserException {
        String data =
                "{\n" +
                "  \"languages\" :\n" +
                "  [\n" +
                "    \"Java\",\n" +
                "    \"C++\",\n" +
                "    \"Python\",\n" +
                "    \"Kotlin\",\n" +
                "    \"Go\"\n" +
                "  ]\n" +
                "}";
        JsonElement jsonData = JsonParser.parse(data);
        // Convert abstract element to JSON object instance
        JsonObject jsonObject = jsonData.toJsonObject();
        // Get element by key and convert it into JSON array
        JsonArray jsonArray = jsonObject.getElementByKey("languages").toJsonArray();
        // Get all elements of array
        List<JsonElement> elements = jsonArray.getArrayElements();
        // Get String representation of JSON element
        for (JsonElement element : elements)
            System.out.println(element.toString());
        // Get value of JsonString
        for (JsonElement element : elements)
            System.out.println(element.getStringValue());
    }
}
```
Output:
```
"Java"
"C++"
"Python"
"Kotlin"
"Go"
Java
C++
Python
Kotlin
Go
```

**Example 3 - Adding new elements to array**

```java
public class Example {
    public void example3() throws JsonParserException {
        String data =
                "{\n" +
                "  \"languages\" :\n" +
                "  [\n" +
                "    \"Java\",\n" +
                "    \"C++\",\n" +
                "    \"Python\",\n" +
                "    \"Kotlin\",\n" +
                "    \"Go\"\n" +
                "  ]\n" +
                "}";
        JsonElement jsonData = JsonParser.parse(data);
        // Convert abstract element to JSON object instance
        JsonObject jsonObject = jsonData.toJsonObject();
        // Get element by key and convert it into JSON array
        JsonArray jsonArray = jsonObject.getElementByKey("languages").toJsonArray();
        // Add JsonElement created before
        JsonNumber number = new JsonNumber(null, -2020);
        jsonArray.addArrayElement(number);
        // Create new elements
        jsonArray.createStringElement("Swift");
        jsonArray.createNullElement();
        jsonArray.createBooleanElement(true);
        jsonArray.createArrayElement();
        jsonArray.createObjectElement();
        // Print updated JSON version without indention
        System.out.println(jsonArray.toString());
        // Print updated JSON version with indention
        System.out.println(jsonArray.toStringWithIndents());
    }
}
```
Output:
```
["Java","C++","Python","Kotlin","Go",-2020,"Swift",null,true,[],{}]
[
  "Java",
  "C++",
  "Python",
  "Kotlin",
  "Go",
  -2020,
  "Swift",
  null,
  true,
  [ ],
  { }
]
```

**Example 4 - Adding new elements to object**

```java
public class Example {
    public void example4() throws JsonParserException {
        String data =
                "{\n" +
                "  name : \"John P.\",\n" +
                "  age : 25,\n" +
                "  score : 82.75,\n" +
                "  hasDiploma : true\n" +
                "}";
        JsonElement jsonData = JsonParser.parse(data);
        // Convert abstract element to JSON object instance
        JsonObject employee = jsonData.toJsonObject();
        // Add JsonElement created before
        JsonNumber years = new JsonNumber(null,3);
        employee.addElement("years of experience", years);
        // Create new content
        // Create array and fill it
        JsonArray languages = employee.createJsonArray("languages");
        languages.createStringElement("Java");
        languages.createStringElement("Kotlin");
        // Create object and fill it
        JsonObject projects = employee.createJsonObject("personal projects");
        projects.createJsonNull("scientific papers");
        projects.createJsonString("GitHub", "https://github.com/...");
        projects.createJsonNumber("articles amount", 2);
        // Print updated JSON version without indention
        System.out.println(employee.toString());
        // Print updated JSON version with indention
        System.out.println(employee.toStringWithIndents());
    }    
}
```
Output:
```
{"age":25,"hasDiploma":true,"languages":["Java","Kotlin"],"name":"John P.","personal projects":{"GitHub":"https://github.com/...","articles amount":2,"scientific papers":null},"score":82.75,"years of experience":3}
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

#### Example of converting Java objects into JSON
Using Java classes mentioned above it is possible to create a whole JSON object with elements or more simple JSON types, e.g. strings, arrays, numbers, and convert them into JSON representation. 

**Example 1 - Creating JSON simple types**

The first argument in type classes constructor is a parent element. It can be null for separate elements.
```java
public class Example {
    public void example1() throws JsonParserException {
        // Create JSON number
        JsonNumber number = new JsonNumber(null, 111);
        // Create empty JSON array
        JsonArray array = new JsonArray(null);
        // Create empty JSON boolean
        JsonBoolean bool = new JsonBoolean(null, true);
        // Create new elements in array or add existing ones
        array.createBooleanElement(false);
        array.addArrayElement(bool);
        array.addArrayElement(number);
        // Print result JSON array with indention
        System.out.println(array.toStringWithIndents());
    }
}
```
Output:
```
[
  false,
  true,
  111
]
```

**Example 2 - Creating JSON objects**

```java
public class Example {
    public void example2() throws JsonParserException {
        // Create JSON object
        JsonObject album = new JsonObject(null);
        // Create key-value pairs
        album.createJsonString("album", "Album name");
        album.createJsonString("group", "Group name");
        album.createJsonNumber("year", 2019);
        JsonArray tracklist = album.createJsonArray("tracklist");
        tracklist.createStringElement("song 1");
        tracklist.createStringElement("song 2");
        // Create nested object
        JsonObject record = album.createJsonObject("record");
        record.createJsonNumber("year", 2020);
        record.createJsonString("country", "Russia");
        record.createJsonString("format", "LP");
        // Convert to string representation of JSON with indention
        String output = album.toStringWithIndents();
        System.out.println(output);
    }
}
```
Output:
```
{
  "album" : "Album name",
  "group" : "Group name",
  "record" :
  {
    "country" : "Russia",
    "format" : "LP",
    "year" : 2020
  },
  "tracklist" :
  [
    "song 1",
    "song 2"
  ],
  "year" : 2019
}
```
