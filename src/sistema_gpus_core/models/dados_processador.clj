(ns sistema-gpus-core.models.dados-processador
  (:require [toucan.models :as models]
            [sistema-gpus-core.domain.model :refer [ModelProtocol transform model-name]]
            [toucan.db :as db]))

(models/defmodel DadosProcessador :dados_processador)

(defrecord DadosProcessadorModel [])

(extend-type DadosProcessadorModel
  ModelProtocol
  (model-name [_] DadosProcessador)
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
    (db/select (model-name this)))

  (get-item [this k v]
    (db/select-one (model-name this) k v))

  (put-item! [this entity]
    (db/insert! (model-name this) (transform this entity)))

  (update-item! [this clause kvs]
    (let [fn-update-where (partial db/update-where! (model-name this) clause)]
      (apply fn-update-where (->> kvs (seq) (flatten)))))

  (delete-item! [this id]
    (db/simple-delete! (model-name this) {:id_dados_processador id}))

  (items-count [this]
    (db/count (model-name this))))

(defn ->DadosProcessador
  "Retorna um record DadosProcessadorModel que implementa ModelProtocol."
  []
  (->DadosProcessadorModel))
