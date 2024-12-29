(ns sistema-gpus-core.models.gpus
  (:require
   [clojure.string :as str]
   [sistema-gpus-core.domain.model :refer [model-name ModelProtocol transform]]
   [toucan.db :as db]
   [toucan.models :as models]))

;; Passo 1: Associamos a Tabela :gpus ao "var" GPU do Toucan.
(models/defmodel GPUs :gpus)

;; Passo 2: Criamos um record para implementar ModelProtocol via extend-type.
(defrecord GPUModel [])

;; Passo 3: Fazemos o extend-type para GPUModel (e não GPU).
(extend-type GPUModel
  ModelProtocol

  (model-name [_]
    "gpus")

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

  (read-all [this]
    (db/select GPUs))

  (get-item [this k v]
    (db/select-one GPUs k v))

  (put-item! [this entity]
    (db/insert! GPUs (transform this entity)))

  (update-item! [this updates k v]
    (db/update-where! GPUs updates k v))

  (delete-item! [this id]
    (db/simple-delete! GPUs {:id_gpu id}))

  (items-count [this]
    (db/count GPUs)))

;; Passo 4: Para facilitar chamadas, criamos uma função que instancia GPUModel.
(defn ->GPU
  "Retorna um record GPUModel que implementa ModelProtocol."
  []
  (->GPUModel))
