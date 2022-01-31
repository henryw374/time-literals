(ns time-literals.data-readers-clj
  (:refer-clojure :exclude [time])
  (:import (java.time LocalDate Instant LocalTime OffsetTime Duration Period ZonedDateTime OffsetDateTime LocalDateTime Year YearMonth ZoneId DayOfWeek Month MonthDay)))

(defn date [x]
  (. LocalDate parse x))

(defn instant [x]
  (. Instant parse x))

(defn time [x]
  (. LocalTime parse x))

(defn offset-time [x]
  (. OffsetTime parse x))

(defn duration [x]
  (. Duration parse x))

(defn period [x]
  (. Period parse x))

(defn zoned-date-time [x]
  (. ZonedDateTime parse x))

(defn offset-date-time [x]
  (. OffsetDateTime parse x))

(defn date-time [x]
  (. LocalDateTime parse x))

(defn year [x]
  (. Year parse x))

(defn year-month [x]
  (. YearMonth parse x))

(defn zone [x]
  (. ZoneId of x))

(defn day-of-week [x]
  (. DayOfWeek valueOf x))

(defn month [x]
  (. Month valueOf x))

(defn month-day [x]
  (. MonthDay parse x))










