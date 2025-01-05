(ns sistema-gpus-core.domain.api-client
  (:refer-clojure :exclude [get]))

(defprotocol ApiProtocol
  "Define as funções básicas que devem ser implementadas pelas APIs."
  (get-all [this])
  (get [this id])
  (post [this entity])
  (put [this entity-fields id])
  (delete [this id]))
