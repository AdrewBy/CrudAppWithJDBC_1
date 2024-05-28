package com.ustsinau.chapter1_3;


import com.ustsinau.chapter1_3.repository.GeneratorId;
import com.ustsinau.chapter1_3.repository.impl.GeneratorIdImpl;
import com.ustsinau.chapter1_3.view.HeadConsole;

import java.io.IOException;


public class Main {
    public static void main(String[] args) {


        GeneratorId cacheService = new GeneratorIdImpl();
        cacheService.initCache();

        HeadConsole headConsole = new HeadConsole();
        try {
            headConsole.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
