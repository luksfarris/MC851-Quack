package tests;

public interface ServerTest {
	// essa classe define os testes automaticos que deverao ser executados em
	// instancias de Server. Cada teste tera suas proprias assertivas, e a
	// interface do JUnit indicara quais testes deram certo, e quais deram
	// errado.

	public void testLogin();
	// testa o login: registra um usuario novo, realiza o login com ele, e
	// verifica se no servidor existe pelo menos um usuario e pelo menos uma
	// sessao.
	
	public void testLogout();
	// testa o logout: registra um usuario novo, realiza o login com ele, realiza 
	// o logout do usuario e verifica se no servidor nao existe nenhuma sessao 
	// aberta.

	public void testSentMessage();
	// testa o login: registra um usuario novo, realiza o login com ele, envia
	// uma mensagem e verifica se existe pelo menos uma mensagem enviada.
	
	public void testFollowUser();
	// testa o follow: registra 2 usuarios novos, realiza login com um deles,
	// adiciona o contato de follow, verifica se o contato foi adicionado nos dois
	
	public void testBlockUser();
	// testa o follow: registra 2 usuarios novos, realiza login com um deles,
	// adiciona o contato de block, verifica se o contato foi adicionado nos dois
	
}
