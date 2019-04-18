package com.example.admin.testaitfriend.ait;

import java.io.Serializable;

/**
 * Description:
 * Data：2019/4/17-14:08
 * Author: ly
 */
public class AitMember implements Serializable {
    private String account ;
    private String name ;

    public AitMember(String account, String name) {
        this.account = account;
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }
}
