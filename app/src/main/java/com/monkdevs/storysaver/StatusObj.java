package com.monkdevs.storysaver;

import java.io.File;

public class StatusObj {

    StatusObj (File status){
        this.status=status;
    }

    File status;
    boolean saved=false;
    String savedPath;
}
