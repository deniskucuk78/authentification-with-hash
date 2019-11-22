package fr.gtm.hello;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface ClientDao extends JpaRepository<User, Long> {
	
	
	//Optional<User> findByNomAndPassword(String nom, String password);
	
	
	@Query(value ="Select * From Users u WHERE u.user = ?1 and u.digest =?2", nativeQuery = true)
	Optional<User> findByNomAndHashPassword(String nom, String password);
	
	@Query(value ="Select u.digest From Users u WHERE u.user", nativeQuery = true)
	String  findDigestByNom(String nom);
	
	Optional<User> findByNom(String nom);
	
	
	//Il faut mettre l'annotation @Modifying car lorsque l'on effectue un Insert ou Update, la partie JDBC s'attend en retour au nbre de colonnes/lignes impactées.. Or on ne veut rien
	
	
	@Transactional
	@Modifying 
	@Query(value ="INSERT INTO Users (user, password, role, digest) VALUES(?1, 'aaaaaaaaaaa', 'admin', SHA2(?2,256))", nativeQuery = true)
	void createNewUser(String nom, String password);
	

}
