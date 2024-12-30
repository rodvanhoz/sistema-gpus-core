(ns sistema-gpus-core.models.arquitetura-processador
  (:require [toucan.models :as models]
            [sistema-gpus-core.domain.model :refer [ModelProtocol transform model-name]]
            [toucan.db :as db]))

(models/defmodel ArquiteturaProcessador :arquitetura_processador)

(defrecord ArquiteturaProcessadorModel [])

(extend-type ArquiteturaProcessadorModel
  ModelProtocol
  (model-name [_] ArquiteturaProcessador)
  (primary-key [_ entity] (:id_arquitetura_proc entity))
  (default-fields [_]
    [:id_arquitetura_proc
     :id_arquitetura
     :id_processador
     :old_id])
  (transform [_ entity]
    entity)

  ;; CRUD
  (read-all [this]
    (db/select (model-name this)))

  (get-item [this k v]
    (db/select-one (model-name this) k v))

  (put-item! [this entity]
    (db/insert! (model-name this) (transform this entity)))

  (update-item! [this clause kvs]
    (let [fn-update-where (partial db/update-where! (model-name this) clause)]
      (apply fn-update-where (->> kvs (seq) (flatten)))))

  (delete-item! [this id]
    (db/simple-delete! (model-name this) {:id_arquitetura_proc id}))

  (items-count [this]
    (db/count (model-name this))))

(defn ->ArquiteturaProcessador
  "Retorna um record ArquiteturaProcessadorModel que implementa ModelProtocol."
  []
  (->ArquiteturaProcessadorModel))
