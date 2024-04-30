package com.app3.Patient;

import com.app3.Patient.dao.PatientRepo;
import com.app3.Patient.entities.Patient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class PatientApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientApplication.class, args);
	}

	@Bean
	public CommandLineRunner start(PatientRepo patientRepo) {
		return args -> {
			patientRepo.save(new Patient(null, "Dupont", "Toto", new Date(), 111, false));
			patientRepo.save(Patient.builder().nom("Durand").prenom("Titi").score(125).build());
			patientRepo.save(Patient.builder().nom("Zidane").prenom("Zizou").score(154).build());
			patientRepo.save(Patient.builder().nom("Rinner").prenom("Tedy").score(144).build());

			List<Patient> patients = patientRepo.findAll();
			patients.forEach(patient -> System.out.println(patient.toString()));
		};
	}

	// On crée ici le bean qui se chargera d'encoder (BCrypt) les password avec Spring Security
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	//@Bean
	CommandLineRunner commandLineRunnerJdbcUsers(JdbcUserDetailsManager jdbcUserDetailsManager) {
		PasswordEncoder passwordEncoder = passwordEncoder();
		return args -> {
			jdbcUserDetailsManager.createUser(User.withUsername("user1").password(passwordEncoder().encode("1234")).roles("USER").build());
			jdbcUserDetailsManager.createUser(User.withUsername("user2").password(passwordEncoder().encode("1234")).roles("USER").build());
			jdbcUserDetailsManager.createUser(User.withUsername("admin").password(passwordEncoder().encode("1234")).roles("USER", "ADMIN").build());
		};
	}
}
