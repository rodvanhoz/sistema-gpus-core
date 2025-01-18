(ns sistema-gpus-core.models.gpus
  (:require
   [clojure.string :as str]
   [sistema-gpus-core.domain.model :refer [ModelProtocol transform model-name]]
   [sistema-gpus-core.helper.utils :refer [build-where-clause]]
   [toucan.db :as db]
   [toucan.models :as models]))

;; Passo 1: Associamos a Tabela :gpus ao "var" GPU do Toucan.
(models/defmodel GPUs :gpus)

;; Passo 2: Criamos um record para implementar ModelProtocol via extend-type.
(defrecord GPUsModel [])

;; Passo 3: Fazemos o extend-type para GPUModel (e não GPU).
(extend-type GPUsModel
  ModelProtocol

  (model-name [_]
    GPUs)

  (primary-key [_ entity]
    (:id_gpu entity))

  (default-fields [_]
    [:id_gpu
     :id_processador_grafico
     :id_caracteristicas_graficas
     :id_render_config
     :nome_fabricante
     :nome_modelo
     :tam_memoria_kb
     :tp_memoria
     :tam_banda
     :tdp
     :gpu_clock
     :boost_clock
     :mem_clock
     :mem_clock_efetivo
     :bus_interface
     :dt_lancto
     :old_id])

  (transform [_ entity]
    (update entity :nome_fabricante #(when % (str/upper-case %))))

  ;; --- CRUD ---

  (read-all
    [this]
    (db/query
     {:select [[:g.id_gpu :id_gpu]
               [:g.nome_fabricante :nome_fabricante]
               [:g.nome_modelo :nome_modelo]
               [:pg.nome_gpu :nome_gpu]
               [:pg.arquitetura :arquitetura]
               [:rc.shading_units :shading_units]
               [:rc.rops :rops]
               [:rc.tmus :tmus]
               [:g.tam_memoria_kb :tam_memoria_kb]
               [:g.tp_memoria :tp_memoria]
               [:g.gpu_clock :gpu_clock]
               [:g.boost_clock :boost_clock]
               [:cg.direct_x :direct_x]
               [:cg.open_gl :open_gl]
               [:cg.open_cl :open_cl]
               [:cg.vulkan :vulkan]
               [:g.mem_clock :mem_clock]
               [:g.mem_clock_efetivo :mem_clock_efetivo]
               [:g.tam_banda :tam_banda]
               [:g.tdp :tdp]
               [:g.bus_interface :bus_interface]
               [:g.dt_lancto :dt_lancto]]
      :from   [[:gpus :g]]
      :join   [[:processador_grafico      :pg] [:= :g.id_processador_grafico       :pg.id_proc_grafico]
               [:caracteristicas_graficas :cg] [:= :g.id_caracteristicas_graficas   :cg.id_carac_grafica]
               [:render_config            :rc] [:= :g.id_render_config              :rc.id_render_config]]}))

  (get-item
    [this kvs]
    (-> (db/query
         (merge
          {:select [[:g.id_gpu :id_gpu]
                    [:g.nome_fabricante :nome_fabricante]
                    [:g.nome_modelo :nome_modelo]
                    [:pg.nome_gpu :nome_gpu]
                    [:pg.arquitetura :arquitetura]
                    [:rc.shading_units :shading_units]
                    [:rc.rops :rops]
                    [:rc.tmus :tmus]
                    [:g.tam_memoria_kb :tam_memoria_kb]
                    [:g.tp_memoria :tp_memoria]
                    [:g.gpu_clock :gpu_clock]
                    [:g.boost_clock :boost_clock]
                    [:cg.direct_x :direct_x]
                    [:cg.open_gl :open_gl]
                    [:cg.open_cl :open_cl]
                    [:cg.vulkan :vulkan]
                    [:g.mem_clock :mem_clock]
                    [:g.mem_clock_efetivo :mem_clock_efetivo]
                    [:g.tam_banda :tam_banda]
                    [:g.tdp :tdp]
                    [:g.bus_interface :bus_interface]
                    [:g.dt_lancto :dt_lancto]]
           :from   [[:gpus :g]]
           :join   [[:processador_grafico      :pg] [:= :g.id_processador_grafico       :pg.id_proc_grafico]
                    [:caracteristicas_graficas :cg] [:= :g.id_caracteristicas_graficas   :cg.id_carac_grafica]
                    [:render_config            :rc] [:= :g.id_render_config              :rc.id_render_config]]
           :limit  1}
          {:where (build-where-clause kvs)}))
        (first)))

  (put-item! [this entity]
    (db/insert! (model-name this) (transform this entity)))

  (update-item! [this clause kvs]
    (let [fn-update-where (partial db/update-where! (model-name this) clause)]
      (apply fn-update-where (->> kvs (seq) (flatten)))))

  (delete-item! [this id]
    (db/simple-delete! (model-name this) {:id_gpu id}))

  (items-count [this]
    (db/count (model-name this))))

;; Passo 4: Para facilitar chamadas, criamos uma função que instancia GPUModel.
(defn ->GPUs
  "Retorna um record GPUModel que implementa ModelProtocol."
  []
  (->GPUsModel))
