(ns mathtree.mathtree
  (:require [arachne.aristotle.registry :as reg]
            [arachne.aristotle :as aa]
            )
  (:gen-class))

(def m (aa/graph :jena-mini))

(reg/prefix 'foaf "http://xmlns.com/foaf/0.1/")
(reg/prefix 'mathtree "http://reepoi.github.io/mathtree#")

; (g/triples [:mathtree/taos :foaf/firstName "Taos"])

(aa/add m {:rdf/about :mathtree/taos
         :foaf/firstName "Taos"
         :foaf/lastName "T"})

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (aa/write m "graph.rdf" :rdfxml)
  (println "Hello, World!"))
