package com.ustsinau;


import com.ustsinau.chapter13.services.CacheService;
import com.ustsinau.chapter13.services.impl.CacheServiceImpl;
import com.ustsinau.chapter13.view.HeadConsole;

import java.io.IOException;


/**
 * Hello world!
 */
public class FirstApp {
    public static void main(String[] args) throws IOException {


        CacheService cacheService = new CacheServiceImpl();
        cacheService.initCache();

        HeadConsole headConsole = new HeadConsole();
        headConsole.run();
    }
}
