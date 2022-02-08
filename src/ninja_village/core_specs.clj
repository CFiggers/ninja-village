(ns ninja-village.core-specs
  (:require [clojure.spec.alpha :as s]))

(s/def :core/stat (s/and int? #(<= % 6) #(>= % 1)))
(s/def :core/name string?)
