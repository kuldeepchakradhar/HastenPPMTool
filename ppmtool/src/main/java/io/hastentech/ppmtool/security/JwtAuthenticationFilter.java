package io.hastentech.ppmtool.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.hastentech.ppmtool.domain.User;
import io.hastentech.ppmtool.service.CustomUserServiceDetails;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	
	@Autowired
	private JwtTokenProvider jwttokenProvider;
	
	@Autowired
	private CustomUserServiceDetails customUserServiceDetails;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			String jwt = getJwtFromRequest(request);
			
			
			if(StringUtils.hasText(jwt)&&jwttokenProvider.validateToken(jwt)) {
				Long userId = jwttokenProvider.getUserIdFromJwt(jwt);
				User userDetails = customUserServiceDetails.loadUserById(userId);
				
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails,null, new ArrayList<>());
				
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Could not set user authentication ", e);
		}
		
		filterChain.doFilter(request, response);
		
		
	}
	
	public String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader(SecurityConstants.HEADER_STRING);
		
		if(StringUtils.hasText(bearerToken)&& bearerToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			return bearerToken.substring(7, bearerToken.length());
		}
		
		return null;
	}

}
