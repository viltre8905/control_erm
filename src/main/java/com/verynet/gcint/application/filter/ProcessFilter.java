package com.verynet.gcint.application.filter;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.model.GeneralProcess;
import com.verynet.gcint.api.model.Process;
import com.verynet.gcint.api.model.User;
import com.verynet.gcint.api.util.enums.Roles;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by day on 05/10/2016.
 */
public class ProcessFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url = request.getRequestURL().toString();
        if (!url.contains("/resources/") && !url.contains("/error/process")
                && !url.contains("/login") && !url.contains("/logout")
                && !url.contains("/assets") && !url.contains(".ico")) {
            User user = Context.getAuthenticatedUser();
            HttpSession session = request.getSession();
            if (session.getAttribute("userValid") == null) {
                if (user != null && !user.hasRole(Roles.ROLE_ADMIN.toString()) && !user.hasRole(Roles.ROLE_SUPER_ADMIN.toString())) {
                    if (Context.getProcessService().getAllProcess().size() > 0) {
                        if (user.hasRole(Roles.ROLE_PROCESS_SUPERVISORY.toString()) || user.hasRole(Roles.ROLE_SUBPROCESS_SUPERVISORY.toString()) ||
                                user.hasRole(Roles.ROLE_EXECUTER.toString())) {
                            List<GeneralProcess> processes = new ArrayList<>();
                            processes.addAll(Context.getProcessService().getAllProcesses(user.getUserName(), true));
                            processes.addAll(Context.getProcessService().getAllSubProcesses(user.getUserName(), true));
                            processes.addAll(Context.getProcessService().getAllSubProcesses(user.getUserName(), false));
                            processes.addAll(Context.getActivityProcessService().getAllActivityProcess(user.getUserName()));
                            if (processes.size() == 0) {
                                response.sendRedirect(request.getContextPath() + "/error/process");
                                request.getSession().setAttribute("userValid", false);
                            } else {
                                request.getSession().setAttribute("userValid", true);
                            }
                        } else {
                            request.getSession().setAttribute("userValid", true);
                        }

                    } else {
                        response.sendRedirect(request.getContextPath() + "/error/process");
                        request.getSession().setAttribute("userValid", false);
                        return;
                    }
                }
            } else if (session.getAttribute("userValid") != null && !(Boolean) session.getAttribute("userValid")) {
                response.sendRedirect(request.getContextPath() + "/error/process");
                return;
            }
            filterChain.doFilter(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
