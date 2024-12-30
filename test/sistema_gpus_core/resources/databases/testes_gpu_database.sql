-- configuracao 1
INSERT INTO public.configuracoes
(id_configuracao, resolucao_abrev, resolucao_detalhe, api, qualidade_grafica, ssao, fxaa, taa, rt, aa, nvidia_tec, old_id)
VALUES('7e7890ca-8ff0-4a8c-aee9-00a1d3e085d8'::uuid, 1080, '1920x1080', 'DX11', 'Ultra Graphics Present Settings', 'N', 'N', 'N', 'N', 'N', 'N', 5);

INSERT INTO public.jogos
(id_jogo, nome_jogo, dt_lancto, old_id)
VALUES('af50855c-4e77-4805-bb03-b5cf4e2d4649'::uuid, 'A Plague Tale: Innocence', '2019-05-14 00:00:00.000', 1);

INSERT INTO public.configuracoes_jogos
(id_configuracao_jogo, id_jogo, id_configuracao, old_id)
VALUES('5d2548f0-819f-4774-85ea-79d65f33bc3f'::uuid, 'af50855c-4e77-4805-bb03-b5cf4e2d4649'::uuid, '7e7890ca-8ff0-4a8c-aee9-00a1d3e085d8'::uuid, 2);

INSERT INTO public.processador_grafico
(id_proc_grafico, nome_gpu, variant_gpu, arquitetura, fundicao, nn_processador, nro_transistors, mm_processador, old_id)
VALUES('64f83ccb-c2f1-493a-9516-a88e86c3dd97'::uuid, 'Polaris 20', 'Polaris 20 XL (215-0910052)', 'GCN 4.0', 'GlobalFoundries', 14, 5.70000000, 232, 2);

INSERT INTO public.caracteristicas_graficas
(id_carac_grafica, direct_x, open_gl, open_cl, vulkan, cuda, shader_model, old_id)
VALUES('bb8e8e6b-8c83-401a-a1a0-2964a049de60'::uuid, '12.0 (12_0)', '4.6', '2.0', '1.1.108', NULL, '6.4', 2);

INSERT INTO public.render_config
(id_render_config, shading_units, tmus, rops, sm_count, l1_cache, l2_cache, tensor_cores, rt_cores, old_id)
VALUES('12b9b661-9e9e-4cc2-a421-6ecf6adc7741'::uuid, 2048, 128, 32, 32, 16, 2048, NULL, NULL, 2);

INSERT INTO public.gpus
(id_gpu, id_processador_grafico, id_caracteristicas_graficas, id_render_config, nome_fabricante, nome_modelo, tam_memoria_kb, tp_memoria, tam_banda, tdp, gpu_clock, boost_clock, mem_clock, mem_clock_efetivo, bus_interface, dt_lancto, old_id)
VALUES('a41adbc8-acf0-432e-bd13-a2eb7d66b034'::uuid, '64f83ccb-c2f1-493a-9516-a88e86c3dd97'::uuid, 'bb8e8e6b-8c83-401a-a1a0-2964a049de60'::uuid, '12b9b661-9e9e-4cc2-a421-6ecf6adc7741'::uuid, 'AMD', 'GIGABYTE AORUS RX 570', 4096, 'GDDR5', 256, 120.00, 1244.00, 1295.00, 1750.00, 7000.00, 'PCIe 3.0 x16', '2017-04-18 00:00:00.000', 2);

INSERT INTO public.dados_processador
(id_dados_processador, socket, foundry, process_size, transistors, package, t_case_max, old_id)
VALUES('f8860454-8867-4115-a836-a937bf018693'::uuid, 'Intel Socket 1151', 'Intel', 14, NULL, 'FC-LGA1151', 72.00, 1);

INSERT INTO public.processadores
(id_processador, id_dados_processador, nome_fabricante, nome_modelo, market, released, codename, generation, memory_support, frequencia, turbofrequencia, base_clock, multiplicador, multipl_desbloqueado, nro_cores, nro_threads, smp, id_gpu, tdp, old_id)
VALUES('f9ce2969-a573-4e8b-b71c-abc263bcb4a5'::uuid, 'f8860454-8867-4115-a836-a937bf018693'::uuid, 'Intel', 'Core i5-8400', 'Desktop', '2017-01-10 00:00:00.000', 'Coffee Lake', NULL, 'DDR4', 2800.00000000, 4000.00000000, 100.00000000, 28.00000000, 'N', 6, 6, 1, NULL, 65.00, 1);

INSERT INTO public.testes_gpu
(id_teste_gpu, id_configuracao_jogo, id_gpu, id_processador, nome_driver_gpu, avg_fps, min_fps, dt_teste, nome_tester, old_id)
VALUES('24aa2801-e697-412b-b4cb-a830683f47fa'::uuid, '5d2548f0-819f-4774-85ea-79d65f33bc3f'::uuid, 'a41adbc8-acf0-432e-bd13-a2eb7d66b034'::uuid, 'f9ce2969-a573-4e8b-b71c-abc263bcb4a5'::uuid, 'Adrenalin Edition 19.7.1', 50, 39, '2019-07-19 00:00:00.000', 'NJ Tech', 1);
