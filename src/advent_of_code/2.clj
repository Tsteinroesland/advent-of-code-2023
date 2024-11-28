(ns advent-of-code.2
  (:require [clojure.string :refer [split trim]]))

(def games
  (split
   (slurp "resources/input2.txt")
   #"\n"))

(def max-red 12)
(def max-green 13)
(def max-blue 14)

(defn get-game-tuple [string]
  (drop 1 (re-find #"Game (\d+): (.*)" string)))

(defn split-game [game]
  (map trim (split
             (second (get-game-tuple game))
             #";")))

(defn get-color-num [color string]
  (try
    (Integer/parseInt
     (second
      (re-find
       (re-pattern (str "(\\d+) " color))
       string)))
    (catch Exception _ 0)))

(defn get-color-num-2 [my-set]
  (->>
   (split my-set #",")
   (map trim)
   (map (fn [x] (split x #" ")))
   (map reverse)
   (map (fn [x] (into [] x)))
   (into {})))

(->
 (get-color-num-2 "1 lube, 2 red")
 (get "lube"))

(into {} [["1" "lube"] ["2" "red"]])

(defn exceeds-max [my-set]
  (->
   (some true? [(> (get-color-num "blue" my-set) max-blue)
                (> (get-color-num "red" my-set) max-red)
                (> (get-color-num "green" my-set) max-green)])
   (or false)))

(defn evaluate-game [game]
  (if (some true? (map exceeds-max (split-game game)))
    0
    (Integer/parseInt (first (get-game-tuple game)))))

(apply + (map evaluate-game games))
