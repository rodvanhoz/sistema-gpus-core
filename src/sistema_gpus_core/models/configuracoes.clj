(ns sistema-gpus-core.models.configuracoes
  (:require [toucan.models :as models]
            [sistema-gpus-core.domain.model :refer [ModelProtocol]]
            [toucan.db :as db]))

(models/defmodel Configuracao :configuracoes)

(extend-type Configuracao
  ModelProtocol
  (table-name [_] :configuracoes)
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
    (db/select (table-name this)))

  (get-item [this id]
    (db/select-one (table-name this) {:id_configuracao id}))

  (put-item! [this entity]
    (db/insert! (table-name this) (transform this entity)))

  (update-item! [this id updates]
    (db/update! (table-name this) {:id_configuracao id} updates))

  (delete-item! [this id]
    (db/delete! (table-name this) {:id_configuracao id}))

  (items-count [this]
    (db/select-one [(str "SELECT COUNT(*) AS count FROM " (name (table-name this)))])))
