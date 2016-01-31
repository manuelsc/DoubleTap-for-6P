/*
 Copyright 2016 Manuel S. Caspari

 This file is part of DoubleTap 6P.

 DoubleTap 6P is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 DoubleTap 6P is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with DoubleTap 6P. If not, see <http://www.gnu.org/licenses/>.

*/

package at.caspari.doubletap6p.utils;

import android.os.AsyncTask;

import java.io.DataOutputStream;

import at.caspari.doubletap6p.interfaces.AsyncResponse;

public class DoubleTabManager extends AsyncTask<Object, Void, Boolean> {

    private Throwable exception;
    private AsyncResponse delegate;

    @Override
    protected Boolean doInBackground(Object... params) { //[0] = AsyncResponse, [1] = enable/disable dtw
        delegate = (AsyncResponse) params[0];
        boolean enable = (Boolean)params[1];
        try {
            dtw(enable);
        } catch (Throwable e) {
            exception = e;
        }
        return enable;
    }

    @Override
    protected void onPostExecute(Boolean enable) {
        delegate.processFinish(enable, exception);
    }

    private void dtw(boolean enable) throws Exception {
        runAsRoot(new String[]{"echo "+(enable ? "1" : "0")+" > /sys/devices/soc.0/f9924000.i2c/i2c-2/2-0070/input/input0/wake_gesture"});
    }

    private void runAsRoot(String[] cmds) throws Exception{
        if(!RootUtil.isDeviceRooted()) throw new Exception("No Root");
        Process p = Runtime.getRuntime().exec("su");
        DataOutputStream os = new DataOutputStream(p.getOutputStream());
        for (String tmpCmd : cmds) {
            os.writeBytes(tmpCmd + "\n");
        }
        os.writeBytes("exit\n");
        os.flush();
        p.waitFor();
    }
}
