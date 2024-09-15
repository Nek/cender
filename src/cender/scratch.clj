(ns cender.scratch
  [:require [cender.v1.client :refer [connect-client]]])

(defn -main [& args]
  (let [client (connect-client "tcp://127.0.0.1:10000")]
   (print (.call client ["move_object" "Cube" 0 0 0]))))