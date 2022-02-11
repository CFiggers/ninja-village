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


(defn get-winner [ninja-att ninja-def]
  (let [attacker-won (< 1 (reduce + (map #(if % 1 0)
                                         (vals (ninja-fight ninja-att ninja-def)))))
        winning-ninja (if attacker-won ninja-att ninja-def)
        losing-ninja (if attacker-won ninja-def ninja-att)]
    {:winner winning-ninja
     :loser losing-ninja}))

(defn print-result [{:keys [winner loser]}]
  (println (str (:core/name winner) " beat " (:core/name loser) "!")))

; ninjas -> find out who won -> add experience
; winners -> print winner to console

(defn give-experience [results]
  (update-in results [:winner :ninja/practice] #(+ % 100)))

(defn save-results [results]
  (spit "results.edn" results))

(defn triple-threat
  "does it all"
  [att-ninja def-ninja]
  (let [result (get-winner att-ninja def-ninja)
        exp-result (give-experience result)]
    (print-result exp-result)
    (save-results exp-result)))
