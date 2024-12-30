INSERT INTO public.jogos
(id_jogo, nome_jogo, dt_lancto, old_id)
VALUES('af50855c-4e77-4805-bb03-b5cf4e2d4649'::uuid, 'A Plague Tale: Innocence', '2019-05-14 00:00:00.000', 1);
INSERT INTO public.jogos
(id_jogo, nome_jogo, dt_lancto, old_id)
VALUES('b17fda4d-b019-4a12-9825-f53d219fbad6'::uuid, 'Rage 2', '2019-05-14 00:00:00.000', 2);
INSERT INTO public.jogos
(id_jogo, nome_jogo, dt_lancto, old_id)
VALUES('73d5ff93-ec73-40a9-bcef-295a8d68afac'::uuid, 'Total War Three Kingdoms', '2019-05-23 00:00:00.000', 3);
INSERT INTO public.jogos
(id_jogo, nome_jogo, dt_lancto, old_id)
VALUES('503f90a2-3d0e-4de7-8834-66492b5abd96'::uuid, 'Darksiders 3', '2018-11-27 00:00:00.000', 13);


INSERT INTO public.configuracoes
(id_configuracao, resolucao_abrev, resolucao_detalhe, api, qualidade_grafica, ssao, fxaa, taa, rt, aa, nvidia_tec, old_id)
VALUES('7e7890ca-8ff0-4a8c-aee9-00a1d3e085d8'::uuid, 1080, '1920x1080', 'DX11', 'Ultra Graphics Present Settings', 'N', 'N', 'N', 'N', 'N', 'N', 5);
INSERT INTO public.configuracoes
(id_configuracao, resolucao_abrev, resolucao_detalhe, api, qualidade_grafica, ssao, fxaa, taa, rt, aa, nvidia_tec, old_id)
VALUES('ff2734a4-dde1-4790-a5ab-b5bb679157f4'::uuid, 1080, '1920x1080', 'Vulkan', 'Medium Graphics Present Settings', 'S', 'S', 'N', 'N', 'N', 'N', 1);
INSERT INTO public.configuracoes
(id_configuracao, resolucao_abrev, resolucao_detalhe, api, qualidade_grafica, ssao, fxaa, taa, rt, aa, nvidia_tec, old_id)
VALUES('a565f883-ca87-4284-8b49-6f409ceb7818'::uuid, 1080, '1920x1080', 'DX11', 'Ultra Graphics Present Settings', 'S', 'N', 'N', 'N', 'N', 'N', 3);


INSERT INTO public.configuracoes_jogos
(id_configuracao_jogo, id_jogo, id_configuracao, old_id)
VALUES('5d2548f0-819f-4774-85ea-79d65f33bc3f'::uuid, 'af50855c-4e77-4805-bb03-b5cf4e2d4649'::uuid, '7e7890ca-8ff0-4a8c-aee9-00a1d3e085d8'::uuid, 2);
INSERT INTO public.configuracoes_jogos
(id_configuracao_jogo, id_jogo, id_configuracao, old_id)
VALUES('de996e86-f9e3-4639-99f1-0b5944050d70'::uuid, 'b17fda4d-b019-4a12-9825-f53d219fbad6'::uuid, 'ff2734a4-dde1-4790-a5ab-b5bb679157f4'::uuid, 3);
INSERT INTO public.configuracoes_jogos
(id_configuracao_jogo, id_jogo, id_configuracao, old_id)
VALUES('5a9ece7c-68fe-499a-9039-b8897af1cad6'::uuid, '73d5ff93-ec73-40a9-bcef-295a8d68afac'::uuid, 'a565f883-ca87-4284-8b49-6f409ceb7818'::uuid, 4);