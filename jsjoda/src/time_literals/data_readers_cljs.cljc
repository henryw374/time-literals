(ns time-literals.data-readers-cljs
  (:refer-clojure :exclude [time])
  #?(:cljs (:require
             [java.time :refer [Period
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
                                MonthDay]])))

(defn date [x]
  #?(:clj
     (list '. 'java.time.LocalDate 'parse x)
     :cljs (. java.time.LocalDate parse x)
     ))

(defn instant [x]
  #?(:clj
     (list '. 'java.time.Instant 'parse x)
     :cljs (. java.time.Instant parse x)
     ))

(defn time [x]
  #?(:clj
     (list '. 'java.time.LocalTime 'parse x)
     :cljs (. java.time.LocalTime parse x)
     ))

(defn offset-time [x]
  #?(:clj
     (list '. 'java.time.OffsetTime 'parse x)
     :cljs (. java.time.OffsetTime parse x)
     ))

(defn duration [x]
  #?(:clj
     (list '. 'java.time.Duration 'parse x)
     :cljs (. java.time.Duration parse x)
     ))

(defn period [x]
  #?(:clj
     (list '. 'java.time.Period 'parse x)
     :cljs (. java.time.Period parse x)
     ))

(defn zoned-date-time [x]
  #?(:clj
     (list '. 'java.time.ZonedDateTime 'parse x)
     :cljs (. java.time.ZonedDateTime parse x)
     ))

(defn offset-date-time [x]
  #?(:clj
     (list '. 'java.time.OffsetDateTime 'parse x)
     :cljs (. java.time.OffsetDateTime parse x)
     ))

(defn date-time [x]
  #?(:clj
     (list '. 'java.time.LocalDateTime 'parse x)
     :cljs (. java.time.LocalDateTime parse x)
     ))

(defn year [x]
  #?(:clj
     (list '. 'java.time.Year 'parse x)
     :cljs (. java.time.Year parse x)
     ))

(defn year-month [x]
  #?(:clj
     (list '. 'java.time.YearMonth 'parse x)
     :cljs (. java.time.YearMonth parse x)
     ))

(defn zone [x]
  #?(:clj
     (list '. 'java.time.ZoneId 'of x)
     :cljs (. java.time.ZoneId of x)
     ))

(defn day-of-week [x]
  #?(:clj
     (list '. 'java.time.DayOfWeek 'valueOf x)
     :cljs (. java.time.DayOfWeek valueOf x)))

(defn month [x]
  #?(:clj
     (list '. 'java.time.Month 'valueOf x)
     :cljs (. java.time.Month valueOf x)))

(defn month-day [x]
  #?(:clj
     (list '. 'java.time.MonthDay 'parse x)
     :cljs (. java.time.MonthDay parse x)))










