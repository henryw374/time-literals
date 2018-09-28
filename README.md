# jsr310-tagged-literals

A Clojure(Script) library to help reading and printing objects from jsr-310,
which on the jvm (bundled with Java 8+ only) is implemented in the java.time library and in Javascript as the 
 '[JSJoda](https://clojars.org/cljsjs/js-joda)' library (which is actually an implementation of JSR-310,
 rather than joda-time, as it's name would suggest).
 
If you're not using it already The [tick](https://clojars.org/tick) library is
 an intuitive Clojure(Script) library for dealing with time, intended as a replacement for clj-time
 
## Alternatives
 
If you only need `Instant` from jsr-310, you could just rebind the tag readers and printer fns for `#inst`

Also [java-time-literals](https://github.com/magnars/java-time-literals) is a library which is similar but currently only works
on the jvm, and also doesn't provide a way to read edn with the literals (via clojure.edn/read-string or cljs.reader). Also the naming of tags
in this library follows the [tick](https://clojars.org/tick/versions/0.4.0-alpha) (v 0.4+) convention, for example
`#jsr310/date` for LocalDate, instead of `#time/ld`.

## Usage

Lein/Boot 

```
[jsr310-tagged-literals "0.1.1"]
```

The library includes the magic file `data_readers.cljc` which Clojure and the Clojurescript
compiler will look for.

Example literals:

```
#jsr310/month "JUNE"
#jsr310/period "P1D"
#jsr310/date "2039-01-01"
#jsr310/date-time "2018-07-25T08:08:44.026"
#jsr310/zoned-date-time "2018-07-25T08:09:11.227+01:00[Europe/London]"
#jsr310/offset-date-time "2018-07-25T08:11:54.453+01:00"
#jsr310/instant "2018-07-25T07:10:05.861Z"
#jsr310/time "08:12:13.366"
#jsr310/duration "PT1S"
#jsr310/year "3030"
#jsr310/year-month "3030-01"
#jsr310/zone "Europe/London"
#jsr310/day-of-week "TUESDAY"
```

### ClojureScript

For example, in a Clojure repl:

```
(require 'jsr310-tagged-literals.data-readers)
 
; Get a node repl going, or equivalent start figwheel and connect with sidecar etc 
(require '[cljs.repl :as cljs-repl])
(require '[cljs.repl.node :as node])          

(cljs-repl/repl* (node/repl-env)
  {:output-dir "out"
   :optimizations :none
   :cache-analysis true
   :source-map true})
  
 ;Now, in cljs repl
 (require '[java.time ])  
 (println #jsr310/duration "PT1S")
 ; => #object[Duration PT1S]
 ; Now, include printing and edn reading
 (require '[jsr310-tagged-literals.read-write])
 (println #jsr310/duration "PT1S")
 ; => #jsr310/duration "PT1S"   
      
 Read an edn string:
      
 (cljs.reader/read-string "#jsr310/date \"2011-01-01\"")     

```

#### Self-hosted Cljs

TODO

### Clojure

As with any non-core tagged literal, the tag reader functions referred to from a data_readers file
 must be loaded before the forms can be read.

```
(require '[jsr310-tagged-literals.data-readers])
(println #jsr310/duration "PT1S")

```

#### Reading and Writing edn
 
 ```
 (require '[jsr310-tagged-literals.read-write])
 ```

Printing will now automatically change, for example re run the println above

Read edn like this:

```
(clojure.edn/read-string {:readers jsr310-tagged-literals.read-write/tags} "#jsr310/date \"2011-01-01\"")
```

Using clojure.core/read-string, you'd need to `eval` after the call to read-string

## License

Copyright © 2018 Widd Industries

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
