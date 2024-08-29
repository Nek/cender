(ns cender.scratch
  (:require
   [cender.v1.api :as c]))

(comment
  (c/undo)
  (c/move "Cube", [0.0 10.0 0.0])

  (! (format "bpy.data.objects['%s'].select_set(state=True)" "Camera"))

  (c/select "Cube")
  (c/deselect)

  (c/delete "Cube")
  (c/object-names)
  (c/! "move_object('Camera', 0.0,0.0,10.0)"))
