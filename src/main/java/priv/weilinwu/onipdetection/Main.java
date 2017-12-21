package priv.weilinwu.onipdetection;

import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;

public class Main {
	public static void main(String[] args) throws InterruptedException, FileNotFoundException, JavaLayerException {
		new PnpNoticeUpdateDetection().start();
		new PnpAcceptionChangeDetection().start();
	}
}
