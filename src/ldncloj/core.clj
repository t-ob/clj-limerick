(ns ldncloj.core
  (:require [clojure.string :as str]))

;; (def reversed-words
;;   (sort (map str/reverse (str/split (slurp "/usr/share/dict/words") #"\n"))))

(def reversed-words
 (-> "/usr/share/dict/words"
     slurp
     (str/split #"\n")
     (#(map str/reverse %))
     sort))

(def f-reversed-words
  (filter #(> (count %) 2) reversed-words))

(def rhyming-map
  (let [m (group-by #(subs % 0 3) f-reversed-words)]
    (into {}
          (for [[k v] m :when (> (count v) 4)]
            [k v]))))

(defn n-rhyming-words [n m]
  (let [rhymes (rand-nth (vals m))]
    (map str/reverse (take n rhymes))))

(def triplets
  [["dreamed of eating his" "awoke with a" "dream"]
   ["went on a bus trip to" "drank too much" "drink"]
   ["ran at the speed of" "set out to" "dream"]])

(defn limerick []
  (let [r1 (vec (n-rhyming-words 2 rhyming-map))
        r2 (vec (n-rhyming-words 3 rhyming-map))
        ws (rand-nth triplets)]
    (str
     "There once was a man from " (r2 0) ",\n"
     "Who " (ws 0) " " (r1 0) ",\n"
     "He " (ws 1) " " (r2 1) ",\n"
     "In the middle of the " (r2 2) ",\n"
     "And found that his " (ws 2) " had come " (r1 1) "!")))

(defn -main
  "I don't do a whole lot."
  [& args]
  (println "Hello, World!"))
