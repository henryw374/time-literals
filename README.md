# time-literals

A Clojure(Script) library which provides tagged literals for objects from jsr-310 domain,
which on the jvm is objects from the `java.time` library and in Javascript is a 
 '[jsr-310 clone](https://clojars.org/cljsjs/js-joda)' library (yes, it is actually an implementation of JSR-310,
 rather than joda-time, as it's name would suggest).
 
This enables copying and pasting these objects within the REPL, conveying these objects across process boundaries & etc. 
 
[My talk at Clojure/North 2019](https://www.youtube.com/watch?v=UFuL-ZDoB2U) provides some more background.

## Related Libraries

[cljc.java-time](https://github.com/henryw374/cljc.java-time) is a one for one mapping of the classes and methods from
java.time into a Clojure(Script) library 
 
The [tick](https://clojars.org/tick) library is an intuitive Clojure(Script) library for dealing with time, intended as a replacement for clj-time. It bundles this library and enables `time-literals` printing
  by default.


## Usage

Lein/Boot 

[![Clojars Project](https://img.shields.io/clojars/v/time-literals.svg)](https://clojars.org/time-literals)

The library includes the magic file `data_readers.cljc` which Clojure and the Clojurescript
compilers() will look for.

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
(require 'time-literals.data-readers)
 
; Get a node repl going, or equivalent start figwheel and connect with sidecar etc 
(require '[cljs.repl :as cljs-repl])
(require '[cljs.repl.node :as node])          

(cljs-repl/repl* (node/repl-env)
  {:output-dir "out"
   :optimizations :none
   :cache-analysis true
   :source-map true})
  
 ;Now, in cljs repl
 (require '[java.time])  
 (println #time/duration "PT1S")
 ; => #object[Duration PT1S]
 ; Now, include printing and edn reading
 (require '[time-literals.read-write])
 (time-literals.read-write/print-time-literals-cljs!)
 (println #time/duration "PT1S")
 ; => #time/duration "PT1S"   
      
 Read an edn string:
      
 (cljs.reader/read-string "#time/date \"2011-01-01\"")     

```

#### Self-hosted Cljs

TODO

### Clojure

As with any non-core tagged literal, the tag reader functions referred to from a data_readers file
 must be loaded before the forms can be read.

```
(require '[time-literals.data-readers]) ;; For literals
(require '[time-literals.read-write]) ;; For printing/writing
(time-literals.read-write/print-time-literals-clj!)
(println #time/duration "PT1S")

```

#### Reading and Writing edn
 
 ```
 (require '[time-literals.read-write])
 (time-literals.read-write/print-time-literals-clj!)
 ```

Printing will now automatically change, for example re run the println above

Read edn like this:

```
(clojure.edn/read-string {:readers time-literals.read-write/tags} "#time/date \"2011-01-01\"")
```

Using clojure.core/read-string, you'd need to `eval` after the call to read-string

## Alternatives
 
If you only need `Instant` from java.time/jsr-310, you could just rebind the tag readers and printer fns for `#inst`

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

Copyright © 2019 [Widd Industries](http://widdindustries.com/about/)

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
