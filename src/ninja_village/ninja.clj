(ns ninja-village.ninja
  (:require [clojure.spec.alpha :as s]))

(s/def :core/stat (s/and int? #(< % 6) #(> % 0)))
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
(s/def :ninja/ninja (s/keys :req [:core/name
                                  :ninja/rank
                                  :ninja/ninja-stats]))
(s/def :ninja/ninjas (s/* :ninja/ninja))


