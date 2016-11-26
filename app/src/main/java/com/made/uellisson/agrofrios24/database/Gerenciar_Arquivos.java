package com.made.uellisson.agrofrios24.database;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;

/**
 * Created by Uellisson on 26/03/2016.
 */
public class Gerenciar_Arquivos {

    //diretorio onde são salvos os arquivos
    String dir_arq_download = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+ Environment.DIRECTORY_DOWNLOADS;

    /*
     Salva os dados da tabela em um arquivo
     */
    public void salvar_tabela(String nome_arquivo, String dados_tabela)  {

        File arq;
        byte[] dados;
        try
        {

            arq = new File(dir_arq_download, nome_arquivo);
            FileOutputStream fos;

            dados = ler_arquivo(nome_arquivo).getBytes();

            fos = new FileOutputStream(arq);
            fos.write(dados);
            fos.flush();
            fos.close();

        }
        catch (Exception e)
        {

        }

    }

    /*
     Salvar arquivo com os dados string passados no parâmetro
     */
    public String salvar_arquivo(String nome_arquivo, String dados_tabela)  {
        String codificação="";
        File arquivo = new File(dir_arq_download+"/"+nome_arquivo);
        try
        {

            arquivo.createNewFile();

            FileOutputStream fos = new FileOutputStream(arquivo);


            OutputStreamWriter escrevendo_arquivo = new OutputStreamWriter(fos);
            escrevendo_arquivo.append(dados_tabela);

            codificação = escrevendo_arquivo.getEncoding();

            escrevendo_arquivo.close();
            fos.close();
        }
        catch (Exception e){

        }

        return codificação;
    }


    /*
    Ler a tabela do banco de dados no arquivo
    */
    public String ler_arquivo(String nome_arquivo){
        String dados_tabela = "";
        File arq;
        String lstrlinha;
        try
        {
             arq = new File(dir_arq_download, nome_arquivo);
            BufferedReader br = new BufferedReader(new FileReader(arq));

            while ((lstrlinha = br.readLine()) != null)
            {
                  dados_tabela = dados_tabela+lstrlinha.toString();
            }
        }
        catch (Exception e)
        {

        }

       return dados_tabela;

    }





//    /*
//     Cria a lista com dados do cliente restaurado de um backup
//     */
//    public ArrayList<Cliente> dados_clientes_do_backup(String nome_arq){
//        ArrayList<Cliente> lista_clientes = new ArrayList<Cliente>();
//
//        String todos_dados_clientes = ler_arquivo(nome_arq);
//
//        String atributo = "";
//
//        String razao_social = "";
//        String fantasia = "";
//        String codigo_cliente = "";
//        String fone = "";
//        String inscricao_est = "";
//        String cnpj_cpf = "";
//
//        //endereco
//        String rua = ""; String numero = "";
//        String bairro = ""; String cep = "";
//        String cidade = ""; String estado = "";
//
//
//
//        int contador_hashtag = 0;
//
//        for (int i = 0; i < todos_dados_clientes.length()-1; i++) {
//
//            if (todos_dados_clientes.substring(i, i+1).equalsIgnoreCase("#")) {
//                System.out.println("encontrei um #");
//                System.out.println("Valor atribuo: "+atributo);
//                atributo = "";
//                contador_hashtag+=1;
//            }
//
//            else{
//                if (todos_dados_clientes.substring(i, i+1).equalsIgnoreCase("%")) {
//                    Cliente c = new Cliente(razao_social, fantasia, codigo_cliente,
//                            fone, inscricao_est, cnpj_cpf,
//                            rua, numero, bairro, cep, cidade, estado);
//                    lista_clientes.add(c);
//
//                    System.out.println("Vai para outro objeto################");
//                    contador_hashtag=0;
//                    //seta todos os atributos pra vazio
//                    atributo = ""; razao_social = ""; fantasia = ""; codigo_cliente = "";
//                    fone = ""; inscricao_est = ""; cnpj_cpf = "";
//                    rua = ""; numero = ""; bairro = ""; cep = ""; cidade = ""; estado = "";
//                }
//                else{
//                    if (contador_hashtag==1) {
//                        atributo=atributo+todos_dados_clientes.substring(i, i+1);
//                        razao_social=atributo;
//                    }
//                    if (contador_hashtag==2) {
//                        atributo=atributo+todos_dados_clientes.substring(i, i+1);
//                        fantasia=atributo;
//                    }
//                    if (contador_hashtag==3) {
//                        atributo=atributo+todos_dados_clientes.substring(i, i+1);
//                        codigo_cliente=atributo;
//                    }
//                    if (contador_hashtag==4) {
//                        atributo=atributo+todos_dados_clientes.substring(i, i+1);
//                        fone=atributo;
//                    }
//                    if (contador_hashtag==5) {
//                        atributo=atributo+todos_dados_clientes.substring(i, i+1);
//                        inscricao_est=atributo;
//                    }
//                    if (contador_hashtag==6) {
//                        atributo=atributo+todos_dados_clientes.substring(i, i+1);
//                        cnpj_cpf=atributo;
//                    }
//                    if (contador_hashtag==7) {
//                        atributo=atributo+todos_dados_clientes.substring(i, i+1);
//                        rua=atributo;
//                    }
//                    if (contador_hashtag==8) {
//                        atributo=atributo+todos_dados_clientes.substring(i, i+1);
//                        numero=atributo;
//                    }
//                    if (contador_hashtag==9) {
//                        atributo=atributo+todos_dados_clientes.substring(i, i+1);
//                        bairro=atributo;
//                    }
//                    if (contador_hashtag==10) {
//                        atributo=atributo+todos_dados_clientes.substring(i, i+1);
//                        cep=atributo;
//
//                    }
//
//                    if (contador_hashtag==11) {
//                        atributo=atributo+todos_dados_clientes.substring(i, i+1);
//                        cidade=atributo;
//
//                    }
//
//                    if (contador_hashtag==12) {
//                        atributo=atributo+todos_dados_clientes.substring(i, i+1);
//                        estado=atributo;
//
//                    }
//                }
//            }
//
//        }//fim do for
//
//        return lista_clientes;
//
//    }//fim do metodo que restaurado a lista de objetos do bakup
//
//    public void importar_dados_clientes(String nome_arq){
//        ArrayList<Cliente> lista_clientes = dados_clientes_do_backup(nome_arq);
//
//        //cria conexão com db
//        BancoDeDados bd = new BancoDeDados(null);
//        SQLiteDatabase conexao = bd.getReadableDatabase();
//        Salvar_no_DB salvar_no_db = new Salvar_no_DB(conexao);
//        bd.onCreate(conexao);
//
//        for (int i=0; i<lista_clientes.size(); i++){
//
//            //salva dados do cliente
//           salvar_no_db.salvar_cliente(lista_clientes.get(i));
//        }
//
//    }

}
