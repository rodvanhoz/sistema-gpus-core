(ns sistema-gpus-core.models.processadores
  (:require [toucan.models :as models]
            [sistema-gpus-core.domain.model :refer [ModelProtocol]]
            [toucan.db :as db]))

(models/defmodel Processador :processadores)

(extend-type Processador
  ModelProtocol
  (table-name [_] :processadores)
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
    (db/select (table-name this)))

  (get-item [this id]
    (db/select-one (table-name this) {:id_processador id}))

  (put-item! [this entity]
    (db/insert! (table-name this) (transform this entity)))

  (update-item! [this id updates]
    (db/update! (table-name this) {:id_processador id} updates))

  (delete-item! [this id]
    (db/delete! (table-name this) {:id_processador id}))

  (items-count [this]
    (db/select-one [(str "SELECT COUNT(*) AS count FROM " (name (table-name this)))])))
