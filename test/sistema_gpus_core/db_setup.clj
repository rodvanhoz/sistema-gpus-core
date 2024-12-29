(ns sistema-gpus-core.db-setup
  "Responsável por fornecer o baseline SQL e funções de inserção de mocks."
  (:require [clojure.java.jdbc :as jdbc]
            [clj-test-containers.core :as tc]
            [toucan.db :as tdb]
            [clojure.java.io :as io]))

(declare apply-baseline!)

;; Declaramos uma var global para armazenar a instância do container
(defonce ^:dynamic *pg-container* nil)

(def resource-path "/home/rodrigo/Documents/projects/sistema-gpus-core/test/sistema_gpus_core/resources/databases")

;; -----------------------------------------------------------------------------
;; 1) Configuração do Container
;; -----------------------------------------------------------------------------
(defn- container->toucan-config
  "Dado o map do container retornado por clj-test-containers,
   monta um map de conexão para Toucan."
  [container]
  (let [host     (:host container)
        port     (get (:mapped-ports container) 5432)
        env-vars (:env-vars container)
        db-name  (get env-vars "POSTGRES_DB" "postgres")
        user     (get env-vars "POSTGRES_USER" "postgres")
        pass     (get env-vars "POSTGRES_PASSWORD" "testpass")]
    {:classname   "org.postgresql.Driver"
     :subprotocol "postgresql"
     ;; subname vai ficar no formato: //host:port/db
     :subname     (str "//" host ":" port "/" db-name)
     :user        user
     :password    pass}))

;; -----------------------------------------------------------------------------
;; 2) Função de fixture para criar/derrubar container via Testcontainers
;; -----------------------------------------------------------------------------
(defn with-postgres-testcontainer
  "Fixture que inicia um container do Postgres, aplica baseline.sql,
   e só então roda os testes."
  [f]
  (let [container (tc/create {:image-name    "postgres:12"
                              :exposed-ports [5432]
                              :env-vars      {"POSTGRES_USER"     "testuser"
                                              "POSTGRES_PASSWORD" "testpass"
                                              "POSTGRES_DB"       "testdb"}
                              :wait-for      {:strategy :health
                                              :startup-timeout 150}})
        started   (tc/start! container)]
    (alter-var-root #'*pg-container* (constantly started))
    (try
      ;; Configura a conexão do Toucan
      (let [conn-config (container->toucan-config started)]
        (tdb/set-default-db-connection! conn-config))

      ;; Aplica o baseline do arquivo "baseline.sql"
      (apply-baseline! (tdb/connection))

      ;; Roda os testes
      (f)
      (finally
        (tc/stop! started)))))

;; -----------------------------------------------------------------------------
;; 3) Leitura do arquivo baseline.sql e execução
;; -----------------------------------------------------------------------------
(defn- apply-baseline!
  "Carrega o arquivo baseline.sql e executa no banco de dados `conn`."
  [conn]
  (prn "===============> " (-> (java.io.File. ".") .getAbsolutePath))
  (let [sql (slurp (io/resource "sistema_gpus_core/resources/databases/baseline.sql"))]
    (Thread/sleep 200)
    (jdbc/execute! conn [sql])))

;; -----------------------------------------------------------------------------
;; 4) Funções de mock (uma para cada model)
;; -----------------------------------------------------------------------------

;; Por enquanto, só o mock de GPUs
(defn apply-mock-gpus!
  "Executa o script `gpus_database.sql` que insere gpus de teste."
  [conn]
  (let [sql (slurp (io/resource "sistema_gpus_core/resources/databases/gpus_database.sql"))]
    (jdbc/execute! conn [sql])))

;; Depois você pode criar apply-mock-processadores!, apply-mock-arquiteturas!, etc.
;; Cada qual lendo seu script .sql respectivo.
