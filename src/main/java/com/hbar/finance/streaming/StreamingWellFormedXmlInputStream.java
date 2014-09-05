package com.hbar.finance.streaming;

import java.io.IOException;
import java.io.InputStream;
/**
 * Class used to add a root element to a stream of xml elements that has no root element & no breaking
 * root element, and is supposed to continue streaming indefinitely. XML is considered to be well
 * formed if it has a starting and closing root element. Useful for SAX parser that 
 * expects a root element, and will error out if it finds more data after a complete well formed XML block.
 * 
 * @author Gerardo Alvarado
 *
 */
public class StreamingWellFormedXmlInputStream extends InputStream{
	private InputStream inputStream;
	private int[] startElement = new int[]{'<','s','t','a','r','t','>'};
	private int[] endElement = new int[]{'<','/','s','t','a','r','t','>'};
	private boolean isStarting=true;
	private boolean isEnding=false;
	
	private short startOrEndIndex=0;
	
	public StreamingWellFormedXmlInputStream( InputStream inputStream ){		
		this.inputStream=inputStream;
	}
	
	@Override
	public int read() throws IOException {
		if(isStarting){
			if(startOrEndIndex<startElement.length){
				int retC=startElement[startOrEndIndex];
				startOrEndIndex++;
				return retC;
			}else{
				startOrEndIndex=0;
				isStarting=false;
			}
			
		}else if(isEnding){
			if(startOrEndIndex<endElement.length){
				int retC=endElement[startOrEndIndex];
				startOrEndIndex++;
				return retC;
			}else{
				return -1;
			}
		}
		int c=inputStream.read();
		if(c==-1){
			isEnding=true;
			int retC=endElement[startOrEndIndex];
			startOrEndIndex++;
			return retC;
		}
		return c;
	}

}
