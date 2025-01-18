(ns sistema-gpus-core.models.testes-gpu
  (:require [toucan.models :as models]
            [sistema-gpus-core.domain.model :refer [ModelProtocol transform model-name]]
            [sistema-gpus-core.helper.utils :refer [build-where-clause]]
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
  (read-all
    [this]
    (db/query
     {:select [[:g.nome_modelo :nome_modelo_gpu]
               [:p.nome_modelo :nome_modelo_processador]
               [:j.nome_jogo :nome_jogo]
               [:c.resolucao_abrev :resolucao_abrev]
               [:c.qualidade_grafica :qualidade_grafica]
               [:c.api :api]
               [:c.fxaa :fxaa]
               [:c.ssao :ssao]
               [:c.aa :aa]
               [:c.rt :rt]
               [:c.taa :taa]
               [:c.nvidia_tec :nvidia_tec]
               [:tg.avg_fps :avg_fps]
               [:tg.min_fps :min_fps]
               [:tg.nome_tester :nome_tester]
               [:tg.dt_teste :dt_teste]
               [:tg.nome_driver_gpu :nome_driver_gpu]
               [:tg.id_teste_gpu :id_teste_gpu]
               [:j.id_jogo :id_jogo]
               [:c.id_configuracao :id_configuracao]
               [:cj.id_configuracao_jogo :id_configuracao_jogo]
               [:g.id_gpu :id_gpu]
               [:p.id_processador :id_processador]]
      :from   [[:testes_gpu :tg]]
      :join   [[:configuracoes_jogos :cj] [:= :tg.id_configuracao_jogo :cj.id_configuracao_jogo]
               [:jogos              :j]  [:= :cj.id_jogo            :j.id_jogo]
               [:configuracoes      :c]  [:= :cj.id_configuracao    :c.id_configuracao]
               [:gpus               :g]  [:= :tg.id_gpu             :g.id_gpu]
               [:processadores      :p]  [:= :tg.id_processador     :p.id_processador]]}))

  (get-item
    [this kvs]
    (-> (db/query
         (merge
          {:select [[:g.nome_modelo :nome_modelo_gpu]
                    [:p.nome_modelo :nome_modelo_processador]
                    [:j.nome_jogo :nome_jogo]
                    [:c.resolucao_abrev :resolucao_abrev]
                    [:c.qualidade_grafica :qualidade_grafica]
                    [:c.api :api]
                    [:c.fxaa :fxaa]
                    [:c.ssao :ssao]
                    [:c.aa :aa]
                    [:c.rt :rt]
                    [:c.taa :taa]
                    [:c.nvidia_tec :nvidia_tec]
                    [:tg.avg_fps :avg_fps]
                    [:tg.min_fps :min_fps]
                    [:tg.nome_tester :nome_tester]
                    [:tg.dt_teste :dt_teste]
                    [:tg.nome_driver_gpu :nome_driver_gpu]
                    [:tg.id_teste_gpu :id_teste_gpu]
                    [:j.id_jogo :id_jogo]
                    [:c.id_configuracao :id_configuracao]
                    [:cj.id_configuracao_jogo :id_configuracao_jogo]
                    [:g.id_gpu :id_gpu]
                    [:p.id_processador :id_processador]]
           :from   [[:testes_gpu :tg]]
           :join   [[:configuracoes_jogos :cj] [:= :tg.id_configuracao_jogo :cj.id_configuracao_jogo]
                    [:jogos              :j]  [:= :cj.id_jogo            :j.id_jogo]
                    [:configuracoes      :c]  [:= :cj.id_configuracao    :c.id_configuracao]
                    [:gpus               :g]  [:= :tg.id_gpu             :g.id_gpu]
                    [:processadores      :p]  [:= :tg.id_processador     :p.id_processador]]
           :limit  1}
          {:where (build-where-clause kvs)}))
        (first)))

  (put-item! [this entity]
    (db/insert! (model-name this) (transform this entity)))

  (update-item! [this clause kvs]
    (let [fn-update-where (partial db/update-where! (model-name this) clause)]
      (apply fn-update-where (->> kvs (seq) (flatten)))))

  (delete-item! [this id]
    (db/simple-delete! (model-name this) {:id_teste_gpu id}))

  (items-count [this]
    (db/count (model-name this))))

(defn melhores-dados-testes
  "Retorna todos os registros de testes_gpu junto com as tabelas relacionadas.
   (SEM group-by). O 'agrupamento' e 'MAX(...)' ficarão para o controller / lógica."
  []
  (db/query
   {:select [[:g.nome_modelo        :nome_modelo_gpu]
             [:p.nome_modelo        :nome_modelo_processador]
             [:j.nome_jogo          :nome_jogo]
             [:c.resolucao_abrev    :resolucao_abrev]
             [:c.qualidade_grafica  :qualidade_grafica]
             [:c.api                :api]
             [:c.fxaa               :fxaa]
             [:c.ssao               :ssao]
             [:c.aa                 :aa]
             [:c.rt                 :rt]
             [:c.taa                :taa]
             [:c.nvidia_tec         :nvidia_tec]
             [:tg.avg_fps           :avg_fps]
             [:tg.min_fps           :min_fps]
             [:tg.id_teste_gpu      :id_teste_gpu]
             [:j.id_jogo            :id_jogo]
             [:c.id_configuracao    :id_configuracao]
             [:cj.id_configuracao_jogo :id_configuracao_jogo]
             [:g.id_gpu             :id_gpu]]
    :from   [[:testes_gpu :tg]]
    :join   [[:configuracoes_jogos :cj] [:= :tg.id_configuracao_jogo :cj.id_configuracao_jogo]
             [:jogos :j]              [:= :cj.id_jogo :j.id_jogo]
             [:configuracoes :c]      [:= :cj.id_configuracao :c.id_configuracao]
             [:gpus :g]               [:= :tg.id_gpu :g.id_gpu]
             [:processadores :p]      [:= :tg.id_processador :p.id_processador]]}))

(defn ->TestesGPU
  "Retorna um record TesteGPUModel que implementa ModelProtocol."
  []
  (->TesteGPUModel))
