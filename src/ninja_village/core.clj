(ns ninja-village.core
  (:gen-class)
  (:require [clojure.spec.alpha :as s]
            [clojure.repl :refer (doc)]
            [ninja-village.ninja :as ninja]
            [ninja-village.village :as village]
            [cli4clj.cli :as cli]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (cli/start-cli {:cmds {:ninja-hello {:fn (fn [& name] (println (str "Hello " (or (first name) "nobody") " ninja!")))}
                         :new-game {:fn (fn [])
                                    :short-description ""
                                    :long-description ""}
                         :load-game {:fn (fn [])}
                         :high-score {:fn (fn [])}}
                  :allow-eval false
                  :prompt-string "🗡️> "
                  ;; Since cli4clj version 1.6.0 an alternate scrolling mode is supported.
                  ;; By default the "old" scrolling is used.
                  ;; The new scrolling mode can be enabled by setting :alternate-scrolling to "true" or by specifying a corresponding "predicate function".
                  :alternate-scrolling (not (some #(= % "alt") args))
                  :alternate-height 3
                  :entry-message (str "Welcome to ninja-village!" "\n"
                                      "\n"
                                      "- To start a new game, type 'new-game.'" "\n"
                                      "- To load a village, type 'load-village.'" "\n"
                                      "\n"
                                      "To see a list of all available commands, press <Tab>.")}))
