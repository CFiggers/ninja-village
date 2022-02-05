(ns ninja-village.village
  (:require [clojure.spec.alpha :as s]
            [ninja-village.ninja :as ninja]))

(s/def :core/stat (s/and int? #(< % 6) #(> % 0)))
(s/def :core/name string?)

(s/def :village/imp-type #{:academy :library :hospital})
(s/def :village/imp-quality :core/stat)
(s/def :village/improvement (s/keys :req [:core/name
                                          :village/imp-type
                                          :village/imp-quality]))

(s/def :village/improvements (s/map-of :village/imp-type
                                       :village/improvement))
(s/def :village/def int?)
(s/def :village/village (s/keys :req [:core/name
                                      :ninja/ninjas
                                      :village/def
                                      :village/improvements]))

(defn generate-skills [level & [academy-quality]]
  (let [skill-base ({:genin 1 :chunin 2 :jonin 3 :kage 5} level)]
    (into (sorted-map)
          (zipmap (shuffle [:core/speed :core/jutsu :core/health 
                            :core/tools :core/stealth])
                  (take 5 (concat (vector (inc skill-base))
                                  (repeat (or academy-quality 0) (inc skill-base))
                                  (repeat skill-base)))))))

(s/fdef recruit-ninja
  :args :village/village
  :ret :village/village)
(defn recruit-ninja
  "Takes in a rank and a ninja village; trains a ninja of that rank"
  [rank village]
  (let [academy-quality (->> village :core/improvements
                             :academy :core/imp-quality)]
    (assoc village :core/ninjas
           (conj (:core/ninjas village)
                 {:core/name "Nina Trainee"
                  :core/rank rank
                  :core/ninja-stats (generate-skills
                                     rank
                                     academy-quality)}))))

