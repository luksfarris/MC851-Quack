package quack;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Server {

	// Uma instância desta classe armazena o estado do servidor {Quack}.
	// Somente uma instância deve ser criada quando o servidor começa a rodar,
	// e deve ser inicializada com o método {inicialize} abaixo.
	// A instância persiste enquanto o servidor estiver rodando.

	// Essa instância contem, entre outras variáveis de estado do servidor,
	// a tabela de usuários (i.e., contas) existentes na rede {Quack} e a tabela
	// de sessões abertas no momento. No objeto {User} que representa um usuário
	// estão
	// pendurados os seus contatos e as mensagens postadas (ou re-postadas).
	// Usuários, contatos e mensagens são espelhados numa base de dados
	// persistente.
	//
	// SESSÕES E COOKIES
	//
	// Alguns dos métodos abaixo somente podem ser executados por
	// usuários cadastrados na rede {Quack} e atualmente logados.
	// Nesses casos, a sessão de login é identificada por um {cookie}
	// atribuído quando o usuário fez login.
	//
	// Outros métodos, como os que mostram o perfil e as mensagens públicas de
	// algum usuário, podem ser executados por qualquer pessoa, sem necessidade
	// de login. Nesses
	// casos o parâmetro {cookie} pode ser omitido ("").
	//
	// Em todos os métodos, caso seja fornecido o {cookie} de uma sessão
	// válida, a página de resultado geralmente vai conter mais
	// informações e botões, referentes ao usuário {u} que é dono da
	// sessão.
	//
	// DATAHORAS
	//
	// Alguns eventos, como o envio ou re-envio de mensagens, são
	// rotulados com uma /datahora/ (data + hora). Externamente, uma
	// datahora é uma {String} no formato "%Y-%m-%d %H:%M:%S (%Z)" onde
	// "%Z" é o fuso horário, por exemplo "(+0300)" para UTC + 3 horas.
	// As datahoras neste formato externo serão convertidas
	// internamente para um {long int}: um número inteiro
	// de segundos na zona padrão UTC.
	//
	// INTERVALOS DE MENSAGENS
	//
	// Alguns dos métodos a seguir (e em outras interfaces) especificam um
	// trecho de uma lista de mensagens através de parâmetros {startTime},
	// {endTime} (datahoras), e {maxN} (inteiro, positivo ou negativo).
	//
	// Supõe-se que a lista completa de mensagens em questão está ordenada em
	// ordem *decrescente* de datahora (de envio ou re-envio); isto é,
	// mensagens mais recentes no início. O trecho selecionado da lista consiste
	// das mensagens com datahora maior ou igual a {startTime} e menor ou igual a
	// {endTime}, truncadas em no máximo {|maxN|} mensagens.
	//
	// Se {endTime} for omitido ("" ou {null} no formato externo, -1 no formato
	// interno), supõe-se que {endTime} é o fim dos tempos. Se {startTime} for 
	// omitido (idem), supõe-se que {startTime} é o início dos tempos.
	//
	// Se houver mais do que {|maxN|} mensagens nesse intervalo de datahoras,
	// o trecho desejado são apenas as {|maxN|} mensagens mais antigas se {maxN}
	// for negativo, e as {|maxN|} mais recentes se {maxN} for positivo.

	// ----------------------------------------------------------------------
	// INICIALIZAÇÃO

	public void initialize(String dbLoginName, String dbName, String dbPassword);

	// Inicializa uma instância recém-criada de {Server}, inicializando
	// as tabelas de usuários, contatos e mensagens a partir do banco de dados
	// {dbName} como usuário {loginName} com senha de acesso {dbPassword}.
	// Também inicializa a tabela de sessões abertas, incialmente vazia.

	// ----------------------------------------------------------------------
	// ESTADO DO SERVIDOR

	public long getNumUsers();

	public long getNumContacts();

	public long getNumMessages();

	// Retornam os contadores totais dos principais elementos da rede {Quack}.

	public long getNumSessions();

	// Retorna o número de sessões abertas no momento.

	// ----------------------------------------------------------------------
	// PROCESSAMENTO DE PEDIDOS HTTP
	//
	// Os métodos abaixo são ativados pelo servidor HTTP ao receber
	// certos comandos GET ou POST do browser do usuário.
	//
	// Cada método tem parâmetros {request}, {response}, e {context}.
	// O parâmetro {request} é basicamente uma tabelas de pares
	// {chave,valor}, extraídos do commando GET ou POST.
	//
	// O parâmetro {response} é um objeto onde cada método deve 
	// escrever o resultado do GET ou POST, uma página HTML que 
	// é passada ao servidor HTTP e devolvida por este ao usuário.
	// Em caso de erro, geralmente a página contém uma mensagem
	// explicativa.
	//
	// ??{ Descrever o parâmetro {context} e quando {IOException} é levantada. }??

	public void processHomePageReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException;
	
	// Chamado quando o servidor HTTP recebe um pedido de vista da home page da
	// rede {Quack}. 
	//
	// Nessa página haverá botões/campos para fazer login e cadastrar novo
	// usuário, listar as mensagens públicas ou o perfil de qualquer usuário, 
	// busca de mensagens e usuários pelo conteúdo, etc.
	
	public List<User> getAllUsers();
	// Chamado para auxiliar o servidor Quack a montar a página
	// que lista todos os usuários do sistema.
	//
	// retorna uma lista {List<User>} contendo todos os usuarios
	// cadastrados no sistema.

	public void processRegistrationReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException;

	// Chamado quando o servidor HTTP recebe um pedido de cadastramento de novo
	// usuário no {Quack}.
	// 
	// Cria um novo usuário com dados {loginName}, {email}, {fullName} e
	// {password}, que devem estar especificados no parâmetro {request}.
	// Acrescenta-o ao cadastro {this.userTable} (e à base de dados
	// persistente).
	//
	// Por enquanto, o cadastramento não faz o login automático. Se o
	// cadastramento tiver sucesso, devolve uma página em {response} 
	// que mostra o perfil público do usuário.

	public void processLoginReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException, ServletException;

	// Chamado quando o servidor HTTP recebe um pedido de login de um usuário
	// existente da rede {Quack}. 
	//
	// O parâmetro {request} deve conter os campos {loginName} e {password} 
	// que o usuario preencheu na pagina de login.
	//
	// Se o login tiver sucesso, a sessão é criada e acrescentada ao
	// table de sessões abertas. O resultado, devolvido em 
	// {response}, é uma página mostrando o perfil privado do usuario


	public void processLogoutReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException;

	// Chamado quando o servidor HTTP recebe um pedido de logout do usuário.
	// 
	// O parâmetro {request} deve conter um campo {cookie} que identifica a sessão.
	// Essa sessão será fechada.  Falha se o {cookie} não corresponder a uma sessão 
	// atualmente aberta.
	//
	// Em caso de sucesso, o parâmetro {respose} conterá a homepage do sistema {Quack},
	// como vista por usuários não logados.

	public void processShowUserProfileReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException, ServletException;

	// Chamado quando o servidor HTTP recebe um pedido de login de um usuário
	// existente da rede {Quack}. 
	//
	// O parâmetro {request} deve conter os campos {id} do user 
	// que se quer ver a pagina
	//
	// Em caso de sucesso, o parâmetro {respose} conterá a pagina do usuario

	public String processShowPostedMessagesReq(String cookie, String loginName,
			String startTime, String endTime, int maxN);

	// Chamado quando o servidor HTTP recebe um pedido da sessão
	// identificada pelo {cookie} para listar as mensagens enviadas ou
	// reenviadas pelo usuário {loginName}, que não é necessariamente o
	// dono da sessão.
	//
	// Este método pode ser chamado com o {cookie} omitido ("").
	//
	// Em caso de sucesso, o resultado é uma página com as mensagens enviadas ou
	// reenviadas por {loginName}, no intervalo especificado por {startTime},
	// {endTime}, e {maxN}
	// A página terá botões "next {maxN}" "prev {maxN}" para acessar
	// outras mensagens vizinhas a essas.

	
	public void processSendMessageReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException;
	// Chamado quando o servidor Quack recebe um pedido para enviar
	// uma mensagem.
	// 
	// Em {request} devem estar os campos {messageText} que contem
	// a string do corpo da mensagem e o campo opcional {replyLoginName}
	// que contem o destinatário da mensagem.  Deve haver também um campo
	// {cookie} que deve identificar uma sessão aberta. O dono dessa
	// sessão será considerado o autor da mesnagem.
	//
	// Este comando atribui uma datahora UTC à nova mensagem, e a armazena nas
	// tabelas de mensagens. Se {replyLoginName} estiver omitido (""), a
	// mensagem será o início de uma nova conversa. Se {replyLoginName}
	// for especificado, a mensagem será marcada como uma resposta à
	// mensagem enviada ou re-enviada pelo usuário {replyLoginName} com
	// datahora de envio ou reenvio {replyTime}.
	//
	// Em caso de sucesso, coloca no parâmetro {response} uma página dizendo "sua
	// mensagem foi enviada com sucesso" e mostrando o texto formatado
	// da mensagem e sua datahora.
	
	public void processRepostMessageReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException;
	// Chamado quando o servidor Quack recebe um pedido para repostar
	// uma mensagem.
	// 
	// Em {request} deve estar o campos {id} que contem
	// o identificador unico da menasgem a ser reopstada.
	// Deve haver também um campo {cookie} que deve identificar uma
	// sessão aberta.
	// O dono dessa sessão será considerado o autor da nova mesnagem.
	//
	// Este comando atribui uma datahora UTC à nova mensagem, e a armazena nas
	// tabelas de mensagens.
	//
	// Em caso de sucesso, coloca no parâmetro {response} uma página dizendo 
	// "mensagem repostada com sucesso" e mostrando o texto formatado
	// da mensagem e sua datahora.

	public List<Message> processShowReceivedMessagesReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException;

	// Chamado quando o servidor HTTP recebe um pedido da sessão
	// identificada pelo request para listar as mensagens recebidas pelo
	// usuário {w} que é dono da sessão (a /timeline/ do usuário {w}).
	//
	// Em request devem estar definidos {startTime}, {endTime} e {maxN}
	// que definem o intervalo de tempo das mensagens a serem exibidas
	// (por padrao desde o começo dos tempos até o final dos tempos) e define
	// o numero máximo de mensagens a serem exibidas (por padrão 15), respectivamente.
	//
	// A lista de mensagens é colocada no parametro {timelineMessages}
	// da sessão.
	//
	// As mensagens em questão podem incluir as mensagem enviadas e re-enviadas
	// pelos
	// usuários que o usuário {w} segue, mensagens públicas que citam o usuário
	// {w},
	// mensagens privadas dirigidas ao usuário {w}, mensagens em resposta a
	// conversas iniciadas pelo usuário {w}, etc., conforme especificado
	// no perfil do usuário {w}.
	//
	// ??{ No futuro talvez essas escolhas entrem como parâmetros em vez de
	// estarem no perfil. }??
	//
	// Em caso de sucesso, o resultado é uma página com as mensagens em
	// questão, com datahora de envio ou reenvio no intervalo
	// especificado por {startTime}, {endTime}, e {maxN}. A página terá
	// botões "next {maxN}" "prev {maxN}" para acessar outras mensagens
	// vizinhas a essas.

	public void processModifyContactReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException;
	// Chamado quando o servidor HTTP recebe um pedido de alteracao de contato
	// entre dois usuarios do sistema {Quack}.
	//
	// O parâmetro {request} deve especificar uma sessão aberta; o dono {u} da sessão
	// é a origem do contato.  O {request} deve conter também 
	// conter um campo {userName} cujo valor é o loginName do usuário alvo {v},
	// e um campo {follow} cujo valor é o novo estado do contato ("Follow","Block" ou "Inactive")
	// do contato.  
	// ??{ Mudar nomes dos campos: "userName" --> "target", "follow" --> "newStatus" }??
	//
	// Em caso de sucesso, o parâmetro {respose} conterá a pagina inicial do usuario.
	// A alteração é gravada na base de dados persistennte.
	
	public void processShowFollowersReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException;
	// Chamado quando o servidor Quack recebe um pedido para mostrar
	// os seguidores de um usuario do sistema.
	// 
	// Em {request} deve estar o campos {id} que contem
	// o identificador unico do usuario que se quer ver os seguidores.
	// Deve haver também um campo {cookie} que deve identificar uma
	// sessão aberta.
	//
	// Em caso de sucesso, o parâmetro {respose} conterá uma página
	// listando todos os seguidores do usuário cuja idenficiação é {id}
	
	public void processShowFollowsReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException;
	// Chamado quando o servidor Quack recebe um pedido para mostrar
	// os usuarios seguidos por um determinado usuario.
	// 
	// Em {request} deve estar o campos {id} que contem
	// o identificador unico do usuario que se quer ver os seguidos.
	// Deve haver também um campo {cookie} que deve identificar uma
	// sessão aberta.
	//
	// Em caso de sucesso, o parâmetro {respose} conterá uma página
	// listando todos os seguidos pelo usuário cuja idenficiação é {id}

	// -------------------------------------------------------------------------------
	// UTLITÁRIOS

	public User getUserFromCookie(String cookie);
	// Este metodo é chamado quando é necessário recuperar um usuário {User} a partir 
	// de um cookie {String}. A busca pelo cookie sera feita na tabela de cookies
	// que fica salva no servidor Apache, e caso exista retorna o usuario correspondente.
	// Caso contrario retorna {null};

	public User getUserFromLoginName(String loginName);
	// Este metodo é chamado quando é necessário recuperar um usuário {User} a partir 
	// de um loginName {String}. A busca pelo loginName sera feita na tabela de usuários
	// que fica salva na instância de {Server}, e caso exista retorna o usuario correspondente.
	// Caso contrario retorna {null};
	
	
}
