package com.github.zhangkaitao.shiro.chapter12.service;

import com.github.zhangkaitao.shiro.chapter12.credentials.RetryLimitHashedCredentialsMatcher;
import com.github.zhangkaitao.shiro.chapter12.entity.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 14-1-28
 * <p>
 * Version: 1.0
 */
public class PasswordHelper {
//	@Autowired
//	private RetryLimitHashedCredentialsMatcher credentialsMatcher;
	private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
	private String algorithmName = "md5";
	private int hashIterations = 2;

	public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
		this.randomNumberGenerator = randomNumberGenerator;
	}

	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}

	public void setHashIterations(int hashIterations) {
		this.hashIterations = hashIterations;
	}

	public void encryptPassword(User user) {

		user.setSalt(randomNumberGenerator.nextBytes().toHex());
//		user.setPassword(credentialsMatcher.toPwd(user.getPassword().toCharArray(),
//				ByteSource.Util.bytes(user.getCredentialsSalt()), credentialsMatcher.getHashIterations()));
		String newPassword = new SimpleHash(algorithmName, user.getPassword(),
				ByteSource.Util.bytes(user.getCredentialsSalt()), hashIterations).toHex();
		user.setPassword(newPassword);
	}
}