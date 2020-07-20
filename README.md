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
##Features of source JSON code format
#### Keys can be without quotes
```
{
  key1 : "value1",
  "key2" : "value2",
  key3 : 123
}
```

#### Trailing commas permitted
#####Example 1
```
{
  "key1" : "value1",
  "key2" : "value2",
  "key3" : 123,
}
```
#####Example 2
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

##Examples of using

#### Example of parsing JSON from source String
```
void func() throws JsonParserException {
        String data = "{\"key1\" : \"value1\", \"key2\" : 2}";
        JsonElement jsData = JsonParser.parse(data);
}
```
or
```
void func() {
        String data = "{\"key1\" : \"value1\", \"key2\" : 2}";
        JsonElement jsData = JsonParser.parseNoThrow(data);
}
```
if you do not want exceptions to be thrown.

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
