(ns sistema-gpus-core.api.arquitetura-processador
  (:require
   [compojure.core :refer :all]
   [ring.util.http-response :refer [ok bad-request no-content created]]
   [clojure.tools.logging :as log]
   [sistema-gpus-core.controller.arquitetura-processador :as ctrl]
   [sistema-gpus-core.domain.controller-client :as ctrl-client]))

;; Instancia o controller
(def controller (ctrl/->arquitetura-processador-controller))

;; Função GET /api/arquitetura-processador
(defn get-all [_]
  (try
    (let [result (ctrl-client/read-all controller)]
      (if (empty? result)
        (no-content)
        (ok result)))
    (catch Exception e
      (log/error (.getMessage e))
      (bad-request (.getMessage e)))))

;; Função GET /api/arquitetura-processador/:id
;;
;; Observação: para compatibilidade com o controller, passamos
;; {:id_arquitetura_proc <valor>} como clause.  
(defn get-by-id [{{:keys [id]} :params}]
  (try
    (let [result (ctrl-client/get-item controller {:id_arquitetura_proc id})]
      (if (empty? result)
        (no-content)
        (ok result)))
    (catch Exception e
      (log/error (.getMessage e))
      (bad-request (.getMessage e)))))

;; Função POST /api/arquitetura-processador
(defn post-item [{:keys [body]}]
  (try
    (let [result (ctrl-client/put-item controller body)]
      (created result))
    (catch Exception e
      (log/error (.getMessage e))
      (bad-request (.getMessage e)))))

;; Função PUT /api/arquitetura-processador/:id
;;
;; Per your controller, update-item recebe (updates, clause).
;; A clause deve ser {:id_arquitetura_proc id}, e updates é o body.
(defn put-item [{{:keys [id]} :params entity :body}]
  (try
    (let [result (ctrl-client/update-item controller entity {:id_arquitetura_proc id})]
      (ok result))
    (catch Exception e
      (log/error (.getMessage e))
      (bad-request (.getMessage e)))))

;; Função DELETE /api/arquitetura-processador/:id
(defn delete-item [{{:keys [id]} :params}]
  (try
    (ctrl-client/delete-item controller id)
    (ok)
    (catch Exception e
      (log/error (.getMessage e))
      (bad-request (.getMessage e)))))

;; Definição das rotas específicas desta API
(defroutes arquitetura-processador-routes
  (context "/api" []
    (context "/arquitetura-processador" []
      (GET "/" [] get-all)
      (GET "/:id" [id] get-by-id)
      (POST "/" [] post-item)
      (PUT "/:id" [id] put-item)
      (DELETE "/:id" [id] delete-item))))
