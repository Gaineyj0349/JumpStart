package com.gainwise.jumpstartlib;

/*
   An instance of this class will help custom code to get executed upon an app crash.
   This will be done by intercepting the default UncaughtException Handler and
   building a custom one to take its place, execute the cstom code then rethrowing it back to the initial
   default handler

 */




public class CrashAllocator {

    //declare variables (the object implementing Crashable,
    //and the default uncaught exception handler
    Crashable crashable;
    Thread.UncaughtExceptionHandler defaultCrashHandler;


    //the constructor intercepts the default handler for uncaught exceptions, then
    // sets a custom one as the new designated uncaught exception handler,
    // once the custom code executes, it will rethrow the exception to the default handler
    //hence the defaultCrashHandler variable

    public CrashAllocator(){

        // uncaught exception handler variable initialized
        defaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();

        // setup handler for uncaught exception
        Thread.setDefaultUncaughtExceptionHandler(ourCrashHandler);
    }

    Thread.UncaughtExceptionHandler ourCrashHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable throwable) {

            //Code passed via the Crashable interface in the class constructor is executed
            crashable.executeOnCrash();

            // re-throw critical exception further to the os (important)
            defaultCrashHandler.uncaughtException(thread, throwable);
        }
    };


    //When constructing a CrashAllocator instance, pass an anonymous or named object implementing this
    //interface
    public interface Crashable {
        void executeOnCrash();
    }

    //this successfully executes code and rethrows the exception back to the default exception handler.


}
