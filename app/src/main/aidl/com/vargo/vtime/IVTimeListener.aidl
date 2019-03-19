// IVTimeListener.aidl
package com.vargo.vtime;

// Declare any non-default types here with import statements

interface IVTimeListener {
    void onReceive(in long code,in String data);
}
