(ns spellchecker.core
  (:require [clojure.string :as string])
  (:import (org.apache.commons.text.similarity LevenshteinDistance))
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
  ([word] (-> (words "resources/words.txt")
               (unique)
               (find-word word))))

(defn distance
  "returns distnce between two words"
  [word query]
  (let [lv (LevenshteinDistance/getDefaultInstance)]
   (.apply lv word query)))

(defn correct?
  "chexk if word is present in dictionary"
  [word unique-words]
  (not (empty? (find-word unique-words word))))

(defn nearest-match
 [word words]
 (apply min-key (partial distance word) words))

(defn -main
  "find closest matching word"
  [& args]
  (let [word (first args)
        uniq-words (unique(words "resources/words.txt"))]
  (if (correct? word uniq-words)
    (println word)
    (println "Did you mean " (nearest-match word (into [] uniq-words))))))
