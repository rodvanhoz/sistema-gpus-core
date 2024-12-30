(ns sistema-gpus-core.models.processadores
  (:require [toucan.models :as models]
            [sistema-gpus-core.domain.model :refer [ModelProtocol transform model-name]]
            [toucan.db :as db]))

(models/defmodel Processadores :processadores)

(defrecord ProcessadoresModel [])

(extend-type ProcessadoresModel
  ModelProtocol
  (model-name [_] Processadores)
  (primary-key [_ entity] (:id_processador entity))
  (default-fields [_]
    [:id_processador
     :id_dados_processador
     :nome_fabricante
     :nome_modelo
     :market
     :released
     :codename
     :generation
     :memory_support
     :frequencia
     :turbofrequencia
     :base_clock
     :multiplicador
     :multipl_desbloqueado
     :nro_cores
     :nro_threads
     :smp
     :id_gpu
     :tdp
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
    (db/simple-delete! (model-name this) {:id_processador id}))

  (items-count [this]
    (db/count (model-name this))))

(defn ->Processadores
  "Retorna um record ProcessadoresModel que implementa ModelProtocol."
  []
  (->ProcessadoresModel))
