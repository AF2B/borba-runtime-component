(ns borba.runtime.system
  (:require
   [integrant.core :as ig]))

(defonce system (atom nil))

(defn start!
  [config]

  (reset! system (ig/init config)))

(defn stop!
  []

  (when @system
    (ig/halt! @system)
    (reset! system nil)))