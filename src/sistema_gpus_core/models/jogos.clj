(ns sistema-gpus-core.models.jogos
  (:require [toucan.models :as models]
            [sistema-gpus-core.domain.model :refer [ModelProtocol transform model-name]]
            [toucan.db :as db]))

(models/defmodel Jogos :jogos)

(defrecord JogosModel [])

(extend-type JogosModel
  ModelProtocol
  (model-name [_] Jogos)
  (primary-key [_ entity] (:id_jogo entity))
  (default-fields [_]
    [:id_jogo
     :nome_jogo
     :dt_lancto
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
    (db/simple-delete! (model-name this) {:id_jogo id}))

  (items-count [this]
    (db/count (model-name this))))

(defn ->Jogos
  "Retorna um record JogosModel que implementa ModelProtocol."
  []
  (->JogosModel))

