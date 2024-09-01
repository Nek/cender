(ns cender.v1.client
  (:import  (org.zerorpc ZRpcClient)
            (java.io ByteArrayInputStream ByteArrayOutputStream))
  (:require [cognitect.transit :as transit]))

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
    [this method-name args]
    "Call a method by its name with a vector of arguments."))

(deftype RpcClient [client addr]
  Connector
  (connect [_]
    (.connect client addr))
  (disconnect [_]
    (.disconnect client))
  Caller
  (call [_ method-name args]
    (let [out (ByteArrayOutputStream. 4096)
          writer (transit/writer out :json)]
      (transit/write writer args)
      (.call client method-name (object-array [(.toString out)]))
      (let [in (ByteArrayInputStream. (.toByteArray out))
            reader (transit/reader in :json)]
        (transit/read reader)))))

(defn connect-client [addr]
  (let [client (->RpcClient (ZRpcClient.) addr)]
    (.connect client)
    client))