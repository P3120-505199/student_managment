package ru.itmo.weblab3.bootstrap;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import ru.itmo.weblab3.config.ConfigKeys;
import ru.itmo.weblab3.config.ConfigProvider;
import ru.itmo.weblab3.service.UserService;

import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Startup
public class AdminSeeder {

    private static final Logger LOG = Logger.getLogger(AdminSeeder.class.getName());

    @Inject
    UserService userService;

    @PostConstruct
    public void init() {
        String username = ConfigProvider.getOrDefault(ConfigKeys.ADMIN_USERNAME, null);
        String password = ConfigProvider.getOrDefault(ConfigKeys.ADMIN_PASSWORD, null);
        String email = ConfigProvider.getOrDefault(ConfigKeys.ADMIN_EMAIL, null);

        if (username == null || password == null || email == null) {
            LOG.info("Admin seeding skipped: ADMIN_USERNAME/ADMIN_PASSWORD/ADMIN_EMAIL not set");
            return;
        }
        try {
            userService.createAdminIfNotExists(username, email, password);
            LOG.info("Admin user ensured: " + username);
        } catch (Exception ex) {
            LOG.log(Level.WARNING, "Admin seeding failed", ex);
        }
    }
}