(ns sistema-gpus-core.controller.arquitetura-processador
  (:require
   [sistema-gpus-core.domain.client :refer [ControllerClientProtocol]]
   [sistema-gpus-core.models.arquitetura-processador :as model]
   [sistema-gpus-core.domain.model :refer :all]
   [sistema-gpus-core.helper.utils :refer [uuid-from-string]]
   [sistema-gpus-core.logic.arquitetura-processador :as logic]
   [clojure.tools.logging :as log]))

;; record que implementa ControllerClientProtocol
(defrecord ArquiteruraProcessadorController []
  ControllerClientProtocol

  ;; -------------------------
  ;; read-all
  ;; -------------------------
  (read-all [_]
    (let [raw-items (read-all (model/->ArquiteturaProcessador))]
      (log/info "Reading all arquitetura-processadores's")
      (map (fn [item]
             (logic/uuid->str item))
           raw-items)))

  ;; -------------------------
  ;; get-item
  ;; (clause ex.: {:id "669e6eb0-..."} )
  ;; -------------------------
  (get-item
    [_ clause]
    (log/info (format "Arquitetura-Processador found with clause: %s" clause))
    (let [clause* (reduce-kv
                   (fn [acc k v]
                     (assoc acc k (logic/->uuid-if-id k v)))
                   {}
                   clause)
          found   (get-item (model/->ArquiteturaProcessador) clause*)]
      (when found
        (logic/uuid->str found))))

  ;; -------------------------
  ;; put-item
  ;; (inserir, convertendo string->uuid / string->date)
  ;; -------------------------
  (put-item [_ entity]
    (let [entity* (logic/build-entity entity)]
      (log/info (format "Putting arquitetura-processador: %s" entity*))
      (put-item! (model/->ArquiteturaProcessador) entity*)
      :ok))

  ;; -------------------------
  ;; update-item
  ;;  - updates: map com campos a atualizar
  ;;  - clause: map com key e valor do where
  ;; -------------------------
  (update-item [_ updates clause]
    (let [updates*  (logic/string->uuid updates)
          clause* (logic/string->uuid clause)]
      (log/info (format "Updating arquitetura-processador [clause: %s] with infos: %s" clause* updates*))
      (update-item! (model/->ArquiteturaProcessador) clause* updates*)
      :ok))

  ;; -------------------------
  ;; delete-item
  ;;  - id (string)
  ;; -------------------------
  (delete-item [_ id]
    (let [uuid-id (uuid-from-string id)]
      (delete-item! (model/->ArquiteturaProcessador) uuid-id)
      :ok))

  ;; -------------------------
  ;; items-count
  ;; -------------------------
  (items-count [_]
    (items-count (model/->ArquiteturaProcessador))))

;; construtor
(defn ->arquitetura-processador-controller []
  (->ArquiteruraProcessadorController))
