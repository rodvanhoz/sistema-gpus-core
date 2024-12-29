(ns sistema-gpus-core.models.configuracoes-jogos
  (:require [toucan.models :as models]
            [sistema-gpus-core.domain.model :refer [ModelProtocol]]
            [toucan.db :as db]))

(models/defmodel ConfiguracaoJogo :configuracoes_jogos)

(extend-type ConfiguracaoJogo
  ModelProtocol
  (table-name [_] :configuracoes_jogos)
  (primary-key [_ entity] (:id_configuracao_jogo entity))
  (default-fields [_]
    [:id_configuracao_jogo
     :id_jogo
     :id_configuracao
     :old_id])
  (transform [_ entity]
    entity)

  ;; CRUD
  (read-all [this]
    (db/select (table-name this)))

  (get-item [this id]
    (db/select-one (table-name this) {:id_configuracao_jogo id}))

  (put-item! [this entity]
    (db/insert! (table-name this) (transform this entity)))

  (update-item! [this id updates]
    (db/update! (table-name this) {:id_configuracao_jogo id} updates))

  (delete-item! [this id]
    (db/delete! (table-name this) {:id_configuracao_jogo id}))

  (items-count [this]
    (db/select-one [(str "SELECT COUNT(*) AS count FROM " (name (table-name this)))])))
