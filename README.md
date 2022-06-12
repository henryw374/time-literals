# time-literals

A Clojure(Script) library which provides tagged literals for java.time objects,
which on the jvm is objects from the `java.time` platform library and in Javascript is a 
 java.time clone, called '[js-joda](https://js-joda.github.io/js-joda/)'.
 
This enables copying and pasting these objects within the REPL, conveying these objects across process boundaries & etc. 
 
[This talk](https://www.youtube.com/watch?v=UFuL-ZDoB2U) provides some more background.

**Note** : To use this from Clojurescript, you must have at least version 1.11.51. If using shadow-cljs, it must be at least version 2.19.3

## Related Libraries

[cljc.java-time](https://github.com/henryw374/cljc.java-time) is a one for one mapping of the classes and methods from
java.time into a Clojure(Script) library 
 
The [tick](https://clojars.org/tick) library is an intuitive Clojure(Script) library for dealing with time, intended as a replacement for clj-time. It bundles this library and enables `time-literals` printing
  by default.


## Usage

Lein/Boot/Deps 

[![Clojars Project](https://img.shields.io/clojars/v/com.widdindustries/time-literals.svg)](https://clojars.org/com.widdindustries/time-literals)


The library includes the magic file `data_readers.cljc` which Clojure and the Clojurescript
compilers will look for.

In order to modify the printer to print these literals, run: 

`(time-literals.read-write/print-time-literals-clj!)`

`(time-literals.read-write/print-time-literals-cljs!)`

Example literals:

```
#time/month "JUNE"
#time/period "P1D"
#time/date "2039-01-01"
#time/date-time "2018-07-25T08:08:44.026"
#time/zoned-date-time "2018-07-25T08:09:11.227+01:00[Europe/London]"
#time/offset-date-time "2018-07-25T08:11:54.453+01:00"
#time/instant "2018-07-25T07:10:05.861Z"
#time/time "08:12:13.366"
#time/duration "PT1S"
#time/year "3030"
#time/year-month "3030-01"
#time/zone "Europe/London"
#time/day-of-week "TUESDAY"
```

### ClojureScript

For example, in a Clojure repl:

```
  
 ;In a cljs repl
 (require '[java.time])  
 (println #time/duration "PT1S")
 ; => #object[Duration PT1S]
 ; Now, include printing and edn reading
 (require '[time-literals.read-write])
 (time-literals.read-write/print-time-literals-cljs!)
 (println #time/duration "PT1S")
 ; => #time/duration "PT1S"   
      

```

### Clojure

As with any non-core tagged literal, the tag reader functions referred to from a data_readers file
 must be loaded before the forms can be read.

```
(require '[time-literals.read-write]) ;; For printing/writing
(time-literals.read-write/print-time-literals-clj!)
(println #time/duration "PT1S")

```

### Reading and Writing edn
 
 ```
 (require '[time-literals.read-write])
 (time-literals.read-write/print-time-literals-clj!)
 ```

Printing will now automatically change, for example re run the println above

Read edn like this:

```
(clojure.edn/read-string {:readers time-literals.read-write/tags} "#time/date \"2011-01-01\"")
```

## Alternatives
 
If you only need `Instant` from java.time/jsr-310, you could just rebind the tag readers and printer fns for 
`#inst`. Note
however that Clojure's `inst` format is based on RFC3339 and so is actually closer to the default format 
for java.time.OffsetDateTime
 (for example to
read an `inst` tag, `OffsetDateTime/parse` will work ok, but Instant/parse will not). But... I think in most use 
cases Instant is preferred over OffsetDateTime as a representation of an absolute point in time. Rebinding inst
 reader and printer might 
also lead 
to problems where a programmer needs to work with both java.util.Date (or js/Date) and java.time.Instant objects - for example if using
Datomic - it only works with java.util.Date objects.    

There is a similar library, [java-time-literals](https://github.com/magnars/java-time-literals) but this currently only works
on the jvm, and also doesn't provide a way to read edn with the literals (via clojure.edn/read-string or cljs.reader). The naming of tags
in this library (`time-literals`) follows the [tick](https://clojars.org/tick) convention, for example
`#time/date` for LocalDate, instead of `#time/ld` as in `java-time-literals`.

## Why use 'time' as namespace?

TL;DR it is sufficiently ambiguous.

This library reads/writes java.time objects. It would be feasible to use the same set of tags with a different 
time library, either on the jvm or other elsewhere. If the namespace were 'jsr310' or 'java.time' that would be
too implementaion specific.

A set of literals for the ISO-8601 specification would 
probably be the ideal for date interchange, with literals such as `#iso8601/ordinal-date"1981-095"``

However, although the Java.time domain overlaps significantly with concepts in ISO-8601, there are differences.
For example, the ISO 'Duration' is roughly a combination of java.time.Duration and java.time.Period, and 
the IANA time zone names (such are you see in the literal representation of ZonedDateTime) are not part of ISO. 

## License

Copyright © 2021 [Widd Industries](https://widdindustries.com/about/)

Distributed under the [MIT License](/LICENSE)
