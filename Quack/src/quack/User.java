package quack;

import java.util.*;

public interface User 
{
	// Método para obter o ID de um usuário.
	public long getId();

	// Método para seguir um usuário
	public boolean follow(User followed);
	
	// Método para deixar de seguir um usuário
	public boolean unfollow(User followed);
	
	// Método para mudar mensagem de perfil	
	public boolean changeProfileMsg(String newMsg);
	
	// Método para mudar imagem de perfil
	public boolean changeProfilePic(String filename);
	
	// Método para mudar localização no perfil
	public boolean changeLocation(String newLocation);
	
	// Método para mudar senha de usuario 
	public boolean changePassword(String newPassword);
	
	// Método para mudar email de usuario
	public boolean changeEmail(String newEmail);
	
	// Método para bloquear um usuário
	public boolean block(User user);
	
	// Método para desbloquear um usuário
	public boolean unblock(User user);
	
	// Método para mudar configuração de privacidade
	public boolean changePrivacy(UserPrivacy privacy);
	
	// Método para mudar website no perfil
	public boolean changeWebsite(String newWebsite);
	
	// Método que retorna lista de usuarios seguidos
	public List<User> following();
	
	// Método que retorna lista de usuarios que seguem
	public List<User> followers();
	
	// Método que retorna número de tweets do usuario
	public int tweetCount();
	
	// Método que retorna numero de imagens postadas
	public int mediaCount();
	
	// Método que retorna numero de usuarios seguidos
	public int followingCount();

	// Método que retorna número de usuarios seguidores
	public int followerCount();

	// Método que retorna numero de tweets favoritos
	public int favoriteCount();
	
	// Método que retorna uma lista de mensagens do usuário
	public List<Message> getMessages(int start, int end);
	
	// Método que retorna uma lista de mensagens dos usuários seguidos
	public List<Message> getFollowingMessages(int start, int end);
	
}
