(ns ninja-village.core
  (:gen-class)
  (:require [clojure.spec.alpha :as s]
            [clojure.repl :refer (doc)]
            [ninja-village.ninja :as ninja]
            [ninja-village.village :as village]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
