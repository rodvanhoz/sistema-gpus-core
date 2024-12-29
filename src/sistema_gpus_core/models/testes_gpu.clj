(ns sistema-gpus-core.models.testes-gpu
  (:require [toucan.models :as models]
            [sistema-gpus-core.domain.model :refer [ModelProtocol]]
            [toucan.db :as db]))

(models/defmodel TesteGPU :testes_gpu)

(extend-type TesteGPU
  ModelProtocol
  (table-name [_] :testes_gpu)
  (primary-key [_ entity] (:id_teste_gpu entity))
  (default-fields [_]
    [:id_teste_gpu
     :id_configuracao_jogo
     :id_gpu
     :id_processador
     :nome_driver_gpu
     :avg_fps
     :min_fps
     :dt_teste
     :nome_tester
     :old_id])
  (transform [_ entity]
    entity)

  ;; CRUD
  (read-all [this]
    (db/select (table-name this)))

  (get-item [this id]
    (db/select-one (table-name this) {:id_teste_gpu id}))

  (put-item! [this entity]
    (db/insert! (table-name this) (transform this entity)))

  (update-item! [this id updates]
    (db/update! (table-name this) {:id_teste_gpu id} updates))

  (delete-item! [this id]
    (db/delete! (table-name this) {:id_teste_gpu id}))

  (items-count [this]
    (db/select-one [(str "SELECT COUNT(*) AS count FROM " (name (table-name this)))])))
