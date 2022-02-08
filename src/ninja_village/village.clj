(ns ninja-village.village
  (:require [clojure.spec.alpha :as s]
            [ninja-village.ninja :as ninja]
            [ninja-village.core-specs :as core]))

(s/def :village/imp-type #{:academy :library :hospital :ramen-shop})
(s/def :village/imp-quality :core/stat)
(s/def :village/improvement (s/keys :req [:core/name
                                          :village/imp-type
                                          :village/imp-quality]))

(s/def :village/improvements (s/map-of :village/imp-type
                                       :village/improvement))
(s/def :village/ninjas :ninja/ninjas)
(s/def :village/def int?)
(s/def :village/village (s/keys :req [:core/name
                                      :village/ninjas
                                      :village/def
                                      :village/improvements]))

;; Related to :village/ninjas

(s/fdef recruit-ninja
  :args :village/village
  :ret :village/village)
(defn recruit-ninja
  "Takes in a rank and a ninja village; trains a ninja of that rank"
  [rank village]
  (let [academy-quality (->> village :village/improvements
                             :academy :village/imp-quality)]
    (assoc village :ninja/ninjas
           (conj (:ninja/ninjas village)
                 (ninja/generate-ninja rank academy-quality)))))

;; Related to :village/def

;; TODO - write tests for this function
(s/fdef upgrade-def 
        :args :village/village
        :ret :village/village)
(defn upgrade-def [village & [improve-by]]
  (update village :village/def
          (partial + (or improve-by 1))))

;; Related to :village/improvements

(def canonical-academy 
  {:core/name "ninja academy"
   :village/imp-type :academy
   :village/imp-quality 1})

(def canonical-library 
  {:core/name "ninja library"
   :village/imp-type :library
   :village/imp-quality 1})

(def canonical-hospital
  {:core/name "ninja hospital"
   :village/imp-type :hospital
   :village/imp-quality 1})

(def canonical-ramen-shop
  {:core/name "ninja ramen shop"
   :village/imp-type :ramen-shop
   :village/imp-quality 1})

;; TODO - write tests for this function
(s/fdef upgrade-improvement
        :args :village/improvement
        :ret :village/improvement)
(defn upgrade-improvement [improvement & [improve-by]]
  (update improvement :village/imp-quality 
          (partial + (or improve-by 1))))

