(ns sistema-gpus-core.db.entities
  (:use korma.core 
    sistema-gpus-core.db)
  (:require [korma.core :refer [defentity pk belongs-to prepare transform entity-fields many-to-many has-many has-one]]))

(declare arquitetura-processador arquiteturas caracteristicas-graficas configuracoes configuracoes-jogos 
         dados-processador gpus jogos processadores render-config testes-gpu processador-grafico)

(defentity arquitetura-processador
  (pk :idArquiteturaProc)
  (table :ArquiteturaProcessador)
  (belongs-to arquiteturas {:fk :idArquitetura})
  (belongs-to processadores {:fk :idProcessador}))

(defentity arquiteturas
  (pk :idArquitetura)
  (table :Arquiteturas))

(defentity caracteristicas-graficas
  (pk :idCaracGrafica)
  (table :CaracteristicasGraficas))

(defentity configuracoes
  (pk :idConfiguracao)
  (table :Configuracoes))
  
(defentity configuracoes-jogos
  (pk :idConfiguracaoJogo)
  (table :ConfiguracoesJogos)
  (belongs-to jogos {:fk :idJogo})
  (belongs-to configuracoes {:fk :idConfiguracao}))

(defentity dados-processador
  (pk :idDadosProcessador)
  (table :DadosProcessador))

(defentity gpus
  (pk :idGpu)
  (table :Gpus)
  (belongs-to processador-grafico {:fk :idProcGrafico})
  (belongs-to caracteristicas-graficas {:fk :idCaracGrafica})
  (belongs-to render-config {:fk :idRenderConfig}))

(defentity jogos
  (pk :idJogo)
  (table :Jogos))

(defentity processadores
  (pk :idProcessador)
  (table :Processadores)
  (belongs-to dados-processador {:fk :idDadosProcessador}))

(defentity render-config
  (pk :idRenderConfig)
  (table :RenderConfig))

(defentity testes-gpu
  (pk :idTesteGpu)
  (table :TestesGpu)
  (belongs-to configuracoes-jogos {:fk :idConfiguracaoJogo})
  (belongs-to gpus {:fk :idGpu})
  (belongs-to processadores {:fk :idProcessador}))

(defentity processador-grafico
  (pk :idProcGrafico)
  (table :ProcessadorGrafico))