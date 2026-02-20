package com.nursultan.security;

import com.nursultan.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtCore jwtCore; // Наш печатный станок

    @Autowired
    private CustomUserDetailsService userDetailsService; // Наш искатель юзеров в БД

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Достаем заголовок Authorization из запроса
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userName;

        // 2. Проверяем: если заголовка нет или он не начинается с "Bearer " - пропускаем запрос дальше
        // (возможно, это запрос на логин или регистрацию, там токена еще нет)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Отрезаем первые 7 символов ("Bearer "), чтобы получить чистый токен
        jwt = authHeader.substring(7);

        // 4. Достаем имя пользователя из токена через наш JwtCore
        userName = jwtCore.extractUsername(jwt);

        // 5. Если имя есть, а в текущем контексте Security (в памяти) юзер еще не авторизован:
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Идем в базу и достаем все данные юзера (включая его роль)
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);

            // Спрашиваем JwtCore: "Токен нормальный? Не просрочен?"
            if (jwtCore.isTokenValid(jwt, userDetails)) {

                // Создаем официальный "пропуск" для Спринга
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities() // передаем роли юзера
                );

                // Прикрепляем детали запроса (IP адрес и т.д.)
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Кладем пропуск в SecurityContext (теперь Спринг знает, что юзер авторизован!)
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 6. Передаем запрос дальше по цепочке к следующему фильтру или контроллеру
        filterChain.doFilter(request, response);
    }
}