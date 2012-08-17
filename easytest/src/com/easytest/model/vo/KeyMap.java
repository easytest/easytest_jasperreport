package com.easytest.model.vo;

import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JRPrintText;

public class KeyMap {

	private String keyComponent;

	private String text;
	private int page;
	private int x;
	private int y;
	private JRPrintText element;
	private JRPrintPage printPage;


	public KeyMap(String keyComponent, int page, JRPrintText element, JRPrintPage printPage) {
		this(keyComponent, page, element, printPage,true);
	}
	

	public KeyMap(String keyComponent, int page, JRPrintText element, JRPrintPage printPage,boolean validXY) {
		if(keyComponent == null){
			//System.out.println("keyComponent: NULL");
		}
		
		this.keyComponent = keyComponent;
		this.page = page;
		if(validXY){
			this.x = element.getX();
			this.y = element.getY();
		}else{
			x = 0;
			y = 0;
		}
		this.element = element;
		this.text = element.getText();
		this.printPage = printPage;
                /*
                if(text != null && text.contains("Salário base")){
                    System.out.println(">>>>> '" + text + "' " + hashCode());
                }
                 * 
                 */


	}
	
	public JRPrintPage getPrintPage() {
		return printPage;
	}

	public void setPrintPage(JRPrintPage printPage) {
		this.printPage = printPage;
	}
	
	public String getText() {
		return text;
	}

	public JRPrintText getElement() {
		return element;
	}

	public String getKeyComponent() {
		return keyComponent;
	}

	public int getPage() {
		return page;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((keyComponent == null) ? 0 : keyComponent.hashCode());
		result = prime * result + page;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KeyMap other = (KeyMap) obj;
		if (keyComponent == null) {
			if (other.keyComponent != null)
				return false;
			
		} else if (!keyComponent.equals(other.keyComponent))
			return false;
		if (page != other.page)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
