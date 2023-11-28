import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

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
        System.out.println("\n ---- MENU ----");
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
        System.out.print("Digite a opção: ");
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.println("Digite o número correto.");
            System.out.print("Digite a opção: ");
        }
        System.out.println("------------------------");
        return scanner.nextInt();
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
                listarHabilidadesDoCampeao();
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

                System.out.println("\n---- CAMPEÕES CADASTRADOS ----");
                while (rs.next()){
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");

                    System.out.println("ID: " + id + ", Nome: " + nome);
                }
                System.out.println("------------------------");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void inserirCampeao(){
        System.out.println("Digite o nome do campeão");
        String nome = scanner.next();
        scanner.nextLine();

        // selecionar regiao
        int idRegiao = selecionarOpcao("regiao");
        if(idRegiao == -1){
            System.out.println("Erro ao selecionar região.");
        }

        // selecionar classe
        int idClasse = selecionarOpcao("classe");
        if(idClasse == -1){
            System.out.println("Erro ao selecionar classe.");
        }

        //inserir o campeão
        String query = "INSERT INTO campeao (nome, regiao_id, classe_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnector.connect();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, nome);
                pstmt.setInt(2, idRegiao);
                pstmt.setInt(3, idClasse);
                pstmt.executeUpdate();
                System.out.println("Campeão " + nome + " inserido com sucesso!");


        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private  static void atualizarCampeao(){
        // listar campeõs selecionados
        int idCampeao = selecionarOpcao("campeao");
        if(idCampeao == -1){
            System.out.println("Erro ao selecionar campeão");
            return;
        }

        // Digitar novo nome
        System.out.println("Digite o novo nome do campeão");
        String novoNome = scanner.next();
        scanner.nextLine();

        // Selecionar nova região
        System.out.println("Digite a nova região do campeão");
        int novaRegiaoId = selecionarOpcao("regiao");

        // Selecionar nova classe
        System.out.println("Digite a nova classe do campeão");
        int novaClasseId = selecionarOpcao("classe");

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
                System.out.println("Campeão atualizado com sucesso!");
            } else {
                System.out.println("Nenhum campeão encontrado com o ID fornecido");
            }


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void deletarCampeao(){
        System.out.println("Digite o ID do campeão que deseja deletar");
        int idCampeao = selecionarOpcao("campeao");
        if (idCampeao == -1) {
            System.out.println("Erro ao selecionar campeão.");
        }

        String query = "DELETE FROM campeao WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idCampeao);
            int afetados = pstmt.executeUpdate();

            if(afetados > 0){
                System.out.println("Campeão, habilidades e skins relacionadas foram deletadas com sucesso!");
            } else {
                System.out.println("Nenhum campeão encontrado com o ID fornecido.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void inserirHabilidade(){
        System.out.println("Selecione o campeão para adicionar a habilidade");
        int idCampeao = selecionarOpcao("campeao");
        if (idCampeao == -1) {
            System.out.println("Erro ao selecionar campeão.");
        }

        System.out.println("Selecione o tipo de habilidade");
        int idTipoHabilidade = selecionarOpcao("tipo_de_habilidade");
        if(idTipoHabilidade == -1){
            System.out.println("Erro ao selecionar tipo de habilidade");
        }

        scanner.nextLine();
        System.out.println("Digite o nome da habilidade:");
        String nomeHabilidade = scanner.nextLine();

        System.out.println("Digite a descrição da habilidade");
        String descricaoHabilidade = scanner.nextLine();

        String query = "INSERT INTO habilidade (nome, descricao, tipo_de_habilidade_id, id_campeao) VALUES (?, ?, ?, ?)";
        try(Connection conn = DatabaseConnector.connect();
            PreparedStatement pstmt = conn.prepareStatement(query)){

            pstmt.setString(1, nomeHabilidade);
            pstmt.setString(2, descricaoHabilidade);
            pstmt.setInt(3, idTipoHabilidade);
            pstmt.setInt(4, idCampeao);

            pstmt.executeUpdate();

            System.out.println("Habilidade " + nomeHabilidade + " inserida com sucesso!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void listarHabilidadesDoCampeao(){
        System.out.println("Selecione o ID do campeão");
        int idCampeao = selecionarOpcao("campeao");
        if(idCampeao == -1){
            System.out.println("Erro ao selecionar campeão");
        }

        System.out.println(idCampeao);

        String query = "SELECT h.nome, h.descricao FROM habilidade h WHERE h.id_campeao = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idCampeao);
            try(ResultSet rs = pstmt.executeQuery()){
                System.out.println("------------------------------");
                System.out.println("Habilidades do campeão ID " + idCampeao + ":");
                while (rs.next()){
                    String nomeHabilidade = rs.getString("nome");
                    String descricaoHabilidade = rs.getString("descricao");

                    System.out.println("Habilidade: " + nomeHabilidade + "\nDescrição: " +descricaoHabilidade + "\n");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static int selecionarOpcao(String tabela){
        try(Connection conn = DatabaseConnector.connect();
        Statement stmt = conn.createStatement()){
            String query = "SELECT * FROM " + tabela;
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("Opções disponiveis: ");
            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                System.out.println(id + " - " + nome);
            }

            int escolha;

            do {
                System.out.print("Digite o ID da opção escolhida: ");
                while(!scanner.hasNextInt()){
                    scanner.next();
                    System.out.println("Digite um numero valido.");
                    System.out.print("Digite o ID da opção escolhida");
                }
                escolha = scanner.nextInt();
                if(!opcaoValida(tabela, escolha)){
                    System.out.println("Opção invalida. Escolha um ID valido.");
                    escolha = -1;
                }
            } while (escolha == -1);
            return escolha;
        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }
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