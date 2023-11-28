-- Inserir uma região
INSERT INTO `mydb`.`regiao` (`nome`, `descricao`, `historia`) VALUES ('Zaun', 'Zaun é um grande distrito subterrâneo', 'A pouca luz que chega lá é filtrada pelas fumaças que saem dos emaranhados de canos corroídos e refletem do vidro manchado de sua arquitetura industrial.');

INSERT INTO `mydb`.`regiao` (`nome`, `descricao`, `historia`) VALUES ('Vazio', 'O Vazio é uma manifestação da vastidão inatingível daquilo que jaz além.', 'É uma força de apetite insaciável, que espera uma eternidade até que seus mestres, os misteriosos Observadores, marquem o momento final da destruição.');

INSERT INTO `mydb`.`regiao` (`nome`, `descricao`, `historia`) VALUES ('Demacia', 'Um reino imponente e legítimo com uma prestigiosa história militar.', 'Os habitantes de Demacia sempre colocaram os ideais de justiça, honra e dever acima de tudo, orgulhando-se intensamente de sua herança cultural.', 'No entanto, apesar desses nobres princípios, essa grande nação autossuficiente foi se tornando cada vez mais fechada.');

-- Inserir uma classe
INSERT INTO `mydb`.`classe` (`nome`, `descricao`, `funcao_principal`, `estrategia_recomendada`) VALUES ('Mago', 'Campeões que aplicam dano explosivo.', 'Dano mágico', 'Foque em habilidades de área.');
INSERT INTO `mydb`.`classe` (`nome`, `descricao`, `funcao_principal`, `estrategia_recomendada`) VALUES ('Atirador', 'Campeões que aplicam dano fisico a longa distancia.', 'Dano fisico', 'Posicionar a longa distancia e aplicar altas quantidades de dano.');
INSERT INTO `mydb`.`classe` (`nome`, `descricao`, `funcao_principal`, `estrategia_recomendada`) VALUES ('Suporte', 'Campeões que aplicam cura ou resistencias em aliados, ou aplicam controle de grupo em inimigos.', 'Auxiliar o time aliado', 'Curar aliados, imobilizar inimigos.');
INSERT INTO `mydb`.`classe` (`nome`, `descricao`, `funcao_principal`, `estrategia_recomendada`) VALUES ('Tanque', 'Campeões que resitem a grandes quantidades de danos.', 'Mitigar o dano do time aliado', 'Se jogar no meio dos inimigos enquanto o atirador e mago aplicam dano nos inimigos.');

-- Inserir tipos de habilidade (Assumindo que você já tem tipos de habilidade definidos, inserimos apenas um novo aqui para o exemplo)
INSERT INTO `mydb`.`tipo_de_habilidade` (`nome`, `descricao`, `efeito_principal`) VALUES ('Dano Mágico', 'Causa dano mágico aos oponentes.', 'Dano');
INSERT INTO `mydb`.`tipo_de_habilidade` (`nome`, `descricao`, `efeito_principal`) VALUES ('Controle de grupo', 'Imobiliza, atordoa, diminui velocida ou outro controle de grupo.', 'Controle de grupo');
INSERT INTO `mydb`.`tipo_de_habilidade` (`nome`, `descricao`, `efeito_principal`) VALUES ('Movimentação', 'Aumenta a propría velocidade, salta ou teleporta.', 'Movimentacao');
INSERT INTO `mydb`.`tipo_de_habilidade` (`nome`, `descricao`, `efeito_principal`) VALUES ('Cura', 'Aumenta a propría vida ou de algum alidado.', 'Recuperar vida');
INSERT INTO `mydb`.`tipo_de_habilidade` (`nome`, `descricao`, `efeito_principal`) VALUES ('Dano verdadeiro', 'Aplica dano ignorando armaduras e resistencia a dano mágico.', 'Aplicar alta quantidade de dano campeões com mui');
INSERT INTO `mydb`.`tipo_de_habilidade` (`nome`, `descricao`, `efeito_principal`) VALUES ('Resistencia', 'Ignora parte de um tipo de dano.', 'Reduz o dano recebido');

-- Inserir o campeão (Vamos usar o ID da classe e região que acabamos de inserir)
INSERT INTO `mydb`.`campeao` (`nome`, `classe_id`, `regiao_id`) VALUES ('Ekko', (SELECT id FROM mydb.classe WHERE ID = 1), (SELECT id FROM mydb.regiao WHERE ID = 1));
INSERT INTO `mydb`.`campeao` (`nome`, `classe_id`, `regiao_id`) VALUES ('Lux', (SELECT id FROM mydb.classe WHERE ID = 1), (SELECT id FROM mydb.regiao WHERE ID = 3));
INSERT INTO `mydb`.`campeao` (`nome`, `classe_id`, `regiao_id`) VALUES ('Kassadin', (SELECT id FROM mydb.classe WHERE ID = 1), (SELECT id FROM mydb.regiao WHERE ID = 2));

-- Insira habilidades para o campeão Kassadin
INSERT INTO `mydb`.`habilidade` (`nome`, `descricao`, `tipo_de_habilidade_id`, `id_campeao`) VALUES 
('PEDRA DO VAZIO', 'Kassadin sofre Dano Mágico reduzido...', (SELECT id FROM mydb.tipo_de_habilidade WHERE ID = 6), (SELECT id FROM mydb.campeao WHERE nome = 'Kassadin')),
('ESFERA NULA', 'Kassadin dispara um orbe de energia do Vazio ...', (SELECT id FROM mydb.tipo_de_habilidade WHERE ID = 1), (SELECT id FROM mydb.campeao WHERE nome = 'Kassadin')),
('LÂMINA ÍNFERA', 'Kassadin pode causar uma anomalia no tempo...', (SELECT id FROM mydb.tipo_de_habilidade WHERE ID = 1), (SELECT id FROM mydb.campeao WHERE nome = 'Kassadin')),
('FORÇA DE PULSO', 'Kassadin causa dano e reduz velocidade de inimigos...', (SELECT id FROM mydb.tipo_de_habilidade WHERE ID = 2), (SELECT id FROM mydb.campeao WHERE nome = 'Kassadin')),
('CAMINHAR NA FENDA', 'Kassadin se teleporta para um local próximo...', (SELECT id FROM mydb.tipo_de_habilidade WHERE ID = 1), (SELECT id FROM mydb.campeao WHERE nome = 'Kassadin'));

-- Insira habilidades para o campeão Ekko
INSERT INTO `mydb`.`habilidade` (`nome`, `descricao`, `tipo_de_habilidade_id`, `id_campeao`) VALUES 
('RESSONÂNCIA REVO-Z', 'Cada terceiro ataque ou habilidade...', (SELECT id FROM mydb.tipo_de_habilidade WHERE ID = 1), (SELECT id FROM mydb.campeao WHERE nome = 'Ekko')),
('GIRATEMPO', 'arremessa uma granada que se expande em um campo de distorção temporal...', (SELECT id FROM mydb.tipo_de_habilidade WHERE ID = 1), (SELECT id FROM mydb.campeao WHERE nome = 'Ekko')),
('CONVERGÊNCIA PARALELA', 'Ekko pode causar uma anomalia no tempo...', (SELECT id FROM mydb.tipo_de_habilidade WHERE ID = 2), (SELECT id FROM mydb.campeao WHERE nome = 'Ekko')),
('MERGULHO FÁSICO', 'Ekko faz um rolamento evasivo...', (SELECT id FROM mydb.tipo_de_habilidade WHERE ID = 1), (SELECT id FROM mydb.campeao WHERE nome = 'Ekko')),
('CRONOQUEBRA', 'Ekko estilhaça sua linha do tempo...', (SELECT id FROM mydb.tipo_de_habilidade WHERE ID = 1), (SELECT id FROM mydb.campeao WHERE nome = 'Ekko'));

-- Insira habilidades para o campeão Lux
INSERT INTO `mydb`.`habilidade` (`nome`, `descricao`, `tipo_de_habilidade_id`, `id_campeao`) VALUES 
('ILUMINAÇÃO', 'As habilidades de dano de Lux carregam o alvo...', (SELECT id FROM mydb.tipo_de_habilidade WHERE ID = 1), (SELECT id FROM mydb.campeao WHERE nome = 'Lux')),
('LIGAÇÃO DA LUZ', 'Lux atira uma esfera de luz...', (SELECT id FROM mydb.tipo_de_habilidade WHERE ID = 2), (SELECT id FROM mydb.campeao WHERE nome = 'Lux')),
('BARREIRA PRISMÁTICA', 'Lux lança sua varinha...', (SELECT id FROM mydb.tipo_de_habilidade WHERE ID = 4), (SELECT id FROM mydb.campeao WHERE nome = 'Lux')),
('SINGULARIDADE LUCENTE', 'Dispara uma luz irregular em uma área...', (SELECT id FROM mydb.tipo_de_habilidade WHERE ID = 1), (SELECT id FROM mydb.campeao WHERE nome = 'Lux')),
('CENTELHA FINAL', 'Lux dispara um feixe de luz que causa dano...', (SELECT id FROM mydb.tipo_de_habilidade WHERE ID = 1), (SELECT id FROM mydb.campeao WHERE nome = 'Lux'));


-- Inserir uma skin para o campeão (Vamos usar o ID do campeão que acabamos de inserir)
INSERT INTO `mydb`.`skin` (`nome`, `preco`, `id_campeao`) VALUES ('PROJETO: Ekko', 1350, (SELECT id FROM mydb.campeao WHERE nome = 'Ekko'));
INSERT INTO `mydb`.`skin` (`nome`, `preco`, `id_campeao`) VALUES ('Ekko True Damage', 1850, (SELECT id FROM mydb.campeao WHERE nome = 'Ekko'));
INSERT INTO `mydb`.`skin` (`nome`, `preco`, `id_campeao`) VALUES ('Lux Elementalista', 3150, (SELECT id FROM mydb.campeao WHERE nome = 'Lux'));
INSERT INTO `mydb`.`skin` (`nome`, `preco`, `id_campeao`) VALUES ('Lux Cosmos Negro', 1350, (SELECT id FROM mydb.campeao WHERE nome = 'Lux'));
INSERT INTO `mydb`.`skin` (`nome`, `preco`, `id_campeao`) VALUES ('Kassadin Rapinante Cósmico', 1350, (SELECT id FROM mydb.campeao WHERE nome = 'Kassadin'));
INSERT INTO `mydb`.`skin` (`nome`, `preco`, `id_campeao`) VALUES ('Kassadin Hextec', 1350, (SELECT id FROM mydb.campeao WHERE nome = 'Kassadin'));

-- Listar Nome do campeão, habilidades, skins e região
SELECT 
    c.nome AS 'Nome do Campeão',
    GROUP_CONCAT(DISTINCT h.nome ORDER BY h.id SEPARATOR ', ') AS 'Habilidades',
    GROUP_CONCAT(DISTINCT s.nome ORDER BY s.id SEPARATOR ', ') AS 'Skins',
    r.nome AS 'Região'
FROM 
    `mydb`.`campeao` c
LEFT JOIN `mydb`.`habilidade` h ON c.id = h.id_campeao
LEFT JOIN `mydb`.`skin` s ON c.id = s.id_campeao
LEFT JOIN `mydb`.`regiao` r ON c.regiao_id = r.id
WHERE 
    c.id = 1
GROUP BY 
    c.id, r.nome;



-- Insira skins para o campeão Ekko (exemplo com uma skin)
INSERT INTO `mydb`.`skin` (`nome`, `preco`, `id_campeao`) VALUES 
('PROJECT: Ekko', 1350, (SELECT id FROM campeao WHERE nome = 'Ekko'));
