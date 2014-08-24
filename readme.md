## Build
``` bash
mvn clean test
```
Note: There are some tests slow tests for thread safety. 
They are all tagged with:
``` java
@Category(SlowTest.class)
```
For your convenience, I modified the number of iterations to 100 (instead of 1000).
Anyway, to run only the fast tests use
''' bash
mvn clean test -DexcludedGroups="ro.j.test.SlowTest"
'''

### Github
`git@github.com:ojusti/cache.git`

## Design
The cache is `MultiLevelCache`. If correctly set up, it overflows to another cache (who could be another instance of MultiLevelCache...).

### EvictionPolicy
A policy is not a simple comparator, it implements its own data structures (a double linked list for LRU, FIFO and LFU). Arguably, they are a little more complex to implement, but the design is more open.

### Concurrency
Map insertions and policy's data structure updates in the Cache's put method are protected by a synchronized block. I think it is acceptable, given that this is a fairly simple solution which respects the requirements (thread-safety). 

I used ConcurrentHashMap as the cache backend as this allows letting the get method not synchronized.

### File backend
`Disk store` is an implementation of `Cache`. It delegates almost everything to its collaborators: it use a `Function` to calculate a filename from a key, a `Marshaller` to serialize and a `Folder`. I provided basic implementations for all of them.  

### Builders & Usage
'Complicated' objects are set up using builders.
See examples in `CacheBuilderTest`.
