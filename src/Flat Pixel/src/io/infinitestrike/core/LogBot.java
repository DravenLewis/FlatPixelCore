package io.infinitestrike.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogBot {

	private static String fileName = "logs/" + new Date().getTime() + ".log";
	private static BufferedWriter fileOut = null;
	private static boolean write = true;

	static {

		if (!new File("./logs").exists()) {
			new File("./logs").mkdir();
		}

		try {
			fileOut = new BufferedWriter(new PrintWriter(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(
					"An Internal Exception occured,\n" + "the logger cannot print data.\n" + "Showing Stack Trace:");
			e.printStackTrace();
		}
	}

	public enum Status {
		INFO("INFO"), WARNING("WARN"), ERROR("ERRO"), SEVERE("SEVE"), UNKNOWN("UNKN"), DEVELOPER("DEV"), DEBUG("DEBU");

		private String value = "";

		private Status(String val) {
			this.value = val;
		}

		private Status() {

		}

		public String getValue() {
			return this.value;
		}
	}

	public static void logData(Object sender, String msg, Level level) {
		if (msg.trim().length() != 0) {
			Logger.getLogger(sender.toString()).log(level, msg);
		}
	}

	public static void logDataVerbose(Exception e, Status tag, String msg) {
		if (msg.trim().length() != 0) {
			System.out.println(String.format("[%s] [%s] %s [Exception: %s because of %s]", new Date(), tag.getValue(),
					msg, e.getClass(), e.getStackTrace()[0]));
			if (LogBot.write == true) {
				writeOut(String.format("[%s] [%s] %s [Exception: %s because of %s]", new Date(), tag.getValue(), msg,
						e.getClass(), e.getStackTrace()[0]));
			}
		}
	}

	public static void logData(Class<?> c, Status tag, String msg) {
		if (msg.trim().length() != 0) {
			logData(tag, "[" + c.getSimpleName() + "] " + msg);
		}
	}

	public static void logData(Status tag, String msg) {
		if (msg.trim().length() != 0) {
			System.out.println(String.format("[%s] [%s] %s", new Date(), tag.getValue(), msg));
			if(LogBot.write == true) {
				writeOut(String.format("[%s] [%s] %s", new Date(), tag.getValue(), msg));
			}
		}
	}

	public static void stopLog() {
		LogBot.write = false;
	}

	public static void startLog() {
		LogBot.write = true;
	}

	private static void writeOut(String s) {
		try {
			LogBot.fileOut.write(s);
			LogBot.fileOut.newLine();
			LogBot.fileOut.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(
					"An Internal Exception occured,\n" + "the logger cannot print data.\n" + "Showing Stack Trace:");
			e.printStackTrace();
		}
	}

	public static class LogBotStream extends PrintStream {
		public LogBotStream(OutputStream out) {
			super(out);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void print(int o) {
			super.print(o);
			/* super.flush(); */}

		@Override
		public void print(char o) {
			super.print(o);
			/* super.flush(); */}

		@Override
		public void print(char[] o) {
			super.print(o);
			/* super.flush(); */}

		@Override
		public void print(long o) {
			super.print(o);
			/* super.flush(); */}

		@Override
		public void print(Object o) {
			super.print(o);
			/* super.flush(); */}

		@Override
		public void print(String o) {
			super.print(o);
			/* super.flush(); */}

		@Override
		public void print(boolean o) {
			super.print(o);
			/* super.flush(); */}

		@Override
		public void print(double o) {
			super.print(o);
			/* super.flush(); */}

		@Override
		public void print(float o) {
			super.print(o);
			/* super.flush(); */}

		@Override
		public void println(int o) {
			super.println(o);
			super.flush();
		}

		@Override
		public void println(char o) {
			super.println(o);
			super.flush();
		}

		@Override
		public void println(char[] o) {
			super.println(o);
			super.flush();
		}

		@Override
		public void println(long o) {
			super.println(o);
			super.flush();
		}

		@Override
		public void println(Object o) {
			super.println(o);
			super.flush();
		}

		@Override
		public void println(String o) {
			super.println(o);
			super.flush();
		}

		@Override
		public void println(boolean o) {
			super.println(o);
			super.flush();
		}

		@Override
		public void println(double o) {
			super.println(o);
			super.flush();
		}

		@Override
		public void println(float o) {
			super.println(o);
			super.flush();
		}

		@Override
		public void println() {
			super.println();
			super.flush();
		}

	}

	public static class LogBotOutputStream extends OutputStream {

		String buffer = "";

		@Override
		public void write(int b) throws IOException {
			// TODO Auto-generated method stub
			buffer += (char) b;
		}

		@Override
		public void write(byte[] b) throws IOException {
			// TODO Auto-generated method stub
			for (int i = 0; i < b.length; i++) {
				this.buffer += (char) b[i];
			}
		}

		@Override
		public void write(byte[] b, int off, int len) throws IOException {
			// TODO Auto-generated method stub
			for (int i = off; i < len; i++) {
				this.buffer += (char) b[i];
			}
		}

		@Override
		public void flush() throws IOException {
			LogBot.logData(this.getClass(), Status.UNKNOWN, buffer);
			buffer = "";
		}
	}

}
