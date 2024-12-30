-- Tabela processador_grafico
INSERT INTO public.processador_grafico
(id_proc_grafico, nome_gpu, variant_gpu, arquitetura, fundicao, nn_processador, nro_transistors, mm_processador, old_id)
VALUES
('01a1ec8a-7925-4038-8e6f-9fce385a0366'::uuid, 'GK104', 'GK104-425-A2', 'Kepler', 'TSMC', 28, 3.54000000, 294, 1),
('64f83ccb-c2f1-493a-9516-a88e86c3dd97'::uuid, 'Polaris 20', 'Polaris 20 XL (215-0910052)', 'GCN 4.0', 'GlobalFoundries', 14, 5.70000000, 232, 2),
('4c3bc7a7-52c0-46be-a301-c93038769336'::uuid, 'GP106', 'GP106-300-A1 ', 'Pascal', 'TSMC', 16, 4.40000000, 200, 3),
('ca9ca528-ff65-44aa-b46a-1c8fb4a90a00'::uuid, 'TU117', 'TU117-300-A1 ', 'Turing', 'TSMC', 12, 4.70000000, 200, 4),
('4bf5cf2c-522a-4140-a137-6fabd920451c'::uuid, 'Hawaii', 'Hawaii XT (215-0852000)', 'GCN 2.0', 'TSMC', 28, 6.20000000, 438, 5),
('106a547c-fba0-4c86-aba0-21ec4a3eafb0'::uuid, 'Polaris 20', 'Polaris 20 XTR (215-0910066)', 'GCN 4.0', 'GlobalFoundries', 14, 5.70000000, 232, 6);

-- Tabela caracteristicas_graficas
INSERT INTO public.caracteristicas_graficas
(id_carac_grafica, direct_x, open_gl, open_cl, vulkan, cuda, shader_model, old_id)
VALUES
('03320cda-708b-4ef8-b7eb-95ffbec741d6'::uuid, '12.0 (11_0)', '4.6', '1.2', '1.1.103', '3.0', '5.1', 1),
('bb8e8e6b-8c83-401a-a1a0-2964a049de60'::uuid, '12.0 (12_0)', '4.6', '2.0', '1.1.108', NULL, '6.4', 2),
('32095b1b-74dc-4021-81b3-af231f2c7bdd'::uuid, '12.0 (12_1)', '4.6', '1.2', '1.1.109', '6.1', '6.4', 3),
('a9a17e79-fa0d-4b54-9d20-5b1a3d8f45c8'::uuid, '12.0 (12_1)', '4.6', '1.2', '1.1.109', '7.5', '6.4', 4),
('b1134edb-e9ca-4a2e-b061-7595e5c22f03'::uuid, '12.0 (12_0)', '4.6', '2.0', '1.1.101', NULL, '6.3', 5);

-- Tabela render_config
INSERT INTO public.render_config
(id_render_config, shading_units, tmus, rops, sm_count, l1_cache, l2_cache, tensor_cores, rt_cores, old_id)
VALUES
('af6b184c-fe73-4bc9-ac45-2e4489fd7c26'::uuid, 1536, 128, 32, 8, 16, 512, NULL, NULL, 1),
('12b9b661-9e9e-4cc2-a421-6ecf6adc7741'::uuid, 2048, 128, 32, 32, 16, 2048, NULL, NULL, 2),
('35f895ea-3ee1-4eb4-ae99-933511c2472f'::uuid, 1152, 72, 48, 9, 48, 1536, NULL, NULL, 3),
('fa878e4d-abc3-46f8-b1cb-15502a584a52'::uuid, 896, 56, 32, 14, 64, 1024, NULL, NULL, 4),
('599474aa-1b66-49d7-a1b9-9c67da3807e9'::uuid, 2816, 176, 64, 44, 16, 1024, NULL, NULL, 5),
('60324dcc-7977-4660-b127-7745b7f16b00'::uuid, 2304, 144, 32, 36, 16, 2048, NULL, NULL, 6);

-- Tabela gpus
INSERT INTO public.gpus
(id_gpu, id_processador_grafico, id_caracteristicas_graficas, id_render_config, nome_fabricante, nome_modelo, tam_memoria_kb, tp_memoria, tam_banda, tdp, gpu_clock, boost_clock, mem_clock, mem_clock_efetivo, bus_interface, dt_lancto, old_id)
VALUES
('669e6eb0-6528-489a-ac90-bb3670cb6a78'::uuid, '01a1ec8a-7925-4038-8e6f-9fce385a0366'::uuid, '03320cda-708b-4ef8-b7eb-95ffbec741d6'::uuid, 'af6b184c-fe73-4bc9-ac45-2e4489fd7c26'::uuid, 'NVIDIA', 'GeForce GTX 770', 2048, 'GDDR5', 256, 230.00, 1046.00, 1085.00, 1753.00, 7012.00, 'PCIe 3.0 x16', '2013-03-30 00:00:00.000', 1),
('a41adbc8-acf0-432e-bd13-a2eb7d66b034'::uuid, '64f83ccb-c2f1-493a-9516-a88e86c3dd97'::uuid, 'bb8e8e6b-8c83-401a-a1a0-2964a049de60'::uuid, '12b9b661-9e9e-4cc2-a421-6ecf6adc7741'::uuid, 'AMD', 'GIGABYTE AORUS RX 570', 4096, 'GDDR5', 256, 120.00, 1244.00, 1295.00, 1750.00, 7000.00, 'PCIe 3.0 x16', '2017-04-18 00:00:00.000', 2),
('2cb6980d-bbe1-43df-bfd3-ce6fc038dc19'::uuid, '4c3bc7a7-52c0-46be-a301-c93038769336'::uuid, '32095b1b-74dc-4021-81b3-af231f2c7bdd'::uuid, '35f895ea-3ee1-4eb4-ae99-933511c2472f'::uuid, 'NVIDIA', 'GeForce GTX 1060 3 GB', 3072, 'GDDR5', 192, 120.00, 1506.00, 1708.00, 2002.00, 8008.00, 'PCIe 3.0 x16', '2016-08-18 00:00:00.000', 3),
('a8fb8a6d-5933-41ff-a118-a7527fa2c354'::uuid, 'ca9ca528-ff65-44aa-b46a-1c8fb4a90a00'::uuid, 'a9a17e79-fa0d-4b54-9d20-5b1a3d8f45c8'::uuid, 'fa878e4d-abc3-46f8-b1cb-15502a584a52'::uuid, 'NVIDIA', 'GeForce GTX 1650', 4096, 'GDDR5', 128, 75.00, 1485.00, 1665.00, 2000.00, 8000.00, 'PCIe 3.0 x16', '2019-04-23 00:00:00.000', 4),
('da28b013-f7ee-4a3f-b646-344052bc2200'::uuid, 'ca9ca528-ff65-44aa-b46a-1c8fb4a90a00'::uuid, 'a9a17e79-fa0d-4b54-9d20-5b1a3d8f45c8'::uuid, 'fa878e4d-abc3-46f8-b1cb-15502a584a52'::uuid, 'NVIDIA', 'MSI GTX 1650 GAMING X', 4096, 'GDDR5', 128, 75.00, 1485.00, 1860.00, 2000.00, 8000.00, 'PCIe 3.0 x16', '2019-04-23 00:00:00.000', 5),
('05d74819-c432-44ff-8bf5-45b9ed1b9c82'::uuid, '4bf5cf2c-522a-4140-a137-6fabd920451c'::uuid, 'b1134edb-e9ca-4a2e-b061-7595e5c22f03'::uuid, '599474aa-1b66-49d7-a1b9-9c67da3807e9'::uuid, 'AMD', 'MSI R9 290X', 4096, 'GDDR5', 512, 290.00, 1000.00, 1000.00, 1250.00, 5000.00, 'PCIe 3.0 x16', '2013-10-24 00:00:00.000', 6),
('78938b34-972a-4297-bc3e-4e8dfeaacbdd'::uuid, '106a547c-fba0-4c86-aba0-21ec4a3eafb0'::uuid, 'bb8e8e6b-8c83-401a-a1a0-2964a049de60'::uuid, '60324dcc-7977-4660-b127-7745b7f16b00'::uuid, 'AMD', 'GIGABYTE AORUS RX 580 XTR', 8192, 'GDDR5', 256, 185.00, 1257.00, 1425.00, 2000.00, 8000.00, 'PCIe 3.0 x16', '2017-04-18 00:00:00.000', 7);
