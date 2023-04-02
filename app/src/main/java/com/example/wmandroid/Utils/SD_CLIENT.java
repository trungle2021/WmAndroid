@Override
public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

    ClientHttpResponse response = execution.execute(request, body);

    if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
        try {
            // Make a call to the /refreshtoken endpoint to get a new access token
            ResponseEntity<TokenResponse> tokenResponse = restTemplate.exchange("/refreshtoken", HttpMethod.POST, new HttpEntity<>(refreshToken), TokenResponse.class);

            // Set the new access token in the Authorization header
            String accessToken = tokenResponse.getBody().getAccessToken();
            headers.setBearerAuth(accessToken);

            // Add or update the refresh token cookie
            List<String> cookieHeaders = response.getHeaders().get(HttpHeaders.SET_COOKIE);
            if (cookieHeaders != null) {
                for (String cookieHeader : cookieHeaders) {
                    if (cookieHeader.startsWith("refresh_token=")) {
                        // Update the value of the refresh token cookie
                        response.getHeaders().set(HttpHeaders.SET_COOKIE, "refresh_token=" + newRefreshToken + "; Path=/; HttpOnly; SameSite=Strict");
                        break;
                    }
                }
            } else {
                // Create a new refresh token cookie
                response.getHeaders().add(HttpHeaders.SET_COOKIE, "refresh_token=" + newRefreshToken + "; Path=/; HttpOnly; SameSite=Strict");
            }

            // Update the token in the security context
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, AuthorityUtils.createAuthorityList("ROLE_USER")));

            // Make the original request again with the new access token
            return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), responseType);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                // The refresh token is invalid or expired
                // Redirect the user to the login page or show an error message
            } else {
                throw e;
            }
        }
    }

    return response;
}