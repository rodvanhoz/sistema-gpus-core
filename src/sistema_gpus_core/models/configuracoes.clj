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

  (get-item [this k v]
    (db/select-one (model-name this) k v))

  (put-item! [this entity]
    (db/insert! (model-name this) (transform this entity)))

  (update-item! [this updates k v]
    (db/update-where! (model-name this) updates k v))

  (delete-item! [this id]
    (db/simple-delete! (model-name this) {:id_configuracao id}))

  (items-count [this]
    (db/count (model-name this))))

(defn ->Configuracoes
  "Retorna um record ConfiguracoesModel que implementa ModelProtocol."
  []
  (->ConfiguracoesModel))

