(ns advent-of-code.1
  (:require [clojure.string :refer [split join starts-with? blank?]]
            [clojure.spec.alpha :refer [int-in-range?]]))

(def strings (split (slurp "./resources/input1.txt") #"\n"))

(defn parse-char-to-int [character]
  (let [res (- (int character) 48)]
    (if (int-in-range? 0 10 res)
      res
      nil)))

(defn find-first-num [string]
  (if (blank? string)
    nil
    (or
     (parse-char-to-int (first string))
     (recur (subs string 1)))))

(defn find-last-num [string]
  (->
   (reverse string)
   (join)
   (find-first-num)))

(defn get-number-from-string [string]
  (+ (* (find-first-num string) 10) (find-last-num string)))

(reduce + 0 (map get-number-from-string strings))

; ----------- PART 2 ------------

(defn check-for-string-number [string nums]
  (loop [numbs nums index 1]
    (if (empty? numbs)
      nil
      (if (starts-with? string (first numbs))
        [index (first numbs)]
        (recur (rest numbs) (inc index))))))

(def nums ["one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "zero"])
(defn find-first-num-2 [string]
  (if (blank? string)
    nil
    (or
     (parse-char-to-int (first string))
     (first (check-for-string-number string nums))
     (recur (subs string 1)))))

(defn find-last-num-2 [string]
  (if (blank? string)
    nil
    (let [string (join (reverse string))]
      (loop [s string]
        (or
         (parse-char-to-int (first s))
         (first (check-for-string-number s (map (fn [s] (join (reverse s))) nums)))
         (recur (subs s 1)))))))

(defn get-number-from-string-2 [string]
  (+ (* (find-first-num-2 string) 10) (find-last-num-2 string)))

(reduce + 0 (map get-number-from-string-2 strings))
