# jsr310-tagged-literals

A Clojure(Script) library to help reading and printing objects from jsr-310,
which on the jvm is implemented in the java.time library and in Javascript as the 
 '[JSJoda](https://clojars.org/cljsjs/js-joda)' library (which is actually an implementation of JSR-310,
 rather than joda-time, as it's name would suggest).
 
 For Clojurists using jsr-310, checkout the cross-platform [tick](https://clojars.org/tick/versions/0.4.0-alpha) (v 0.4+) library
 
## Alternatives
 
If you only need `Instant` from jsr-310, you could just rebind the tag readers and printer fns for `#inst`
 
## TODO
 
 Support complete set of jsr-310 objects.

## Usage

Lein/Boot 

```
[jsr310-tagged-literals "0.1.0"]
```

I'll go through a few use cases and if you have different ones, hopefully
you can work it out and submit a PR ;-).

The library includes the magic file `data_readers.cljc` which Clojure and the Clojurescript
compiler will look for.

### ClojureScript

For example, in a Clojure repl:

```
(require 'jsr310-tagged-literals.data-readers)
; The following sets the system property for cljs
(jsr310-tagged-literals.data-readers/set-cljs-mode!)
 
; Get a node repl going, or equivalent start figwheel and connect with sidecar etc 
(require '[cljs.repl :as cljs-repl])
(require '[cljs.repl.node :as node])          

(cljs-repl/repl* (node/repl-env)
  {:output-dir "out"
   :optimizations :none
   :cache-analysis true
   :source-map true})
  
 ;Now, in cljs repl, assuming you have tick or just cljsjs.js-joda in your classpath  
 (require '[cljsjs.js-joda])  
 (println #jsr310/duration "PT1S")
 ; => #object[Duration PT1S]
 ; Now, include printing and edn reading
 (require '[jsr310-tagged-literals.read-write])
 (println #jsr310/duration "PT1S")
 ; => #jsr310/duration "PT1S"   
      
 Read an edn string:
      
 (cljs.reader/read-string "#jsr310/date \"2011-01-01\"")     

```

#### Compiling Cljs files containing jsr-310 tagged literals

When compiling cljs, the `jsr310-tagged-literals.clojurescript` system property needs to be
`true`. Programmatically this can be done via `(jsr310-tagged-literals.data-readers/set-cljs-mode!)`

But, let's say you're compiling cljs with leiningen and cljsbuild ...

Have a profile that sets the system property
```
...
:profiles {:cljs {:jvm-opts ["-Djsr310-tagged-literals.clojurescript=true"]}
...
```
and then from cmd line
```
lein with-profile cljs cljsbuild once
```

Lein uberjar with both aot and cljs compiling can be done similarly. Invoke any cljs-building
steps in a profile that has the aforesaid property set to true, and any clojure building steps in 
a profile with it set to false

```
:prep-tasks ["build-clj" "build-cljs"]
:aliases { "build-cljs" ["with-profile" "cljs" "cljsbuild" "once"]
           "build-clj"  ["with-profile" "clj" "compile"] }
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
