(ns sistema-gpus-core.models.testes-gpu
  (:require [toucan.models :as models]
            [sistema-gpus-core.domain.model :refer [ModelProtocol transform model-name]]
            [toucan.db :as db]))

(models/defmodel TestesGPU :testes_gpu)
(defrecord TesteGPUModel [])

(extend-type TesteGPUModel
  ModelProtocol
  (model-name [_] TestesGPU)
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
    (db/select (model-name this)))

  (get-item [this kvs]
    (let [fn-select (partial db/select-one (model-name this))]
      (apply fn-select (->> kvs (seq) (flatten)))))

  (put-item! [this entity]
    (db/insert! (model-name this) (transform this entity)))

  (update-item! [this clause kvs]
    (let [fn-update-where (partial db/update-where! (model-name this) clause)]
      (apply fn-update-where (->> kvs (seq) (flatten)))))

  (delete-item! [this id]
    (db/simple-delete! (model-name this) {:id_teste_gpu id}))

  (items-count [this]
    (db/count (model-name this))))

(defn ->TestesGPU
  "Retorna um record TesteGPUModel que implementa ModelProtocol."
  []
  (->TesteGPUModel))
