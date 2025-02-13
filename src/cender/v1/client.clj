(ns cender.v1.client
  (:import  (org.zerorpc ZRpcClient)
            (java.io ByteArrayInputStream ByteArrayOutputStream))
  (:require [cognitect.transit :as transit]))

(defrecord Vector [vals])
(defrecord Color [r g b])

(def writers {Vector (transit/write-handler "blender-vector" (fn [o] (:vals o)))
              Color (transit/write-handler "blender-color" (fn [{:keys [r g b]}] [r g b]))})
(def readers {"blender-vector" (transit/read-handler (fn [v] (Vector. v)))
              "blender-color" (transit/read-handler (fn [[r g b]] (Color. r g b)))})

(defprotocol Connector
  "Something that can connect to a server."
  (connect
    [this]
    "Connect to server.")
  (disconnect
    [this]
    "Disconnect from server."))

(defprotocol Caller
  "Something that can call a method with a vector of arguments."
  (call
    [this args]
    "Call a method by its name with a vector of arguments."))

(deftype RpcClient [client addr]
  Connector
  (connect [_]
    (.connect client addr))
  (disconnect [_]
    (.disconnect client))
  Caller
  (call [_ args]
    (let [out (ByteArrayOutputStream. 4096)
          writer (transit/writer out :json {:handlers writers})]
      (transit/write writer args)
      (let [res (.call client "call_fn" (object-array [(.toString out)]))
            in (ByteArrayInputStream. (.getBytes res))
            reader (transit/reader in :json {:handlers readers})]
        (transit/read reader)))))

(def client (atom nil))

(defn connect-client [addr]
  (let [cl (->RpcClient (ZRpcClient.) addr)]
    (reset! client cl)
    (.connect cl)
    cl))

(defn disconnect []
  (.disconnect @client))

(defn call-fn [name args]
  (.call @client (cons name args)))

(defn eval-code [code]
  (call-fn "eval_code" [code]))


(defn import-ns [ns]
  (call-fn "import_ns" [ns]))


(defn set-var [var val]
  (call-fn "set_var" [var val]))


(defn get-var [var]
  (call-fn "get_var" [var]))

(defn set-obj-prop [obj prop val]
  (call-fn "set_obj_prop" [obj prop val]))

(defn get-obj-prop [obj prop]
  (call-fn "get_obj_prop" [obj prop]))

(defn set-item-prop [coll-name item-name prop-name val]
  (call-fn "set_item_prop" [coll-name item-name prop-name val]))

(defn get-item-prop [coll-name item-name prop-name]
  (call-fn "get_item_prop" [coll-name item-name prop-name]))


(defn add-cube [name size]
  (call-fn "add_cube" [name size]))

