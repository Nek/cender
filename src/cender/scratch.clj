(ns cender.scratch
  (:import  (org.zerorpc ZRpcClient)
            (java.io ByteArrayInputStream ByteArrayOutputStream))
  (:require [cognitect.transit :as transit]))

(defn -main [& args]
  (let [client (ZRpcClient.)
        out (ByteArrayOutputStream. 4096)
        writer (transit/writer out :json)]
    (.connect client "tcp://127.0.0.1:10000")
    (transit/write writer [#{1 2 3}])
    (let [args (.toString out)
          s (.call client "send_set" (object-array [args]))]
      (println "SENT")
      (let [in (ByteArrayInputStream. (.toByteArray out))
            reader (transit/reader in :json)]
        (apply println (transit/read reader))))))