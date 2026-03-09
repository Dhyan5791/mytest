package com.hitachi.assessment.security;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hitachi.assessment.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	private final JwtService jwtService;

	public JwtAuthFilter(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = authHeader.substring(7);

		try {
			var claims = jwtService.parseClaims(token);
			Integer userId = claims.get("userId", Integer.class);
			String username = claims.getSubject();
			Object rolesObj = claims.get("roles");

			List<String> rolesList = (List<String>) rolesObj;
			if (rolesList == null) {
				rolesList = Collections.emptyList();
			}

			Set<String> roles = new HashSet<>(rolesList);

			var authorities = roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r)).toList();

			AuthUserPrincipal principal = new AuthUserPrincipal(userId, username, roles);

			var auth = new UsernamePasswordAuthenticationToken(principal, null, authorities);

			auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(auth);

		} catch (Exception e) {
			System.out.println("Exception di JwtAuthFilter");
		}

		filterChain.doFilter(request, response);
	}
}
