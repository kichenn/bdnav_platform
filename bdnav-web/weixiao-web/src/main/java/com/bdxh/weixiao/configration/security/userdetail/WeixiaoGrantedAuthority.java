package com.bdxh.weixiao.configration.security.userdetail;

import com.bdxh.weixiao.configration.security.entity.WeixiaoPermit;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

public class WeixiaoGrantedAuthority implements GrantedAuthority {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final String role;


    public WeixiaoGrantedAuthority(WeixiaoPermit weixiaoPermit) {
        Assert.hasText(weixiaoPermit.getRole(), "A granted authority textual representation is required");
        this.role = weixiaoPermit.getRole();
    }

    @Override
    public String getAuthority() {
        return role;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof WeixiaoGrantedAuthority) {
            return role.equals(((WeixiaoGrantedAuthority) obj).role);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return this.role.hashCode();
    }

    @Override
    public String toString() {
        return this.role;
    }
}
