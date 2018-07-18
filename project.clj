(defproject jsr310-tagged-literals "0.1.0-SNAPSHOT"
  :description "Clojure(Script) tagged literals for jsr-310 entities"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [cljsjs/js-joda "1.6.2-0"]]
  :profiles {:dev
            {:source-paths ["dev"]
             :dependencies [[org.clojure/clojure "1.9.0"]
                            [org.clojure/clojurescript "1.10.238"]]
             :plugins [[lein-doo "0.1.10"]]
             :jvm-opts ["-Dclojure.spec.compile-asserts=true"]}})
