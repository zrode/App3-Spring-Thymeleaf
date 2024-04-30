Petite app qui gère des patients.

Spring boot + Rendu côté Serveur en utilisant Thymeleaf et Bootstrap pour la mise en forme

J'ai utilisé une bdd H2 puis j'ai basculé vers PostgreSQL sous Ubuntu que je gère avec phpPgAdmin.

Dépendances du projet :<br>
Web<br>
JPA<br>
H2 Database<br>
Postgresql<br>
Lombok<br>
Webjars (Bootstrap)<br>
Thymeleaf<br>
Thymeleaf Layout Dialect <br>
Spring Boot Validation <br>
MAJ -> Spring Security <br>
MAJ -> tyhmeleaf-extras-springsecurity6 (pour la sécu côté front vu qu'on utilise Thymeleaf dans cette app)

MAJ : J'ai ajouté une couche sécurité.
J'ai donc ajouté une classe SecurityConfig dans laquelle j'ai déclaré 2 beans (1 pour la stratégie d'authentification et 1 filtre)
Concernant la stratégie d'authentification, j'ai exposé les 2 cas possibles d'où les 2 méthodes dans SecurityConfig