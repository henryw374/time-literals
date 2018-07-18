(ns jsr310-tagged-literals.printers
  #?(:cljs
     (:require [cljsjs.js-joda])
     :clj
     (:import (java.io Writer)
              [java.time Instant LocalDate LocalTime Duration])))

(defn- print-to-string [t o]
  (str "#jsr310/" t " \"" (str o) "\""))

(def print-date (partial print-to-string "date"))
(def print-instant (partial print-to-string "instant"))
(def print-time (partial print-to-string "time"))
(def print-duration (partial print-to-string "duration"))

#?(:cljs
   (do
     (def Clock (.. js/JSJoda -Clock))
     (def ZoneOffset (.. js/JSJoda -ZoneOffset))
     (def Instant (.. js/JSJoda -Instant))
     (def Duration (.. js/JSJoda -Duration))
     (def Period (.. js/JSJoda -Period))
     (def DayOfWeek (.. js/JSJoda -DayOfWeek))
     (def Month (.. js/JSJoda -Month))
     (def ZonedDateTime (.. js/JSJoda -ZonedDateTime))
     (def LocalTime (.. js/JSJoda -LocalTime))
     (def LocalDateTime (.. js/JSJoda -LocalDateTime))
     (def LocalDate (.. js/JSJoda -LocalDate))
     (def Year (.. js/JSJoda -Year))
     (def YearMonth (.. js/JSJoda -YearMonth))
     (def ZoneId (.. js/JSJoda -ZoneId))
     (def ChronoUnit (.. js/JSJoda -ChronoUnit))
     (def ChronoField (.. js/JSJoda -ChronoField))
     (def TemporalAdjusters (.. js/JSJoda -TemporalAdjusters))
     (def Temporal (.. js/JSJoda -Temporal))
     (def TemporalAmount (.. js/JSJoda -TemporalAmount))
     (def DateTimeFormatter (.. js/JSJoda -DateTimeFormatter))
     (def ResolverStyle (.. js/JSJoda -ResolverStyle))

     ;; Following are not yet implemented in js-joda https://github.com/js-joda/js-joda/issues/165
     (def OffsetDateTime (.. js/JSJoda -ZonedDateTime))
     (def OffsetTime (.. js/JSJoda -LocalTime))
     ))

#?(:cljs
   (extend-protocol IPrintWithWriter
     LocalDate
     (-pr-writer [d writer opts]
       (-write writer (print-date d)))
     Instant
     (-pr-writer [d writer opts]
       (-write writer (print-instant d)))
     LocalTime
     (-pr-writer [d writer opts]
       (-write writer (print-time d)))
     Duration
     (-pr-writer [d writer opts]
       (-write writer (print-duration d)))
     ))

#?(:clj
   (defmethod print-method LocalDate [c ^Writer w]
     (.write w (print-date c))))

#?(:clj
   (defmethod print-method Instant [c ^Writer w]
     (.write w (print-instant c))))

#?(:clj
   (defmethod print-method LocalTime [c ^Writer w]
     (.write w (print-time c))))

#?(:clj
   (defmethod print-method Duration [c ^Writer w]
     (.write w (print-duration c))))
