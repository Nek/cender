(ns cender.scratch
  (:require [cender.v1.client :refer [connect-client call-fn eval-code set-var get-var import-ns set-obj-prop get-obj-prop set-item-prop get-item-prop add-cube]])
  (:import [cender.v1.client Vector Color]))

#_(defn -main [& args]
  (connect-client "tcp://127.0.0.1:10000")
  (println (call-fn "move_object" ["Cube" (Vector. [0 1 0])])))


;; Custom types support is easily implementable in this version!
;; TODO:
;; [x] Vector,
;; [x] Color,
;; [ ] Euler,
;; [ ] Matrix,
;; [ ] Quaternion.

(connect-client "tcp://127.0.0.1:10000")

(println "Send:" (set-var "a" 100))
(println "Receive:" (get-var "a"))
(call-fn "move_object" ["Cube" (Vector. [0 1 0])])
(set-obj-prop "Cube" "location" (Vector. [-1 2 0]))
(println (get-obj-prop "Cube" "location"))
(set-item-prop "materials" "Material" "diffuse_color" [1 0 0 1])

;; This doesn't work as I intended it to because we are using quite a hackish way of controlling Blender.
;; We are modifying objects from a background thread. This is huge DON'T DO IT in the manual.
;; The next step is to unhack it. 
#_(add-cube "ShinyCube" 1)