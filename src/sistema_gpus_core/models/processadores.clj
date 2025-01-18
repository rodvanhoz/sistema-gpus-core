(ns sistema-gpus-core.models.processadores
  (:require [toucan.models :as models]
            [sistema-gpus-core.domain.model :refer [ModelProtocol transform model-name]]
            [sistema-gpus-core.helper.utils :refer [build-where-clause]]
            [toucan.db :as db]))

(models/defmodel Processadores :processadores)

(defrecord ProcessadoresModel [])

(extend-type ProcessadoresModel
  ModelProtocol
  (model-name [_] Processadores)
  (primary-key [_ entity] (:id_processador entity))
  (default-fields [_]
    [:id_processador
     :id_dados_processador
     :nome_fabricante
     :nome_modelo
     :market
     :released
     :codename
     :generation
     :memory_support
     :frequencia
     :turbofrequencia
     :base_clock
     :multiplicador
     :multipl_desbloqueado
     :nro_cores
     :nro_threads
     :smp
     :id_gpu
     :tdp
     :old_id])
  (transform [_ entity]
    entity)

  ;; CRUD
  (read-all
    [this]
    (db/query
     {:select [[:p.id_processador :id_processador]
               [:p.nome_fabricante :nome_fabricante]
               [:p.nome_modelo :nome_modelo]
               [:p.nro_cores :nro_cores]
               [:p.nro_threads :nro_threads]
               [:p.tdp :tdp]
               [:p.frequencia :frequencia]
               [:p.turbofrequencia :turbofrequencia]
               [:p.codename :codename]
               [:dp.socket :socket]
               [:g.nome_modelo :grafico_integrado]
               [:dp.id_dados_processador :id_dados_processador]
               [:g.id_gpu :id_gpu]]
      :from   [[:processadores :p]]
      :join   [[:dados_processador :dp] [:= :p.id_dados_processador :dp.id_dados_processador]]
      :left-join [[:gpus :g] [:= :p.id_gpu :g.id_gpu]]}))

  (get-item
    [this kvs]
    (-> (db/query
         (merge
          {:select [[:p.id_processador :id_processador]
                    [:p.nome_fabricante :nome_fabricante]
                    [:p.nome_modelo :nome_modelo]
                    [:p.nro_cores :nro_cores]
                    [:p.nro_threads :nro_threads]
                    [:p.tdp :tdp]
                    [:p.frequencia :frequencia]
                    [:p.turbofrequencia :turbofrequencia]
                    [:p.codename :codename]
                    [:dp.socket :socket]
                    [:g.nome_modelo :grafico_integrado]
                    [:dp.id_dados_processador :id_dados_processador]
                    [:g.id_gpu :id_gpu]]
           :from   [[:processadores :p]]
           :join   [[:dados_processador :dp] [:= :p.id_dados_processador :dp.id_dados_processador]]
           :left-join [[:gpus :g] [:= :p.id_gpu :g.id_gpu]]
           :limit  1}
          {:where (build-where-clause kvs)}))
        (first)))

  (put-item! [this entity]
    (db/insert! (model-name this) (transform this entity)))

  (update-item! [this clause kvs]
    (let [fn-update-where (partial db/update-where! (model-name this) clause)]
      (apply fn-update-where (->> kvs (seq) (flatten)))))

  (delete-item! [this id]
    (db/simple-delete! (model-name this) {:id_processador id}))

  (items-count [this]
    (db/count (model-name this))))

(defn ->Processadores
  "Retorna um record ProcessadoresModel que implementa ModelProtocol."
  []
  (->ProcessadoresModel))
