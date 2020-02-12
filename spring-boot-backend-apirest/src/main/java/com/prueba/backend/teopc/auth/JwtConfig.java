package com.prueba.backend.teopc.auth;

//clase que contiene la llave privada y publica de RSA para ser agregada al token y tener mas seguridad
public class JwtConfig {

	//CLAVE SECRETA DE LA APLICACION EN EL MOMENTO DE GENERAR EL TOKEN CON LOS DEMAS DATOS -clave mac-	
	public static final String LLAVE_SECRETA = "alguna.clave.secreta.12345678";
	
	public static final String RSA_PRIVATE = "-----BEGIN RSA PRIVATE KEY-----\n" + 
			"MIIEpAIBAAKCAQEAwCZayDzx9w/f92l6zuJIDinLStiFxg7OAQ7MMOuN2Up+pCvO\n" + 
			"oswAi9whKIO0cnCRgXOtOXAlTCAifCzMX/qtYC/dLZBhGVD327fLjLSIYlN+7aAC\n" + 
			"/4HOgHsxouz8mmuZILxnW66CfItiBfnozt5trd+kKcjI683fA6O8TTsacYkODAQl\n" + 
			"OMotJaY3JRcTfdAfqiCQwaQRqPwmpk9q3/m/datPyYgIc5n0z3qOac/vqm+80RC+\n" + 
			"t+nipSFl9BQlFX3PJIPYDGq8mRrKE86HjFPgDqeKIm9Dg3HCBQmBVY2qIlVKYBcx\n" + 
			"oxyi6rfLI13QnGN4OGu+xqSSvh8uA6vxKJHUMQIDAQABAoIBAQCpHpj0eL+25mAH\n" + 
			"0OzoPj2RSbPGMoC7a7hhMfcCb7CvuWNxd6UtoEQy1RLSJ+mgd/d5aqjiTxRkxjvT\n" + 
			"81IGgAotCLOxMMxljeA5oLElLJiFglvLJfS9kjCA4O01K0TBXUavi2OlTBlJKRyj\n" + 
			"iewgd6EtUhOZO0GsWcyZ1XkcuJJzrdeNfd6oKcMkrnReNdSgUISVEpKbbnCbTRVK\n" + 
			"zjJQNqxMqDS7gYgtO31YhammFgvwGttPVRDiVzqOw1uWWunbK11fNR3kqPm4J7fS\n" + 
			"QL8+erkm4Ck4OAEFKlEqJjzDhdrhkESdHJql4re6yoY7D/4EKJYQT+wVFfQ5pT7A\n" + 
			"JJGRcOzhAoGBAPLFX8k58qxp8Ye+4uWKm1Wpu9w11zg77LYnf5T6KahioXm4Uws2\n" + 
			"NDSWJKYVcNw1LmKhRHXvhiu49r9SEd3JaS7XX43YoLZ8ZeMWIfTvPRDYpJG4POxA\n" + 
			"fLzE6hZC7yHuHEvuLrXUYNJoTdDWL76vliBmfg7IHDdws9GMTv21/LS9AoGBAMqe\n" + 
			"0j+5dpGB4z7ND5sJ3LlKFDL2L7HyEoRRB8lWA1/10w3/bTsGJzTLtZoGPnSm/ojS\n" + 
			"WyND9tWd3FWMr5hj6GuHi5rDn/4+JV+XulMChKeZGaqha1RswiGpSo2Dfu5Ikh6d\n" + 
			"gL0yXQ/sbkl9BaABquJrMd09rZq+Jm9yR69LsYaFAoGBAJ/AzhVmspBObwf6zuhp\n" + 
			"22n1dY+lRmjU1iHPwB7St9L8hHHKn6sDEjuAicFY3pmpzUpOAPTyl4HyBz1Czkr6\n" + 
			"HOMcCt8WrDsphQp4KAQ74h1R0ompiT2V+f9mPPxFM4NKYwh8RfSVOtl6lapVJ7n9\n" + 
			"ZXcQuucTO64n1cyQ3fIKba6pAoGABVVK2WyLR2cw0Q1atMz8p0/g2jqKpNdnT15G\n" + 
			"ozEFY7g3tu2bNWuDCqu1+vjhGfbvSjCnP3VhgNvn2cSbNpqimnn13bv6cRm3qB15\n" + 
			"RQgS78pi14/qIE3AilxHXxQv+huk5PNmWRSDSJEDkiGdTUnd8pm5tUYTVLhekJ5k\n" + 
			"bqPCZ3UCgYBK3lorHHb3QrBXvUWxshW4yt1x29COsew4t0Vkwq5zEhC3D2Qit8CK\n" + 
			"dlg/2QYAithcKGO6WfU+IKIBSIEQN0pOoRk9ZevKe+ZvS5J+ziyWzDFtT2/uhAwV\n" + 
			"huS4p3nLMV4E9PEplfjgRgjDgpqsXFDMyvRw1HqjO5jEDoiZJERYVA==\n" + 
			"-----END RSA PRIVATE KEY-----";
	public static final String RSA_PUBLICA = "-----BEGIN PUBLIC KEY-----\n" + 
			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwCZayDzx9w/f92l6zuJI\n" + 
			"DinLStiFxg7OAQ7MMOuN2Up+pCvOoswAi9whKIO0cnCRgXOtOXAlTCAifCzMX/qt\n" + 
			"YC/dLZBhGVD327fLjLSIYlN+7aAC/4HOgHsxouz8mmuZILxnW66CfItiBfnozt5t\n" + 
			"rd+kKcjI683fA6O8TTsacYkODAQlOMotJaY3JRcTfdAfqiCQwaQRqPwmpk9q3/m/\n" + 
			"datPyYgIc5n0z3qOac/vqm+80RC+t+nipSFl9BQlFX3PJIPYDGq8mRrKE86HjFPg\n" + 
			"DqeKIm9Dg3HCBQmBVY2qIlVKYBcxoxyi6rfLI13QnGN4OGu+xqSSvh8uA6vxKJHU\n" + 
			"MQIDAQAB\n" + 
			"-----END PUBLIC KEY-----";
	
}
