
[![Tests build](https://github.com/henryw374/time-literals/actions/workflows/tests.yaml/badge.svg)](https://github.com/henryw374/time-literals/actions/workflows/tests.yaml)


# time-literals

```clojure 
#time/date "2039-01-01"
```

A Clojure(Script) library which provides tagged literals for date-time objects

There are two artifacts:
* [![java.time and js-joda](https://img.shields.io/clojars/v/com.widdindustries/time-literals.svg)](https://clojars.org/com.widdindustries/time-literals). on js runtimes the objects are those of a
  java.time clone, called '[js-joda](https://js-joda.github.io/js-joda/)'
* [![java.time and temporal](https://img.shields.io/clojars/v/com.widdindustries/time-literals-tempo.svg)](https://clojars.org/com.widdindustries/time-literals-tempo) on js-runtimes the objects are from the platform Temporal API - see [Tempo](https://github.com/henryw374/tempo) lib for more info
  
[This talk](https://www.youtube.com/watch?v=UFuL-ZDoB2U) provides some more background.

**Note** : To use this from Clojurescript, you must have at least version 1.11.51. If using shadow-cljs, it must be at least version 2.19.3

## Rationale 

### What is `#inst` ?

Reader Literals were a [headline new feature in Clojure 1.4](https://github.com/clojure/clojure/blob/master/changes.md#21-reader-literals) and with that came built-in support for the [#inst tag](https://github.com/clojure/clojure/blob/master/changes.md#211-instant-literals). The `#inst` tag is a part of the edn spec, where it is defined as representing [an instant in time](https://github.com/edn-format/edn#inst-rfc-3339-format), which means a point in time relative to UTC that is given to (at least) millisecond precision.

In Clojure(script), `#inst` is read as a legacy platform `Date` object by default, but as is made clear by the edn spec and by [this talk from Rich Hickey](https://github.com/matthiasn/talk-transcripts/blob/master/Hickey_Rich/AreasOfInterestForClojuresCore.md#extensible-reader) the default implementation is just that: `#inst` may be read to whatever internal representation is useful to a consuming program. For example a program running on the jvm could read `#inst` tags to java.time.Instant (or java.time.OffsetDateTime if wishing to preserve the UTC offset information). It seems to me unfortunate that Clojure(script) provided defaults for `#inst` because users may not realise it is 'just a default', but that's just my opinion. My guess is that Clojure is trying to be both simple and easy in this case.

When conveying data using edn format, built-in tagged elements are preferred to user defined elements.

### The need for more Tagged Elements representing Dates in edn

There are many kinds of things relating to date and time that are not an `instant in time`, so `#inst` would not be an appropriate way to tag them. For example the month of a particular year such as 'January 1990' or a calendar date such as 'the first of June, 3030'. There are no built-in edn tags for these.

Note that the default Clojure reader behaviour is to accept partially specified instants, such as `#inst "2020"` (and read that to a Date with millisecond precision) - but this is specific to the Clojure implementation and not valid edn (AFAIK). 

### Round-tripping at the REPL

Clojure provides two mechanisms for printing objects - abstract and concrete as this code printing the same object shows:

```clojure
(let [h (java.util.HashMap.)]
  {:abstract (pr-str h)
   :concrete (binding [*print-dup* true]
               (pr-str h))})
=> {:abstract "{}", :concrete "#=(java.util.HashMap. {})"}
```

When at the REPL and using say, persistent datastructures, the concrete representation is rarely useful to know, but when dealing with date-time objects it is always useful. Also, the string output can be passed back to the reader to recreate the same internal representation again, which is known as `round-tripping`. 

The default readers and printers of platform date objects don't allow round-tripping, [the reason for which is unknown](https://ask.clojure.org/index.php/11898/printing-and-reading-date-types).

This is relevant to the java.time types which logically correspond to `#inst` (java.time.Instant and java.time.OffsetDateTime). This library contains specific readers and printers for those objects so that they do round-trip. When conveying these objects out of process in edn format, they should be tagged as `#inst` of course. To do that, simply provide your own implementation of `clojure.core/print-method` for those types. With `*print-dup*` true, the concrete type will still show.

## Related Libraries

[cljc.java-time](https://github.com/henryw374/cljc.java-time) is a one for one mapping of the classes and methods from
java.time into a Clojure(Script) library 
 
The [tick](https://clojars.org/tick) library is an intuitive Clojure(Script) library for dealing with time, intended as a replacement for clj-time. It bundles this library and enables `time-literals` printing
  by default.


## Usage

Note: IMHO one should avoid putting tag literals in source code because a tag can be bound to different readers in different contexts, but code will be expecting some specific API. Additionally one has to add a side-effecting require of the tag-reader-namespace to make sure the reader function (ie the one you hope is bound to the tag) exists. tl;dr - it is too magical.

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
