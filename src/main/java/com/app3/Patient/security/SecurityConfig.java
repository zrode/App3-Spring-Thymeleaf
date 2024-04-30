package com.app3.Patient.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration // indique à Spring que c'est une classe de configuration qui sera traité en priorité au démarrage du projet
@EnableWebSecurity // on active les sécurités Web
public class SecurityConfig {

    // On doit créer 2 beans : 1 stratégie d'authentification et 1 filtre selon le cas d'utilisation

    // Ce bean est pour la stratégie Jdbc authentication
    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        return jdbcUserDetailsManager;

    }

    //Ce bean est pour la stratégie in memory authentication
    //@Bean
    public InMemoryUserDetailsManager memoryUser(PasswordEncoder passwordEncoder) {
        // On va utiliser un mot de passe simple et l'encoder sinon ça génèrera une exception
        String encodedPassword = passwordEncoder.encode("1234");
        // on spécifie ici les utilisateurs qui ont le droit d'accéder à l'appli (username + password + role)
        return new InMemoryUserDetailsManager(
                User.withUsername("user1").password(encodedPassword).roles("USER").build(),
                User.withUsername("user2").password(encodedPassword).roles("USER").build(),
                User.withUsername("admin").password(encodedPassword).roles("USER", "ADMIN").build()
        );
    }

    // On crée le filtre de sécurité qui va intercepter les requêtes avant de les envoyer à DispatcherServlet
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // on utilise formLogin() pour utiliser un formulaire d'authentification
        // on va protéger nos ressources : ici on va autoriser seulement l'Admin à supprimer des patients
        // la 3e ligne authorizeHttpRequests... est pour dire que toutes les requêtes nécessiteront une authentification

        return httpSecurity
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(ar->ar.requestMatchers("/admin/deletePatient/**", "/admin/editPatient/**", "/admin/formPatient/**").hasRole("ADMIN"))
                .authorizeHttpRequests((ar->ar.anyRequest().authenticated()))
                .exceptionHandling((exception)->exception.accessDeniedPage("/notAuthorized"))
                .build();
    }

}
