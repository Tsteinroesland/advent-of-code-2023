(ns advent-of-code.2
  (:require [clojure.string :refer [split trim]]))

(def testgames
  (split "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"
         #"\n"))

(def games
  (split
   (slurp "resources/input2.txt")
   #"\n"))

(def max-red 12)
(def max-green 13)
(def max-blue 14)

(defn split-game [game]
  (map trim (split
             (second (drop 1 (get-game-num game)))
             #";")))

(defn get-game-num [string]
  (re-find #"Game (\d+): (.*)" string))

(defn get-color-num [color string]
  (try
    (Integer/parseInt
     (second
      (re-find
       (re-pattern (str "(\\d+) " color))
       string)))
    (catch Exception e 0)))

(def get-blue-num (partial get-color-num "blue"))
(def get-red-num (partial get-color-num "red"))
(def get-green-num (partial get-color-num "green"))

(defn exceeds-max [string]
  (->
   (some true? [(> (get-blue-num string) max-blue)
                (> (get-red-num string) max-red)
                (> (get-green-num string) max-green)])
   (or false)))

(defn evaluate-game [game]
  (if (some true? (map exceeds-max (split-game game)))
    0
    (Integer/parseInt (second (get-game-num game)))))

(apply + (map evaluate-game games))
