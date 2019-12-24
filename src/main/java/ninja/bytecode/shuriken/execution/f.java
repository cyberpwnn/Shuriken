package ninja.bytecode.shuriken.execution;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class f {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			JFrame w = new JFrame();
			
			w.setSize(500, 700);
			
			w.show();
		});
	}

}
