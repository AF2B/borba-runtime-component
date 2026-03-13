(ns borba.runtime.config
  (:require
   [aero.core :as aero]
   [clojure.java.io :as io]))

(defn load-config
  [profile]
  (aero/read-config
   (io/resource "config.edn")
   {:profile profile}))
