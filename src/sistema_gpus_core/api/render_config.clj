(ns sistema-gpus-core.api.render-config
  (:require
   [compojure.core :refer :all]
   [ring.util.http-response :refer [ok bad-request no-content created]]
   [clojure.tools.logging :as log]
   [sistema-gpus-core.controller.render-config :as ctrl]
   [sistema-gpus-core.domain.controller-client :as ctrl-client]))

  ;; Instancia o controller
(def controller (ctrl/->render-config-controller))

  ;; Função GET /api/id_render_config
(defn get-all [_]
  (try
    (let [result (ctrl-client/read-all controller)]
      (if (empty? result)
        (no-content)
        (ok result)))
    (catch Exception e
      (log/error (.getMessage e))
      (bad-request (.getMessage e)))))

;; Função GET /api/id_render_config/:id
  ;;
  ;; Observação: para compatibilidade com o controller, passamos
  ;; {:id_render_config <valor>} como clause.  
(defn get-by-id [{{:keys [id]} :params}]
  (try
    (let [result (ctrl-client/get-item controller {:id_render_config id})]
      (if (empty? result)
        (no-content)
        (ok result)))
    (catch Exception e
      (log/error (.getMessage e))
      (bad-request (.getMessage e)))))

;; Função POST /api/id_render_config
(defn post-item [{:keys [body]}]
  (try
    (let [result (ctrl-client/put-item controller body)]
      (created result))
    (catch Exception e
      (log/error (.getMessage e))
      (bad-request (.getMessage e)))))

;; Função PUT /api/id_render_config/:id
  ;;
  ;; Per your controller, update-item recebe (updates, clause).
  ;; A clause deve ser {:id_render_config id}, e updates é o body.
(defn put-item [{{:keys [id]} :params entity :body}]
  (try
    (let [result (ctrl-client/update-item controller entity {:id_render_config id})]
      (ok result))
    (catch Exception e
      (log/error (.getMessage e))
      (bad-request (.getMessage e)))))

;; Função DELETE /api/id_render_config/:id
(defn delete-item [{{:keys [id]} :params}]
  (try
    (ctrl-client/delete-item controller id)
    (ok)
    (catch Exception e
      (log/error (.getMessage e))
      (bad-request (.getMessage e)))))

;; Definição das rotas específicas desta API
(defroutes render-config-routes
  (context "/api" []
    (context "/render-config" []
      (GET "/" [] get-all)
      (GET "/:id" [id] get-by-id)
      (POST "/" [] post-item)
      (PUT "/:id" [id] put-item)
      (DELETE "/:id" [id] delete-item))))
