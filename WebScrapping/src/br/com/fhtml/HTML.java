package br.com.fhtml;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("html")
public class HTML implements Serializable{

	private String head;
	private String body;


	//Constructor
	public HTML(String head, String body) {
		setHead(head);
		setBody(body);
	}

	//Getters & Setters
	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}



}
