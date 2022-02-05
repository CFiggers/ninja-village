(ns ninja-village.ninja
  (:require [clojure.spec.alpha :as s]))

(s/def :core/stat (s/and int? #(<= % 6) #(>= % 1)))
(s/def :core/name string?)

(s/def :ninja/speed :core/stat)
(s/def :ninja/jutsu :core/stat)
(s/def :ninja/health :core/stat)
(s/def :ninja/tools :core/stat)
(s/def :ninja/stealth :core/stat)
(s/def :ninja/ninja-stats (s/keys :req [:ninja/health
                                         :ninja/jutsu
                                         :ninja/speed
                                         :ninja/stealth
                                         :ninja/tools]))

(s/def :ninja/rank #{:genin :chunin :jonin :kage})
(s/def :ninja/practice (s/and int? #(<= % 100) #(>= % 0)))
(s/def :ninja/ninja (s/keys :req [:core/name
                                  :ninja/rank
                                  :ninja/ninja-stats
                                  :ninja/practice]))
(s/def :ninja/ninjas (s/* :ninja/ninja))

(defn randomstats []
  (shuffle [:ninja/speed :ninja/jutsu :ninja/health 
                            :ninja/tools :ninja/stealth]))

(defn generate-skills [level & [academy-quality]]
  (let [skill-base ({:genin 1 :chunin 2 :jonin 3 :kage 5} level)]
    (into (sorted-map)
          (zipmap (randomstats)
                  (take 5 (concat (vector (min (+ 2 skill-base) 6))
                                  (repeat (or academy-quality 0) (inc skill-base))
                                  (repeat skill-base)))))))


(defn generate-ninja [& [rank academy-quality]]
  {:core/name "Ninja Trainee"
   :ninja/rank (or rank :genin)
   :ninja/ninja-stats (generate-skills
                       (or rank :genin)
                       (or academy-quality 0))
   :ninja/practice 0})


