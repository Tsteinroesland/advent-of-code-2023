(ns advent-of-code.2
  (:require [clojure.string :refer [split]]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(def max-red 12)
(def max-green 13)
(def max-blue 14)

(defn get-game-num [string]
  (re-find #"Game (\d+): (.*)" string))

(skip (get-game-num "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"))

(defn get-color-num [color string]
  (Integer/parseInt
   (second
    (re-find
     (re-pattern (str "(\\d+) " color))
     string))))

(def get-blue-num (partial get-color-num "blue"))
(def get-red-num (partial get-color-num "red"))
(def get-green-num (partial get-color-num "green"))

(map
 (fn [string] [(get-game-num string)
               (get-blue-num string)
               (get-green-num string)
               (get-red-num string)]))


