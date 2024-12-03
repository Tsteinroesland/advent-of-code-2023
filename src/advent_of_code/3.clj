(ns advent-of-code.3
  (:require [clojure.string :refer [split]]
            [clojure.spec.alpha :refer [int-in-range?]]
            [clojure.math :refer [pow]]))

(defn parse-char-to-int [character]
  (let [res (- (int character) 48)]
    (if (int-in-range? 0 10 res)
      res
      nil)))

(def matrix (split
             (slurp "./resources/input3.txt")
             #"\n"))

(defn is-symbol [ch]
  (if (false? ch)
    false
    (cond
      (nil? ch) false
      (= \. ch) false
      (= \  ch) false
      (int-in-range? 48 58 (int ch)) false
      (int-in-range? 65 91 (int ch)) false
      (int-in-range? 97 123 (int ch)) false
      (int-in-range? 193 256 (int ch)) false
      :else true)))

(defn get-matrix [x y]
  (->
   (get matrix y)
   (get x)))

(defn has-adjacent-symbol [[x y]]
  (cond
    (is-symbol (get-matrix (dec x) (dec y))) true
    (is-symbol (get-matrix x (dec y))) true
    (is-symbol (get-matrix (inc x) (dec y))) true

    (is-symbol (get-matrix (dec x) y)) true
    (is-symbol (get-matrix (inc x) y)) true

    (is-symbol (get-matrix (dec x) (inc y))) true
    (is-symbol (get-matrix x (inc y))) true
    (is-symbol (get-matrix (inc x) (inc y))) true))

(defn list-of-int-to-int [vtr]
  (->> vtr
       (reverse)
       (map-indexed (fn [idx x] (* x (pow 10 idx))))
       (reduce +)
       (int)))

(defn evaluate-line [string y]
  (->> string
       (map-indexed (fn [idx x]
                      (let [res (parse-char-to-int x)]
                        (if res
                          [idx res]
                          nil))))
       (reduce (fn [acc x] (if (nil? x)
                             (conj acc [])
                             (conj (vec (butlast acc)) (into (last acc) [x])))) [[]])
       (filter not-empty)
       (map (fn [x] {:coordinates (map (comp (fn [kuk] [kuk y]) first) x) :number (list-of-int-to-int (map second x))}))))

(time
 (->> matrix
      (map-indexed (fn [idx line] (evaluate-line line idx)))
      (apply concat)
      (filter (fn [x] (some true? (map has-adjacent-symbol (:coordinates x)))))
      (map :number)
      (reduce +)))

; ------ PART 2 --------
(time
 (->>
  (slurp "./resources/input3.txt")
  (map-indexed (fn [y-idx row]
                 (keep-indexed (fn [x-idx x] (if (Character/isDigit x)
                                               [(str "y" y-idx "x" x-idx) x]
                                               nil))
                               row)))))



