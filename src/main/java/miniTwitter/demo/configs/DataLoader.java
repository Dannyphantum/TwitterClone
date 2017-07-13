package miniTwitter.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import miniTwitter.demo.models.Role;
import miniTwitter.demo.models.User;
import miniTwitter.demo.repositories.RoleRepository;
import miniTwitter.demo.repositories.UserRepository;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner{

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... strings) throws Exception {
        System.out.println("Loading data . . .");

        roleRepository.save(new Role("USER"));
        roleRepository.save(new Role("ADMIN"));

        Role adminRole = roleRepository.findByRole("ADMIN");
        Role userRole = roleRepository.findByRole("USER");

        User user = new User("bob@bob.com","bob","Bob","Bobberson", true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList(userRole));
        userRepository.save(user);

        user = new User("jim@jim.com","jim","Jim","Jimmerson", true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList(userRole));
        userRepository.save(user);

        user = new User("admin@secure.com","password","Admin","User", true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList(adminRole));
        userRepository.save(user);

        user = new User("sam@every.com","password","Sam","Everyman", true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList(userRole, adminRole));
        userRepository.save(user); 
  }
  
}

