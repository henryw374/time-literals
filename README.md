# time-literals

A Clojure(Script) library which provides tagged literals for objects from jsr-310 domain,
which on the jvm is objects from the `java.time` library and in Javascript is a 
 '[jsr-310 clone](https://clojars.org/cljsjs/js-joda)' library (yes, it is actually an implementation of JSR-310,
 rather than joda-time, as it's name would suggest).
 
This enables copying and pasting these objects within the REPL and conveying these objects across process boundaries. 
 
Pro-tip: The [tick](https://clojars.org/tick) library is
 an intuitive Clojure(Script) library for dealing with time, intended as a replacement for clj-time. It bundles this library and enables `time-literals` printing
  by default.

## Usage

Lein/Boot 

[![Clojars Project](https://img.shields.io/clojars/v/time-literals.svg)](https://clojars.org/time-literals)

The library includes the magic file `data_readers.cljc` which Clojure and the Clojurescript
compiler will look for.

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
(require '[time-literals.read-write])
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

## License

Copyright © 2019 Widd Industries

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
