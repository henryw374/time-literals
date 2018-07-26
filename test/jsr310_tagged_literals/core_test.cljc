(ns jsr310-tagged-literals.core-test
  (:require #?(:clj [clojure.test :refer :all]
               :cljs [cljs.test :refer-macros [deftest testing run-tests run-all-tests is]])
                    [jsr310-tagged-literals.data-readers]
                    [jsr310-tagged-literals.read-write]
    #?(:cljs [cljsjs.js-joda-timezone])
    #?(:cljs [cljs.java-time.extend-eq-and-compare])))

(defn read-tagged [o]
  #?(:clj (clojure.edn/read-string {:readers jsr310-tagged-literals.read-write/tags} o)
     :cljs (cljs.reader/read-string o)))

(def all (merge
           #?(:clj {;; Following are not yet implemented in js-joda https://github.com/js-joda/js-joda/issues/165
                    :a-offset-time #jsr310/offset-time "08:09:46.150+01:00"
                    }
              :cljs nil)
           {
            :a-month            #jsr310/month "JUNE"
            :a-period           #jsr310/period "P1D"
            :a-date             #jsr310/date "2039-01-01"
            :a-date-time        #jsr310/date-time "2018-07-25T08:08:44.026"
            :a-zoned-date-time  #jsr310/zoned-date-time "2018-07-25T08:09:11.227+01:00[Europe/London]"
            :a-offset-date-time #jsr310/offset-date-time "2018-07-25T08:11:54.453+01:00"
            :a-instant          #jsr310/instant "2018-07-25T07:10:05.861Z"
            :a-time             #jsr310/time "08:12:13.366"
            :a-duration         #jsr310/duration "PT1S"
            :a-year             #jsr310/year "3030"
            :a-year-month       #jsr310/year-month "3030-01"
            :a-zone             #jsr310/zone "Europe/London"
            :a-day-of-week      #jsr310/day-of-week "TUESDAY"
            }))

(deftest a-test
  (testing "all can be printed and read"
    (doseq [[n v] all]
      (testing n
        (is (= v (-> v pr-str read-tagged)))))))
