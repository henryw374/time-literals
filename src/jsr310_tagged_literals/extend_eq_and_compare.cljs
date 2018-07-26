(ns jsr310-tagged-literals.extend-eq-and-compare
  (:require [java.time :refer [Period
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
                               YearMonth]]))



(extend-protocol IComparable
  Period          (-compare [x y] (.compareTo x y))
  LocalDate       (-compare [x y] (.compareTo x y))
  LocalDateTime   (-compare [x y] (.compareTo x y))
  ZonedDateTime   (-compare [x y] (.compareTo x y))
  OffsetTime      (-compare [x y] (.compareTo x y))
  Instant         (-compare [x y] (.compareTo x y))
  OffsetDateTime  (-compare [x y] (.compareTo x y))
  LocalTime       (-compare [x y] (.compareTo x y))
  Duration        (-compare [x y] (.compareTo x y))
  Year            (-compare [x y] (.compareTo x y))
  YearMonth       (-compare [x y] (.compareTo x y))
  ZoneId          (-compare [x y] (.compareTo x y))
  DayOfWeek       (-compare [x y] (.compareTo x y))
  Month           (-compare [x y] (.compareTo x y))
  )

(extend-protocol IEquiv
  Period           (-equiv [x y] (.equals x y))
  LocalDate        (-equiv [x y] (.equals x y))
  LocalDateTime    (-equiv [x y] (.equals x y))
  ZonedDateTime    (-equiv [x y] (.equals x y))
  OffsetTime       (-equiv [x y] (.equals x y))
  Instant          (-equiv [x y] (.equals x y))
  OffsetDateTime   (-equiv [x y] (.equals x y))
  LocalTime        (-equiv [x y] (.equals x y))
  Duration         (-equiv [x y] (.equals x y))
  Year             (-equiv [x y] (.equals x y))
  YearMonth        (-equiv [x y] (.equals x y))
  ZoneId           (-equiv [x y] (.equals x y))
  DayOfWeek        (-equiv [x y] (.equals x y))
  Month            (-equiv [x y] (.equals x y)))
