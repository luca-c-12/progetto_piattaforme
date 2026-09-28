package com.example.progetto_piattaforme.security;


import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ConvertitoreJwt implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        return new JwtAuthenticationToken(jwt, ruoli(jwt), jwt.getSubject());
    }

     private Collection<GrantedAuthority> ruoli(@NonNull Jwt jwt) {
        Set<GrantedAuthority> autorita = new HashSet<>();
        Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
        if (realmAccess == null)
            return autorita;
        Object ruoli = realmAccess.get("roles");
         if (ruoli instanceof Collection<?> elenco) {
             if (elenco.contains("ADMIN")) autorita.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
             else if (elenco.contains("CLIENTE")) autorita.add(new SimpleGrantedAuthority("ROLE_CLIENTE"));
         }
        return autorita;
    }
}
