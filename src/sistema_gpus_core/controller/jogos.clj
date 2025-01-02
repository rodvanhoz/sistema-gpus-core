(ns sistema-gpus-core.controller.jogos
  (:require
   [sistema-gpus-core.domain.client :refer [ControllerClientProtocol]]
   [sistema-gpus-core.models.jogos :as model]
   [sistema-gpus-core.domain.model :refer :all]
   [sistema-gpus-core.helper.utils :refer [uuid-from-string]]
   [sistema-gpus-core.logic.jogos :as logic]
   [clojure.tools.logging :as log]))

(def model (model/->Jogos))

          ;; record que implementa ControllerClientProtocol
(defrecord JogosController []
  ControllerClientProtocol

            ;; -------------------------
            ;; read-all
            ;; -------------------------
  (read-all [_]
    (let [raw-items (read-all model)]
      (log/info "Reading all jogos's")
      (map (fn [item]
             (logic/object->string-fields item))
           raw-items)))

            ;; -------------------------
            ;; get-item
            ;; (clause ex.: {:id "669e6eb0-..."} )
            ;; -------------------------
  (get-item
    [_ clause]
    (log/info (format "jogos found with clause: %s" clause))
    (let [clause* (reduce-kv
                   (fn [acc k v]
                     (assoc acc k (logic/->coerce-object-field k v)))
                   {}
                   clause)
          found   (get-item model clause*)]
      (when found
        (logic/object->string-fields found))))

            ;; -------------------------
            ;; put-item
            ;; (inserir, convertendo string->uuid / string->date)
            ;; -------------------------
  (put-item [_ entity]
    (let [entity* (logic/prepare entity)]
      (log/info (format "Putting jogos: %s" entity*))
      (put-item! model entity*)
      :ok))

            ;; -------------------------
            ;; update-item
            ;;  - updates: map com campos a atualizar
            ;;  - clause: map com key e valor do where
            ;; -------------------------
  (update-item [_ updates clause]
    (let [updates*  (logic/string-fields->object updates)
          clause* (logic/string-fields->object clause)]
      (log/info (format "Updating jogos [clause: %s] with infos: %s" clause* updates*))
      (update-item! model clause* updates*)
      :ok))

            ;; -------------------------
            ;; delete-item
            ;;  - id (string)
            ;; -------------------------
  (delete-item [_ id]
    (let [uuid-id (uuid-from-string id)]
      (delete-item! model uuid-id)
      :ok))

            ;; -------------------------
            ;; items-count
            ;; -------------------------
  (items-count [_]
    (items-count model)))

          ;; construtor
(defn ->jogos-controller []
  (->JogosController))
