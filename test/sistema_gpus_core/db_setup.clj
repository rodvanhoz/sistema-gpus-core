(ns sistema-gpus-core.db-setup
  "Responsável por fornecer o baseline SQL e funções de inserção de mocks."
  (:require [clojure.java.jdbc :as jdbc]
            [clj-test-containers.core :as tc]
            [toucan.db :as tdb]
            [clojure.java.io :as io]))

(declare apply-baseline!)

;; Declaramos uma var global para armazenar a instância do container
(defonce ^:dynamic *pg-container* nil)

(def resource-path "sistema_gpus_core/resources/databases")

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
  (let [sql (slurp (io/resource (format "%s/baseline.sql" resource-path)))]
    (Thread/sleep 200)
    (jdbc/execute! conn [sql])))

;; -----------------------------------------------------------------------------
;; 4) Funções de mock (uma para cada model)
;; -----------------------------------------------------------------------------
(defn apply-mock-gpus!
  "Executa o script `gpus_database.sql` que insere gpus de teste."
  [conn]
  (let [sql (slurp (io/resource (format "%s/gpus_database.sql" resource-path)))]
    (jdbc/execute! conn [sql])))

(defn apply-mock-arquitetura-processador!
  "Executa o script `gpus_database.sql` que insere gpus de teste."
  [conn]
  (let [sql (slurp (io/resource (format "%s/arq_processador_database.sql" resource-path)))]
    (jdbc/execute! conn [sql])))

(defn apply-mock-caracteristicas-graficas!
  "Executa o script `gpus_database.sql` que insere gpus de teste."
  [conn]
  (let [sql (slurp (io/resource (format "%s/carac_graficas_database.sql" resource-path)))]
    (jdbc/execute! conn [sql])))

(defn apply-mock-arquiteturas!
  "Executa o script `arquiteturas_database.sql` que insere arquiteturas de teste."
  [conn]
  (let [sql (slurp (io/resource (format "%s/arquiteturas_database.sql" resource-path)))]
    (jdbc/execute! conn [sql])))

(defn apply-mock-configuracoes-jogos!
  "Executa o script `arquiteturas_database.sql` que insere arquiteturas de teste."
  [conn]
  (let [sql (slurp (io/resource (format "%s/conf_jogos_database.sql" resource-path)))]
    (jdbc/execute! conn [sql])))

(defn apply-mock-configuracoes!
  "Executa o script `configuracoes_database.sql` que insere configuracoes de teste."
  [conn]
  (let [sql (slurp (io/resource (format "%s/configuracoes_database.sql" resource-path)))]
    (jdbc/execute! conn [sql])))

(defn apply-mock-dados-processador!
  "Executa o script `dados_processador.sql` que insere dados_processador de teste."
  [conn]
  (let [sql (slurp (io/resource (format "%s/dados_processador_database.sql" resource-path)))]
    (jdbc/execute! conn [sql])))

(defn apply-mock-jogos!
  "Executa o script `jogos_database.sql` que insere jogos de teste."
  [conn]
  (let [sql (slurp (io/resource (format "%s/jogos_database.sql" resource-path)))]
    (jdbc/execute! conn [sql])))

(defn apply-mock-processador-grafico!
  "Executa o script `processador_grafico_database.sql` que insere processador_grafico de teste."
  [conn]
  (let [sql (slurp (io/resource (format "%s/processador_grafico_database.sql" resource-path)))]
    (jdbc/execute! conn [sql])))

(defn apply-mock-processadores!
  "Executa o script `processadores_database.sql` que insere processadores de teste."
  [conn]
  (let [sql (slurp (io/resource (format "%s/processadores_database.sql" resource-path)))]
    (jdbc/execute! conn [sql])))

(defn apply-mock-render-config!
  "Executa o script `render_config_database.sql` que insere render_config de teste."
  [conn]
  (let [sql (slurp (io/resource (format "%s/render_config_database.sql" resource-path)))]
    (jdbc/execute! conn [sql])))

(defn apply-mock-testes-gpu!
  "Executa o script `testes_gpu_database.sql` que insere testes_gpu de teste."
  [conn]
  (let [sql (slurp (io/resource (format "%s/testes_gpu_database.sql" resource-path)))]
    (jdbc/execute! conn [sql])))