(ns sistema-gpus-core.models.configuracoes-jogos
  (:require [toucan.models :as models]
            [sistema-gpus-core.domain.model :refer [ModelProtocol transform model-name]]
            [toucan.db :as db]))

(models/defmodel ConfiguracaoJogo :configuracoes_jogos)

(defrecord ConfiguracaoJogoModel [])

(extend-type ConfiguracaoJogoModel
  ModelProtocol
  (model-name [_] ConfiguracaoJogo)
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
    (db/select (model-name this)))

  (get-item [this k v]
    (db/select-one (model-name this) k v))

  (put-item! [this entity]
    (db/insert! (model-name this) (transform this entity)))

  (update-item! [this clause kvs]
    (let [fn-update-where (partial db/update-where! (model-name this) clause)]
      (apply fn-update-where (->> kvs (seq) (flatten)))))

  (delete-item! [this id]
    (db/simple-delete! (model-name this) {:id_configuracao_jogo id}))

  (items-count [this]
    (db/count (model-name this))))

(defn ->ConfiguracoesJogos
  "Retorna um record ConfiguracaoJogoModel que implementa ModelProtocol."
  []
  (->ConfiguracaoJogoModel))
