package com.sunyard.frameworkset;

import com.dexcoder.dgen.main.DgenStarter;

/**
 * Write class comments here
 * User: liulu
 * Date: 2017/4/19 11:26
 * version $Id : AutoCode.java, v 0.1 Exp $
 */

public class AutoCode {

    public static void main(String[] args) {
        DgenStarter dgenStarter = new DgenStarter("/tsp-quickrun/src/test/resources/dgen/dgen.xml");
        dgenStarter.run ();
    }


}
