# VertxWithWordsProject


## Excercise <- (the projects name)

### Explanation

#### An Excercise:
##### REQUIREMENTS
  1. The program will expose an HTTP REST interface that responds to POST requests on the
  URL /analyze.
  2. When an HTTP client POSTs a JSON object with the string property “text”, the server
  will compare the content of the provided “text” field to the list of words previously
  provided through the same API, and return a JSON response containing an object with
  two fields:
  a. The field “value” will contain the word closest to the word provided in the
  request in term of total character value, where character values are listed as a=1,
  b=2 and so on.
  b. The field “lexical” will contain the word closest to the word provided in the
  request in term of lexical closeness - i.e. that word that sorts lexically closest to the
  provided request.

  3. The server will store the word provided in the request locally to compare against future
  requests.
  4. If no words are found to match against (as in the first request), the server will return null
  for both response fields.

### How To Use

#### To launch:
```
>mvn clean package
```
#### To run:
```
java -jar target/excercise-1.0.0-SNAPSHOT-fat.jar
```
or
###### java -jar target/__*[The-Name-Of-The-Project-Here]*__-1.0.0-SNAPSHOT-fat.jar
  

[](https://img.shields.io/badge/vert.x-3.5.3-purple.svg)
This application was generated using http://start.vertx.io
