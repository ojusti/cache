## Build
``` bash
mvn clean test```
Note: There are some tests slow tests for thread safety (~3 minutes each on my machine). They are all tagged with:
``` java
@Category(SlowTest.class)
```
To run only the fast tests use
''' bash
mvn clean test -DexcludedGroups="ro.j.test.SlowTest"
'''
