CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE public.arquiteturas (
	id_arquitetura uuid DEFAULT uuid_generate_v4() NOT NULL,
	nome_arquitetura varchar(50) NULL,
	old_id int4 NULL,
	CONSTRAINT arquiteturas_pkey PRIMARY KEY (id_arquitetura)
);

CREATE TABLE public.caracteristicas_graficas (
	id_carac_grafica uuid DEFAULT uuid_generate_v4() NOT NULL,
	direct_x varchar(15) NULL,
	open_gl varchar(15) NULL,
	open_cl varchar(15) NULL,
	vulkan varchar(15) NULL,
	cuda varchar(15) NULL,
	shader_model varchar(15) NULL,
	old_id int4 NULL,
	CONSTRAINT caracteristicas_graficas_pkey PRIMARY KEY (id_carac_grafica)
);

CREATE TABLE public.configuracoes (
	id_configuracao uuid DEFAULT uuid_generate_v4() NOT NULL,
	resolucao_abrev int4 NULL,
	resolucao_detalhe varchar(20) NULL,
	api varchar(30) NULL,
	qualidade_grafica varchar(50) NULL,
	ssao bpchar(1) NULL,
	fxaa bpchar(1) NULL,
	taa bpchar(1) NULL,
	rt bpchar(1) NULL,
	aa varchar(50) NULL,
	nvidia_tec varchar(50) NULL,
	old_id int4 NULL,
	CONSTRAINT configuracoes_pkey PRIMARY KEY (id_configuracao)
);

CREATE TABLE public.dados_processador (
	id_dados_processador uuid DEFAULT uuid_generate_v4() NOT NULL,
	socket varchar(50) NULL,
	foundry varchar(25) NULL,
	process_size int4 NULL,
	transistors numeric(16, 2) NULL,
	package varchar(50) NULL,
	t_case_max numeric(16, 2) NULL,
	old_id int4 NULL,
	CONSTRAINT dados_processador_pkey PRIMARY KEY (id_dados_processador)
);

CREATE TABLE public.jogos (
	id_jogo uuid DEFAULT uuid_generate_v4() NOT NULL,
	nome_jogo varchar(100) NULL,
	dt_lancto timestamp NULL,
	old_id int4 NULL,
	CONSTRAINT jogos_pkey PRIMARY KEY (id_jogo)
);

CREATE TABLE public.processador_grafico (
	id_proc_grafico uuid DEFAULT uuid_generate_v4() NOT NULL,
	nome_gpu varchar(40) NULL,
	variant_gpu varchar(40) NULL,
	arquitetura varchar(40) NULL,
	fundicao varchar(40) NULL,
	nn_processador int4 NULL,
	nro_transistors numeric(16, 8) NULL,
	mm_processador int4 NULL,
	old_id int4 NULL,
	CONSTRAINT processador_grafico_pkey PRIMARY KEY (id_proc_grafico)
);

CREATE TABLE public.render_config (
	id_render_config uuid DEFAULT uuid_generate_v4() NOT NULL,
	shading_units int4 NULL,
	tmus int4 NULL,
	rops int4 NULL,
	sm_count int4 NULL,
	l1_cache int4 NULL,
	l2_cache int4 NULL,
	tensor_cores int4 NULL,
	rt_cores int4 NULL,
	old_id int4 NULL,
	CONSTRAINT render_config_pkey PRIMARY KEY (id_render_config)
);

CREATE TABLE public.configuracoes_jogos (
	id_configuracao_jogo uuid DEFAULT uuid_generate_v4() NOT NULL,
	id_jogo uuid NULL,
	id_configuracao uuid NULL,
	old_id int4 NULL,
	CONSTRAINT configuracoes_jogos_pkey PRIMARY KEY (id_configuracao_jogo),
	CONSTRAINT fk_config_configuracao FOREIGN KEY (id_configuracao) REFERENCES public.configuracoes(id_configuracao),
	CONSTRAINT fk_config_jogo FOREIGN KEY (id_jogo) REFERENCES public.jogos(id_jogo)
);

CREATE TABLE public.gpus (
	id_gpu uuid DEFAULT uuid_generate_v4() NOT NULL,
	id_processador_grafico uuid NOT NULL,
	id_caracteristicas_graficas uuid NOT NULL,
	id_render_config uuid NOT NULL,
	nome_fabricante varchar(30) NULL,
	nome_modelo varchar(50) NULL,
	tam_memoria_kb int4 NULL,
	tp_memoria varchar(10) NULL,
	tam_banda int4 NULL,
	tdp numeric(16, 2) NULL,
	gpu_clock numeric(16, 2) NULL,
	boost_clock numeric(16, 2) NULL,
	mem_clock numeric(16, 2) NULL,
	mem_clock_efetivo numeric(16, 2) NULL,
	bus_interface varchar(20) NULL,
	dt_lancto timestamp NULL,
	old_id int4 NULL,
	CONSTRAINT gpus_pkey PRIMARY KEY (id_gpu),
	CONSTRAINT fk_gpu_carac_graficas FOREIGN KEY (id_caracteristicas_graficas) REFERENCES public.caracteristicas_graficas(id_carac_grafica),
	CONSTRAINT fk_gpu_proc_grafico FOREIGN KEY (id_processador_grafico) REFERENCES public.processador_grafico(id_proc_grafico),
	CONSTRAINT fk_gpu_render_config FOREIGN KEY (id_render_config) REFERENCES public.render_config(id_render_config)
);

CREATE TABLE public.processadores (
	id_processador uuid DEFAULT uuid_generate_v4() NOT NULL,
	id_dados_processador uuid NULL,
	nome_fabricante varchar(30) NULL,
	nome_modelo varchar(40) NULL,
	market varchar(20) NULL,
	released timestamp NULL,
	codename varchar(20) NULL,
	generation varchar(40) NULL,
	memory_support varchar(20) NULL,
	frequencia numeric(16, 8) NULL,
	turbofrequencia numeric(16, 8) NULL,
	base_clock numeric(16, 8) NULL,
	multiplicador numeric(16, 8) NULL,
	multipl_desbloqueado bpchar(1) NULL,
	nro_cores int4 NULL,
	nro_threads int4 NULL,
	smp int4 NULL,
	id_gpu uuid NULL,
	tdp numeric(16, 2) NULL,
	old_id int4 NULL,
	CONSTRAINT processadores_pkey PRIMARY KEY (id_processador),
	CONSTRAINT fk_processador_dados FOREIGN KEY (id_dados_processador) REFERENCES public.dados_processador(id_dados_processador),
	CONSTRAINT fk_processador_gpu FOREIGN KEY (id_gpu) REFERENCES public.gpus(id_gpu)
);

CREATE TABLE public.testes_gpu (
	id_teste_gpu uuid DEFAULT uuid_generate_v4() NOT NULL,
	id_configuracao_jogo uuid NULL,
	id_gpu uuid NULL,
	id_processador uuid NULL,
	nome_driver_gpu varchar(100) NULL,
	avg_fps int4 NULL,
	min_fps int4 NULL,
	dt_teste timestamp NULL,
	nome_tester varchar(100) NULL,
	old_id int4 NULL,
	CONSTRAINT testes_gpu_pkey PRIMARY KEY (id_teste_gpu),
	CONSTRAINT fk_teste_gpu_config_jogo FOREIGN KEY (id_configuracao_jogo) REFERENCES public.configuracoes_jogos(id_configuracao_jogo),
	CONSTRAINT fk_teste_gpu_gpu FOREIGN KEY (id_gpu) REFERENCES public.gpus(id_gpu),
	CONSTRAINT fk_teste_gpu_processador FOREIGN KEY (id_processador) REFERENCES public.processadores(id_processador)
);

CREATE TABLE public.arquitetura_processador (
	id_arquitetura_proc uuid DEFAULT uuid_generate_v4() NOT NULL,
	id_arquitetura uuid NOT NULL,
	id_processador uuid NOT NULL,
	old_id int4 NULL,
	CONSTRAINT arquitetura_processador_pkey PRIMARY KEY (id_arquitetura_proc),
	CONSTRAINT fk_arquitetura FOREIGN KEY (id_arquitetura) REFERENCES public.arquiteturas(id_arquitetura),
	CONSTRAINT fk_processador FOREIGN KEY (id_processador) REFERENCES public.processadores(id_processador)
);
