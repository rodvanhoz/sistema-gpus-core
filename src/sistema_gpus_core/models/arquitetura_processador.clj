(ns sistema-gpus-core.models.arquitetura-processador
  (:require [toucan.models :as models]
            [sistema-gpus-core.domain.model :refer [ModelProtocol]]
            [toucan.db :as db]))

(models/defmodel ArquiteturaProcessador :arquitetura_processador)

(extend-type ArquiteturaProcessador
  ModelProtocol
  (table-name [_] :arquitetura_processador)
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
    (db/select (table-name this)))

  (get-item [this id]
    (db/select-one (table-name this) {:id_arquitetura_proc id}))

  (put-item! [this entity]
    (db/insert! (table-name this) (transform this entity)))

  (update-item! [this id updates]
    (db/update! (table-name this) {:id_arquitetura_proc id} updates))

  (delete-item! [this id]
    (db/delete! (table-name this) {:id_arquitetura_proc id}))

  (items-count [this]
    (db/select-one [(str "SELECT COUNT(*) AS count FROM " (name (table-name this)))])))
