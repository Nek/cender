(ns cender.scratch
  (:require [cender.v1.client :refer [connect-client call-fn eval-code set-var get-var import-ns set-obj-prop]])
  (:import [cender.v1.client Vector]))

#_(defn -main [& args]
  (connect-client "tcp://127.0.0.1:10000")
  (println (call-fn "move_object" ["Cube" (Vector. [0 1 0])])))


;; TODO:
;; [ ] Color,
;; [ ] Euler,
;; [ ] Matrix,
;; [ ] Quaternion,
;; [x] Vector,

(connect-client "tcp://127.0.0.1:10000")
;; (println (eval "a = 100"))
;; (println "Send:" (set-var "a" 100))
;; (println "Receive:" (get-var "a"))
;; (call-fn "move_object" ["Cube" (Vector. [0 1 0])])
(set-obj-prop "Cube" "location" (Vector. [1 1 1]))

