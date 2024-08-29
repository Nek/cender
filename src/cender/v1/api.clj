(ns cender.v1.api
  "The entry point to key cender functions."
  (:require [necessary-evil.core :as xml-rpc]
            [cender.v1.api :as cender]))

;; TODO: Should all side-affecting functions end with an exclamation mark or will this just be tedious?

(def default-url--xml-rpc "http://localhost:8000")

(defn !
  "Evaluates blender code (using xml-rpc) at the given `url`."
  ([str--code]
   (! str--code default-url--xml-rpc))
  ([str--code url--xml-rpc]
   (xml-rpc/call url--xml-rpc :eval_code str--code)))

(defn object-names []
  (!  "bpy.data.objects.keys()"))

(defn select
  ([]
   (! "bpy.ops.object.select_all(action='SELECT')"))
  ([name]
   "TODO: Currently has an unexpected nil problem."
   (! (format "bpy.data.objects['%s'].select_set(state=True)" name))))

(defn deselect
  ([]
   (! "bpy.ops.object.select_all(action='DESELECT')"))
  ([name]))

(defn delete
  "Remove object and associated data by calling object name.

  TODO: Currently doesn't work."
  [name]
  (deselect-all)
  (select name)
  (! "bpy.ops.object.delete()"))

(! "bpy.ops.object.delete()")

(defn move [name, [x y z]]
  (! (format "move_object(%s, %f, %f, %f)"
             name x y z)))

(defn undo []
  "TODO: Doesn't currently work"
  ;; (! "bpy.ops.ed.undo()")
  (! "bpy.app.timers.register(lambda: bpy.ops.ed.undo() and None)"))
(defn redo []
  (! "bpy.ops.ed.redo()"))
