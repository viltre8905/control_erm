package com.verynet.gcint.web.taglib;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.context.UserContext;

import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by day on 02/06/2017.
 */
public class NotRole extends TagSupport {
    private String role;

    public int doStartTag() {

        UserContext userContext = Context.getUserContext();
        boolean hasPrivilege = true;
        if (role.contains(",")) {
            String[] privs = role.split(",");
            for (String p : privs) {
                if (userContext.hasRole(p)) {
                    hasPrivilege = false;
                    break;
                }
            }
        } else {
            hasPrivilege = !userContext.hasRole(role);
        }
        if (hasPrivilege) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
