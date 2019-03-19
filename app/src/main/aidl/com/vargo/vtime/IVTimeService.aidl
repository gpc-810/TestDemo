// IVTimeService.aidl
package com.vargo.vtime;
import com.vargo.vtime.IVTimeListener;

interface IVTimeService {
   String handler(in String method, in String params, IVTimeListener listener);
}
