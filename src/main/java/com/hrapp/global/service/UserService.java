package com.hrapp.global.service;

import com.hrapp.global.config.Constants;
import com.hrapp.global.dto.AdminUserDto;
import com.hrapp.global.dto.UserDto;
import com.hrapp.global.entity.Authority;
import com.hrapp.global.entity.User;
import com.hrapp.global.exception.EmailAlreadyUsedException;
import com.hrapp.global.exception.InvalidPasswordException;
import com.hrapp.global.exception.UsernameAlreadyUsedException;
import com.hrapp.global.repository.AuthorityRepo;
import com.hrapp.global.repository.UserRepository;
import com.hrapp.global.security.AuthoritiesConstants;
import com.hrapp.global.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing users.
 */
@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final AuthorityRepo authorityRepo;

	private final CacheManager cacheManager;


	//Account
	public Optional<User> activateRegistration(String key) {
		log.debug("Activating user for activation key {}", key);
		return userRepository.findOneByActivationKey(key).map(user -> {
			user.setActivated(true);
			user.setActivationKey(null);
			this.clearUserCaches(user);
			log.debug("Activated user: {}", user);
			return user;
		});
	}

	//Account
	public Optional<User> completePasswordReset(String newPassword, String key) {
		log.debug("Reset user password for reset key {}", key);
		return userRepository.findOneByResetKey(key)
				.filter(user -> user.getResetDate().isAfter(Instant.now().minus(1, ChronoUnit.DAYS))).map(user -> {
					user.setPassword(passwordEncoder.encode(newPassword));
					user.setResetKey(null);
					user.setResetDate(null);
					this.clearUserCaches(user);
					return user;
				});
	}

	//Account
	public Optional<User> requestPasswordReset(String mail) {
		log.debug("Searching for email: {}", mail);
		return userRepository.findOneByEmailIgnoreCase(mail).filter(User::isActivated).map(user -> {
			log.debug("User found and activated: {}", user.getEmail());
			user.setResetKey(RandomUtil.generateResetKey().substring(0, 20));
			user.setResetDate(Instant.now());
			this.clearUserCaches(user);
			return user;
		});
	}
	
//Account
	public User registerUser(AdminUserDto userDTO, String password) {
		userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).ifPresent(existingUser -> {
			boolean removed = removeNonActivatedUser(existingUser);
			if (!removed) {
				throw new UsernameAlreadyUsedException();
			}
		});
		userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).ifPresent(existingUser -> {
			boolean removed = removeNonActivatedUser(existingUser);
			if (!removed) {
				throw new EmailAlreadyUsedException();
			}
		});

		User newUser = new User();
		String encryptedPassword = passwordEncoder.encode(password);
		newUser.setLogin(userDTO.getLogin().toLowerCase());
		// new user gets initially a generated password
		newUser.setPassword(encryptedPassword);
		newUser.setFirstName(userDTO.getFirstName());
		newUser.setLastName(userDTO.getLastName());
		if (userDTO.getEmail() != null) {
			newUser.setEmail(userDTO.getEmail().toLowerCase());
		}

		if (userDTO.getLangKey() == null) {
			newUser.setLangKey(Constants.DEFAULT_LANGUAGE);
		} else {
			newUser.setLangKey(userDTO.getLangKey());
		}

		newUser.setImageUrl(userDTO.getImageUrl());
		// new user is not active
		newUser.setActivated(false);
		// new user gets registration key
		newUser.setActivationKey(RandomUtil.generateActivationKey().substring(0, 20));
		Set<Authority> authorities = new HashSet<>();
		
		authorityRepo.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
		newUser.setAuthorities(authorities);
		userRepository.save(newUser);
		this.clearUserCaches(newUser);
		log.debug("Created Information for User: {}", newUser);
		return newUser;
	}
//system
	private boolean removeNonActivatedUser(User existingUser) {
		if (existingUser.isActivated()) {
			return false;
		}
		userRepository.delete(existingUser);
		userRepository.flush();
		this.clearUserCaches(existingUser);
		return true;
	}

	//Admin
	public User createUser(AdminUserDto userDTO) {
		User user = new User();
		user.setLogin(userDTO.getLogin().toLowerCase());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		if (userDTO.getEmail() != null) {
			user.setEmail(userDTO.getEmail().toLowerCase());
		}
		if (userDTO.getLangKey() == null) {
			user.setLangKey(Constants.DEFAULT_LANGUAGE);
		} else {
			user.setLangKey(userDTO.getLangKey());
		}

		user.setImageUrl(userDTO.getImageUrl());
		String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
		user.setPassword(encryptedPassword);
		user.setResetKey(RandomUtil.generateResetKey().substring(0, 20));
		user.setResetDate(Instant.now());
		user.setActivated(true);
		if (userDTO.getAuthorities() != null) {
			Set<Authority> authorities = userDTO.getAuthorities().stream().map(authorityRepo::findById)
					.filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());
			user.setAuthorities(authorities);
		}
		userRepository.save(user);
		this.clearUserCaches(user);
		log.debug("Created Information for User: {}", user);
		return user;
	}

	/**
	 * Update all information for a specific user, and return the modified user.
	 *
	 * @param userDTO user to update.
	 * @return updated user.
	 */
	//Admin
	public Optional<AdminUserDto> updateUser(AdminUserDto userDTO) {
		return Optional.of(userRepository.findById(userDTO.getId())).filter(Optional::isPresent).map(Optional::get)
				.map(user -> {
					this.clearUserCaches(user);
					user.setLogin(userDTO.getLogin().toLowerCase());
					user.setFirstName(userDTO.getFirstName());
					user.setLastName(userDTO.getLastName());
					if (userDTO.getEmail() != null) {
						user.setEmail(userDTO.getEmail().toLowerCase());
					}
					user.setLangKey(userDTO.getLangKey());
					user.setImageUrl(userDTO.getImageUrl());
					user.setActivated(userDTO.isActivated());
					Set<Authority> managedAuthorities = user.getAuthorities();
					managedAuthorities.clear();
					userDTO.getAuthorities().stream().map(authorityRepo::findById).filter(Optional::isPresent)
							.map(Optional::get).forEach(managedAuthorities::add);
					this.clearUserCaches(user);
					log.debug("Changed Information for User: {}", user);
					return user;
				}).map(AdminUserDto::new);
	}

	//Admin
	public void deleteUser(String login) {
		userRepository.findOneByLogin(login).ifPresent(user -> {
			userRepository.delete(user);
			this.clearUserCaches(user);
			log.debug("Deleted User: {}", user);
		});
	}

	/**
	 * Update basic information (first name, last name, email, language) for the current user.
	 *
	 * @param firstName first name of user.
	 * @param lastName  last name of user.
	 * @param email     email id of user.
	 * @param langKey   language key.
	 * @param imageUrl  image URL of user.
	 */
	//Account
	public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
		SecurityUtils
				.getCurrentUserLogin()
				.flatMap(userRepository::findOneByLogin)
				.ifPresent(user -> {
					user.setFirstName(firstName);
					user.setLastName(lastName);
					if (email != null) {
						user.setEmail(email.toLowerCase());
					}
					user.setLangKey(langKey);
					user.setImageUrl(imageUrl);
					this.clearUserCaches(user);
					log.debug("Changed Information for User: {}", user);
				});
	}


	//Account
	@Transactional
	public void changePassword(String currentClearTextPassword, String newPassword) {
		SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin).ifPresent(user -> {
			String currentEncryptedPassword = user.getPassword();
			if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
				throw new InvalidPasswordException();
			}
			String encryptedPassword = passwordEncoder.encode(newPassword);
			user.setPassword(encryptedPassword);
			this.clearUserCaches(user);
			log.debug("Changed password for User: {}", user);
		});
	}

	//Admin
	@Transactional(readOnly = true)
	public Page<AdminUserDto> getAllManagedUsers(Pageable pageable) {
		return userRepository.findAll(pageable).map(AdminUserDto::new);
	}

	//Admin
	@Transactional(readOnly = true)
	public Page<UserDto> getAllPublicUsers(Pageable pageable) {
		return userRepository.findAllByIdNotNullAndActivatedIsTrue(pageable).map(UserDto::new);
	}

	//Admin
	@Transactional(readOnly = true)
	public Optional<User> getUserWithAuthoritiesByLogin(String login) {
		return userRepository.findOneWithAuthoritiesByLogin(login);
	}

	//Account
	@Transactional(readOnly = true)
	public Optional<User> getUserWithAuthorities() {
		return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
	}

	@Scheduled(cron = "0 0 1 * * ?")
	public void removeNotActivatedUsers() {
		userRepository.findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(
				Instant.now().minus(30, ChronoUnit.DAYS)).forEach(user -> {
					log.debug("Deleting not activated user {}", user.getLogin());
					userRepository.delete(user);
					this.clearUserCaches(user);
				});
	}

	/**
	 * Gets a list of all the authorities.
	 * 
	 * @return a list of all the authorities.
	 */

	//Admin
	@Transactional(readOnly = true)
	public List<String> getAuthorities() {
		return authorityRepo.findAll().stream().map(Authority::getName).collect(Collectors.toList());
	}

	private void clearUserCaches(User user) {
		Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
		if (user.getEmail() != null) {
			Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
		}
	}



}

/**
 * Utility class for generating random values.
 */
class RandomUtil {

	private static final SecureRandom RANDOM = new SecureRandom();
	private static final int DEFAULT_LENGTH = 20;

	public static String generateRandomKey(int length) {
		byte[] randomBytes = new byte[length];
		RANDOM.nextBytes(randomBytes);
		return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
	}

	public static String generateResetKey() {
		return generateRandomKey(DEFAULT_LENGTH);
	}

	public static String generateActivationKey() {
		return generateRandomKey(DEFAULT_LENGTH);
	}

	public static String generatePassword() {
		return generateRandomKey(16);
	}
}
