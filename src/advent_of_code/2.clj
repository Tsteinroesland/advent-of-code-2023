(ns advent-of-code.2
  (:require [clojure.string :refer [split trim]]))

(def games
  (split
   (slurp "resources/input2.txt")
   #"\n"))

(def max-red 12)
(def max-green 13)
(def max-blue 14)

(defn get-game-num [string]
  (re-find #"Game (\d+): (.*)" string))

(defn split-game [game]
  (map trim (split
             (second (drop 1 (get-game-num game)))
             #";")))

(defn get-color-num [color string]
  (try
    (Integer/parseInt
     (second
      (re-find
       (re-pattern (str "(\\d+) " color))
       string)))
    (catch Exception _ 0)))

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
