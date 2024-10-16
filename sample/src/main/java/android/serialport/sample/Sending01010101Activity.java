/*
 * Copyright 2011 Cedric Priscal
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package android.serialport.sample;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import android.os.Bundle;
import android.util.Log;

public class Sending01010101Activity extends SerialPortActivity {

    SendingThread mSendingThread;
    byte[] mBuffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sending01010101);
//        mBuffer = new byte[1024];
//        Arrays.fill(mBuffer, (byte) 0x55);
//        mBuffer = new byte[] {
//                (byte) 0x01, (byte) 0x05, (byte) 0x00, (byte) 0x02,
//                (byte) 0xFF, (byte) 0x00, (byte) 0x2D, (byte) 0xFA
//        };

        mBuffer = Hex.hexStringToByteArray("010200000001B9CA");
        if (mSerialPort != null) {
            mSendingThread = new SendingThread();
            mSendingThread.start();
        }
    }

    @Override
    protected void onDataReceived(byte[] buffer, int size) {
        Log.e("0101", "onDataReceived size = " + size + ", buffer = " + Hex.print(buffer, size) + ", time=" +System.currentTimeMillis());
    }

    private class SendingThread extends Thread {
        private boolean sendFinish = false;
        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    if (mOutputStream != null && !sendFinish) {
                        mOutputStream.write(mBuffer);
                        sendFinish = true;
                        Log.e("0101", "write to os mBuffer = " + Hex.print(mBuffer, 0) + ", time=" +System.currentTimeMillis());
                    } else {
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
}
