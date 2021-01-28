package co.antiqu.afkroom.util;

import lombok.Getter;

public enum Perm {

    ADMIN("afkroom.admin");

    @Getter
    private String perm;

    Perm(String perm) {
        this.perm = perm;
    }

}
