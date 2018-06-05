package victor.keys.logger;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

public class PopClickListener extends MouseAdapter {
	private final JPopupMenu menu;
	
    public PopClickListener(JPopupMenu menu) {
		this.menu = menu;
	}

	public void mousePressed(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    public void mouseReleased(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    private void doPop(MouseEvent e){
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
}
