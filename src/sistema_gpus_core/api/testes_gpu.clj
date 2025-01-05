(ns sistema-gpus-core.api.testes-gpu
  (:require
   [compojure.core :refer :all]
   [ring.util.http-response :refer [ok bad-request no-content created]]
   [clojure.tools.logging :as log]
   [sistema-gpus-core.controller.testes-gpu :as ctrl]
   [sistema-gpus-core.domain.controller-client :as ctrl-client]))

  ;; Instancia o controller
(def controller (ctrl/->testes-gpu-controller))

  ;; Função GET /api/id_teste_gpu
(defn get-all [_]
  (try
    (let [result (ctrl-client/read-all controller)]
      (if (empty? result)
        (no-content)
        (ok result)))
    (catch Exception e
      (log/error (.getMessage e))
      (bad-request (.getMessage e)))))

;; Função GET /api/id_teste_gpu/:id
  ;;
  ;; Observação: para compatibilidade com o controller, passamos
  ;; {:id_teste_gpu <valor>} como clause.  
(defn get-by-id [{{:keys [id]} :params}]
  (try
    (let [result (ctrl-client/get-item controller {:id_teste_gpu id})]
      (if (empty? result)
        (no-content)
        (ok result)))
    (catch Exception e
      (log/error (.getMessage e))
      (bad-request (.getMessage e)))))

;; Função POST /api/id_teste_gpu
(defn post-item [{:keys [body]}]
  (try
    (let [result (ctrl-client/put-item controller body)]
      (created result))
    (catch Exception e
      (log/error (.getMessage e))
      (bad-request (.getMessage e)))))

;; Função PUT /api/id_teste_gpu/:id
  ;;
  ;; Per your controller, update-item recebe (updates, clause).
  ;; A clause deve ser {:id_teste_gpu id}, e updates é o body.
(defn put-item [{{:keys [id]} :params entity :body}]
  (try
    (let [result (ctrl-client/update-item controller entity {:id_teste_gpu id})]
      (ok result))
    (catch Exception e
      (log/error (.getMessage e))
      (bad-request (.getMessage e)))))

;; Função DELETE /api/id_teste_gpu/:id
(defn delete-item [{{:keys [id]} :params}]
  (try
    (ctrl-client/delete-item controller id)
    (ok)
    (catch Exception e
      (log/error (.getMessage e))
      (bad-request (.getMessage e)))))

;; Definição das rotas específicas desta API
(defroutes testes-gpu-routes
  (context "/api" []
    (context "/testes-gpu" []
      (GET "/" [] get-all)
      (GET "/:id" [id] get-by-id)
      (POST "/" [] post-item)
      (PUT "/:id" [id] put-item)
      (DELETE "/:id" [id] delete-item))))
