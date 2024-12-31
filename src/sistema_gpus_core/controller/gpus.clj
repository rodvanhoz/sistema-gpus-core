(ns sistema-gpus-core.controller.gpus
  (:require
   [sistema-gpus-core.controller.client :refer [ControllerClientProtocol]]
   [sistema-gpus-core.models.gpus :as gpus-model]
   [sistema-gpus-core.domain.model :refer :all]
   [sistema-gpus-core.helper.utils :refer [uuid-from-string str->date date->str]]
   [sistema-gpus-core.logic.gpus :as logic]
   [clojure.tools.logging :as log]))

;; record que implementa ControllerClientProtocol
(defrecord GpusController []
  ControllerClientProtocol

  ;; -------------------------
  ;; read-all
  ;; -------------------------
  (read-all [_]
    (let [raw-items (read-all (gpus-model/->GPUs))]
      (log/info "Reading all gpu's")
      (map (fn [item]
             (-> item
                 (update :id_gpu str)  ;; uuid -> string
                 (update :id_processador_grafico str)
                 (update :id_caracteristicas_graficas str)
                 (update :id_render_config str)
                 (update :dt_lancto date->str)))
           raw-items)))

  ;; -------------------------
  ;; get-item
  ;; (clause ex.: {:id_gpu "669e6eb0-..."} )
  ;; -------------------------
  (get-item
    [_ clause]
    (log/info (format "Gpu found with clause: %s" clause))
    (let [clause* (reduce-kv
                   (fn [acc k v]
                     (assoc acc k (logic/->uuid-if-id k v)))
                   {}
                   clause)
          found   (get-item (gpus-model/->GPUs) clause*)]
      (when found
        (-> found
            (update :id_gpu str)
            (update :id_processador_grafico str)
            (update :id_caracteristicas_graficas str)
            (update :id_render_config str)
            (update :dt_lancto date->str)))))

  ;; -------------------------
  ;; put-item
  ;; (inserir, convertendo string->uuid / string->date)
  ;; -------------------------
  (put-item [_ entity]
    (let [entity* (-> entity
                      (update :id_processador_grafico #(when % (uuid-from-string %)))
                      (update :id_caracteristicas_graficas #(when % (uuid-from-string %)))
                      (update :id_render_config #(when % (uuid-from-string %)))
                      (update :dt_lancto #(when % (str->date %))))]
      (log/info (format "Putting gpu: %s" entity*))
      (put-item! (gpus-model/->GPUs) entity*)
      :ok))

  ;; -------------------------
  ;; update-item
  ;;  - updates: map com campos a atualizar
  ;;  - clause: map com key e valor do where
  ;; -------------------------
  (update-item [_ updates clause]
    (let [updates*  (-> updates
                        (cond-> (:id_processador_grafico updates) (update :id_processador_grafico #(when % (uuid-from-string %))))
                        (cond-> (:id_caracteristicas_graficas updates) (update :id_caracteristicas_graficas #(when % (uuid-from-string %))))
                        (cond-> (:id_render_config updates) (update :id_render_config #(when % (uuid-from-string %))))
                        (cond-> (:dt_lancto updates) (update :dt_lancto #(when % (str->date %)))))
          clause* (-> clause
                      (cond-> (:id_gpu clause) (update :id_gpu #(when % (uuid-from-string %))))
                      (cond-> (:id_processador_grafico clause) (update :id_processador_grafico #(when % (uuid-from-string %))))
                      (cond-> (:id_caracteristicas_graficas clause) (update :id_caracteristicas_graficas #(when % (uuid-from-string %))))
                      (cond-> (:id_render_config clause) (update :id_render_config #(when % (uuid-from-string %))))
                      (cond-> (:dt_lancto clause) (update :dt_lancto #(when % (str->date %)))))]
      (log/info (format "Updating gpu [clause: %s] with infos: %s" clause* updates*))
      (update-item! (gpus-model/->GPUs) clause* updates*)
      :ok))

  ;; -------------------------
  ;; delete-item
  ;;  - id (string)
  ;; -------------------------
  (delete-item [_ id]
    (let [uuid-id (uuid-from-string id)]
      (delete-item! (gpus-model/->GPUs) uuid-id)
      :ok))

  ;; -------------------------
  ;; items-count
  ;; -------------------------
  (items-count [_]
    (items-count (gpus-model/->GPUs))))

;; construtor
(defn ->gpus-controller []
  (->GpusController))
