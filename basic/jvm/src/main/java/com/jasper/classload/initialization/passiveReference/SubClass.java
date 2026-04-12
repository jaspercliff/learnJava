package com.jasper.classload.initialization.passiveReference;

    class SubClass extends SuperClass {
        static { System.out.println("SubClass init!"); }
    }

