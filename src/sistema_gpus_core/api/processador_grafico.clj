(ns sistema-gpus-core.api.processador-grafico
  (:require
   [compojure.core :refer :all]
   [ring.util.http-response :refer [ok bad-request no-content created]]
   [clojure.tools.logging :as log]
   [sistema-gpus-core.controller.processador-grafico :as ctrl]
   [sistema-gpus-core.domain.controller-client :as ctrl-client]))

  ;; Instancia o controller
(def controller (ctrl/->processador-grafico-controller))

  ;; Função GET /api/id_proc_grafico
(defn get-all [_]
  (try
    (let [result (ctrl-client/read-all controller)]
      (if (empty? result)
        (no-content)
        (ok result)))
    (catch Exception e
      (log/error (.getMessage e))
      (bad-request (.getMessage e)))))

;; Função GET /api/id_proc_grafico/:id
  ;;
  ;; Observação: para compatibilidade com o controller, passamos
  ;; {:id_proc_grafico <valor>} como clause.  
(defn get-by-id [{{:keys [id]} :params}]
  (try
    (let [result (ctrl-client/get-item controller {:id_proc_grafico id})]
      (if (empty? result)
        (no-content)
        (ok result)))
    (catch Exception e
      (log/error (.getMessage e))
      (bad-request (.getMessage e)))))

;; Função POST /api/id_proc_grafico
(defn post-item [{:keys [body]}]
  (try
    (let [result (ctrl-client/put-item controller body)]
      (created result))
    (catch Exception e
      (log/error (.getMessage e))
      (bad-request (.getMessage e)))))

;; Função PUT /api/id_proc_grafico/:id
  ;;
  ;; Per your controller, update-item recebe (updates, clause).
  ;; A clause deve ser {:id_proc_grafico id}, e updates é o body.
(defn put-item [{{:keys [id]} :params entity :body}]
  (try
    (let [result (ctrl-client/update-item controller entity {:id_proc_grafico id})]
      (ok result))
    (catch Exception e
      (log/error (.getMessage e))
      (bad-request (.getMessage e)))))

;; Função DELETE /api/id_proc_grafico/:id
(defn delete-item [{{:keys [id]} :params}]
  (try
    (ctrl-client/delete-item controller id)
    (ok)
    (catch Exception e
      (log/error (.getMessage e))
      (bad-request (.getMessage e)))))

;; Definição das rotas específicas desta API
(defroutes processador-grafico-routes
  (context "/api" []
    (context "/processador-grafico" []
      (GET "/" [] get-all)
      (GET "/:id" [id] get-by-id)
      (POST "/" [] post-item)
      (PUT "/:id" [id] put-item)
      (DELETE "/:id" [id] delete-item))))