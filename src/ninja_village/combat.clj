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

;; TODO - Refactor this to separate determining winner from
;;        declaring winner in terminal
(defn det-winner [ninja1 ninja2]
  (if (< 1 (reduce + (map #(if % 1 0)
                          (vals (ninja-fight ninja1 ninja2)))))
    (println (:core/name ninja1) "has won the fight!")
    (println (:core/name ninja2) "has won the fight!")))

