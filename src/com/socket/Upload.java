/*
 * Copyright (C) 2015 HERU
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.socket;

import com.ui.ChatFrame;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Upload implements Runnable{

    public String addr;
    public int port;
    public Socket socket;
    public FileInputStream In;
    public OutputStream Out;
    public File file;
    public ChatFrame ui;
    
    public Upload(String addr, int port, File filepath, ChatFrame frame){
        super();
        try {
            file = filepath; ui = frame;
            socket = new Socket(InetAddress.getByName(addr), port);
            Out = socket.getOutputStream();
            In = new FileInputStream(filepath);
        } 
        catch (Exception ex) {
            System.out.println("Exception [Upload : Upload(...)]");
        }
    }
    
    @Override
    public void run() {
        try {       
            byte[] buffer = new byte[1024];
            int count;
            int prog;
            
            ui.jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ui/GlogLoading.gif")));
            while((count = In.read(buffer)) >= 0){
                Out.write(buffer, 0, count);                
            }
            Out.flush();
            ui.jLabel6.setIcon(null);
            ui.jTextArea1.append("[Applcation > Me] : File upload complete\n");
            ui.jButton5.setEnabled(true); ui.jButton6.setEnabled(true);
            ui.jTextField5.setVisible(true);
            
            if(In != null){ In.close(); }
            if(Out != null){ Out.close(); }
            if(socket != null){ socket.close(); }
        }
        catch (Exception ex) {
            System.out.println("Exception [Upload : run()]");
            ex.printStackTrace();
        }
    }

}