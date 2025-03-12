(ns time-literals.core-test
  (:require
    [clojure.test :refer [deftest is testing run-tests is]]
    #?(:clj [time-literals.data-readers])
    [time-literals.data-readers-cljs]
    [time-literals.read-write]
    [clojure.edn :as edn]
    #?@(:cljs [[cljs.reader :as rdr]])
    [clojure.tools.reader :as reader]))

(comment 
  (cljs.test/run-tests)
  )

(time-literals.read-write/print-time-literals-clj!)
(time-literals.read-write/print-time-literals-cljs!)

(def all (merge
           {
            ;:a-offset-time      #time/offset-time "08:09:46.150+01:00"
            ;:a-month            #time/month "JUNE"
            :a-month-day        #time/month-day "--09-09"
            ;:a-period           #time/period "P1D"
            :a-date             #time/date "2039-01-01"
            :a-date-time        #time/date-time "2018-07-25T08:08:44.026"
            :a-zoned-date-time  #time/zoned-date-time "2018-07-25T08:09:11.227+01:00[Europe/London]"
            ;:a-offset-date-time #time/offset-date-time "2018-07-25T08:11:54.453+01:00"
            :a-instant          #time/instant "2018-07-25T07:10:05.861Z"
            :a-time             #time/time "08:12:13.366"
            :a-duration         #time/duration "PT1S"
            ;:a-year             #time/year "3030"
            :a-year-month       #time/year-month "3030-01"
            ;:a-zone             #time/zone "Europe/London"
            ;:a-day-of-week      #time/day-of-week "TUESDAY"
            }))

#?(:cljs
   (set! (.-equals js/Temporal.Duration.prototype) (fn [o]
                                                     (this-as this
                                                       (zero? (js/Temporal.Duration.compare this o)))))
   )

(deftest symmetricity-test
  (testing "all can be printed and read"
    (doseq [[n v] all]
      (testing n
        (testing "edn"
          (is (#?(:clj = :cljs .equals) v (->> v pr-str (edn/read-string
                                   {:readers time-literals.read-write/tags})))))
        (testing "native read"
          (let [read-fn #?(:clj read-string :cljs rdr/read-string)]
            (is (#?(:clj = :cljs .equals) v (->> v pr-str read-fn)))))
        (testing "tools.reader"
          (binding [reader/*data-readers* time-literals.read-write/tags]
            (is (#?(:clj = :cljs .equals) v (->> v pr-str reader/read-string)))))))))


(comment
  clj -Mdev --main cljs.main --repl
  (require 'time-literals.core-test)
  (in-ns 'time-literals.core-test)
  (run-tests)
  (edn/read-string "#time/day-of-week \"TUESDAY\""
    {:readers time-literals.read-write/tags})
  (cljs.reader/read-string "#time/day-of-week \"TUESDAY\"")
  )
