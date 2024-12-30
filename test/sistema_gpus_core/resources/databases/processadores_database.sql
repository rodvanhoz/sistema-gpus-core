INSERT INTO public.render_config
(id_render_config, shading_units, tmus, rops, sm_count, l1_cache, l2_cache, tensor_cores, rt_cores, old_id)
VALUES('ff7bc84e-3c99-4e16-9b3b-a7dcf730eefa'::uuid, 184, 23, 3, 23, 0, 0, NULL, NULL, 14);

INSERT INTO public.caracteristicas_graficas
(id_carac_grafica, direct_x, open_gl, open_cl, vulkan, cuda, shader_model, old_id)
VALUES('9d7cb299-8385-4b77-93c3-de47b9712bbb'::uuid, '12.0 (12_1)', '4.6', '2.1', '1.1.103', NULL, '6.4', 8);

INSERT INTO public.processador_grafico
(id_proc_grafico, nome_gpu, variant_gpu, arquitetura, fundicao, nn_processador, nro_transistors, mm_processador, old_id)
VALUES('7a51b787-c248-41fa-ba1c-78159fc81793'::uuid, 'Coffee Lake GT2', 'Generation 9.5', NULL, 'Intel', 14, NULL, NULL, 14);

INSERT INTO public.dados_processador
(id_dados_processador, socket, foundry, process_size, transistors, package, t_case_max, old_id)
VALUES('f8860454-8867-4115-a836-a937bf018693'::uuid, 'Intel Socket 1151', 'Intel', 14, NULL, 'FC-LGA1151', 72.00, 1);

INSERT INTO public.gpus
(id_gpu, id_processador_grafico, id_caracteristicas_graficas, id_render_config, nome_fabricante, nome_modelo, tam_memoria_kb, tp_memoria, tam_banda, tdp, gpu_clock, boost_clock, mem_clock, mem_clock_efetivo, bus_interface, dt_lancto, old_id)
VALUES('02040c0a-15ae-45af-a82e-2ff28a4c4f31'::uuid, '7a51b787-c248-41fa-ba1c-78159fc81793'::uuid, '9d7cb299-8385-4b77-93c3-de47b9712bbb'::uuid, 'ff7bc84e-3c99-4e16-9b3b-a7dcf730eefa'::uuid, 'Intel', 'UHD Graphics 630', 0, 'DDR4', 0, 15.00, 350.00, 1150.00, 0.00, 0.00, NULL, '2017-01-09 00:00:00.000', 16);


INSERT INTO public.processadores
(id_processador, id_dados_processador, nome_fabricante, nome_modelo, market, released, codename, generation, memory_support, frequencia, turbofrequencia, base_clock, multiplicador, multipl_desbloqueado, nro_cores, nro_threads, smp, id_gpu, tdp, old_id)
VALUES('f9ce2969-a573-4e8b-b71c-abc263bcb4a5'::uuid, 'f8860454-8867-4115-a836-a937bf018693'::uuid, 'Intel', 'Core i5-8400', 'Desktop', '2017-01-10 00:00:00.000', 'Coffee Lake', NULL, 'DDR4', 2800.00000000, 4000.00000000, 100.00000000, 28.00000000, 'N', 6, 6, 1, NULL, 65.00, 1);
INSERT INTO public.processadores
(id_processador, id_dados_processador, nome_fabricante, nome_modelo, market, released, codename, generation, memory_support, frequencia, turbofrequencia, base_clock, multiplicador, multipl_desbloqueado, nro_cores, nro_threads, smp, id_gpu, tdp, old_id)
VALUES('672d0b70-41da-4aa6-abc7-56f670c7c564'::uuid, 'f8860454-8867-4115-a836-a937bf018693'::uuid, 'Intel', 'Core i5-9600K', 'Desktop', '2018-01-10 00:00:00.000', 'Coffee Lake', NULL, 'DDR4', 3700.00000000, 4600.00000000, 100.00000000, 37.00000000, 'S', 6, 6, 1, '02040c0a-15ae-45af-a82e-2ff28a4c4f31'::uuid, 95.00, 2);
INSERT INTO public.processadores
(id_processador, id_dados_processador, nome_fabricante, nome_modelo, market, released, codename, generation, memory_support, frequencia, turbofrequencia, base_clock, multiplicador, multipl_desbloqueado, nro_cores, nro_threads, smp, id_gpu, tdp, old_id)
VALUES('16c75a80-55dd-44e5-bf2a-9bece7b361c7'::uuid, 'f8860454-8867-4115-a836-a937bf018693'::uuid, 'Intel', 'Core i3-8100', 'Desktop', '2017-01-10 00:00:00.000', 'Coffee Lake', NULL, 'DDR4', 2800.00000000, 3600.00000000, 100.00000000, 28.00000000, 'N', 4, 4, 1, NULL, 62.00, 3);