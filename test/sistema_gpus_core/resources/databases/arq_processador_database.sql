INSERT INTO public.arquiteturas
(id_arquitetura, nome_arquitetura, old_id)
VALUES('ed2e6c15-8485-4aba-8573-08fd47eacda1'::uuid, 'MMX', 1);
INSERT INTO public.arquiteturas
(id_arquitetura, nome_arquitetura, old_id)
VALUES('e001456e-4691-4755-99ad-ea90ff4f19fd'::uuid, 'SSE', 2);
INSERT INTO public.arquiteturas
(id_arquitetura, nome_arquitetura, old_id)
VALUES('5d997cec-8f40-4a1f-a584-083f7cc5f610'::uuid, 'SSE2', 3);
INSERT INTO public.arquiteturas
(id_arquitetura, nome_arquitetura, old_id)
VALUES('64a2cc9c-e67f-4c2e-bb66-ec98e3286cbf'::uuid, 'SSE3', 4);
INSERT INTO public.arquiteturas
(id_arquitetura, nome_arquitetura, old_id)
VALUES('a2be633c-46f3-47b9-896f-57fc44a65d61'::uuid, 'SSSE3', 5);
INSERT INTO public.arquiteturas
(id_arquitetura, nome_arquitetura, old_id)
VALUES('954fb9a1-f09c-4c6c-a279-ebca7282690d'::uuid, 'SSE4.2', 6);


INSERT INTO public.dados_processador
(id_dados_processador, socket, foundry, process_size, transistors, package, t_case_max, old_id)
VALUES('f8860454-8867-4115-a836-a937bf018693'::uuid, 'Intel Socket 1151', 'Intel', 14, NULL, 'FC-LGA1151', 72.00, 1);


INSERT INTO public.processadores
(id_processador, id_dados_processador, nome_fabricante, nome_modelo, market, released, codename, generation, memory_support, frequencia, turbofrequencia, base_clock, multiplicador, multipl_desbloqueado, nro_cores, nro_threads, smp, id_gpu, tdp, old_id)
VALUES('f9ce2969-a573-4e8b-b71c-abc263bcb4a5'::uuid, 'f8860454-8867-4115-a836-a937bf018693'::uuid, 'Intel', 'Core i5-8400', 'Desktop', '2017-01-10 00:00:00.000', 'Coffee Lake', NULL, 'DDR4', 2800.00000000, 4000.00000000, 100.00000000, 28.00000000, 'N', 6, 6, 1, NULL, 65.00, 1);


INSERT INTO public.arquitetura_processador
(id_arquitetura_proc, id_arquitetura, id_processador, old_id)
VALUES('537c1da6-8ae5-4746-9f7b-88bd45323ea7'::uuid, 'ed2e6c15-8485-4aba-8573-08fd47eacda1'::uuid, 'f9ce2969-a573-4e8b-b71c-abc263bcb4a5'::uuid, 1);
INSERT INTO public.arquitetura_processador
(id_arquitetura_proc, id_arquitetura, id_processador, old_id)
VALUES('74cefcbb-039e-4cb6-b772-bc871f309999'::uuid, 'e001456e-4691-4755-99ad-ea90ff4f19fd'::uuid, 'f9ce2969-a573-4e8b-b71c-abc263bcb4a5'::uuid, 2);
INSERT INTO public.arquitetura_processador
(id_arquitetura_proc, id_arquitetura, id_processador, old_id)
VALUES('3d4430a3-244c-4547-9572-f2af864b4648'::uuid, '5d997cec-8f40-4a1f-a584-083f7cc5f610'::uuid, 'f9ce2969-a573-4e8b-b71c-abc263bcb4a5'::uuid, 3);
INSERT INTO public.arquitetura_processador
(id_arquitetura_proc, id_arquitetura, id_processador, old_id)
VALUES('1f1f796a-ef94-4ec1-b1a7-8f5ab51cb857'::uuid, '64a2cc9c-e67f-4c2e-bb66-ec98e3286cbf'::uuid, 'f9ce2969-a573-4e8b-b71c-abc263bcb4a5'::uuid, 4);
INSERT INTO public.arquitetura_processador
(id_arquitetura_proc, id_arquitetura, id_processador, old_id)
VALUES('81373dd7-d45a-4365-9682-0a2998ec60cc'::uuid, 'a2be633c-46f3-47b9-896f-57fc44a65d61'::uuid, 'f9ce2969-a573-4e8b-b71c-abc263bcb4a5'::uuid, 5);