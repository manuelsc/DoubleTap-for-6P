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

public class SupportDetector {

    public boolean is6P(){
        return android.os.Build.DEVICE.equals("angler");
    }
}
