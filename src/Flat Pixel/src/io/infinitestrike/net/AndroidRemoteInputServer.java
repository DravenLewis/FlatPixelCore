package io.infinitestrike.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import io.infinitestrike.core.LogBot;
import io.infinitestrike.core.LogBot.Status;

public class AndroidRemoteInputServer extends Thread{
	
	public byte[] onConnectData = new byte[2048];
	
	public static enum opCode {
		
		CLOA(0xA1), DCON(0xA2), CHNG(0xA3);
		
		private int numVal = 0;
		
		opCode(int val){
			this.numVal = val;
		}
		
		public int getValue(){
			return this.numVal;
		}
	}
	
	
	private HashMap<String,Action> actions = new HashMap<String,Action>();
	private ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	ServerSocket socket = null;
	
	
	public AndroidRemoteInputServer(int port){
		try{
		socket = new ServerSocket(port);
		
		InetAddress IP = InetAddress.getLocalHost();
		String ip = IP.getHostAddress();
		serverLogPrint("[Input Server] Server Created! - IP: " + ip + ":" + port);
		}catch(Exception e){
			
			//TODO implement the GameFrameWork logger
			LogBot.logDataVerbose(e, Status.ERROR, e.getLocalizedMessage());
		}
	}
	
	
	public void run(){
		if(socket != null){
			while(!socket.isClosed()){
				try{
					serverLogPrint("[Input Server] Waiting for Connection on Port: " + socket.getLocalPort());
					ClientThread thread = new ClientThread(socket.accept());
					serverLogPrint("[Input Server] Connected to: " + thread.connection.getInetAddress() + ":" + socket.getLocalPort());
					thread.setServer(this);
					this.clients.add(thread);
					thread.start();
					//thread.writeOut(AndroidRemoteInputServer.buildString(onConnectData));
				}catch(Exception e){
					LogBot.logDataVerbose(e, Status.ERROR, e.getLocalizedMessage());
				}
			}
		}
	}
	
	public void setServerConnectData(byte[] b){
		this.onConnectData = b;
	}
	
	public void onRecieve(ClientThread sender, Object o){
		if(this.actions.containsKey(o)){
			this.actions.get(o).onActionPerformed();
		}
	}
	
	public void broadcast(String s){
		for(ClientThread t : clients){
			t.writeOut(s);
		}
	}
	
	public void brodcast(int index, String s){
		if(index <= clients.size()){
			clients.get(index).writeOut(s);
		}
	}
	
	public void bindAction(String s, Action a){
		actions.put(s, a);
	}
	
	public void unBindAction(String s){
		actions.remove(s);
	}
	
	public static void serverLogPrint(String message){
		LogBot.logData(LogBot.Status.INFO, message);
	}

	public static String buildString(byte[] bytes){
		String dat = "";
		for(int i = 0; i < bytes.length; i++){
			dat += bytes[i];
		}
		return dat;
	}
	
	public static class ClientThread extends Thread{
		private AndroidRemoteInputServer server = null;
		Socket connection = null;
		DataInputStream in = null;
		DataOutputStream out = null;
		
		public ClientThread(Socket s){
			this.connection = s;
			try{
				in = new DataInputStream(connection.getInputStream());
				out = new DataOutputStream(connection.getOutputStream());
			}catch(Exception e){
				serverLogPrint("[Input Server] Error: " + e.getMessage());
			}
		}
		
		protected AndroidRemoteInputServer getServer(){
			return server;
		}
		
		protected void setServer(AndroidRemoteInputServer server){
			this.server = server;
		}
		
		public void run(){
			while(connection.isConnected()){
				try{
					String s = in.readUTF();
					if(s != null && !s.equals("")){
						this.onRecieve(s);
					}
				}catch(Exception e){
					serverLogPrint("[Input Server] Error: " +  e.getMessage());
					this.exit();
				}
			}
		}
		
		public void onRecieve(Object s){
			server.onRecieve(this, s);
		}
		
		public void writeOut(String s){
			try{
				out.writeUTF(s);
			}catch(Exception e){
				serverLogPrint("[Input Server] Error: " + e.getMessage());
			}
		}
		
		public void exit(){
			try{
				serverLogPrint("[Input Server] Closing Connection for Client: " + connection.getInetAddress() + ":" + connection.getPort());
				server.clients.remove(this);
				connection.close();
				this.join();
			}catch(Exception e){
				serverLogPrint("[Input Server] Error: " +  e.getMessage());
			}
		}
	}

	public String getServerIPAddress(){
		if(this.socket != null){
			String ip = "";
			try {
				InetAddress IP= InetAddress.getLocalHost();
				ip = IP.getHostAddress() + ":" + this.socket.getLocalPort();
				return ip;
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "0.0.0.0:0000";
	}
	
	public static interface Action{
		public void onActionPerformed();
	}
}
