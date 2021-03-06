package com.prueba.backend.teopc.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class AutorizationServerConfig extends AuthorizationServerConfigurerAdapter{

	@Autowired
	private BCryptPasswordEncoder contraseniaEncriptada;
	
	@Autowired
	//se puede especificar el  bean que queremos inyectar en caso de tener mas instancias en el programa	
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;

	@Autowired
	private InfoAdicionalToken infoAditionalToken;

	//metodo encargado de gener token y valida	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		//unir informacion de token
		TokenEnhancerChain  tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAditionalToken,accessTokenConverter()));
		
		endpoints.authenticationManager(authenticationManager)
		.accessTokenConverter(accessTokenConverter())
		.tokenEnhancer(tokenEnhancerChain);
	}
	
	
	
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		//llave secreta del token que corresponde a la aplicacion, o puede ser el firmante que es la llave privada 		
		jwtAccessTokenConverter.setSigningKey(JwtConfig.RSA_PRIVATE);
		//llave que valida, es la llave publica que generamos con el RSA
		jwtAccessTokenConverter.setVerifierKey(JwtConfig.RSA_PUBLICA);
		return jwtAccessTokenConverter;
	}

	//los permisos de los endPoin
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		//permit all es para que todos puedan autorizarse, login puedan intentar entrar		
		security.tokenKeyAccess("permitAll()")
		//endpoint que verifica el token y su firma		
		.checkTokenAccess("isAuthenticated()");
	}

	//se configuran los clientes, un cliente es el front que utiliza esta seguridad,	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("angularapp")
		.secret(contraseniaEncriptada.encode("12345"))
		.scopes("read","write")
		//refresh token es para que no toque inciiar sesiion cada que se refresque
		.authorizedGrantTypes("password","refresh_token")
		//tiempo de caducidad del token
		.accessTokenValiditySeconds(3600)
		.refreshTokenValiditySeconds(3600);
	}
}
