package victor.keys.typer;

import static java.lang.Thread.sleep;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class RobotTyper {

    private static Map<Character,KeyStroke> strokeMap = new HashMap<>();
    private static Robot robot;
    
    static {
        try {
			robot = new Robot();
			robot.setAutoDelay(10);
		    robot.setAutoWaitForIdle(true);
			buildStrokeMap();
        } catch (AWTException e) {
			throw new RuntimeException(e);
		}
    }
    
    public static void main(String[] args) {
		System.out.println(((int)'\r'));
	}
	private static void buildStrokeMap() {
		//initialize a map from the input char to the keystroke,
        //using the fact that sometimes the KeyEvent key codes correspond to ASCII
		strokeMap.put('\n',new KeyStroke(KeyEvent.VK_ENTER,false));
		strokeMap.put('\t',new KeyStroke(KeyEvent.VK_TAB,false));
		strokeMap.put('\r',new KeyStroke(KeyEvent.VK_HOME,false));
		strokeMap.put(' ',new KeyStroke(KeyEvent.VK_SPACE,false));
		strokeMap.put('!',new KeyStroke(KeyEvent.VK_1,true));
		strokeMap.put('"',new KeyStroke(KeyEvent.VK_QUOTE,true));
		strokeMap.put('#',new KeyStroke(KeyEvent.VK_3,true));
		strokeMap.put('$',new KeyStroke(KeyEvent.VK_4,true));
		strokeMap.put('%',new KeyStroke(KeyEvent.VK_5,true));
		strokeMap.put('&',new KeyStroke(KeyEvent.VK_7,true));
		strokeMap.put('\'',new KeyStroke(KeyEvent.VK_QUOTE,false));
		strokeMap.put('(',new KeyStroke(KeyEvent.VK_9,true));
		strokeMap.put(')',new KeyStroke(KeyEvent.VK_0,true));
		strokeMap.put('*',new KeyStroke(KeyEvent.VK_8,true));
		strokeMap.put('+',new KeyStroke(KeyEvent.VK_EQUALS,true));
		strokeMap.put(',',new KeyStroke(KeyEvent.VK_COMMA,false));
		strokeMap.put('-',new KeyStroke(KeyEvent.VK_MINUS,false));
		strokeMap.put('.',new KeyStroke(KeyEvent.VK_PERIOD,false));
		strokeMap.put('/',new KeyStroke(KeyEvent.VK_SLASH,false));
		for(int i=(int)'0';i<=(int)'9';i++){
		    strokeMap.put((char)i,new KeyStroke(i,false));
		}
		strokeMap.put(':',new KeyStroke(KeyEvent.VK_SEMICOLON,true));
		strokeMap.put(';',new KeyStroke(KeyEvent.VK_SEMICOLON,false));
		strokeMap.put('<',new KeyStroke(KeyEvent.VK_COMMA,true));
		strokeMap.put('=',new KeyStroke(KeyEvent.VK_EQUALS,false));
		strokeMap.put('>',new KeyStroke(KeyEvent.VK_PERIOD,true));
		strokeMap.put('?',new KeyStroke(KeyEvent.VK_SLASH,true));
		strokeMap.put('@',new KeyStroke(KeyEvent.VK_2,true));
		for(int i=(int)'A';i<=(int)'Z';i++){
		    strokeMap.put((char)i,new KeyStroke(i,true));
		}
		strokeMap.put('[',new KeyStroke(KeyEvent.VK_OPEN_BRACKET,false));
		strokeMap.put('\\',new KeyStroke(KeyEvent.VK_BACK_SLASH,false));
		strokeMap.put(']',new KeyStroke(KeyEvent.VK_CLOSE_BRACKET,false));
		strokeMap.put('^',new KeyStroke(KeyEvent.VK_6,true));
		strokeMap.put('_',new KeyStroke(KeyEvent.VK_MINUS,true));
		strokeMap.put('`',new KeyStroke(KeyEvent.VK_BACK_QUOTE,false));
		for(int i=(int)'A';i<=(int)'Z';i++){
		    strokeMap.put((char)(i+((int)'a'-(int)'A')),new KeyStroke(i,false));
		}
		strokeMap.put('{',new KeyStroke(KeyEvent.VK_OPEN_BRACKET,true));
		strokeMap.put('|',new KeyStroke(KeyEvent.VK_BACK_SLASH,true));
		strokeMap.put('}',new KeyStroke(KeyEvent.VK_CLOSE_BRACKET,true));
		strokeMap.put('~',new KeyStroke(KeyEvent.VK_BACK_QUOTE,true));
	}
    public static void typeKey(char key){
        try{
            strokeMap.get(key).type();
//            sleep(5);
        }catch(NullPointerException ex){
            System.err.println("'"+key+"': no such key in mappings");
		}
    }
    private static class KeyStroke{
        int code;
        boolean isShifted;
        public KeyStroke(int keyCode,boolean shift){
            code=keyCode;
            isShifted=shift;
        }
        public void type(){
            try{
                if (isShifted) {
                    robot.keyPress(KeyEvent.VK_SHIFT);
                }
                robot.keyPress(code);
                if (isShifted) {
                	robot.keyRelease(code);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                }
//                if(code==KeyEvent.VK_ENTER){
//                    robot.keyPress(KeyEvent.VK_HOME);
//                    robot.keyRelease(KeyEvent.VK_HOME);
//                }

            }catch(IllegalArgumentException ex){
                String ch="";
                for(char key:strokeMap.keySet()){
                    if(strokeMap.get(key)==this){
                        ch=""+key;
                        break;
                    }
                }
                System.err.println("Key Code Not Recognized: '"+ch+"'->"+code);
            }
        }
    }
	public static void typeLine(String s) {
		try {
			System.out.println("Typing: " + s);
			for (char c : s.toCharArray()) {
				typeKey(c);
			}
			typeKey('\n');
			sleep(500);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}