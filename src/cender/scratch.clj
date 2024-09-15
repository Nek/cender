(ns cender.scratch
  (:require [cender.v1.client :refer [connect-client call-fn]])
  (:import [cender.v1.client Vector]))

#_(defn -main [& args]
    (let [client (connect-client "tcp://127.0.0.1:10000")]
      (print (.call client ["move_object" "Cube" 0 0 0]))))

(defn -main [& args]
  (connect-client "tcp://127.0.0.1:10000")
  (println (call-fn "move_object" ["Cube" (Vector. [0 1 0])])))
  

;; Color,
;; Euler,
;; Matrix,
;; Quaternion,
;; Vector,