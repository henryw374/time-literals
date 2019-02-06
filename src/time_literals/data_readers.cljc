(ns time-literals.data-readers
  (:refer-clojure :exclude [time]))

(defn date [x]
  (list '. 'java.time.LocalDate 'parse x))

(defn instant [x]
  (list '. 'java.time.Instant 'parse x))

(defn time [x]
  (list '. 'java.time.LocalTime 'parse x))

(defn offset-time [x]
  (list '. 'java.time.OffsetTime 'parse x))

(defn duration [x]
  (list '. 'java.time.Duration 'parse x))

(defn period [x]
  (list '. 'java.time.Period 'parse x))

(defn zoned-date-time [x]
  (list '. 'java.time.ZonedDateTime 'parse x))

(defn offset-date-time [x]
  (list '. 'java.time.OffsetDateTime 'parse x))

(defn date-time [x]
  (list '. 'java.time.LocalDateTime 'parse x))

(defn year [x]
  (list '. 'java.time.Year 'parse x))

(defn year-month [x]
  (list '. 'java.time.YearMonth 'parse x))

(defn zone [x]
  (list '. 'java.time.ZoneId 'of x))

(defn day-of-week [x]
  (list '. 'java.time.DayOfWeek 'valueOf x))

(defn month [x]
  (list '. 'java.time.Month 'valueOf x))










