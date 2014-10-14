package tests;

public interface ServerTest {
	// essa classe define os testes automaticos que deverao ser executados em
	// instancias de Server. Cada teste tera suas proprias assertivas, e a
	// interface do JUnit indicara quais testes deram certo, e quais deram
	// errado.

	public void testLogin();
	// testa o login: regista um usuario novo, realiza o login com ele, e
	// verificia se no servidor existe pelo menos um usuario e pelo menos uma
	// sessao.
	
	public void testLogout();
	// testa o logout: regista um usuario novo, realiza o login com ele, realiza 
	// o logout do usuario e verificia se no servidor nao existe nenhuma sessao 
	// aberta.

	public void testSentMessage();
	// testa o login: regista um usuario novo, realiza o login com ele, envia
	// uma mensagem e verifica se existe pelo menos uma mensagem enviada.
}
