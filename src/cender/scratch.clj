(ns cender.scratch
  (:require [necessary-evil.core :as xml-rpc]))

(def url--xml-rpc "http://localhost:8000")

(defn !
  "Evaluates blender code (using xml-rpc) at the given `url`."
  ([str--code]
   (! str--code url--xml-rpc))
  ([str--code url]
   (xml-rpc/call url :eval_code str--code)))

(defn objects []
  (!  "bpy.data.objects.keys()"))

(defn deselect-all []
  (! "bpy.ops.object.select_all(action='DESELECT')"))

(defn select [name]
  "TODO: Currently has an unexpected nil problem."
  (! (format "bpy.data.objects['%s'].select_set(state=True)" name)))

(defn delete
  "Remove object and associated data by calling object name.

  TODO: Currently doesn't work."
  [name]
  (deselect-all)
  (select name)
  (! "bpy.ops.object.delete()"))

(! "bpy.ops.object.delete()")

(defn move [name, [x y z]]
  (! (format "move_object('Camera', %f, %f, %f)"
             x y z)))

(defn undo []
  "TODO: Doesn't currently work"
  ;; (! "bpy.ops.ed.undo()")
  (! "bpy.app.timers.register(lambda: bpy.ops.ed.undo() and None)"))
(defn redo []
  (! "bpy.ops.ed.redo()"))

(comment
  (undo)
  (move "Camera", [0.0 10.0 0.0])

  (select "Cube")

  (delete "Cube")
  (objects)
  (! "move_object('Camera', 0.0,0.0,10.0)"))
