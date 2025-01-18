(ns sistema-gpus-core.models.configuracoes-jogos
  (:require [toucan.models :as models]
            [sistema-gpus-core.domain.model :refer [ModelProtocol transform model-name]]
            [sistema-gpus-core.helper.utils :refer [build-where-clause]]
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
  (read-all
    [this]
    (db/query
     {:select [[:b.nome_jogo :nome_jogo]
               [:b.dt_lancto :dt_lancto]
               [:c.resolucao_abrev :resolucao_abrev]
               [:c.resolucao_detalhe :resolucao_detalhe]
               [:c.api :api]
               [:c.qualidade_grafica :qualidade_grafica]
               [:c.ssao :ssao]
               [:c.fxaa :fxaa]
               [:c.taa :taa]
               [:c.rt :rt]
               [:c.aa :aa]
               [:c.nvidia_tec :nvidia_tec]
               [:b.id_jogo :id_jogo]
               [:c.id_configuracao :id_configuracao]
               [:a.id_configuracao_jogo :id_configuracao_jogo]]
      :from   [[:configuracoes_jogos :a]]
      :join   [[:jogos         :b] [:= :a.id_jogo        :b.id_jogo]
               [:configuracoes :c] [:= :a.id_configuracao :c.id_configuracao]]}))

  (get-item
    [this kvs]
    (-> (db/query
         (merge
          {:select [[:b.nome_jogo :nome_jogo]
                    [:b.dt_lancto :dt_lancto]
                    [:c.resolucao_abrev :resolucao_abrev]
                    [:c.resolucao_detalhe :resolucao_detalhe]
                    [:c.api :api]
                    [:c.qualidade_grafica :qualidade_grafica]
                    [:c.ssao :ssao]
                    [:c.fxaa :fxaa]
                    [:c.taa :taa]
                    [:c.rt :rt]
                    [:c.aa :aa]
                    [:c.nvidia_tec :nvidia_tec]
                    [:b.id_jogo :id_jogo]
                    [:c.id_configuracao :id_configuracao]
                    [:a.id_configuracao_jogo :id_configuracao_jogo]]
           :from   [[:configuracoes_jogos :a]]
           :join   [[:jogos         :b] [:= :a.id_jogo        :b.id_jogo]
                    [:configuracoes :c] [:= :a.id_configuracao :c.id_configuracao]]
           :limit  1}
          {:where (build-where-clause kvs)}))
        (first)))

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
