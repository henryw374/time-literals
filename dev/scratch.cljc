 (ns scratch)

(comment


  (require 'jsr310-tagged-literals.core)

  (jsr310-tagged-literals.core/set-cljs-mode!)
  (node-repl)

  (require '[cljsjs.js-joda])
  (.. js/JSJoda -LocalDate (parse "2018-06-06"))
  (println #jsr310/date  "2018-06-06")
:cljs/quit

  )
