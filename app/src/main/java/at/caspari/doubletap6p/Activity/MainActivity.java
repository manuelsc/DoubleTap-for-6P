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

package at.caspari.doubletap6p.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import at.caspari.doubletap6p.R;
import at.caspari.doubletap6p.utils.DoubleTabManager;
import at.caspari.doubletap6p.utils.SupportDetector;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView desc;
    private SupportDetector sup;
    private Button justDoIt;
    private LinearLayout lay;
    private SharedPreferences prefs;
    private DoubleTabManager mgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        desc = (TextView) findViewById(R.id.description);
        justDoIt = (Button) findViewById(R.id.button);
        lay = (LinearLayout) findViewById(R.id.main);
        sup = new SupportDetector();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mgr = new DoubleTabManager();

        init();
    }

    private void init(){
        if(sup.is6P()){
            revalidateButton();
            justDoIt.setOnClickListener(this);
        } else {
            desc.setText(this.getResources().getString(R.string.desc_not_6p));
            justDoIt.setVisibility(View.INVISIBLE);
            lay.setBackgroundColor(0xFFcc3737);
        }
    }

    @Override
    public void onClick(View v) {
        justDoIt.setEnabled(false);
        normalize();
        try {
            if(!prefs.getBoolean("enabled", false)) { // Enable
                mgr.enableDTW();
                prefs.edit().putBoolean("enabled", true).apply();
                Toast.makeText(MainActivity.this, "Enabled", Toast.LENGTH_SHORT).show();
            } else { // Disable
                mgr.disableDTW();
                prefs.edit().putBoolean("enabled", false).apply();
                Toast.makeText(MainActivity.this, "Disabled", Toast.LENGTH_SHORT).show();
            }
            revalidateButton();
        } catch (Exception e) {
            desc.setText(getResources().getString(R.string.desc_no_root));
            justDoIt.setText(getResources().getString(R.string.but_failed));
            justDoIt.setEnabled(true);
            lay.setBackgroundColor(0xFFcc3737);
        }
    }

    private void revalidateButton(){
        if(prefs.getBoolean("enabled", false)){
            justDoIt.setText(getResources().getString(R.string.but_dis));
            justDoIt.setBackgroundColor(getResources().getColor(R.color.colorAccent2));
        } else {
            justDoIt.setText(getResources().getString(R.string.but));
            justDoIt.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
        justDoIt.setEnabled(true);
    }

    private void normalize(){
        desc.setText(getResources().getString(R.string.desc_normal));
        justDoIt.setText(getResources().getString(R.string.but));
        justDoIt.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        lay.setBackgroundColor(0xFF175ea2);
        justDoIt.setEnabled(true);
    }
}
