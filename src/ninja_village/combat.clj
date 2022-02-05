(ns ninja-village.combat
  (:require [clojure.spec.alpha :as s]
            [ninja-village.ninja :as ninja]))

(defn ninja-fight [ninja-attacking ninja-defending]
  (let [tests (take 3 (ninja/randomstats))]
    (into (sorted-map)
          (for [test tests]
            [test (> (test (:ninja/ninja-stats
                            ninja-attacking))
                     (test (:ninja/ninja-stats
                            ninja-defending)))]))))