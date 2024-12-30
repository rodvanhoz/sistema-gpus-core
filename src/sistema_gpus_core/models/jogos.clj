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

  (get-item [this k v]
    (db/select-one (model-name this) k v))

  (put-item! [this entity]
    (db/insert! (model-name this) (transform this entity)))

  (update-item! [this updates k v]
    (db/update-where! (model-name this) updates k v))

  (delete-item! [this id]
    (db/simple-delete! (model-name this) {:id_jogo id}))

  (items-count [this]
    (db/count (model-name this))))

(defn ->Jogos
  "Retorna um record JogosModel que implementa ModelProtocol."
  []
  (->JogosModel))

