(ns advent-of-code.3
  (:require [clojure.string :refer [split join starts-with? blank?]]
            [clojure.spec.alpha :refer [int-in-range?]]
            [clojure.pprint :refer [pprint]]))

(defn parse-char-to-int [character]
  (let [res (- (int character) 48)]
    (if (int-in-range? 0 10 res)
      res
      nil)))

(def matrix (split
             (slurp "./resources/input3.txt")
             #"\n"))

(defn is-symbol [ch]
  (cond
    (= \. ch) false
    (= \  ch) false
    (int-in-range? 48 58 (int ch)) false
    (int-in-range? 65 91 (int ch)) false
    (int-in-range? 97 123 (int ch)) false
    (int-in-range? 193 256 (int ch)) false
    :else true))

(defn get-matrix [x y]
  (->
   (get matrix y)
   (get x)))

(defn has-adjacent-symbol [x y]
  (try
    (cond
      (is-symbol (get-matrix (dec x) (dec y))) true
      (is-symbol (get-matrix (x) (dec y))) true
      (is-symbol (get-matrix (inc x) (dec y))) true

      (is-symbol (get-matrix (dec x) (y))) true
      (is-symbol (get-matrix (x) (y))) true
      (is-symbol (get-matrix (inc x) (y))) true

      (is-symbol (get-matrix (dec x) (inc y))) true
      (is-symbol (get-matrix (x) (inc y))) true
      (is-symbol (get-matrix (inc x) (inc y))) true)

    (catch Exception _ false)))






