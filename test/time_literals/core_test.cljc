(ns time-literals.core-test
  (:require #?(:clj [clojure.test :refer :all]
               :cljs [cljs.test :refer-macros [deftest testing run-tests run-all-tests is]])
                    [time-literals.data-readers]
                    [time-literals.read-write]
    #?(:cljs [cljsjs.js-joda-timezone])
    #?(:cljs [cljs.java-time.extend-eq-and-compare])))

(defn read-tagged [o]
  #?(:clj (clojure.edn/read-string {:readers time-literals.read-write/tags} o)
     :cljs (cljs.reader/read-string o)))

(def all (merge
           #?(:clj {;; Following are not yet implemented in js-joda https://github.com/js-joda/js-joda/issues/165
                    :a-offset-time #time/offset-time "08:09:46.150+01:00"
                    }
              :cljs nil)
           {
            :a-month            #time/month "JUNE"
            :a-period           #time/period "P1D"
            :a-date             #time/date "2039-01-01"
            :a-date-time        #time/date-time "2018-07-25T08:08:44.026"
            :a-zoned-date-time  #time/zoned-date-time "2018-07-25T08:09:11.227+01:00[Europe/London]"
            :a-offset-date-time #time/offset-date-time "2018-07-25T08:11:54.453+01:00"
            :a-instant          #time/instant "2018-07-25T07:10:05.861Z"
            :a-time             #time/time "08:12:13.366"
            :a-duration         #time/duration "PT1S"
            :a-year             #time/year "3030"
            :a-year-month       #time/year-month "3030-01"
            :a-zone             #time/zone "Europe/London"
            :a-day-of-week      #time/day-of-week "TUESDAY"
            }))

(deftest a-test
  (testing "all can be printed and read"
    (doseq [[n v] all]
      (testing n
        (is (= v (-> v pr-str read-tagged)))))))
