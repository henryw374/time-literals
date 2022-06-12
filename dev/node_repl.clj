(ns node-repl
  (:require [cljs.repl :as cljs-repl]
            [cljs.repl.node :as node]
            [time-literals.data-readers]))

(defn node-repl []
  (cljs-repl/repl* (node/repl-env)
    {:output-dir "out"
     :optimizations :none
     :cache-analysis true
     :source-map true}))

(comment 
  (node-repl)
  
  )