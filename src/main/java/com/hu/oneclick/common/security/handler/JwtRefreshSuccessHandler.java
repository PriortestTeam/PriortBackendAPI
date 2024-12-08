package com.hu.oneclick.common.security.handler;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.hu.oneclick.model.domain.dto.AuthLoginUser;
import com.hu.oneclick.common.security.JwtAuthenticationToken;
import com.hu.oneclick.common.security.service.JwtUserServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

/**
 * @author qingyang
 */
@Component
public class JwtRefreshSuccessHandler implements AuthenticationSuccessHandler {
	/**
	 * 刷新间隔5分钟
	 */
	private static final int TOKEN_REFRESH_INTERVAL = 300;

	private final JwtUserServiceImpl jwtUserServiceImpl;

	public JwtRefreshSuccessHandler(JwtUserServiceImpl jwtUserServiceImpl) {
		this.jwtUserServiceImpl = jwtUserServiceImpl;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        DecodedJWT jwt = ((JwtAuthenticationToken) authentication).getToken();
        AuthLoginUser user = (AuthLoginUser) authentication.getPrincipal();
        boolean shouldRefresh = shouldTokenRefresh(jwt.getIssuedAt(), jwt.getExpiresAt());

        if (shouldRefresh) {
            // Generate a new token with project information and add it to the response header
            String newToken = jwtUserServiceImpl.saveUserLoginInfo(user);
            response.setHeader("Authorization", newToken);
        }
	}

	protected boolean shouldTokenRefresh(Date issueAt, Date expiryAt){
        LocalDateTime issueTime = LocalDateTime.ofInstant(issueAt.toInstant(), ZoneId.systemDefault());
        LocalDateTime expiryTime = LocalDateTime.ofInstant(expiryAt.toInstant(), ZoneId.systemDefault());

        return LocalDateTime.now().minusSeconds(TOKEN_REFRESH_INTERVAL).isAfter(issueTime) ||
            LocalDateTime.now().isAfter(expiryTime);
    }

}
