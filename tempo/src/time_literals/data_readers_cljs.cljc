(ns time-literals.data-readers-cljs
  (:refer-clojure :exclude [time]))

(defn date [x]
  #?(:clj
     (list 'js/Temporal.PlainDate.from x)
     :cljs (js/Temporal.PlainDate.from x)
     ))

(defn instant [x]
  #?(:clj
     (list 'js/Temporal.Instant.from x)
     :cljs (js/Temporal.Instant.from x)
     ))

(defn time [x]
  #?(:clj
     (list 'js/Temporal.PlainTime.from x)
     :cljs (js/Temporal.PlainTime.from x)
     ))

(defn duration [x]
  #?(:clj
     (list 'js/Temporal.Duration.from x)
     :cljs (js/Temporal.Duration.from x)
     ))

(defn zoned-date-time [x]
  #?(:clj
     (list 'js/Temporal.ZonedDateTime.from x)
     :cljs (js/Temporal.ZonedDateTime.from x)
     ))

(defn date-time [x]
  #?(:clj
     (list 'js/Temporal.PlainDateTime.from x)
     :cljs (js/Temporal.PlainDateTime.from x)
     ))

(defn year-month [x]
  #?(:clj
     (list 'js/Temporal.PlainYearMonth.from x)
     :cljs (js/Temporal.PlainYearMonth.from x)
     ))

(defn zone [x]
  #?(:clj
     (list 'js/Temporal.TimeZone.from x)
     :cljs (js/Temporal.TimeZone.from x)
     ))

#_(defn day-of-week [x]
  #?(:clj
     (list 'js/Temporal.PlainMonthDay.from x)
     :cljs (js/Temporal.PlainMonthDay.from x)))

(defn month-day [x]
  #?(:clj
     (list 'js/Temporal.PlainMonthDay.from x)
     :cljs (js/Temporal.PlainMonthDay.from x)
     
     ))










