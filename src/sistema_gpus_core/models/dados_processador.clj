(ns sistema-gpus-core.models.dados-processador
  (:require [toucan.models :as models]
            [sistema-gpus-core.domain.model :refer [ModelProtocol]]
            [toucan.db :as db]))

(models/defmodel DadosProcessador :dados_processador)

(extend-type DadosProcessador
  ModelProtocol
  (table-name [_] :dados_processador)
  (primary-key [_ entity] (:id_dados_processador entity))
  (default-fields [_]
    [:id_dados_processador
     :socket
     :foundry
     :process_size
     :transistors
     :package
     :t_case_max
     :old_id])
  (transform [_ entity]
    entity)

  ;; CRUD
  (read-all [this]
    (db/select (table-name this)))

  (get-item [this id]
    (db/select-one (table-name this) {:id_dados_processador id}))

  (put-item! [this entity]
    (db/insert! (table-name this) (transform this entity)))

  (update-item! [this id updates]
    (db/update! (table-name this) {:id_dados_processador id} updates))

  (delete-item! [this id]
    (db/delete! (table-name this) {:id_dados_processador id}))

  (items-count [this]
    (db/select-one [(str "SELECT COUNT(*) AS count FROM " (name (table-name this)))])))
