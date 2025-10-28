(ns time-literals.read-write
  #?@(:cljs
      [(:require
         [cljs.reader :as reader]
         [time-literals.data-readers-cljs])]
      :clj
      [(:require [clojure.java.io :as io]
                 [time-literals.data-readers])
       (:import (java.io Writer)
                [java.time Period
                           LocalDate
                           LocalDateTime
                           ZonedDateTime
                           OffsetTime
                           Instant
                           OffsetDateTime
                           ZoneId
                           DayOfWeek
                           LocalTime
                           Month
                           Duration
                           Year
                           YearMonth
                           MonthDay])]))

(defn- print-to-string [t o]
  (str "#time/" t " \"" (str o) "\""))

(def print-period (partial print-to-string "period"))
(def print-date (partial print-to-string "date"))
(def print-date-time (partial print-to-string "date-time"))
(def print-zoned-date-time (partial print-to-string "zoned-date-time"))
(def print-offset-time (partial print-to-string "offset-time"))
(def print-instant (partial print-to-string "instant"))
(def print-offset-date-time (partial print-to-string "offset-date-time"))
(def print-zone (partial print-to-string "zone"))
(def print-day-of-week (partial print-to-string "day-of-week"))
(def print-time (partial print-to-string "time"))
(def print-month (partial print-to-string "month"))
(def print-month-day (partial print-to-string "month-day"))
(def print-duration (partial print-to-string "duration"))
(def print-year (partial print-to-string "year"))
(def print-year-month (partial print-to-string "year-month"))

(defn print-time-literals-cljs! []

  #?(:cljs
     (extend-protocol IPrintWithWriter
       ;Period (-pr-writer [d writer opts] (-write writer (print-period d)))
       js/Temporal.PlainDate (-pr-writer [d writer opts] (-write writer (print-date d)))
       js/Temporal.PlainDateTime (-pr-writer [d writer opts] (-write writer (print-date-time d)))
       js/Temporal.ZonedDateTime (-pr-writer [d writer opts] (-write writer (print-zoned-date-time d)))
       ;OffsetTime (-pr-writer [d writer opts] (-write writer (print-offset-time d)))
       js/Temporal.Instant (-pr-writer [d writer opts] (-write writer (print-instant d)))
       ;OffsetDateTime (-pr-writer [d writer opts] (-write writer (print-offset-date-time d)))
       ;js/Temporal.TimeZone (-pr-writer [d writer opts] (-write writer (print-zone d)))
       ;DayOfWeek (-pr-writer [d writer opts] (-write writer (print-day-of-week d)))
       js/Temporal.PlainTime (-pr-writer [d writer opts] (-write writer (print-time d)))
       ;Month (-pr-writer [d writer opts] (-write writer (print-month d)))
       js/Temporal.PlainMonthDay (-pr-writer [d writer opts] (-write writer (print-month-day d)))
       js/Temporal.Duration (-pr-writer [d writer opts] (-write writer (print-duration d)))
       ;Year (-pr-writer [d writer opts] (-write writer (print-year d)))
       js/Temporal.PlainYearMonth (-pr-writer [d writer opts] (-write writer (print-year-month d)))
       )))

(defn print-time-literals-clj! []
  #?(:clj (defmethod print-method Period [c ^Writer w] (.write w ^String (print-period c))))
  #?(:clj (defmethod print-method LocalDate [c ^Writer w] (.write w ^String (print-date c))))
  #?(:clj (defmethod print-method LocalDateTime [c ^Writer w] (.write w ^String (print-date-time c))))
  #?(:clj (defmethod print-method ZonedDateTime [c ^Writer w] (.write w ^String (print-zoned-date-time c))))
  #?(:clj (defmethod print-method OffsetTime [c ^Writer w] (.write w ^String (print-offset-time c))))
  #?(:clj (defmethod print-method Instant [c ^Writer w] (.write w ^String (print-instant c))))
  #?(:clj (defmethod print-method OffsetDateTime [c ^Writer w] (.write w ^String (print-offset-date-time c))))
  #?(:clj (defmethod print-method ZoneId [c ^Writer w] (.write w ^String (print-zone c))))
  #?(:clj (defmethod print-method DayOfWeek [c ^Writer w] (.write w ^String (print-day-of-week c))))
  #?(:clj (defmethod print-method LocalTime [c ^Writer w] (.write w ^String (print-time c))))
  #?(:clj (defmethod print-method Month [c ^Writer w] (.write w ^String (print-month c))))
  #?(:clj (defmethod print-method MonthDay [c ^Writer w] (.write w ^String (print-month-day c))))
  #?(:clj (defmethod print-method Duration [c ^Writer w] (.write w ^String (print-duration c))))
  #?(:clj (defmethod print-method Year [c ^Writer w] (.write w ^String (print-year c))))
  #?(:clj (defmethod print-method YearMonth [c ^Writer w] (.write w ^String (print-year-month c))))

  #?(:clj (defmethod print-dup Period [c ^Writer w] (.write w ^String (print-period c))))
  #?(:clj (defmethod print-dup LocalDate [c ^Writer w] (.write w ^String (print-date c))))
  #?(:clj (defmethod print-dup LocalDateTime [c ^Writer w] (.write w ^String (print-date-time c))))
  #?(:clj (defmethod print-dup ZonedDateTime [c ^Writer w] (.write w ^String (print-zoned-date-time c))))
  #?(:clj (defmethod print-dup OffsetTime [c ^Writer w] (.write w ^String (print-offset-time c))))
  #?(:clj (defmethod print-dup Instant [c ^Writer w] (.write w ^String (print-instant c))))
  #?(:clj (defmethod print-dup OffsetDateTime [c ^Writer w] (.write w ^String (print-offset-date-time c))))
  #?(:clj (defmethod print-dup ZoneId [c ^Writer w] (.write w ^String (print-zone c))))
  #?(:clj (defmethod print-dup DayOfWeek [c ^Writer w] (.write w ^String (print-day-of-week c))))
  #?(:clj (defmethod print-dup LocalTime [c ^Writer w] (.write w ^String (print-time c))))
  #?(:clj (defmethod print-dup Month [c ^Writer w] (.write w ^String (print-month c))))
  #?(:clj (defmethod print-dup MonthDay [c ^Writer w] (.write w ^String (print-month-day c))))
  #?(:clj (defmethod print-dup Duration [c ^Writer w] (.write w ^String (print-duration c))))
  #?(:clj (defmethod print-dup Year [c ^Writer w] (.write w ^String (print-year c))))
  #?(:clj (defmethod print-dup YearMonth [c ^Writer w] (.write w ^String (print-year-month c))))
  )


(def tags
  ;necessarily a straight copy of data_readers.cljc. cannot read it in macro bc could be many on classpath
  {
   'time/period           #?(:cljs str :clj time-literals.data-readers/period)
   'time/date             #?(:cljs time-literals.data-readers-cljs/date :clj time-literals.data-readers/date)
   'time/date-time        #?(:cljs time-literals.data-readers-cljs/date-time :clj time-literals.data-readers/date-time)
   'time/zoned-date-time  #?(:cljs time-literals.data-readers-cljs/zoned-date-time :clj time-literals.data-readers/zoned-date-time)
   'time/offset-time      #?(:cljs str :clj time-literals.data-readers/offset-time)
   'time/instant          #?(:cljs time-literals.data-readers-cljs/instant :clj time-literals.data-readers/instant)
   'time/offset-date-time #?(:cljs str :clj time-literals.data-readers/offset-date-time)
   ;'time/zone             #?(:cljs time-literals.data-readers-cljs/zone :clj time-literals.data-readers/zone)
   'time/day-of-week      #?(:cljs str :clj time-literals.data-readers/day-of-week)
   'time/time             #?(:cljs time-literals.data-readers-cljs/time :clj time-literals.data-readers/time)
   'time/month            #?(:cljs str :clj time-literals.data-readers/month)
   'time/month-day        #?(:cljs time-literals.data-readers-cljs/month-day :clj time-literals.data-readers/month-day)
   'time/duration         #?(:cljs time-literals.data-readers-cljs/duration :clj time-literals.data-readers/duration)
   'time/year             #?(:cljs str :clj time-literals.data-readers/year)
   'time/year-month       #?(:cljs time-literals.data-readers-cljs/year-month :clj time-literals.data-readers/year-month)
   })


