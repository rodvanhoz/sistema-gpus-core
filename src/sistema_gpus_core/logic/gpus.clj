(ns sistema-gpus-core.logic.gpus
  (:require
   [sistema-gpus-core.helper.utils :refer [uuid-from-string]]))

(defn ->uuid-if-id
  "Se a chave k for algum campo de ID, converte v de string -> UUID.
   Caso contrário, retorna v inalterado."
  [k v]
  (case k
    :id_gpu                     (uuid-from-string v)
    :id_processador_grafico     (uuid-from-string v)
    :id_caracteristicas_graficas (uuid-from-string v)
    :id_render_config           (uuid-from-string v)
    ;; fallback - não converte
    v))