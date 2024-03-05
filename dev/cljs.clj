(ns cljs
  (:require [clojure.java.io :as io]
            [com.widdindustries.tiado-cljs2 :as util]))

(defn test-watch []
  (util/browser-test-build :watch {}))

(defn temporal-test-build [compile-mode opts]
  ((get util/compile-fns compile-mode)
   (util/browser-test-config) opts)
  (.mkdirs (io/file "web-target" "public" "browser-test"))
  (spit "web-target/public/browser-test/index.html"
    "<!DOCTYPE html>
    <html><head>
    <title>kaocha.cljs2.shadow-runner</title>
    <meta charset=\"utf-8\">
    </head>
    <body>
   
    <script>
        if(!window.Temporal){
          document.write('<script src=\"https://tc39.es/proposal-temporal/docs/playground.js\"></script>');
                  }
    </script>
    
    <script src=\"/browser-test/js/test.js\">
    </script>
    <script>kaocha.cljs2.shadow_runner.init();</script></body></html>"
    ))

(defn temporal-test-watch []
  (temporal-test-build :watch {}))

(defn tests-ci-temporal [{:keys [compile-mode]}]
  (util/start-server)
  (temporal-test-build compile-mode {})
  (try
    (util/kaocha-exit-if-fail (util/run-tests-headless nil))
    (catch Exception e
      (println e)
      (System/exit 1))))


(comment

  ; start up live-compilation of tests
  (test-watch)
  ; run cljs tests, having opened browser at test page (see print output of above "for tests, open...")
  (util/run-tests)
  ; start a cljs repl session in the test build. :cljs/quit to exit
  (util/repl :browser-test-build)
  ; run tests in headless browser
  (util/compile-and-run-tests-headless* :release)

  (util/stop-server)

  )