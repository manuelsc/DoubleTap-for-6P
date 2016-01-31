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

package at.caspari.doubletap6p;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import at.caspari.doubletap6p.interfaces.AsyncResponse;
import at.caspari.doubletap6p.utils.DoubleTabManager;

public class BootReceiver extends BroadcastReceiver implements AsyncResponse{

	@Override
	public void onReceive(Context context, Intent intent) {
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
		if (sharedPrefs.getBoolean("enabled", false)) {
            for(int i=0; i < 2; i++)
                new DoubleTabManager().execute(this, true);
        }
	}

    @Override
    public void processFinish(boolean enable, Throwable exception) {
        if(exception != null){
            Log.d("at.caspari.doubletap6p", "dt2w failed");
            exception.printStackTrace();
        } else {
            Log.d("at.caspari.doubletap6p", "dt2w activated");
        }
    }
}