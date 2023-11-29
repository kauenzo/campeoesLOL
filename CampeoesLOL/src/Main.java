import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) {
        int opcao;
        do {
            exibirMenu();
            opcao = lerOpcao();
            processarOpcao(opcao);
        } while (opcao != 0);
        System.out.println("---- PROGRAMA ENCERRADO ----");
        /*try (Connection conn = DatabaseConnector.connect()){
            System.out.println("Conectado ao banco de dados!");

            try(Statement stmt = conn.createStatement()){
                String query = "SELECT * FROM campeao";
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");

                    System.out.println("ID: " + id + ", Nome: " + nome);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }*/
    }

    private static void exibirMenu() {

        System.out.println("\n *------ MENU ------*");
        System.out.println("1 - Listar o nome de todos os campeões");
        System.out.println("2 - Inserir novo campeão");
        System.out.println("3 - Atualizar um campeão");
        System.out.println("4 - Deletar um campeão");
        System.out.println("5 - Inserir nova habilidade para um campeão");
        System.out.println("6 - Listar todas as habiliades de um campeão");
        System.out.println("7 - Atualizar habilidade para um campeão");
        System.out.println("8 - Deletar habilidade para um campeão");
        System.out.println("9 - Cadastrar skin para um campeão");
        System.out.println("10 - Listar todas as informações de um campeão");
        System.out.println("11 - Listar todos os campeões de uma região");
        System.out.println("12 - Listar todos os campeões de uma classe");
        System.out.println("0 - Finalizar");
    }

    private static int lerOpcao() {
        int opcao = -1;
        while (opcao == -1) {
            System.out.print("Digite a opção: ");
            try{
                String line = reader.readLine();
                opcao = Integer.parseInt(line);
                System.out.println("-------------------------------");
            } catch (NumberFormatException e){
                System.out.println("Digite o número correto.");
            } catch (IOException e){
                e.printStackTrace();
            }

        }
        return opcao;
    }

    private static void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                listarCampeoes();
                break;
            case 2:
                inserirCampeao();
                break;
            case 3:
                atualizarCampeao();
                break;
            case 4:
                deletarCampeao();
                break;
            case 5:
                inserirHabilidade();
                break;
            case 6:
                listarHabilidadesDoCampeao(-1);
                break;
            case 7:
                atualizarHabilidade();
                break;
            case 8:
                deletarHabilidade();
                break;
            case 9:
                inserirSkin();
                break;
            case 10:
                listarInformacoesDoCampeao();
                break;
            case 11:
                listarCampeoesPorRegiao();
                break;
            case 12:
                listarCampeoesPorClasse();
                break;
            case 0:
                break;
            default:
                System.out.println("Opção invalida.");
                break;
        }
    }

    private static void listarCampeoes() {
        try (Connection conn = DatabaseConnector.connect()){
            try(Statement stmt = conn.createStatement()){
                String query = "SELECT * FROM campeao";
                ResultSet rs = stmt.executeQuery(query);

                System.out.println("\n------- CAMPEÕES CADASTRADOS -------");
                while (rs.next()){
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");

                    System.out.println("ID: " + id + ", Nome: " + nome);
                }
                System.out.println("-------------------------------");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void inserirCampeao(){
        try {
            System.out.println("---- INSERIR NOVO CAMPEÃO ---- ");
            System.out.print("Digite o nome do campeão: ");
            String nome = reader.readLine();

            System.out.println("A qual região '" + nome + "' pertence?");
            int idRegiao = selecionarOpcao("regiao");
            if(idRegiao == -1){
                System.out.println("Erro ao selecionar região.");
            }

            System.out.println("A qual classe '" + nome + "' pertence?");
            int idClasse = selecionarOpcao("classe");
            if(idClasse == -1){
                System.out.println("Erro ao selecionar classe.");
            }

            String nomeClasse = getNomePeloID(idClasse, "classe");
            String nomeRegiao = getNomePeloID(idClasse, "regiao");

            String query = "INSERT INTO campeao (nome, regiao_id, classe_id) VALUES (?, ?, ?)";
            try (Connection conn = DatabaseConnector.connect();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setString(1, nome);
                    pstmt.setInt(2, idRegiao);
                    pstmt.setInt(3, idClasse);
                    pstmt.executeUpdate();
                    System.out.println("Campeão '" + nome
                            + "', da classe '" + nomeClasse +
                            "' inserido com sucesso na região '" + nomeRegiao +"'!");
            } catch (Exception e){
                e.printStackTrace();
            }
        } catch (IOException e){
        e.printStackTrace();
        }
    }

    private  static void atualizarCampeao(){
        System.out.println("---- ATUALIZAR UM CAMPEÃO ---- ");
        int idCampeao = selecionarOpcao("campeao");
        if(idCampeao == -1){
            System.out.println("Erro ao selecionar campeão");
            return;
        }

        String nomeAntigo = getNomePeloID(idCampeao, "campeao");

        System.out.println("-------------------------------\n");
        System.out.print("Digite o novo nome do campeão: ");
        String novoNome = "";
        try{
            novoNome = reader.readLine();
        } catch (IOException e){
            e.printStackTrace();
        }

        System.out.println("-------------------------------\n");
        System.out.println("A qual região '" + novoNome + "' irá pertencer?");
        int novaRegiaoId = selecionarOpcao("regiao");
        String nomeRegiao = getNomePeloID(novaRegiaoId, "regiao");

        System.out.println("-------------------------------\n");
        System.out.println("A qual classe '" + novoNome + "' irá pertencer?");
        int novaClasseId = selecionarOpcao("classe");
        String nomeClasse =getNomePeloID(novaClasseId, "classe");

        String query = "UPDATE campeao SET nome = ?, regiao_id = ?, classe_id = ? WHERE id = ?";
        try(Connection conn = DatabaseConnector.connect();
            PreparedStatement pstmt = conn.prepareStatement(query)){

            if(novoNome.isEmpty()){
                pstmt.setNull(1, Types.VARCHAR);
            } else {
                pstmt.setString(1, novoNome);
            }

            if(novaRegiaoId == -1){
                pstmt.setNull(2, Types.VARCHAR);
            } else {
                pstmt.setInt(2, novaRegiaoId);
            }

            if(novaClasseId == -1){
                pstmt.setNull(3, Types.VARCHAR);
            } else {
                pstmt.setInt(3, novaClasseId);
            }

            pstmt.setInt(4, idCampeao);

            int afetados = pstmt.executeUpdate();
            if(afetados > 0){
                System.out.println(
                        "Campeão '" + nomeAntigo +
                        "' teve o nome atualizado para '" + novoNome +
                        "', e agora pertence a classe '" + nomeClasse +
                        "' e região '" + nomeRegiao +"'!"
                );
            } else {
                System.out.println("Nenhum campeão encontrado com o ID fornecido");
            }


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void deletarCampeao(){
        System.out.println("---- DELETAR CAMPEÃO ---- ");
        System.out.println("Qual campeão deseja deletar?");
        int idCampeao = selecionarOpcao("campeao");
        if (idCampeao == -1) {
            System.out.println("Erro ao selecionar campeão.");
        }

        String nomeCampeao = getNomePeloID(idCampeao, "campeao");

        String query = "DELETE FROM campeao WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idCampeao);
            int afetados = pstmt.executeUpdate();

            if(afetados > 0){
                System.out.println("Campeão '" + nomeCampeao + "' foi completamente deletado, incluindo suas habilidades e skins relacionadas!");
            } else {
                System.out.println("Nenhum campeão encontrado com o ID fornecido.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void inserirHabilidade(){
        System.out.println("---- INSERIR NOVA HABILIDADE ---- ");
        System.out.println("A nova habilidade pertencerá a qual campeão?");
        int idCampeao = selecionarOpcao("campeao");
        if (idCampeao == -1) {
            System.out.println("Erro ao selecionar campeão.");
        }

        String nomeDoCampeao = getNomePeloID(idCampeao, "campeao");

        System.out.println("Qual será o tipo dessa habilidade?");
        int idTipoHabilidade = selecionarOpcao("tipo_de_habilidade");
        if(idTipoHabilidade == -1){
            System.out.println("Erro ao selecionar tipo de habilidade");
        }

        System.out.print("Nome da habilidade: ");
        String nomeHabilidade = "";
        try{
            nomeHabilidade = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print("Descrição breve da habilidade: ");
        String descricaoHabilidade = "";
        try {
            descricaoHabilidade = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String query = "INSERT INTO habilidade (nome, descricao, tipo_de_habilidade_id, id_campeao) VALUES (?, ?, ?, ?)";
        try(Connection conn = DatabaseConnector.connect();
            PreparedStatement pstmt = conn.prepareStatement(query)){

            pstmt.setString(1, nomeHabilidade);
            pstmt.setString(2, descricaoHabilidade);
            pstmt.setInt(3, idTipoHabilidade);
            pstmt.setInt(4, idCampeao);

            pstmt.executeUpdate();

            System.out.println("Habilidade '" + nomeHabilidade + "' inserida com sucesso para o campeão '" + nomeDoCampeao + "'!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static int listarHabilidadesDoCampeao(int campeao){
        System.out.println("---- LISTAR HABILIDADES DE UM CAMPEÃO ---- ");
        System.out.println("Escolha um campeão para exibir suas habilidades");
        int idCampeao;
        if (campeao == -1) {
            idCampeao = selecionarOpcao("campeao");
            if(idCampeao == -1){
                System.out.println("Erro ao selecionar campeão");
            }
        } else {
            idCampeao = campeao;
        }

        System.out.println(idCampeao);

        String query = "SELECT h.id, h.nome, h.descricao FROM habilidade h WHERE h.id_campeao = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idCampeao);
            try(ResultSet rs = pstmt.executeQuery()){
                System.out.println("------------------------------");
                System.out.println("Habilidades do campeão ID " + idCampeao + ":");
                while (rs.next()){
                    int idHabilidade = rs.getInt("id");
                    String nomeHabilidade = rs.getString("nome");
                    String descricaoHabilidade = rs.getString("descricao");

                    System.out.println("Habilidade [" +idHabilidade+ "]: " + nomeHabilidade + "\nDescrição: " +descricaoHabilidade + "\n");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    private static void atualizarHabilidade(){
        System.out.println("---- ATUALIZAR UMA HABILIDADE ---- ");
        System.out.println("Selecione um campeão");
        int idCampeao = selecionarOpcao("campeao");
        if (idCampeao == -1){
            System.out.println("Erro ao selecionar campeão");
        }

        listarHabilidadesDoCampeao(idCampeao);
        System.out.print("Qual o ID da habilidade que deseja atualizar: ");
        int idHabilidade = -1;
        try {
            idHabilidade = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Por favor, insira um número válido.");
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        System.out.print("Qual será o novo nome da habilidade ["+idHabilidade+"]? R: ");
        String novoNome = "";
        try {
            novoNome = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print("Qual a descrição para a habilidade '" + novoNome +"'? R:");
        String novaDescricao = "";
        try {
            novaDescricao = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String nomeCampeao = getNomePeloID(idCampeao, "campeao");

        String query = "UPDATE habilidade SET nome = ?, descricao = ? WHERE id = ? AND id_campeao = ?";
        try(Connection conn = DatabaseConnector.connect();
            PreparedStatement pstmt = conn.prepareStatement(query)){

            pstmt.setString(1, novoNome);
            pstmt.setString(2, novaDescricao);
            pstmt.setInt(3, idHabilidade);
            pstmt.setInt(4, idCampeao);

            int afetados = pstmt.executeUpdate();
            if (afetados > 0){
                System.out.println(
                        "Habilidade atualizada. Agora '" + nomeCampeao
                        + "'possui a habilidade '" + novoNome + "'!'"
                );
            } else {
                System.out.println(
                        "O campeão '" + nomeCampeao +
                        "' não possui nenhuma habilidade com o ID [" + idHabilidade +"]."
                );
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void deletarHabilidade(){
        System.out.println("---- DELETAR UMA HABILIDADE ---- ");
        System.out.println("Selecione um campeão");
        int idCampeao = selecionarOpcao("campeao");
        if (idCampeao == -1) {
            System.out.println("Erro ao selecionar o campeão.");
        }

        listarHabilidadesDoCampeao(idCampeao);

        System.out.print("Digite o ID da habilidade que deseja deletar: ");
        int idHabilidade = -1;
        try {
            idHabilidade = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Por favor, insira um número válido.");
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }

        String nomeHabilidade =getNomePeloID(idHabilidade, "habilidade");
        String nomeCampeao =getNomePeloID(idCampeao, "habilidade");

        String query = "DELETE FROM habilidade WHERE id = ? AND id_campeao = ?";
        try(Connection conn = DatabaseConnector.connect();
            PreparedStatement pstmt = conn.prepareStatement(query)){

            pstmt.setInt(1, idHabilidade);
            pstmt.setInt(2, idCampeao);

            int afetados = pstmt.executeUpdate();
            if (afetados > 0){
                System.out.println(
                        "Habilidade '" + nomeHabilidade+
                        "' deletada para o campeão '" + nomeCampeao + "'!");
            } else {
                System.out.println(
                        "O campeão '" + nomeCampeao +
                        "' não possui nenhuma habilidade com o ID [" + idHabilidade +"]."
                );
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void inserirSkin(){
        System.out.println("---- INSERIR UMA NOVA SKIN ---- ");
        System.out.println("Qual campeão irá receber uma nova skin?");
        int idCampeao = selecionarOpcao("campeao");
        if (idCampeao == -1) {
            System.out.println("Erro ao selecionar o campeão.");
        }

        System.out.print("Nome da skin: ");
        String nomeSkin = "";
        try {
            nomeSkin = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print("Preço da skin: ");
        int precoSkin = -1;
        while (precoSkin == -1) {
            try {
                precoSkin = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, insira um número válido para o preço.");
                System.out.print("Digite o preço da skin: ");
                precoSkin = -1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String nomeCampeao = getNomePeloID(idCampeao, "campeao");

        String query = "INSERT INTO skin (nome, preco, id_campeao) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nomeSkin);
            pstmt.setInt(2, precoSkin);
            pstmt.setInt(3, idCampeao);

            pstmt.executeUpdate();
            System.out.println(
                    "Agora o campeão '" + nomeCampeao +
                    "' possui a skin '" + nomeSkin + "'!"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void listarInformacoesDoCampeao(){
        System.out.println("---- LISTAR INFORMAÇÕES DE UM CAMPEÃO ---- ");
        System.out.println("Escolha um campeão");
        int idCampeao = selecionarOpcao("campeao");
        if (idCampeao == -1) {
            System.out.println("Erro ao selecionar o campeão.");
        }

        String query = "SELECT " +
                "c.nome AS 'Nome do Campeão', " +
                "GROUP_CONCAT(DISTINCT h.nome ORDER BY h.id SEPARATOR ', ') AS 'Habilidades', " +
                "GROUP_CONCAT(DISTINCT s.nome ORDER BY s.id SEPARATOR ', ') AS 'Skins', " +
                "r.nome AS 'Região' " +
                "FROM campeao c " +
                "LEFT JOIN habilidade h ON c.id = h.id_campeao " +
                "LEFT JOIN skin s ON c.id = s.id_campeao " +
                "LEFT JOIN regiao r ON c.regiao_id = r.id " +
                "WHERE c.id = ? " +
                "GROUP BY c.id, r.nome";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idCampeao);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String nomeCampeao = rs.getString("Nome do Campeão");
                    String habilidades = rs.getString("Habilidades");
                    String skins = rs.getString("Skins");
                    String regiao = rs.getString("Região");

                    System.out.println("Nome do Campeão: " + nomeCampeao);
                    System.out.println("Habilidades: " + habilidades);
                    System.out.println("Skins: " + skins);
                    System.out.println("Região: " + regiao);
                } else {
                    System.out.println("Nenhum campeão encontrado com o ID [" + idCampeao + "]");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void listarCampeoesPorRegiao() {
        System.out.println("---- LISTAR CAMPEÕES POR REGIÃO ---- ");
        System.out.println("Escolha um região");
        int idRegiao = selecionarOpcao("regiao");
        if (idRegiao == -1) {
            System.out.println("Erro ao selecionar a região.");
        }

        String query = "SELECT c.nome FROM campeao c WHERE c.regiao_id = ?";

        String nomeRegiao = getNomePeloID(idRegiao, "regiao");

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idRegiao);

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("\n-------------------------------");
                System.out.println("Campeões da região '" + nomeRegiao + "':");
                boolean temCampeoes = false;
                while (rs.next()) {
                    String nomeCampeao = rs.getString("nome");
                    System.out.println(nomeCampeao);
                    temCampeoes = true;
                }

                if (!temCampeoes) {
                    System.out.println("Nenhum campeão encontrado para a região '" + nomeRegiao + "'!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void listarCampeoesPorClasse() {
        System.out.println("---- LISTAR CAMPEÕES POR CLASSE ---- ");
        System.out.println("Escolha uma classe");
        int idClasse = selecionarOpcao("classe");
        if (idClasse == -1) {
            System.out.println("Erro ao selecionar a classe.");
            return;
        }

        String query = "SELECT c.nome FROM campeao c WHERE c.classe_id = ?";

        String nomeClasse = getNomePeloID(idClasse, "classe");

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idClasse);

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("\n-------------------------------");
                System.out.println("Campeões da classe '" + nomeClasse + "':");
                boolean temCampeoes = false;
                while (rs.next()) {
                    String nomeCampeao = rs.getString("nome");
                    System.out.println(nomeCampeao);
                    temCampeoes = true;
                }

                if (!temCampeoes) {
                    System.out.println("Nenhum campeão encontrado para a região '" + nomeClasse + "'!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int selecionarOpcao(String tabela){
        try (Connection conn = DatabaseConnector.connect();
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM " + tabela;
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                System.out.println(id + " - " + nome);
            }

            int escolha = -1;

            do {
                System.out.print("ID da opção escolhida: ");
                String line = reader.readLine();
                try{
                    escolha = Integer.parseInt(line);
                    if (!opcaoValida(tabela, escolha)) {
                        System.out.println("Opção invalida. Escolha um ID valido.");
                        escolha = -1;
                    }
                }catch (NumberFormatException e){
                    System.out.println("Digite um numero válido.");
                }
            } while (escolha == -1);
            return escolha;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static String getNomePeloID(int idCampeao, String tabela) {
        String query = "SELECT nome FROM " + tabela + " WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idCampeao);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nome");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean opcaoValida(String tabela, int id){
        try(Connection conn = DatabaseConnector.connect();
            PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM " + tabela + " WHERE id = ?")){
            pstmt.setInt(1, id);
            try(ResultSet rs = pstmt.executeQuery()){
                if (rs.next()){
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}