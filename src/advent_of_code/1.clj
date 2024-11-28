(ns advent-of-code.1
  (:require [clojure.string :refer [split join]]))

(defn find-first-num [string]
  (let [res (Character/digit (first string) 10)]
    (if (not= res -1)
      res
      (recur (subs string 1)))))

(defn find-last-num [string]
  (find-first-num (join (reverse string))))

(def strings (split (slurp "./resources/input1.txt") #"\n"))

(time
 (reduce
  (fn [acc string] (+
                    acc
                    (Integer/parseInt
                     (str
                      (find-first-num string)
                      (find-last-num string)))))

  0 strings))




