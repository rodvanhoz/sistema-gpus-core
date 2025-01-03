(ns sistema-gpus-core.models.configuracoes
  (:require [toucan.models :as models]
            [sistema-gpus-core.domain.model :refer [ModelProtocol transform model-name]]
            [toucan.db :as db]))

(models/defmodel Configuracoes :configuracoes)

(defrecord ConfiguracoesModel [])

(extend-type ConfiguracoesModel
  ModelProtocol
  (model-name [_] Configuracoes)
  (primary-key [_ entity] (:id_configuracao entity))
  (default-fields [_]
    [:id_configuracao
     :resolucao_abrev
     :resolucao_detalhe
     :api
     :qualidade_grafica
     :ssao
     :fxaa
     :taa
     :rt
     :aa
     :nvidia_tec
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
    (db/simple-delete! (model-name this) {:id_configuracao id}))

  (items-count [this]
    (db/count (model-name this))))

(defn ->Configuracoes
  "Retorna um record ConfiguracoesModel que implementa ModelProtocol."
  []
  (->ConfiguracoesModel))

