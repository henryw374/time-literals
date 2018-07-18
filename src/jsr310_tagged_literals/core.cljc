(ns jsr310-tagged-literals.core
  (:refer-clojure :exclude [time]))

;(def ^{:dynamic true :private true} *cljs* false)

#_(defn set-clojure-mode! []
  #?(:clj (alter-var-root #'*cljs* (constantly false))
     :cljs (set! *cljs* false)))

#_(defn set-cljs-mode! []
  #?(:clj (alter-var-root #'*cljs* (constantly true))
     :cljs (set! *cljs* true)))

(defn clojurescript? []
  #?(:clj (boolean (System/getProperty "jsr310-tagged-literals.clojurescript"))
     :cljs true))

(defn date [x]
  (if (clojurescript?)
    `(.parse (. js/JSJoda -LocalDate) ~x)
    `(java.time.LocalDate/parse ~x)))

(defn instant [x]
  (if (clojurescript?)
    `(.parse (. js/JSJoda -Instant) ~x)
    `(java.time.Instant/parse ~x)))

(defn time [x]
  (if (clojurescript?)
    `(.parse (. js/JSJoda -LocalTime) ~x)
    `(java.time.LocalTime/parse ~x)))

(defn duration [x]
  (if (clojurescript?)
    `(.parse (. js/JSJoda -Duration) ~x)
    `(java.time.Duration/parse ~x)))
