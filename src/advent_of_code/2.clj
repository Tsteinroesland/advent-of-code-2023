(ns advent-of-code.2
  (:require [clojure.string :refer [split trim]]))

(def games
  (split
   (slurp "resources/input2.txt")
   #"\n"))

(def max-red 12)
(def max-green 13)
(def max-blue 14)

(defn get-game-sets [game]
  (->
   (split game #":")
   (second)
   (trim)))

(defn get-game-num [game]
  (->
   (split game #"\:")
   (first)
   (subs 5)
   (Integer/parseInt)))

(defn split-game-to-sets [game]
  (->
   (get-game-sets game)
   (split  #";")
   (#(map trim %))))

(defn get-color-num [color game-set]
  (try
    (->
     (re-pattern (str "(\\d+) " color))
     (re-find game-set)
     (second)
     (Integer/parseInt))
    (catch Exception _ 0)))

(defn exceeds-max [game-set]
  (->
   (some true? [(> (get-color-num "blue" game-set) max-blue)
                (> (get-color-num "red" game-set) max-red)
                (> (get-color-num "green" game-set) max-green)])
   (or false)))

(defn evaluate-game [game]
  (->
   (split-game-to-sets game)
   (#(map exceeds-max %))
   (#(if (some true? %)
       0
       (get-game-num game)))))

(apply + (map evaluate-game games))

; ------------ PART 2 ---------------
(defn get-max [acc vtr] [(max (get vtr 0) (get acc 0))
                         (max (get vtr 1) (get acc 1))
                         (max (get vtr 2) (get acc 2))])

(defn evaluate-game-2 [game]
  (apply * (reduce get-max (map (fn [x] [(get-color-num "red" x)
                                         (get-color-num "green" x)
                                         (get-color-num "blue" x)])
                                (split-game-to-sets game)))))

(apply + (map evaluate-game-2 games))
