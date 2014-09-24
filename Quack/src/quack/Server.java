package quack;

public interface Server {

    // Uma instância desta classe armazena o estado do servidor {Quack}.
    // Somente uma instância deve ser criada quando o servidor começa a rodar,
    // e deve ser inicializada com o método {inicialize} abaixo.
    // A instância persiste enquanto o servidor estiver rodando.
    
    // Essa instância contem as tabelas do sistema {Quack}: de usuários
    // (i.e., contas), de contatos, de mensagens, e de sessões abertas.
    // Usuários, contatos e mensagens são espelhados numa base de dados
    // persistente.
    //
    // SESSÕES E COOKIES
    //
    // Alguns dos métodos abaixo somente podem ser executados por
    // usuários cadastrados e atualmente logados no sistema {Quack}.
    // Nesses casos, a sessão de login é identificada por um {cookie}
    // atribuído quando o usuário fez login.
    // 
    // Outros métodos podem ser executados por qualquer pessoa; nesses
    // casos o parâmetro {cookie} pode ser omitido ("").
    //
    // Em todos os métodos, caso seja fornecido o {cookie} de uma sessão
    // válida, a página de resultado geralmente vai conter mais
    // informações e botões referentes ao usuário {u} que é dono da
    // sessão.
    // 
    // DATAHORAS
    //
    // Alguns eventos, como o envio ou re-envio de mensagens, são
    // rotulados com uma /datahora/ (data + hora). Externamente, uma
    // datahora é uma {String} no formato "%Y-%m-%d %H:%M:%S (%Z)" onde
    // "%Z" é o fuso horário. As datahoras serão convertidas
    // internamente para um número inteiro de segundos na zona padrão UTC.
    //
    // INTERVALOS DE MENSAGENS
    // 
    // Alguns dos métodos a seguir especificam um trecho de uma lista de mensagens através 
    // de parâmetros {startTime}, {endTime} (datahoras), e {maxN} (inteiro positivo).
    //
    // Supõe-se que a lista está ordenada em ordem crescente de datahora
    // (de envio ou re-envio). O trecho consiste das {maxN} primeiras
    // mensagens da lista com datahora maior ou igual a
    // {startTime} e menor ou igual a {endTime}.
    // 
    // Se {endTime} for omitido ("") supõe fim dos tempos. Se {startTime} for omitido
    // mas {endTime} for especificado, lista as {max} *últimas*
    // mensagens com datahora de envio ou reenvio anterior ou igual a
    // {endTime}. Se ambos forem omitidos, lista as {maxN} primeiras
    // mensagens da lista.

    // ----------------------------------------------------------------------
    // INICIALIZAÇÃO
    
    public void initialize(String dbName, String dbPassword);
    // Inicializa uma instância recém-criada de {Server}, inicializando 
    // as tabelas de usuários, contatos e mensagens a partir do banco de dados 
    // {dbName} com senha de acesso {dbPassword}.  Também inicializa a tabela
    // de sessões abertas, incialmente vazia.
    
    // ----------------------------------------------------------------------
    // PROCESSAMENTO DE PEDIDOS HTTP
    // 
    // Os métodos abaixo são ativados pelo servidor HTTP ao receber 
    // certos comandos GET ou PUT do browser do usuário.
    //
    // ??{ Descrever aqui detalhes sobre o processo de conversão do 
    // GET/PUT em chamada de métodos, incluindo obtenção dos parâmetros. }?? 
    // ??{ Descrever a "saída" de cada método, ou seja a
    // página HTML que é resposta ao GET/PUT; inclusive em casos de
    // erro. }??
    
    public String processRegistrationReq(String userName, String email, String fulName, String password);
    // Chamado quando o servidor HTTP recebe um pedido de cadastramento de novo usuário,
    // Cria um novo usuário com dados {userName}, {email}, {fullName} e {password}.
    // Acrescenta-o ao cadastro {this.userTable}.
    // 
    // Por enquanto, o cadastramento não faz o login automático; o browser
    // é direcionado para a página de login.
    // 
    // Se o cadastramento tiver sucesso, devolve a página inicial do usuário.

    public String processLoginReq(String userName, String password);
    // Chamado quando o servidor HTTP recebe um pedido de login do usuário,
    // com campos {userName} (identificador do usuário) e {password} (senha)
    // preenchidos pelo usuário.
    // 
    // Se o login tiver sucesso, a sessão é criada e acrescentada ao 
    // table de sessões abertas. 
    //
    // ??{ Descrever quem escolhe o {cookie} (string que o servidor HTTP
    // deve enviar ao browser, e que este deve enviar de volta para
    // identificar comandos subsequentes da mesma sessão). Se quem
    // escolhe o {cookie} é este procedimento, dizer como ele é
    // repassado ao servidor HTTP. }??
     
    public String processLogoutReq(String cookie);
    // Chamado quando o servidor HTTP recebe um pedido de logout do usuário.
    // A sessão a ser fechada é a identificada pelo {cookie}. 
   
    public String processSendMessageReq(String cookie, String text, String replyUserName, String replyTime);
    // Chamado quando o servidor HTTP recebe um pedido da sessão
    // identificada pelo {cookie} para enviar mais uma mensagem original
    // do usuário {u} que é dono da sessão (e não um re-envio).
    //
    // Este comando atribui um datahora UTC à mensagem, e a armazena nas 
    // tabelas de mensagens. O autor da mensagem é por definição o usuário
    // {u}. Se {replyUserName} estiver omitido (""), a
    // mensagem será o início de uma nova conversa. Se {replyUserName}
    // for especificado, a mensagem será marcada como uma resposta à
    // mensagem enviada ou re-enviada pelo usuário {replyUserName}
    // datahora de envio ou reenvio {replyTime}.
    //
    // Em caso de sucesso, o resultado é uma página dizendo "sua
    // mensagem foi enviada com sucesso" e mostrando o texto formatado 
    // da mensagem e seu datahora.
    
    public String processShowOutMessagesReq(String cookie, String userName, String startTime, String endTime, int maxN);
    // Chamado quando o servidor HTTP recebe um pedido da sessão
    // identificada pelo {cookie} para listar as mensagens enviadas ou
    // reenviadas pelo usuário {userName}, que não é necessariamente o
    // dono da sessão.
    // 
    // Este método pode ser chamado com o {cookie} omitido ("").
    //
    // Em caso de sucesso, o resultado é uma página com as mensagens enviadas ou
    // reenviadas por {userName}, no intervalo especificado por {startTime}, {endTime}, e {maxN}
    // A página terá botões "next {maxN}" "prev {maxN}" para acessar
    // outras mensagens vizinhas a essas.
    
    public String processShowInMessagesReq(String cookie, String startTime, String endTime, int maxN);
    // Chamado quando o servidor HTTP recebe um pedido da sessão
    // identificada pelo {cookie} para listar as mensagens recebidas pelo
    // usuário {u} que é dono da sessão (a /timeline/ do usuário {u}).
    //
    // As mensagens em questão podem incluir as mensagem enviadas e re-enviadas pelos
    // usuários que o usuário {u} segue, mensagens públicas que citam o usuário {u},
    // mensagens privadas dirigidas ao usuário {u}, mensagens em resposta a
    // conversas iniciadas pelo usuário {u}, etc., conforme especificado 
    // no perfil do usuário {u}.
    //
    // ??{ No futuro talvez essas escolhas entrem como parâmetros em vez de estarem no perfil. }??
    //
    // Em caso de sucesso, o resultado é uma página com as mensagens em
    // questão, com datahora de envio ou reenvio no intervalo
    // especificado por {startTime}, {endTime}, e {maxN} A página terá
    // botões "next {maxN}" "prev {maxN}" para acessar outras mensagens
    // vizinhas a essas.
    
}
