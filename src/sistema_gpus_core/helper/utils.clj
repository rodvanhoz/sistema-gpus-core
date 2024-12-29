(ns sistema-gpus-core.helper.utils
  (:import (java.util Properties))
  (:require [clj-time.coerce :as c]))

(defn uuid
  []
  (java.util.UUID/randomUUID))

(defn uuid-from-string
  [uuid]
  (when uuid
    (if (uuid? uuid)
      uuid
      (java.util.UUID/fromString uuid))))

(defn from-sql-date
  [date]
  (c/from-sql-date date))

(defn str->date
  [date]
  (c/to-timestamp date))