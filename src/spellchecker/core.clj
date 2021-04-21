(ns spellchecker.core
  (:require [clojure.string :as string])
  (:gen-class))

(defn -trim-words
  "trims words in an array of words"
  [words]
  (map string/trim words))

(defn unique
  "gets unique set"
  [collection] (set (doall collection)))

(defn -in?
  "does element exist in collection"
  [collection query] (some #{query} collection))

(defn words
  "returns list of strings in given file seperated by \\n"
  [file-path]
  (-trim-words
    (string/split-lines (slurp file-path))))

(defn find-word
  "find word in dictionary"
  ([unique-words word] (-in? unique-words word))
  ([word] (find-word (unique (words "resources/words.txt")) word)))

(defn sum
  ([a b] (+ a b))
  ([a b c] (+ c (sum a b))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
