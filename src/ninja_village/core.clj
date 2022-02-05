(ns ninja-village.core
  (:gen-class)
  (:require [clojure.spec.alpha :as s]
            [clojure.repl :refer (doc)]))

(s/def :core/stat (s/and int? #(< % 6) #(> % 0)))
(s/def :core/speed :core/stat)
(s/def :core/jutsu :core/stat)
(s/def :core/health :core/stat)
(s/def :core/tools :core/stat)
(s/def :core/stealth :core/stat)
(s/def :core/ninja-stats (s/keys :req [:core/health
                                       :core/jutsu
                                       :core/speed
                                       :core/stealth
                                       :core/tools]))
(s/def :core/name string?)
(s/def :core/rank #{:genin :chunin :jonin :kage})
(s/def :core/ninja (s/keys :req [:core/name
                                 :core/rank
                                 :core/ninja-stats]))

(s/def :core/imp-type #{:academy :library :hospital})
(s/def :core/imp-quality :core/stat)
(s/def :core/improvement (s/keys :req [:core/name
                                       :core/imp-type
                                       :core/imp-quality]))

(s/def :core/ninjas (s/* :core/ninja))
(s/def :core/improvements (s/map-of :core/imp-type
                                    :core/improvement))
(s/def :core/def int?)
(s/def :core/village (s/keys :req [:core/name
                                   :core/def
                                   :core/ninjas
                                   :core/improvements]))

(def a-ninja {:core/name "Naruto"
              :core/rank :genin
              :core/ninja-stats {:core/health 3
                                 :core/jutsu 3
                                 :core/speed 2
                                 :core/stealth 2
                                 :core/tools 2}
              :core/special {}})

(s/valid? :core/ninja a-ninja)

(def leaf-ninjas [a-ninja])

(s/valid? :core/ninjas leaf-ninjas)

(def an-improvement {:core/name "ninja academy"
                     :core/imp-type :academy
                     :core/imp-quality 1})

(s/valid? :core/improvement an-improvement)

(def leaf-improvements {:academy an-improvement})

(s/valid? :core/improvements leaf-improvements)

(def leaf-village {:core/name "hidden leaf"
                   :core/def 5
                   :core/ninjas leaf-ninjas
                   :core/improvements leaf-improvements})

(s/valid? :core/village leaf-village)

(defn generate-skills [level & [academy-quality]]
  (let [skill-base ({:genin 1 :chunin 2 :jonin 3 :kage 5} level)]
    (into (sorted-map)
          (zipmap (shuffle [:core/speed :core/jutsu :core/health 
                            :core/tools :core/stealth])
                  (take 5 (concat (vector (inc skill-base))
                                  (repeat (or academy-quality 0) (inc skill-base))
                                  (repeat skill-base)))))))

(s/fdef recruit-ninja
  :args :core/village
  :ret :core/village)

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

(s/conform :core/village (->> leaf-village
                             (recruit-ninja :genin)
                             (recruit-ninja :chunin)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
