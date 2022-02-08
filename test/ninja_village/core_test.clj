(ns ninja-village.core-test
  (:require [clojure.test :as test]
            [clojure.spec.alpha :as s]
            [ninja-village.core :refer :all]
            [ninja-village.ninja :as ninja]
            [ninja-village.village :as village]
            [ninja-village.combat :as combat]))

(def a-ninja {:core/name "Naruto"
              :ninja/rank :genin
              :ninja/ninja-stats {:ninja/health 3
                                  :ninja/jutsu 3
                                  :ninja/speed 2
                                  :ninja/stealth 2
                                  :ninja/tools 2}
              :ninja/practice 0
              :ninja/special {}
              :ninja/hero true})

(if (:ninja/hero a-ninja)
  "it was true"
  "It was false")

(s/valid? :ninja/ninja a-ninja)

(def leaf-ninjas [a-ninja])

(def an-improvement village/canonical-academy)

(def leaf-improvements {:academy an-improvement})

(def leaf-village {:core/name "hidden leaf"
                   :village/def 5
                   :ninja/ninjas leaf-ninjas
                   :village/improvements leaf-improvements})

(def ninja1 (ninja/generate-ninja))

(def ninja2 (ninja/generate-ninja))

(combat/ninja-fight
 ninja1
 ninja2)

(def shikamaru 
  {:core/name "Shikamaru Nara"
   :ninja/rank :genin
   :ninja/ninja-stats #:ninja{:health 2, 
                              :jutsu 3, 
                              :speed 2, 
                              :stealth 1, 
                              :tools 1}
   :ninja/practice 0
   :ninja/hero true})

(def rando-ninja (ninja/generate-ninja))

(combat/det-winner shikamaru rando-ninja)

(test/deftest basic-test
  (prn "Test run: a basic ninja, improvements, and village")
  (let [test-ninja a-ninja
        test-ninjas leaf-ninjas
        test-improvement an-improvement
        test-improvements leaf-improvements
        test-village leaf-village]
    (test/testing "a ninja"
      (test/testing "should pass spec"
        (test/is (= true (s/valid? :ninja/ninja test-ninja)))))
    (test/testing "a collection of ninja"
      (test/testing "should pass spec"
        (test/is (= true (s/valid? :ninja/ninjas test-ninjas)))))
    (test/testing "a village improvement"
      (test/testing "should pass spec"
        (test/is (= true (s/valid? :village/improvement test-improvement)))))
    (test/testing "a map of improvements"
      (test/testing "should pass spec"
        (test/is (= true (s/valid? :village/improvements test-improvements)))))
    (test/testing "a ninja village"
      (test/testing "should pass spec"
        (test/is (= true (s/valid? :village/village test-village)))))))

(test/deftest recruit-test
  (prn "Test run: Recruit ninjas function")
  (let [test-village leaf-village]
    (test/testing "recruit a genin"
      (let [plus-genin (village/recruit-ninja :genin test-village)]
        (test/testing "should pass spec"
          (test/is (s/valid? :village/village plus-genin)))
        (test/testing "should have one additional ninja"
          (test/is (= (inc (count (:ninja/ninjas test-village)))
                      (count (:ninja/ninjas plus-genin)))))))
    (let [plus-chunin (village/recruit-ninja :chunin test-village)]
      (test/testing "recruit a chunin"
        (test/testing "should pass spec"
          (test/is (s/valid? :village/village plus-chunin)))
        (test/testing "should have one additional ninja"
          (test/is (= (inc (count (:ninja/ninjas test-village)))
                      (count (:ninja/ninjas plus-chunin)))))))
    (let [plus-jonin (village/recruit-ninja :jonin test-village)]
      (test/testing "recruit a jonin"
        (test/testing "should pass spec"
          (test/is (s/valid? :village/village plus-jonin))
          (test/testing "should have one additional ninja"
            (test/is (= (inc (count (:ninja/ninjas test-village)))
                        (count (:ninja/ninjas plus-jonin))))))))
    (let [plus-kage (village/recruit-ninja :kage test-village)]
      (test/testing "recruit a kage"
        (test/testing "should pass spec"
          (test/is (s/valid? :village/village plus-kage)))
        (test/testing "should have one additional ninja"
          (test/is (= (inc (count (:ninja/ninjas test-village)))
                      (count (:ninja/ninjas plus-kage)))))))))
