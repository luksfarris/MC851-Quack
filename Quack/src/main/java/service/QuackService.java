package service;

import javax.servlet.ServletContext;

import quack.Server;
import quack.ServerImpl;

public final class QuackService {
	// Esta classe não pode ser instanciada, e possui um método 
	// estático que é usado para pegar a instância de server que é
	// compartilhada entre TODOS os clientes. A instância de server é a
	// mesma, e durará enquanto o servidor de aplicação Tomcat existir.
	// Na primeira vez que ela for requisitada, ela precisará inicializar,
	// este processo pode demorar muito tempo, por isso a página /ServerInit
	// pode ser usada para inicializar o servidor.
	
	//http://stackoverflow.com/questions/3106452/how-do-servlets-work-instantiation-session-variables-and-multithreading
	//http://stackoverflow.com/questions/20190070/thread-safety-of-servletcontext-objects
	//http://stackoverflow.com/questions/2185951/how-do-i-keep-a-user-logged-into-my-site-for-months
	
	private static final String SERVER_CONTEXT_NAME = "QUACK_SERVER";
	
	public static Server getServer (ServletContext context) {
		Server sharedServer = (Server) context.getAttribute(SERVER_CONTEXT_NAME);
		if (sharedServer == null) {
			// Primeira vez executando, inicializa o server quack.
			sharedServer = new ServerImpl();
			context.setAttribute(SERVER_CONTEXT_NAME, sharedServer);
			// inicializa o banco de dados
			sharedServer.initialize("mc851u4", "mc851db4", "ohjairah", context);
		}
		return sharedServer;
	}
}
