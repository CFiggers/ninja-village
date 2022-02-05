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
              :ninja/special {}})

(def leaf-ninjas [a-ninja])

(def an-improvement {:core/name "ninja academy"
                     :village/imp-type :academy
                     :village/imp-quality 1})

(def leaf-improvements {:academy an-improvement})

(def leaf-village {:core/name "hidden leaf"
                   :village/def 5
                   :ninja/ninjas leaf-ninjas
                   :village/improvements leaf-improvements})

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
    (test/testing "should recruit a genin"
      (test/is (s/valid? :village/village
                         (village/recruit-ninja :genin test-village))))
    (test/testing "should recruit a chunin"
      (test/is (s/valid? :village/village
                         (village/recruit-ninja :chunin test-village))))
    (test/testing "should recruit a jonin"
      (test/is (s/valid? :village/village
                         (village/recruit-ninja :jonin test-village))))
    (test/testing "should recruit a kage"
      (test/is (s/valid? :village/village
                         (village/recruit-ninja :kage test-village))))))