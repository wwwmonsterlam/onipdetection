package priv.weilinwu.onipdetection;

import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;

public class Main {
	public static void main(String[] args) throws InterruptedException, FileNotFoundException, JavaLayerException {
		//new StayInOneKeyAccount2().start();
		//new StayInOneKeyAccount1().start();
		new PnpNoticeUpdateDetection().start();
		//new PnpAcceptionChangeDetection().start();
	}
}
