(ns sistema-gpus-core.api.jogos
  (:require
   [compojure.core :refer :all]
   [ring.util.http-response :refer [ok bad-request no-content created]]
   [clojure.tools.logging :as log]
   [sistema-gpus-core.controller.jogos :as ctrl]
   [sistema-gpus-core.domain.controller-client :as ctrl-client]))

  ;; Instancia o controller
(def controller (ctrl/->jogos-controller))

  ;; Função GET /api/id_jogo
(defn get-all [_]
  (try
    (let [result (ctrl-client/read-all controller)]
      (if (empty? result)
        (no-content)
        (ok result)))
    (catch Exception e
      (log/error (.getMessage e))
      (bad-request (.getMessage e)))))

;; Função GET /api/id_jogo/:id
  ;;
  ;; Observação: para compatibilidade com o controller, passamos
  ;; {:id_jogo <valor>} como clause.  
(defn get-by-id [{{:keys [id]} :params}]
  (try
    (let [result (ctrl-client/get-item controller {:id_jogo id})]
      (if (empty? result)
        (no-content)
        (ok result)))
    (catch Exception e
      (log/error (.getMessage e))
      (bad-request (.getMessage e)))))

;; Função POST /api/id_jogo
(defn post-item [{:keys [body]}]
  (try
    (let [result (ctrl-client/put-item controller body)]
      (created result))
    (catch Exception e
      (log/error (.getMessage e))
      (bad-request (.getMessage e)))))

;; Função PUT /api/id_jogo/:id
  ;;
  ;; Per your controller, update-item recebe (updates, clause).
  ;; A clause deve ser {:id_jogo id}, e updates é o body.
(defn put-item [{{:keys [id]} :params entity :body}]
  (try
    (let [result (ctrl-client/update-item controller entity {:id_jogo id})]
      (ok result))
    (catch Exception e
      (log/error (.getMessage e))
      (bad-request (.getMessage e)))))

;; Função DELETE /api/id_jogo/:id
(defn delete-item [{{:keys [id]} :params}]
  (try
    (ctrl-client/delete-item controller id)
    (ok)
    (catch Exception e
      (log/error (.getMessage e))
      (bad-request (.getMessage e)))))

;; Definição das rotas específicas desta API
(defroutes jogos-routes
  (context "/api" []
    (context "/jogos" []
      (GET "/" [] get-all)
      (GET "/:id" [id] get-by-id)
      (POST "/" [] post-item)
      (PUT "/:id" [id] put-item)
      (DELETE "/:id" [id] delete-item))))
