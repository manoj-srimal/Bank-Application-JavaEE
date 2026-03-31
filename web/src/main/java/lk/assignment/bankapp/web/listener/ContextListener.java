package lk.assignment.bankapp.web.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lk.assignment.bankapp.core.provider.MailServiceProvider;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        MailServiceProvider.getInstance().start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        MailServiceProvider.getInstance().shutdown();
    }
}
