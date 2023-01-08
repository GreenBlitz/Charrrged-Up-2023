package edu.greenblitz.pegasus.utils;

import edu.greenblitz.pegasus.subsystems.GBSubsystem;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.SerialPort;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;

import static edu.wpi.first.wpilibj.smartdashboard.SmartDashboard.putBoolean;
import static edu.wpi.first.wpilibj.smartdashboard.SmartDashboard.putString;

public class UARTCommunication extends GBSubsystem {

	private static final int BAUD_RATE = 115200;
	private static final long BETWEEN_PINGS = 100;
	private static final int RESPONSE_WAIT_TIME = 5;
	private static final int DEFAULT_TIMEOUT = 20;
	private static final int PING_PAYLOAD = 5;
	private static UARTCommunication instance;
	private final SerialPort channel;
	private final Random rn = new Random();
	private final byte[] getReq;
	protected NetworkTable table;
	private boolean started;
	private long timeConnectionEst = 0;
	private boolean ping = false;
	private byte[] pingReq;
	private long lastPing = 0;

	private UARTCommunication() {
		super();

		started = false;

		NetworkTable table = NetworkTableInstance.getDefault().getTable("uart");
		channel = new SerialPort(BAUD_RATE, SerialPort.Port.kMXP);
		channel.disableTermination();
		channel.read(channel.getBytesReceived());

		byte[] toSend = new byte[]{(byte) REQUESTS.CONN_START.ordinal()};
		channel.write(toSend, toSend.length);

		getReq = new byte[1];
		getReq[0] = (byte) REQUESTS.GET.ordinal();
		initializeTable();
	}

	public static UARTCommunication getInstance() {
		if (instance == null) {
			instance = new UARTCommunication();
		}

		return instance;
	}

	public NetworkTableEntry getEntry(String key) {
		return table.getEntry(key);
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		return getEntry(key).getBoolean(defaultValue);
	}

	private void initializeTable() {
		String name = this.getClass().getSimpleName();
		table = NetworkTableInstance.getDefault().getTable(name);
		NetworkTableEntry entry = NetworkTableInstance.getDefault().getTable("general").getEntry("tables");
		String[] tablesString = entry.getStringArray(new String[0]);
		if (!Arrays.asList(tablesString).contains(name)) {
			tablesString = Arrays.copyOf(tablesString, tablesString.length + 1);
			tablesString[tablesString.length - 1] = name;
			entry.forceSetStringArray(tablesString);
		}
	}

	private byte[] getResponse(int size) {
		return getResponse(size, DEFAULT_TIMEOUT);
	}

	private byte[] getResponse(int size, long timeout) {
		long tStart = System.currentTimeMillis();
		while (channel.getBytesReceived() < size) {
			try {
				Thread.sleep(RESPONSE_WAIT_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (System.currentTimeMillis() - tStart > timeout) {
				return new byte[0];
			}
		}
		return channel.read(size);
	}

	private byte[] sendRequest(byte[] request) {
		channel.write(request, request.length);
		channel.flush();
		REQUESTS requestType = REQUESTS.values()[request[0]];
		return getResponse(requestType.responseSize);
	}

	public boolean ping() {
		return ping;
	}

	public boolean checkConnection() {
		byte[] toSend = new byte[PING_PAYLOAD + 1];
		rn.nextBytes(toSend);
		toSend[0] = (byte) REQUESTS.PING.ordinal();
		byte[] resp = sendRequest(toSend);
		if (resp.length != PING_PAYLOAD) {
			System.out.println("No ping");
			ping = false;
			started = false;
			return false;
		}

		ping = true;

		putString("Sent = ", Arrays.toString(toSend));
		putString("Got = ", Arrays.toString(resp));

		for (int i = 0; i < resp.length; i++) {
			if (resp[i] != toSend[i + 1]) {
				return false;
			}
		}
		return true;
	}

	public double detailedCheckConnection() {
		byte[] toSend = new byte[PING_PAYLOAD + 1];
		for (int i = 1; i < toSend.length; i++) {
			toSend[i] = (byte) ((Math.abs(rn.nextInt()) % 122) + 48);
		}
		toSend[0] = (byte) REQUESTS.PING.ordinal();
		byte[] resp = sendRequest(toSend);
		if (resp.length != PING_PAYLOAD) {
			ping = false;
			return 1;
		}

		ping = true;
		double mistakeCount = 0;

		for (int i = 0; i < resp.length; i++) {
			mistakeCount += diffCount(resp[i], toSend[i + 1], 8);
			System.out.println(diffCount(resp[i], toSend[i + 1], 8));
		}
		return mistakeCount / (PING_PAYLOAD * 8);
	}

	public boolean connectionActive() {
		return started && System.currentTimeMillis() - timeConnectionEst > 1000;
	}

	public int diffCount(int first, int second, int toCount) {
		if ((first == 0 && second == 0) || toCount == 0) return 0;
		return (Math.abs(first % 2) ^ Math.abs(second % 2))
				+ diffCount(first >> 2, second >> 2, toCount - 1);
	}

	public VisionLocation get() {
		if (!connectionActive()) {
			return null;
		}
		byte[] resp = sendRequest(getReq);
		if (resp.length == 0 || resp[0] == 0) {
			return null;
		}

		ByteBuffer buff = ByteBuffer.wrap(resp);
		buff.get();
		double x = buff.getDouble();
		double y = buff.getDouble();
		double z = buff.getDouble();
		// Different because ido dumb
		// z before y, not a mistake
		return new VisionLocation(x, z, y);
	}

	public boolean setAlgo(VisionMaster.Algorithm algo) {
		if (!connectionActive()) return false;
		byte[] algoReq = new byte[2];
		algoReq[0] = (byte) REQUESTS.SET_ALGO.ordinal();
		algoReq[1] = (byte) algo.ordinal();
		byte[] response = sendRequest(algoReq);
		return response.length > 0 && response[0] == 1;
	}

	@Override
	public void periodic() {
		putBoolean("Started comm", connectionActive());
		if (connectionActive()) {
			if (System.currentTimeMillis() - lastPing > BETWEEN_PINGS) {
				putBoolean("uart connection good", checkConnection());
				putBoolean("uart ping", ping());
				lastPing = System.currentTimeMillis();
			}
		} else if (!started && channel.getBytesReceived() > 0) {
			channel.read(channel.getBytesReceived());
			timeConnectionEst = System.currentTimeMillis();
			started = true;
		}
	}

	public enum REQUESTS {
		PING(PING_PAYLOAD),
		GET(1 + Double.BYTES * 3),
		SET_ALGO(1),
		CONN_START(1);

		public final int responseSize;

		REQUESTS(int rSize) {
			responseSize = rSize;
		}
	}
}
