(ns build
  (:require [clojure.tools.build.api :as b]
            [deps-deploy.deps-deploy :as dd]
            [clojure.java.shell :as sh]
            [clojure.string :as string]))

(defn lib [artifact]
  (if (= "tempo" artifact)
    'com.widdindustries/time-literals-tempo
    'com.widdindustries/time-literals))

(def version (some-> (sh/sh "git" "describe" "--tags" "--abbrev=0")
                     :out
                     (string/trim-newline)))

(println "version " version)
(def class-dir "target/classes")

(defn basis [artifact]
  (if (= "tempo" artifact) 
    (b/create-basis {:project "deps.edn" :aliases [:tempo]})
    (b/create-basis {:project "deps.edn" :aliases [:jsjoda]})))

(defn jar-file [lib] (format "target/%s-%s.jar" (name lib) version))

(defn clean [_]
  (b/delete {:path "target"})
  (sh/sh "rm" "-rf" "web-target/public/*")
  )

(defn jar [{:keys [artifact]}]
  (println "jarring... " artifact  )
  (let [lib (lib (str artifact))
        basis (basis (str artifact))]
    (b/write-pom {:src-pom   (str artifact "/src-pom.xml")
                  :class-dir class-dir
                  :lib       lib
                  :version   version
                  :basis     basis})
    (b/copy-dir {:src-dirs   (->> (:classpath-roots basis)
                                  (filter #(.isDirectory (java.io.File. %))))
                 :target-dir class-dir})
    (b/jar {:class-dir class-dir
            :jar-file  (jar-file lib)})))

(defn install [{:keys [artifact]}]
  (println "installing... " artifact)
  (let [lib (lib (str artifact))
        basis (basis (str artifact))]
    (b/install {:basis     basis
                :lib       lib
                :version   version
                :jar-file  (jar-file lib)
                :class-dir class-dir}))
  (println (str "clj -Sdeps '{:deps {com.widdindustries/time-literals {:mvn/version \"" version "\"}}}'"))
  )

(defn deploy [{:keys [artifact]}]
  (let [lib (lib (str artifact))
        basis (basis (str artifact))]
    (dd/deploy {:installer :remote
                :artifact  (jar-file lib)
                :pom-file  (b/pom-path {:lib lib :class-dir class-dir})})))