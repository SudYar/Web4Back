package ru.itmo.security;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Service;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class RegistrationFilter extends AnonymousAuthenticationFilter {
    @Setter @Autowired
    private JwtTokenRepository repository;

    /*@Autowired
    public void setRepository(JwtTokenRepository repository){
        this.repository = repository;
    }*/

    /*@Autowired
    public RegistrationFilter(String key, Object principal, List<GrantedAuthority> authorities, JwtTokenRepository repository) {
        super(key, principal, authorities);
        this.repository = repository;
    }*/

    public RegistrationFilter(String key) {
        super(key);
    }

    public RegistrationFilter(String key, Object principal, List<GrantedAuthority> authorities) {
        super(key, principal, authorities);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            //create token

            CsrfToken csrfToken = repository.generateToken();
            repository.saveToken(csrfToken, (HttpServletRequest) req, (HttpServletResponse) res);

            SecurityContextHolder.getContext().setAuthentication(this.createAuthentication((HttpServletRequest)req));
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Populated SecurityContextHolder with new token: '" + SecurityContextHolder.getContext().getAuthentication() + "'");
            }
        } else if (this.logger.isDebugEnabled()) {
            this.logger.debug("SecurityContextHolder not populated with new token, as it already contained: '" + SecurityContextHolder.getContext().getAuthentication() + "'");
        }

        chain.doFilter(req, res);
    }
}
