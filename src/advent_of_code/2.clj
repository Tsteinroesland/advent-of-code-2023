(ns advent-of-code.2
  (:require [clojure.string :refer [split trim]]))

(def games
  (split
   (slurp "resources/input2.txt")
   #"\n"))

(def max-red 12)
(def max-green 13)
(def max-blue 14)

(defn get-game-set [string]
  (->
   (split string #":")
   (second)
   (trim)))

(defn get-game-num [game]
  (->
   (split game #"\:")
   (first)
   (subs 5)
   (Integer/parseInt)))

(defn split-game [game]
  (->
   (get-game-set game)
   (split  #";")
   (#(map trim %))))

(defn get-color-num [color string]
  (try
    (->
     (re-pattern (str "(\\d+) " color))
     (re-find string)
     (second)
     (Integer/parseInt))
    (catch Exception _ 0)))

(defn exceeds-max [my-set]
  (->
   (some true? [(> (get-color-num "blue" my-set) max-blue)
                (> (get-color-num "red" my-set) max-red)
                (> (get-color-num "green" my-set) max-green)])
   (or false)))

(defn evaluate-game [game]
  (->
   (split-game game)
   (as-> x (map exceeds-max x))
   (as-> x (if (some true? x)
             0
             (get-game-num game)))))

(apply + (map evaluate-game games))
