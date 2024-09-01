(ns cender.scratch
  [:require [cender.v1.client :refer [connect-client]]])

(defn -main [& args]
  (let [client (connect-client "tcp://127.0.0.1:10000")]
    (println (.call client "echo" [#{1 2 3}]))))