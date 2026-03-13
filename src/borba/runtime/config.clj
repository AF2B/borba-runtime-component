(ns borba.runtime.config
  (:require
   [aero.core :as aero]
   [clojure.java.io :as io]))

(defmethod aero/reader 'deep-merge
  [opts _ values]
  (apply merge-with
         (fn deep-merge-fn [v1 v2]
           (if (and (map? v1) (map? v2))
             (merge-with deep-merge-fn v1 v2)
             v2))
         values))

(defn load-config
  [profile]
  (aero/read-config
   (io/resource "config.edn")
   {:profile profile}))
