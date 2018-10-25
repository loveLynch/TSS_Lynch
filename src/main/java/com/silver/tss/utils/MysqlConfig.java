package com.silver.tss.utils;

import org.hibernate.dialect.MySQL5InnoDBDialect;

/**
 * Created by lynch on 2018/10/22. <br>
 **/

public class MysqlConfig extends MySQL5InnoDBDialect {
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
