package org.springframework.social.wechat.api.impl;

import com.tianma315.core.sys.domain.RoleDO;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WeChatImplTest {

    @Test
    public void as() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        List<RoleDO> roles = new ArrayList<RoleDO>() {{
            add(new RoleDO() {{
                setRoleName("1");
            }});
            add(new RoleDO() {{
                setRoleName("2");
            }});
            add(new RoleDO() {{
                setRoleName("3");
            }});
            add(new RoleDO() {{
                setRoleName("4");
            }});
        }};
        if (roles != null) {
            for (RoleDO role : roles) {
                authorities.add(role::getRoleName);
            }
        }

        for (GrantedAuthority authority: authorities){
            System.out.println(authority.getAuthority());
        }


    }

}