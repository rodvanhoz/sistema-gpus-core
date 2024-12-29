(ns sistema-gpus-core.models.jogos
  (:require [toucan.models :as models]
            [sistema-gpus-core.domain.model :refer [ModelProtocol]]
            [toucan.db :as db]))

(models/defmodel Jogo :jogos)

(extend-type Jogo
  ModelProtocol
  (table-name [_] :jogos)
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
    (db/select (table-name this)))

  (get-item [this id]
    (db/select-one (table-name this) {:id_jogo id}))

  (put-item! [this entity]
    (db/insert! (table-name this) (transform this entity)))

  (update-item! [this id updates]
    (db/update! (table-name this) {:id_jogo id} updates))

  (delete-item! [this id]
    (db/delete! (table-name this) {:id_jogo id}))

  (items-count [this]
    (db/select-one [(str "SELECT COUNT(*) AS count FROM " (name (table-name this)))])))
