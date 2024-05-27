package com.ustsinau.chapter1_3;


import com.ustsinau.chapter1_3.services.CacheService;
import com.ustsinau.chapter1_3.services.impl.CacheServiceImpl;
import com.ustsinau.chapter1_3.view.HeadConsole;

import java.io.IOException;


public class Main {
    public static void main(String[] args) {


        CacheService cacheService = new CacheServiceImpl();
        cacheService.initCache();

        HeadConsole headConsole = new HeadConsole();
        try {
            headConsole.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
